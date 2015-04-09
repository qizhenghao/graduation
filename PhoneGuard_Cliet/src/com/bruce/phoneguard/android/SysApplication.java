package com.bruce.phoneguard.android;

import java.util.LinkedList;
import java.util.List;

import com.bruce.phoneguard.android.config.FescoConfig;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.model.UserInfo;
import com.bruce.phoneguard.android.utils.LockPatternUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author qizhenghao
 * date: 2014年12月27日16:30:27
 * @version 1.0
 */
public class SysApplication extends Application {

	public static String mPackageName;
	public static Context mContext;
	private static SysApplication mInstance;
	private static UserInfo user = null;
	private static SharedPreferences sp;

	private LockPatternUtils mLockPatternUtils;


	private List<Activity> mList = new LinkedList<Activity>();
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		mPackageName = mContext.getPackageName();
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
		InitData.getInstance().initDBFile(getAssets(), FescoConfig.FILES_PATH , FescoConfig.ANTIVIRUS_DB);
		InitData.getInstance().initDBFile(getAssets(), FescoConfig.FILES_PATH , FescoConfig.ADDRESS_DB);
	}

	public static SysApplication getInstance() {
		return mInstance;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public static UserInfo getUser() {
		if (user != null) {
			return user;
		} else {
			user = new UserInfo();
			user.setUserNo(110);
			user.setUserAge(24);
			user.setUserName("齐政浩");
			user.setUserPassword("123456");
			user.setUserSex(1);
			user.setUserTelNum("18215600693");
			user.setEmail("qizhenghao258@163.com");
			user.setUserHeadImg("");
			return user;
		}

	}
	
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

}
