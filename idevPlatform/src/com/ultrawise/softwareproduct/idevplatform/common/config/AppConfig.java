package com.ultrawise.softwareproduct.idevplatform.common.config;

/**
 * Created by JNinBer on 2014/5/7 0007.
 */
public class AppConfig {
	public static boolean showLog = true;
	public final static int FESCO_APP_VERSION = 1001; // 获取FESCO的版本号
	public final static int FESCO_APP_CONTENTlIST = 1002; // 获取FESCO内容
	public  final static  int FESCO_APP_PERSONLIST = 1003;  //获取FESCO人员管理列表
    public  final static  int FESCO_APP_MODIFY_PSD = 1004;  //修改FESCO账户密码
    public  final static  int FESCO_APP_MODIFY_PROFILE = 1005;  //修改FESCO账户资料
	
    public  final static  int FESCO_APP_SINGTASK =1006;  //获取FESCO内容
    public  final static  int FESCO_APP_SINGTASK_DETAIL =1007;  //获取FESCO内容
    
    public final static int FESCO_APP_REQFLEAVEHISTORY_LIST = 1008;	//获取FESCO的请假历史列表
    public final static int FESCO_APP_APPROVAL_LIST = 1009;	//获取FESCO的审批列表，由经理审批
    public final static int FESCO_APP_REQFLEAVE_DETAIL = 1010;//获取FESCO的请假详情
    public final static int FESCO_APP_APPROVAL_DETAIL = 1011;	//获取FESCO的审批详情
    public final static int FESCO_APP_REQFLEAVE_COMMIT = 1012;	//提价FESCO请假消息
    public final static int FESCO_APP_REQFLEAVE_MODIFY = 1013;	//修改FESCO请假消息
    public final static int FESCO_APP_APPROVALSTATE_COMMIT = 1015;	//经理提交审批详情
    public final static int FESCO_APP_REQFLEAVEINFO_MODIFY = 1016;	//获取FESCO修改的数据
    
    public final static int FESCO_APP_NOTICE_PERSONNEL_LIST = 1020;		// 获取FESCO人事列表
    public final static int FESCO_APP_NOTICE_PUBLIC_LIST = 1021;		// 获取FESCO公告列表
    public final static int FESCO_APP_NOTICE_PUBLIC_DETAIL = 1022;		// 获取FESCO公告详情
    public final static  int FRSCO_APP_DAY_TASK_LIST=10023;   //轨迹列表


	
    public final static int FESCO_APP_NEW_SINGTASK_DETAIL =1014;  //新建签到详情
	
    /********************高就**********************************/
	public final static int RECRUIT_HOME_JOBLIST = 2000; // 高就获取首页的职位信息
	public final static int RECRUIT_GET_ONEPOSITION = 2001; // 高就获取一条职位信息
	public final static int RECRUIT_RECRUIT_JOBS = 2002; // 高就获取搜索页面职位类型
	public final static int RECRUIT_MORE_JOBS = 2003; // 高就获取搜索页面"更多"职位信息
	public final static int RECRUIT_APPLY_REASON = 2004; // 高就发送“申请原因”的文字
	public final static int RECRUIT_THREE_TYPE = 2005; // 高就在搜索页面点击一个三级分类的请求
	public final static int RECRUIT_SEARCH_JOBS = 2006; // 高就在搜索页面点击搜索的请求
	public final static int RECRUIT_GET_LOGIN = 2011; // 或者登录信息
	public final static int RECRUIT_GET_REGIST = 2012; // 或者注册信息
	public final static int RECRUIT_GET_USERINFO = 2013;// 用户信息
	public final static int RECRUIT_MODIFY_PASSWORD = 2014;// 修改密码信息
	public final static int RECRUIT_MODIFY_USER_PERSONAL = 2015;// 修改用户信息
	public final static int RECRUIT_NOTICE_NUM = 2016;// 高就设置界面新消息数目
	public final static int RECRUIT_NEW_JOBLIST = 2017;// 高就新的招聘职位
	public final static int RECRUIT_PASSING_INFO = 2018;// 高就新的招聘职位
	public final static int RECRUIT_APP_VERSION = 2019; // 获取高就的版本号
	
    /********************高就学生端**********************************/
	public final static int RECRUIT_RESUME_LIST = 3000; // 高就获取首页的职位信息
	public final static int RECRUIT_RESUME_DETAIL = 3001; // 高就获取首页的职位信息
	
	/********************高就企业端**********************************/
	public final static int RECRUIT_APPL_LIST = 4000; // 申请人列表
	public final static int RECRUIT_CANOTICE_LIST = 4001; // 应聘者消息列表
	public final static int RECRUIT_APPNOTICE_LIST = 4002; // 申请人消息列表
	public final static int RECRUIT_CANDIDATE_LIST = 4003; // 应聘者列表
}
