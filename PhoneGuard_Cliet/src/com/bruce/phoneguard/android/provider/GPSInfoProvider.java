package com.bruce.phoneguard.android.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSInfoProvider {

	// 保证位置提供者只会注册一次监听器 只会初始化一次.

	private GPSInfoProvider() {
    } // 私有化构造方法

	private static GPSInfoProvider mGpsInfoProvider;
	private static LocationManager lm;
	private static SharedPreferences sp;
	private static MyListener listener;
	public static GPSInfoProvider getInstance(Context context) {
		if (mGpsInfoProvider == null) {
			mGpsInfoProvider = new GPSInfoProvider();
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_HIGH);
			criteria.setSpeedRequired(true);
			String provider = lm.getBestProvider(criteria, true);
			// 第一个参数 位置提供者 第二个参数 最短更新时间 第三参数 最短的更新的距离
			listener = mGpsInfoProvider.new MyListener();
			lm.requestLocationUpdates(provider, 0, 0, listener);
		}

		return mGpsInfoProvider;
	}

	private class MyListener implements LocationListener {

		/**
		 * 当位置改变的时候
		 */
		public void onLocationChanged(Location location) {
			float accuracy = location.getAccuracy();
			double wlong = location.getLatitude(); // 纬度
			double jlong = location.getLongitude(); // 经度
			//经度和纬度信息存起来.
			Editor editor = sp.edit();
			editor.putString("lastlocation", wlong+"-"+jlong+"-"+accuracy);
			editor.commit();
		}

		/**
		 * 某一个位置提供者的状态发生改变的时候调用的方法
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onProviderDisabled(String provider) {

		}

	}

	/**
	 * 返回最后一次手机的位置.
	 * @return
	 */
	public String getPhoneLocation() {
		return	sp.getString("lastlocation", "");
	}
}
