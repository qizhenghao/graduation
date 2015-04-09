package com.bruce.phoneguard.android.utils;

import com.ultrawise.softwareproduct.idevplatform.request.RequestDialog;

/**
 * Created by JNinBer on 2014/5/5 0005.
 */
public class DialogHelper {


public static  RequestDialog  systemCancel(){
        RequestDialog requestDialog=new RequestDialog(true ,true);
         requestDialog.setTouchCancel(true);
         requestDialog.setBackCancel(true);
        return requestDialog;
}
    public static  RequestDialog  notShow(){
        RequestDialog requestDialog=new RequestDialog(false ,true);
        requestDialog.setTouchCancel(true);
        requestDialog.setBackCancel(true);
        return requestDialog;
    }
    public  static  RequestDialog notStemCancel(){
        RequestDialog requestDialog=new RequestDialog(true ,false);
        requestDialog.setTouchCancel(true);
        requestDialog.setBackCancel(true);
        return requestDialog;
    }
    public  static  RequestDialog notStemTouchCancel(){
        RequestDialog requestDialog=new RequestDialog(true ,false);
        requestDialog.setTouchCancel(false);
        requestDialog.setBackCancel(true);
        return requestDialog;
    }
    public  static  RequestDialog notStemTouchBackCancel(){
        RequestDialog requestDialog=new RequestDialog(true ,false);
        requestDialog.setTouchCancel(false);
        requestDialog.setBackCancel(false);
        return requestDialog;
    }
    public  static  RequestDialog defaultInfo(){
        return new RequestDialog(true,true,true,true);
    }

}
