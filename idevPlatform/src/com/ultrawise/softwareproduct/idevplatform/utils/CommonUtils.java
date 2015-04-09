package com.ultrawise.softwareproduct.idevplatform.utils;

import java.io.File;
import android.os.Environment;

public class CommonUtils {
	private static String getCachePath(String name) {
		String path = "";
		if (hasSdcard()) {
			path = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "Android/data/"
					+ MSysApplication.getMPackageName() + "/download/";
		} else {
			path = getDataPath();
		}
		BeanUtils.checkFileExist(path);
		path = path + File.separator + name + File.separator;
		BeanUtils.checkFileExist(path);
		return path;
	}
	private static String getDataPath() {
		String path = "";
		path = "/data/data/" + MSysApplication.getMPackageName() + "/config/";
		BeanUtils.checkFileExist(path);
		return path;
	}
	
	/** 图片缓存目录 */
	public static String getImageCachePath() {
		return getCachePath("cache");
	}
	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

}
