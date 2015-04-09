package com.bruce.phoneguard.android.receivers;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class KillAllRecevier extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningAppProcessInfo info: am.getRunningAppProcesses()){
			am.killBackgroundProcesses(info.processName);
		}
		Toast.makeText(context, "清理完毕i", 0).show();
	}

}
