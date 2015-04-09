package com.ultrawise.softwareproduct.idevplatform.request;

import android.util.Log;
import com.ultrawise.softwareproduct.idevplatform.common.config.AppConfig;
import com.ultrawise.softwareproduct.idevplatform.entity.ResultItem;
import com.ultrawise.softwareproduct.idevplatform.type.ContentType;
import com.ultrawise.softwareproduct.idevplatform.utils.BeanUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求帮助类
 * 
 * @author
 */
public class HttpRequestHelper {

	/** 处理Json格式的数据返回 */
	public static ResultItem processJson(String context) {
		ResultItem item = new ResultItem();
		try {
			JSONObject jsonObj = null;
			if (context != null && context.trim().startsWith("[")) {
				jsonObj = new JSONObject();
				jsonObj.put("list", new JSONArray(context));
			} else {
				jsonObj = new JSONObject(context);
			}
			// 转换为统一的ResultItem
			item = BeanUtils.convertJSONObject(jsonObj);
			// 主要那个用于监听用户色session 或者 个别需要处理的错误，或者统一的错误
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/** 拼装 URL */
	public static HttpGet buildHttGet(HttpRequestParams httpRequestParams) {
		// 参数信息
		StringBuffer argsUrl = new StringBuffer();
		if (httpRequestParams.getParams() != null) {
			List<NameValuePair> params = new ArrayList<NameValuePair>(
					httpRequestParams.getParams().size());
			Map<String, Object> paramMap = httpRequestParams.getParams();
			for (String key : httpRequestParams.getParams().keySet()) {
				Object obj = paramMap.get(key);
				params.add(new BasicNameValuePair(key, obj == null ? null : obj
						.toString()));
				try {
					argsUrl.append((argsUrl.length() != 0 ? "&" : "")
							+ key
							+ "="
							+ URLEncoder.encode(
									obj == null ? "" : obj.toString(),
									HTTP.UTF_8));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 获取请求的地址并且拼加参数
		String url = httpRequestParams.getUrl();
		// 最终请求的URL
		url = BeanUtils.urlAppend(url, argsUrl.toString());

		HttpGet get = new HttpGet(url);
		if (AppConfig.showLog) {
			Log.i("get", url);
		}
		return get;
	}

	public static HttpPost buildHttPost(HttpRequestParams httpRequestParams)
			throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(httpRequestParams.getUrl());
        post.addHeader("charset", "utf-8");
        if (httpRequestParams.getParams() != null) {
            post.setEntity(httpRequestParams.getContentType() == ContentType.NORMAL ? getUrlEncodedFormEntity(httpRequestParams)
                    : getMultipartImgEntity(httpRequestParams));
		}
		if (AppConfig.showLog) {
			Log.i("post url", httpRequestParams.getUrl());
			try {
				Log.i("post param", EntityUtils.toString(post.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return post;
	}
	private static UrlEncodedFormEntity getUrlEncodedFormEntity(
			HttpRequestParams httpRequestParams)
			throws UnsupportedEncodingException {
		List<NameValuePair> params = new ArrayList<NameValuePair>(
				httpRequestParams.getParams().size());
		Map<String, Object> paramMap = httpRequestParams.getParams();
		for (String key : httpRequestParams.getParams().keySet()) {
			Object obj = paramMap.get(key);
			params.add(new BasicNameValuePair(key, obj == null ? null : obj
					.toString()));
		}
		return new UrlEncodedFormEntity(params, HTTP.UTF_8);

	}
    private static MultipartEntity getMultipartEntity(
            HttpRequestParams httpRequestParams)
            throws UnsupportedEncodingException {
        MultipartEntity reqEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        Map<String, Object> paramMap = httpRequestParams.getParams();
        for (String key : httpRequestParams.getParams().keySet()) {
            Object obj = paramMap.get(key);
            if (obj != null) {
                reqEntity.addPart(
                        key,
                        new StringBody(obj.toString(), Charset
                                .forName(HTTP.UTF_8)));
            }
        }
        return  reqEntity;

    }
    private static MultipartEntity getMultipartImgEntity(
            HttpRequestParams httpRequestParams)
            throws UnsupportedEncodingException {
        MultipartEntity reqEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        Map<String, Object> paramMap = httpRequestParams.getParams();
        for (String key : httpRequestParams.getParams().keySet()) {
            Object obj = paramMap.get(key);
            if (obj != null) {
              if (!key.equalsIgnoreCase("content")) {
                  reqEntity.addPart(
                          key,
                          new FileBody(new File(obj.toString())));
              }else{
                  reqEntity.addPart(
                          key,
                          new StringBody(new String(obj.toString()),Charset
                                  .forName("UTF-8")));
              }
            }
        }
        return reqEntity;

    }

	public static DefaultHttpClient buildHttpClient() {
		// 使用默认的HttpClient
		DefaultHttpClient client = new DefaultHttpClient();
		// 设置连接超时时间
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60);
		// 设置读取内容连接超时时间
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				1000 * 60);
		return client;
	}

}
