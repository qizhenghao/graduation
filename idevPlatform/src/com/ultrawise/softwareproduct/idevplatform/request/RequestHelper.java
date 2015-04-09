package com.ultrawise.softwareproduct.idevplatform.request;

/**
 * Created by matti on 2014/4/9.
 */
public class RequestHelper {

    public static void addRequest(int what ,BaseRequest<?> request,BaseCallBack callBack){
        request.setCallBack(callBack);  
        request.setWhat(what);
        request.processing();
    }


}
