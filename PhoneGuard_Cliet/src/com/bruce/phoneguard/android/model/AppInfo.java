package com.bruce.phoneguard.android.model;

import android.graphics.drawable.Drawable;

/**
 * 应用程序信息的业务bean
 * 
 * @author qizhenghao
 * @version 1.2 增加属性uid，增加checked字段
 * @Time 2015年3月14日12:02:50
 */
public class AppInfo {

	private String apkpath;

	private int uid;
    private boolean checked = true;
	public String getApkpath() {
		return apkpath;
	}

	public void setApkpath(String apkpath) {
		this.apkpath = apkpath;
	}

	/**
	 * 应用程序的图标
	 */
	private Drawable icon;

	/**
	 * 应用程序名称
	 */
	private String name;

	/**
	 * 应用程序安装的位置，true手机内存 ，false外部存储卡
	 */
	private boolean inRom;

	/**
	 * 应用程序的大小
	 */
	private long appSize;

	/**
	 * 是否是用户程序 true 用户程序 false 系统程序
	 */
	private boolean userApp;

	/**
	 * 应用程序的包名
	 */
	private String packname;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInRom() {
		return inRom;
	}

	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public boolean isUserApp() {
		return userApp;
	}

	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", inRom=" + inRom + ", appSize="
				+ appSize + ", userApp=" + userApp + ", packname=" + packname
				+ "]";
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
