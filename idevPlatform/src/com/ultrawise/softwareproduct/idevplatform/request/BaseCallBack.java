package com.ultrawise.softwareproduct.idevplatform.request;

import android.app.Dialog;

public abstract class BaseCallBack<T> implements IRequestCallBack<T> {

    @Override
    public void onSuccess(Response<T> response) {

    }
    @Override
    public void onError(Response<T> response) {

    }
    @Override
    public void onFinished(Response<T> response) {

    }

    @Override
    public void onSuccess(Response<T> response, Dialog dialog) {

    }

    @Override
    public void onError(Response<T> response, Dialog dialog) {

    }

    @Override
    public void onFinished(Response<T> response, Dialog dialog) {

    }
}
