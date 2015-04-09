package com.bruce.phoneguard.android.model;

import java.io.Serializable;

import com.ultrawise.softwareproduct.idevplatform.entity.ResultItem;

/**
 * 
 * 用户信息表
 * 
 * @author qizhenghao
 * data: 2014年12月27日11:06:59
 * @version 1.0
 * 
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = -5839896993336592080L;
	
	private int userNo;
	private int userSex; // 0女1男
	private int userAge;
	private String userTelNum;
	private String email;
	private String userHeadImg; // 员工头像
	private String userName;
	private String userPassword;
	
	/**
	 * @author qizhenghao
	 * @param resultItem
	 *            用于构造人员列表中的人员信息
	 */
	public UserInfo(ResultItem item) {
		this.userNo = item.getIntValue("userNo");
		this.userName = item.getString("userName");
		this.userSex = item.getIntValue("userSex");
		this.userAge = item.getIntValue("userAge");
		this.userTelNum = item.getString("userTelNum");
		this.email = item.getString("email");
		this.userHeadImg = item.getString("userHeadImg");
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserTelNum() {
		return userTelNum;
	}

	public void setUserTelNum(String userTelNum) {
		this.userTelNum = userTelNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public UserInfo() {
	}

	public UserInfo(int id) {
		this.userNo = id;
	}

	
}
