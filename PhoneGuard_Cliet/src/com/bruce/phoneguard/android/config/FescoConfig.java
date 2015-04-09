package com.bruce.phoneguard.android.config;

import java.io.File;

import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.utils.FileUtils;

import android.os.Environment;

public class FescoConfig {

	//当前登录用户的sharedPrefence，用于保存“常用”列表信息
	public static final String PHONEGUARD_FREQUENTLY_SP = 
			"PHONEGUARD_FREQUENTLY_SP"+SysApplication.getUser().getUserNo();
	/**包下files目录*/
	public static final String FILES_PATH = SysApplication.getInstance().getFilesDir().getAbsolutePath();

	/**assets目录下antivirus.db文件名*/
	public static final String ANTIVIRUS_DB = "antivirus.db";
	
	/**assets目录下address.db文件名*/
	public static final String ADDRESS_DB = "address.db";
	
	public static final int TAG_MODIFY_PSD = 1000;
	public static final int TAG_MODIFY_PROFILE = 1001;
	public static final int TAG_REQUEST_PERSONEL_LIST = 1002;
	public static final int TYPE_MANAGEMENT = 100;
	public static final int TYPE_FREQUENTLY = 101;
	
	public static final File SDCARD_DIRECTORY = Environment.getExternalStorageDirectory();
//	public static final String PATH_PHOTO_CACHE = SDCARD_DIRECTORY.getPath() + "/fesco/cache/photo/";
	public static final String PATH_PHOTO_CACHE = FileUtils.getCachePath("photo");
	
	public static final int ADD_NEW = 0;         //添加
	public static final String ADD_NEW_STR = "ADD_NEW";
	public static final int SOFTWARE_MANA = 1;        //审批
	public static final String APPROVAL_STR = "APPROVAL";        
	public static final int TASK_MANA = 2;           //轨迹
	public static final String TRAIL_STR = "TRAIL";           
	public static final int GARBAGE_CLEAN = 3; //人员管理
	public static final String PERSONAL_MANAGE_STR = "PERSONAL_MANAGE"; 
	public static final int ANTI_VIRUS = 4;         //签到
	public static final String SIGN_IN_STR = "SIGN_IN";         
	public static final int TRAFFIC_MANAGE = 5; //人事
	public static final String HUMAN_RESOURCES_STR = "HUMAN_RESOURCES";
	public static final int AGAINST_THEFT = 6;
	public static final String AGAINST_THEFT_STR = "AGAINST_THEFT";
	public static final int TEL_ENQUIRIES = 7;
	public static final String TEL_ENQUIRIES_STR = "TEL_ENQUIRIES";
	public static final int DATA_BAKEUP = 8;
	public static final String DATA_BAKEUP_STR = "DATA_BAKEUP";
	public static final int PROGRAM_LOCK = 9;
	public static final String PROGRAM_LOCK_STR = "PROGRAM_LOCK";
	public static final int BIG_FILE_MANAGE = 10;
	public static final String BIG_FILE_MANAGE_STR = "BIG_FILE_MANAGE";

	
	public static final int STAFF_SEX_MALE = 0 ;	//男
	public static final int STAFF_SEX_FEMALE = 1 ;	//女
	
	public static final int STAFF_POSITION_AREAMANAGER = 0 ;	//大区经理
	public static final int STAFF_POSITION_CITYMANAGER = 1 ;	//城市经理
	public static final int STAFF_POSITION_SUPERVISOR = 2 ;	//督导
	
	public static final int REQFLEAVE_STATE_AGREE = 0;	//同意请假
	public static final int REQFLEAVE_STATE_DISAGREE = 1 ; //不同意请假
	public static final int REQFLEAVE_STATE_WAITING = 2 ; //审核中
	
	public static final String AGREE = "同意";
	public static final String DISAGREE = "不同意";
	public static final String WAITTING = "审批中";
	
	public static final int LEAVETYPE_BUSINESS = 0;	//事假
	public static final int LEAVETYPE_SICK = 1;	//病假
	public static final int LEAVETYPE_OTHERS = 2; //其它
    public static  final  int  CAMERA_REQUEST_CODE=0;
    public static  final  int  IMG_REQUEST_CODE=1;
    public static  final  int  PLAY_REQUEST_CODE=2;
	

	public static final String BUSINESS = "事假";
	public static final String SICK = "病假";
	public static final String OTHERS = "其它";
	
	public static final int NOTICE_NOTREAD = 0;	// 未读
	public static final int NOTICE_ISREAD = 1;	// 已读
    public static final String GESTURE_FLAG = "GESTURE_FLAG"; //打开手势密码界面的标记
    public static final String GESTURE_FLAG_SERVICE = "GESTURE_FLAG_SERVICE"; //从服务打开手势密码界面的标记
    public static final String GESTURE_FLAG_SETTING = "GESTURE_FLAG_SETTING"; //从设置打开手势密码界面的标记

    public static String getStateInString(int state){
		switch(state){
		case FescoConfig.REQFLEAVE_STATE_AGREE:
			return AGREE;
		case FescoConfig.REQFLEAVE_STATE_DISAGREE:
			return DISAGREE;
		case FescoConfig.REQFLEAVE_STATE_WAITING:
			return WAITTING;
			default:
				return WAITTING;
		}
	}
	
	public static String getLeaveTypeInString(int type){
		switch (type) {
		case FescoConfig.LEAVETYPE_BUSINESS:
			return BUSINESS;
		case FescoConfig.LEAVETYPE_SICK:
			return SICK;
		case FescoConfig.LEAVETYPE_OTHERS:
			return OTHERS;
		default:
			return OTHERS;
		}
	}
	public static boolean hasSdcard() {
		// TODO Auto-generated method stub
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	public static final int NOTICE_TYPE_SYS = 0;	//系统公告
	public static final int NOTICE_TYPE_LEAVE = 1;	//请假消息

	public static final String URL_NOTICE_PERSONNEL_LIST = "http://www.baidu.com/getPersonnelMsgList?staffNO=2424？pageIndex=1&pageSize=20";
	public static final String URL_NOTICE_PUBLIC_LIST = "URL :http://www.baidu.com/getNoticeMsgList?pageIndex=1&pageSize=20";
	public static final String URL_NOTICE_PUBLIC_DETAIL = "http://www.baidu.com/getNoticeDetail?NoticeNo=111&StaffNo=111";
	public static final String URL_APPVERSION = "http://www.baidu.com/getAppVersion";

	public static final int VERSION = 10;
	
	public static final int DOWNLOAD_START = 0;
	public static final int DOWNLOAD_UPDATE = 1;
	public static final int DOWNLOAD_FINISH = 2;
	public static final int GET_DATA_ERROR = 4;
}
