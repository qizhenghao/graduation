package com.ultrawise.softwareproduct.idevplatform.type;

/**
 * 所有的请求类型 都要写注释
 * 
 * @author
 * 
 */
public enum HttpRequestType {

	LOGIN_SERER("/login.action"), // 登录
	FESCO_MODIFY_PSD_SERER("/modify_psd.action"), // fesco端修改密码
	FESCO_MODIFY_PROFILE_SERER("/modify_profile.action"); // fesco端修改资料
	private String urlPath;

	private HttpRequestType(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getUrlPath() {
		return urlPath;
	}
}
