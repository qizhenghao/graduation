package com.bruce.phoneguard.android.services;

import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.receivers.DesktopWidget;
import com.bruce.phoneguard.android.utils.SystemInfoUtils;

public class UpdateWidgetService extends Service {
	protected static final String TAG = "UpdateWidgetService";
	private InnerScreenOffReceiver offReceiver;
	private InnerScreenOnReceiver onReceiver;
	private Timer timer;
	private TimerTask task;
	private AppWidgetManager awm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class InnerScreenOffReceiver extends BroadcastReceiver{
		private static final String TAG = "InnerScreenOffReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG,"屏幕被锁定了。。。");
			if(timer!=null&&task!=null){
				timer.cancel();
				task.cancel();
				timer = null;
				task = null;
			}
			
		}
	}
	private class InnerScreenOnReceiver extends BroadcastReceiver{
		private static final String TAG = "InnerScreenOffReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG,"屏幕解锁。。。");
			if(timer==null&&task ==null){
				startWidgetUpdate();
			}
			
		}
	}
	
	@Override
	public void onCreate() {
		offReceiver = new InnerScreenOffReceiver();
		onReceiver = new InnerScreenOnReceiver();
		
		IntentFilter offfilter = new IntentFilter();
		offfilter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(offReceiver, offfilter);
		
		IntentFilter onfilter = new IntentFilter();
		onfilter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(onReceiver, onfilter);
		
		//获取到widget的管理器
		awm = AppWidgetManager.getInstance(this);
		startWidgetUpdate();
		super.onCreate();
	}

	private void startWidgetUpdate() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				Log.i(TAG,"我开始费电了。。我要更新界面");
				//更新另外一个进程的操作。 ipc调用。 inter process communication 进程间通讯
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
				//指定要更新的是哪个widget
				ComponentName provider = new ComponentName(getApplicationContext(), DesktopWidget.class);
				views.setTextViewText(R.id.process_count, "正在运行进程数量："+SystemInfoUtils.getRunningProcessCount(getApplicationContext()));
				views.setTextViewText(R.id.process_memory, "可用内存："+Formatter.formatFileSize(getApplicationContext(), SystemInfoUtils.getAvailMem(getApplicationContext())));
				//另外一个应用程序执行的延期的意图
				//一个延期的意图  发送一个自定义的广播
				Intent i = new Intent();
				i.setAction("com.bruce.phoneguard.killall");
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				awm.updateAppWidget(provider, views);
			}
		};
		timer.schedule(task, 0, 5000);
	}
	
	@Override
	public void onDestroy() {
		if(timer!=null&&task!=null){
			timer.cancel();
			task.cancel();
			timer = null;
			task = null;
		}
		
		unregisterReceiver(offReceiver);
		unregisterReceiver(onReceiver);
		offReceiver = null;
		onReceiver = null;
		
		super.onDestroy();
	}
	
}
