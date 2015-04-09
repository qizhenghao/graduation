package com.bruce.phoneguard.android.services;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import com.bruce.phoneguard.android.activity.UnlockGesturePasswordActivity;
import com.bruce.phoneguard.android.config.FescoConfig;
import com.bruce.phoneguard.android.dao.AppLockDao;
import com.bruce.phoneguard.android.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class WatchDogService extends Service {
    protected static final String TAG = "WatchDogService";
	private AppLockDao dao;
	private List<String> lockapps;
	private ActivityManager am;
	private Intent lockappintent;
	private boolean flag;
	private MyBinder myBinder;
	private KeyguardManager keyguardManager;
	private List<String> tempstopapps;

	@Override
	public IBinder onBind(Intent intent) {

		return myBinder;
	}

	public class MyBinder extends Binder implements IService {

		public void callAppProtectStart(String packname) {

			appProtectStart(packname);
		}

		public void callAppProtectStop(String packname) {
			appProtectStop(packname);

		}

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

	/**
	 * 重新开启对应用的保护
	 * 
	 * @param packname
	 */
	public void appProtectStart(String packname) {
		if (tempstopapps.contains(packname)) {
			tempstopapps.remove(packname);
		}
	}

	/**
	 * 临时停止对某个app的保护
	 * 
	 * @param packname
	 */
	public void appProtectStop(String packname) {
		tempstopapps.add(packname);
	}

	/**
	 * 服务第一次创建的时候 调用的方法
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		getContentResolver().registerContentObserver(Uri.parse("content://com.bruce.applockprovider"), true, new MyObserver(new Handler()));
		keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

		
		myBinder = new MyBinder();
		dao = new AppLockDao(this);
		tempstopapps = new ArrayList<String>();
		flag = true;
		// 得到所有的要锁定的应用程序
		lockapps = dao.getAllApps();
		lockappintent = new Intent(this, UnlockGesturePasswordActivity.class);
		// 服务是不存在任务栈的 要在服务里面开启activity的话 必须添加这样一个flag
		lockappintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle bundle = new Bundle();
//        bundle.putString(FescoConfig.GESTURE_FLAG, FescoConfig.GESTURE_FLAG_SERVICE);
//        bundle.putParcelable("serviceBinder", myBinder);
		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		new Thread() {

			@Override
			public void run() {
				// 开启看门狗
				while (flag) {
					try {
						// 判断屏幕是否是锁屏状态
						if(keyguardManager.inKeyguardRestrictedInputMode()){
							//清空临时的集合
							tempstopapps.clear();
							
						}

                        String packname = SystemInfoUtils.getCurrentTask(WatchDogService.this);
						Log.i(TAG, "当前运行" + packname);
						if (lockapps.contains(packname)) {
							// todo : 如果当前的应用程序 需要临时的被终止保护
							if (tempstopapps.contains(packname)) {
								sleep(1000);
								continue;
							}
							Log.i(TAG, "需要锁定" + packname);
							// todo 弹出来一个锁定的界面 让用户输入密码
							lockappintent.putExtra("packname", packname);
							startActivity(lockappintent);

						} else {
							// todo 放行执行
						}
						sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
	}

	private class MyObserver extends ContentObserver{

		public MyObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
			
		}

		@Override
		public void onChange(boolean selfChange) {
			
			super.onChange(selfChange);
			//重新更新lockapps集合里面的内容
			Log.i("change","----------------------------------数据库内容变化了");
			lockapps = dao.getAllApps();
		}
		
	}
}
