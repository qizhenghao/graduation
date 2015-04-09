package com.ultrawise.softwareproduct.idevplatform.utils;

import android.app.Application;

public class MSysApplication extends Application {
	private static String packageName;

	@Override
	public void onCreate() {
		super.onCreate();

		packageName = getApplicationContext().getPackageName();
	}

	public static String getMPackageName() {
		return packageName;
	}

}
