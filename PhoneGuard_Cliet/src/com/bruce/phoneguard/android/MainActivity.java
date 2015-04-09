package com.bruce.phoneguard.android;

import java.util.HashMap;
import java.util.Map;

import com.bruce.phoneguard.android.utils.DialogHelper;
import com.ultrawise.softwareproduct.idevplatform.entity.ResultItem;
import com.ultrawise.softwareproduct.idevplatform.request.BaseCallBack;
import com.ultrawise.softwareproduct.idevplatform.request.HttpRequest;
import com.ultrawise.softwareproduct.idevplatform.request.HttpRequestParams;
import com.ultrawise.softwareproduct.idevplatform.request.RequestHelper;
import com.ultrawise.softwareproduct.idevplatform.request.Response;
import com.ultrawise.softwareproduct.idevplatform.type.ContentType;
import com.ultrawise.softwareproduct.idevplatform.type.HttpMethod;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findId();
        
    }
	
    private void findId() {
    	textView = (TextView) findViewById(R.id.login_txt);
	}

	BaseCallBack<ResultItem> callBack = new BaseCallBack<ResultItem>() {
		
    	public void onError(Response<ResultItem> response) {
    		Toast.makeText(MainActivity.this, "error"+response.getData().toString(), Toast.LENGTH_SHORT).show();
    		textView.setText( "error"+response.getData().toString());
    	};
    	
    	public void onSuccess(Response<ResultItem> response) {
    		Toast.makeText(MainActivity.this, "success"+response.getData().toString(), Toast.LENGTH_SHORT).show();
    		textView.setText( "success"+response.getData().toString());
    	};
    	
    	public void onFinished(Response<ResultItem> response) {
    		Toast.makeText(MainActivity.this, "finished"+response.getData().toString(), Toast.LENGTH_SHORT).show();
    		textView.setText( "finished"+response.getData().toString());
    	};
	};
	@Override
	public void onClick(View v) {
		
		HttpRequestParams params = getModifyPsdRequestParams(0, " ");
		HttpRequest<ResultItem> request = new HttpRequest<ResultItem>(MainActivity.this, params, DialogHelper.notStemTouchCancel());
		RequestHelper.addRequest(0, request, callBack);
	}
    
	/**
	 * @author qizhenghao
	 * @return 返回修改密码请求参数
	 * @param 用户Id
	 */
	private HttpRequestParams getModifyPsdRequestParams(int id, String psd) {
		HttpRequestParams httpRequestParams = new HttpRequestParams();
		httpRequestParams.setMethod(HttpMethod.GET);
		httpRequestParams.setUrl("http://192.168.1.122:8080/PhoneGuard/LoginServlet");
		httpRequestParams.setContentType(ContentType.MULTI_FORM);
		httpRequestParams.setMockDatas(false);
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("staffNo", id);
		httpRequestParams.setParams(content);
		return httpRequestParams;
	}

}
