package com.bruce.phoneguard.android.receivers;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.model.MyAdmin;
import com.bruce.phoneguard.android.provider.GPSInfoProvider;


public class SmsReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsReceiver";

	@Override
    public void onReceive(Context context, Intent intent) {

		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody();
			String sender = smsMessage.getOriginatingAddress();
			if ("#*location*#".equals(body)) {
				abortBroadcast();// 终止短信的广播事件.
				String lastloaction = GPSInfoProvider.getInstance(context)
						.getPhoneLocation();
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(sender, null, "loaction:"
						+ lastloaction, null, null);
			} else if ("#*alarm*#".equals(body)) {
				abortBroadcast();// 终止短信的广播事件.
				MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1.0f, 1.0f);
				player.start();
			} else if ("#*wipedate*#".equals(body)) {
				abortBroadcast();// 终止短信的广播事件.
				// 判断 当前应用程序是否已经得到了超级管理员的权限
				DevicePolicyManager dpm = (DevicePolicyManager) context
						.getSystemService(Context.DEVICE_POLICY_SERVICE);
				if (dpm.isAdminActive(new ComponentName(context, MyAdmin.class)))
					dpm.wipeData(0);

			} else if ("#*lockscreen*#".equals(body)) {
				abortBroadcast();// 终止短信的广播事件.
				DevicePolicyManager dpm = (DevicePolicyManager) context
						.getSystemService(Context.DEVICE_POLICY_SERVICE);
				if (dpm.isAdminActive(new ComponentName(context, MyAdmin.class))) {
					dpm.resetPassword("321", 0);
					dpm.lockNow();
				}else{
					//todo:
				}
			}
		}

	}

}
