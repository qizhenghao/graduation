package com.bruce.phoneguard.android.receivers;

import com.bruce.phoneguard.android.services.UpdateWidgetService;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * 特殊的广播接受者，屏幕内存清理小控件
 * 
 * @author qizhenghao 1.写一个类 继承AppWidgetProvider
 * 
 */
public class DesktopWidget extends AppWidgetProvider {
	@Override
	public void onEnabled(Context context) {
		// 开启服务 定期的更新widget
		Intent i = new Intent(context, UpdateWidgetService.class);
		context.startService(i);
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		// 停止服务 不再去更新widget
		Intent i = new Intent(context, UpdateWidgetService.class);
		context.stopService(i);
		super.onDisabled(context);
	}
}
