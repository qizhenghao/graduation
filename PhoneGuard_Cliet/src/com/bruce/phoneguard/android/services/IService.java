package com.bruce.phoneguard.android.services;

import android.os.Parcelable;

public interface IService extends Parcelable{
	public void callAppProtectStart(String packname);
	public void callAppProtectStop(String packname);
	
}
