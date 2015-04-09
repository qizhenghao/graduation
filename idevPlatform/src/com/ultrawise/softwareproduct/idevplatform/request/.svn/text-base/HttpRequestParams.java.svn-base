package com.ultrawise.softwareproduct.idevplatform.request;



import com.ultrawise.softwareproduct.idevplatform.type.ContentType;
import com.ultrawise.softwareproduct.idevplatform.type.HttpMethod;

import java.util.Map;

/**
 * 
 * @author matii
 * 请求的全部参数
 */
public class HttpRequestParams {
	
	private Map<String,Object> params;  //参数属性
	
	private String url;//链接地址

    public int getRequsetcode() {
        return requsetcode;
    }

    public void setRequsetcode(int requsetcode) {
        this.requsetcode = requsetcode;
    }

    private int requsetcode;
	
	private HttpMethod method = HttpMethod.GET; //请求模式 &&默认GET方式
	
	private ContentType contentType = ContentType.NORMAL;
	
	private boolean mockDatas;				//不网络请求取假数据

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public boolean isMockDatas() {
		return mockDatas;
	}

	public void setMockDatas(boolean mockDatas) {
		this.mockDatas = mockDatas;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	
}
