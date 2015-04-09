package com.ultrawise.softwareproduct.idevplatform.request;

import android.app.Dialog;

/**
 * Created by matti on 2014/4/9.
 */
public interface IRequestCallBack<T> {

    public void onSuccess(Response<T> response);//when scuess
    public void onError(Response<T> response);//when error
    public void onFinished(Response<T> response);//when finished ,always callBack this one
    public void onSuccess(Response<T> response,Dialog dialog);
    public void onError(Response<T> response,Dialog dialog);
    public void onFinished(Response<T> response,Dialog dialog);

}
