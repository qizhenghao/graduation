package com.ultrawise.softwareproduct.idevplatform.request;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.ultrawise.softwareproduct.idevplatform.common.config.AppConfig;
import com.ultrawise.softwareproduct.idevplatform.type.HttpMethod;

/**
 * Created by matti on 2014/4/9.
 */
public  class HttpRequest<ResultItem> extends BaseRequest {

    private  HttpRequestParams param;

//    public int getWhat() {
//        return what;
//    }
//
//    public void setWhat(int what) {
//        this.what = what;
//    }
//
//    private  int what;
    @Override
    public void parseData() {
    }
    @Override
   public Response<ResultItem> request(){

        Response<ResultItem> resopnse = new Response<ResultItem>();
        if(param.isMockDatas()){
            resopnse.setData(mockDatas(param));
            if(AppConfig.showLog){
            Map<String,Object> map =	param.getParams();
            	 for (Map.Entry<String, Object> entry : map.entrySet()) {
            		   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            		  }
            }
        }else {
            // 先封装一个 JSON 对象
            try {
                //使用默认的HttpClient
                DefaultHttpClient client = HttpRequestHelper.buildHttpClient();
                //根据提交的方式创建HttpRequestBase
                HttpRequestBase requestBase = param.getMethod() == HttpMethod.GET? HttpRequestHelper.buildHttGet(param) : HttpRequestHelper.buildHttPost(param);
                //执行HTTP请求
                HttpResponse httpResponse = client.execute(requestBase);
                if(httpResponse.getStatusLine().getStatusCode() == 200){
                    String reslutStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    if(AppConfig.showLog){
                        Log.i("reslut", reslutStr);
                    }
                    resopnse.setPrimitiveStr(reslutStr);
                    resopnse.setData((ResultItem) HttpRequestHelper.processJson(reslutStr));
                }else if(httpResponse.getStatusLine().getStatusCode() == 401){
                    if(AppConfig.showLog){
                        Log.i("authority", "401 权限错误");
                    }
                    resopnse.setError(new RequestError("neterror" ,httpResponse.getStatusLine().getStatusCode()+""));
//                    resopnse.setErrorCode(CommonError.AUTH_ERROR.name());
                }else{
                    if(AppConfig.showLog){
                        Log.i("net error", "StatusCode:"+httpResponse.getStatusLine().getStatusCode());
                    }
//                    resopnse.setErrorCode(CommonError.NETWORK_ERROR.name());
                    resopnse.setError(new RequestError("neterror" ,httpResponse.getStatusLine().getStatusCode()+""));
                }            } catch (Exception e) {
                resopnse.setError(new RequestError("neterror",e.getMessage()));
                e.printStackTrace();
            }
        }
        return resopnse;
     }
    public HttpRequest(Context context, HttpRequestParams param, RequestDialog requestDialog){
    super(context,requestDialog);
    this.param=param;
   }
    public HttpRequest(int what,Context context, HttpRequestParams param, RequestDialog requestDialog){
        super(context,requestDialog);
        this.param=param;
//        this.what=what;
        setWhat(what);
    }

    public ResultItem mockDatas(HttpRequestParams param) {
        return (ResultItem) MockDataSources.getDatas(param);
    }
}
