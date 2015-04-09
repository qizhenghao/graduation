package com.ultrawise.softwareproduct.idevplatform.request;

import android.annotation.SuppressLint;
import android.text.format.Time;
import android.util.Log;
import com.ultrawise.softwareproduct.idevplatform.common.config.AppConfig;
import com.ultrawise.softwareproduct.idevplatform.entity.ResultItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SuppressLint("SimpleDateFormat")
public class MockDataSources {
	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static int PAGESUM = 5;	// 消息总页数
	
	public static ResultItem getDatas(HttpRequestParams param) {

		ResultItem item = null;
		switch (param.getRequsetcode()) {
		case AppConfig.FESCO_APP_VERSION:
			item = getVersion();
			break;
		case AppConfig.FESCO_APP_CONTENTlIST:
			item = getList();
			break;
		case AppConfig.RECRUIT_HOME_JOBLIST:
			item = getHomeFragmentJobList();
			break;
		case AppConfig.RECRUIT_RECRUIT_JOBS:
			item = getRecuitTFragmentJobs();
			break;
		case AppConfig.RECRUIT_MORE_JOBS:
			item = getRecuitTFragmentMoreJobs();
			break;
		case AppConfig.RECRUIT_THREE_TYPE:
			item = getOneThreeTypeJobList();
			break;
		case AppConfig.RECRUIT_APPLY_REASON:
			item = getApplyResult();
			break;
		case AppConfig.RECRUIT_GET_ONEPOSITION:
			item = getOnePosition();
			break;
		case AppConfig.RECRUIT_SEARCH_JOBS:
			item = searchJobs();
			break;
		case AppConfig.FESCO_APP_PERSONLIST:
			item = getFescoPersonList(param);
			break;
		case AppConfig.FESCO_APP_SINGTASK:
			item = getOneJsonSingtask();
			break;
		case AppConfig.FESCO_APP_SINGTASK_DETAIL:
			item = getOneJsonSingtaskDetail();
			break;
		case AppConfig.FESCO_APP_MODIFY_PSD:
			item = getFescoModifyPsd();
			break;
		case AppConfig.FESCO_APP_MODIFY_PROFILE:
			item = getFescoModifyProfile();
			break;
		case AppConfig.FESCO_APP_REQFLEAVE_COMMIT:
			item = getFescoReqfLeaveCommit();
			break;
		case AppConfig.FESCO_APP_REQFLEAVE_MODIFY:
			item = getFescoReqfLeaveModify();
			break;
		case AppConfig.FESCO_APP_REQFLEAVEINFO_MODIFY:
			item = getFescoReqfLeaveInfoModify();
			break;
		case AppConfig.FESCO_APP_REQFLEAVEHISTORY_LIST:
			item = getFescoReqfLeaveHistoryList();
			break;
		case AppConfig.FESCO_APP_REQFLEAVE_DETAIL:
			item = getFescoReqfLeaveDetail();
			break;
		case AppConfig.FESCO_APP_APPROVAL_LIST:
			item = getFescoApprovalList();
			break;
			
		case AppConfig.FESCO_APP_APPROVAL_DETAIL:
			item = getFescoApprovalDetail();
			break;
		case AppConfig.FESCO_APP_APPROVALSTATE_COMMIT:
			item = getFescoApprovalState();
			break;
		case AppConfig.RECRUIT_GET_LOGIN:
			item = getLogin();
			break;
		case AppConfig.RECRUIT_GET_REGIST:
			item = getRegister();
			break;
		case AppConfig.RECRUIT_MODIFY_PASSWORD:
			item = ModifyPassword();
			break;
		case AppConfig.FESCO_APP_NOTICE_PERSONNEL_LIST:
			item = getPersonnelNoticeList(param);
			break;
		case AppConfig.FESCO_APP_NOTICE_PUBLIC_LIST:
			item = getPublicNoticeList(param);
			break;
		case AppConfig.FESCO_APP_NOTICE_PUBLIC_DETAIL:
			item = getPublicNoticeDetail();
			break;
		case AppConfig.RECRUIT_MODIFY_USER_PERSONAL:
			item = ModifyPersonalInfo();
			break;
		case AppConfig.RECRUIT_NOTICE_NUM:
			item = getNoticeNum();
			break;
		case AppConfig.RECRUIT_NEW_JOBLIST:
			item = getNewJobList();
			break;
		case AppConfig.RECRUIT_PASSING_INFO:
			item = getPasingInfo();
			break;
		case AppConfig.RECRUIT_APP_VERSION:
			item = getRecuitAppVersion();
			break;
		case AppConfig.RECRUIT_APPL_LIST:
			item = getApplicantList();
			break;
		case AppConfig.RECRUIT_CANOTICE_LIST:
			item = getCandidateNoticetList();
			break;
		case AppConfig.RECRUIT_APPNOTICE_LIST:
			item = getAPPlincantNoticetList();
			break;
		case AppConfig.RECRUIT_CANDIDATE_LIST:
			item = getCandidateList();
			break;
		case AppConfig.RECRUIT_RESUME_LIST:
			item = getRecuitResumeList();
			break;
		case AppConfig.RECRUIT_RESUME_DETAIL:
			item = getRecuitResumeDetail();
			break;
		default:
			break;
		}
		return item;
	}
	

	private static ResultItem getRecuitResumeList() {
		// TODO Auto-generated method stub
		// 1name.2image,3,date,4,beizhu,5,finish(true or false)
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 5; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("HeadImg",
					"http://ww3.sinaimg.cn/mw600/8cd00fc4jw1dzhjk9etpxj.jpg");
			item2.addValue("resumeId", i);
			item2.addValue("resumename", "简历" + i);
			item2.addValue("date",
					df.format(new Date(System.currentTimeMillis())));
			if (i % 3 == 0) {
				item2.addValue("remark", "IT");
				item2.addValue("finish", false);
			} else {
				item2.addValue("remark", "经济");
				item2.addValue("finish", true);
			} 
			items.add(item2);
		}
		item.addValue("RecuitResumeList", items);
		return item;
	}

	private static ResultItem getRecuitResumeDetail() {
		// TODO Auto-generated method stub
		// 1name.2image,3,date,4,beizhu,5,finish(true or false)
		/*
		 * Button topbtn_left; TextView jobresume, resume_name, resume_detail_1,
		 * resume_detail_2, resume_detail_3, phone, mail, workname, timeUp,
		 * workteno, worktype, job_categories, where, treat_condition,
		 * self_evla;
		 */
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 5; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("HeadImg",
					"http://ww3.sinaimg.cn/mw600/8cd00fc4jw1dzhjk9etpxj.jpg");
			item2.addValue("resumeId", i);
			item2.addValue("resume_name", "李小" + i);
			item2.addValue("jobresume", "java" + i);
			if(i%2==0){
			item2.addValue("resume_sex", "男");
			}else{
				item2.addValue("resume_sex", "女");
			}
			item2.addValue("resume_bir", "1992-01-01");
			item2.addValue("resume_nation", "汉族");
			item2.addValue("resume_college", "本科");
			item2.addValue("resume_hometown", "成都");
			item2.addValue("resume_bodycondition", "良好");
			item2.addValue("resume_live", "深圳");
			
			item2.addValue("jobresume", "java" + i);
			
			item2.addValue("phone", "1820022222" + i);
			item2.addValue("mail", "123312" + i + "qq.com");
			item2.addValue("workname", "UI设计师" + i);
			item2.addValue("timeUp", "一周之内");
			item2.addValue("workteno", "PShuhj");
			if (i % 2 == 0) {
				item2.addValue("worktype", "全职");
			} else {
				item2.addValue("worktype", "兼职");
			}
			item2.addValue("job_categories", "编程");
			item2.addValue("where", "广州");
			item2.addValue("treat_condition", "10000-15000");
			item2.addValue("self_evla",
					"自我评价自我评价自我评价自我评价自我评价自我评价自我评价自我评价自我评价自我评价");
			items.add(item2);
		}
		item.addValue("RecuitResumeDetail", items);
		return item;
	}
	
	private static ResultItem getCandidateList() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("id", i);
			item2.addValue("candidateName", "张三"+i);
			item2.addValue("psitionName", "产品经理" + i);
			item2.addValue("psitionType", "互联网");
			item2.addValue("age", 23);
			item2.addValue("positionExperience", "1-3年");
			item2.addValue("educationalBackground", "大专");
			item2.addValue("positionAddr", "成都");
			item2.addValue("scale", 68);
			item2.addValue("img", "http://ww1.sinaimg.cn/bmiddle/bd33ebcbjw1eh9gewj3f5j20dc0dc0tl.jpg");
			items.add(item2);
		}
		item.addValue("candidateList", items);
		return item;
	}

	private static ResultItem getAPPlincantNoticetList() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("id", i);
			item2.addValue("candidateName", "张三"+i);
			item2.addValue("psitionName", "产品经理" + i);
			item2.addValue("isFinish", "已完成");
			item2.addValue("createTime", "04-12");
			items.add(item2);
		}
		item.addValue("applincantNoticetList", items);
		return item;
	}

	/**
	 * @author WQD
	 * 应聘者消息列表
	 * @return
	 */
	private static ResultItem getCandidateNoticetList() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("noticeId", i);
			item2.addValue("candidateName", "张三"+i);
			item2.addValue("psitionName", "产品经理" + i);
			item2.addValue("positionTime", "全职");
			item2.addValue("positionExperience", "1-3年");
			item2.addValue("educationalBackground", "大专");
			item2.addValue("createTime", "04-12");
			items.add(item2);
		}
		item.addValue("candidateNoticetList", items);
		return item;
	}
	/**
	 * @author WQD
	 * 
	 *@description 获取申请人列表
	 */
	private static ResultItem getApplicantList() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		item.addValue("positionName","产品经理");
		item.addValue("applyNum",24);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("candidate", "张三");
			item2.addValue("profile", "我能很好的帮您完成任务，请务必相信我");
			item2.addValue("img", "http://ww1.sinaimg.cn/bmiddle/bd33ebcbjw1eh9gewj3f5j20dc0dc0tl.jpg");
			items.add(item2);
		}
		item.addValue("aplicantList", items);
		return item;
	}
	/**
	 * @author 滕宏梦
	 * 2014年6月9日
	 * @description 搜索需求页面，搜索的模拟数据
	 */
	private static ResultItem searchJobs() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("positionId", i);
			item2.addValue("psitionName", "Java高级工程师" + i);
			item2.addValue("positionAddr", "成都");
			item2.addValue("positionExperience", "经验1-3年");
			item2.addValue("educationalBackground", "大专");
			item2.addValue("companyName", "成都点明科技有限公司");
			item2.addValue("createTime", df.format(new Date(System.currentTimeMillis())));
			item2.addValue("positionSalary", "1100-5000元");
			item2.addValue("demandDescription", "我需要找个好手");
			item2.addValue("peopleSum", "1-3人");
			item2.addValue("endtime", i + "天后截止");
			item2.addValue("rebate", "500元");
			items.add(item2);
		}
		item.addValue("oneThreeTypeJobList", items);
		return item;
	}
	private static ResultItem getRecuitAppVersion() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		item.addValue("versionInfo", "1.修补了无数的bug"+"\n"+"2.速度更快"+"\n"+"3.效率更高");
		item.addValue("versionName", "3.0");
		item.addValue("URL", "http://gdown.baidu.com/data/wisegame/37cfe04c8cccf4a2/shoudiantong_11.apk");
		return item;
	}
	/**
	 * 新的通过信息
	 * @return
	 */
	private static ResultItem getPasingInfo() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 5; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("id", i);
			item2.addValue("positionId", i);
			item2.addValue("psitionName", "Java高级工程师" + i);
			item2.addValue("positionAddr", "成都" + i);
			item2.addValue("profile", "我们是专业的职业推荐师..." + i);
			item2.addValue("createTime",
					df.format(new Date(System.currentTimeMillis())));
			items.add(item2);
		}
		item.addValue("passingList", items);
		return item;

	}
	/**
	 * 消息界面新消息列表
	 * @author WQD
	 */
	private static ResultItem getNewJobList() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 5; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("positionId", i);
			item2.addValue("psitionName", "Java高级工程师" + i);
			item2.addValue("positionAddr", "成都" + i);
			item2.addValue("positionExperience", "1-3年");
			item2.addValue("educationalBackground", "本科");
			item2.addValue("companyName", "成都点名科技有限公司 " + i);
			item2.addValue("createTime",
					df.format(new Date(System.currentTimeMillis())));
			item2.addValue("positionSalary", "3000-5000");
			items.add(item2);
		}
		item.addValue("HomeFragmentJobList", items);
		return item;
	}

	//设置界面消息数量
	//@author WQD
	private static ResultItem getNoticeNum() {
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		item.addValue("newMsgCount", 2);
		return item;
	}

	//登录
	private static ResultItem getLogin() {
		ResultItem item = new ResultItem();
		item.addValue("status", true);		
		ResultItem userInfo = new ResultItem();
		userInfo.addValue("sid", 2001);
		userInfo.addValue("userId", 1001);
		userInfo.addValue("userAccount", "admin");
		userInfo.addValue("userName", "张三");
		userInfo.addValue("userAge",22);
		userInfo.addValue("userSex", "女");
		userInfo.addValue("userAddr", "四川成都");
		userInfo.addValue("userDescription","我是一名Android工程师");
		userInfo.addValue("password", "admin");
		userInfo.addValue("userPicture", "");
		userInfo.addValue("focusIndustry", "银行，计算机硬件");
//		item.addValue("error_key", "0");
//		item.addValue("error_msg", "这是错误信息");
		item.addValue("userInfo", userInfo);
		return item;
	}
	// 注册
    private static ResultItem getRegister(){
    	ResultItem item = new ResultItem();
    	item.addValue("status", true);
    	//item.addValue("error_key", 101);
    	//item.addValue("error_msg", "用户名已存在");
    	return item;
    }
    
    //修改密码 
    private static ResultItem ModifyPassword(){
    	ResultItem item = new ResultItem();
    	item.addValue("status", true);
    	return item;
    }
    
    //修改个人信息 
    private static ResultItem ModifyPersonalInfo(){
    	ResultItem item = new ResultItem();
    	item.addValue("status", true);
    	return item;
    }
	public static String getStringDatas(int what, HttpRequestParams param) {
		String item = null;
		switch (what) {
		default:
			break;
		}
		return item;
	}
/*
	private static ResultItem getAppVersion() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("name", "name ");
		item.addValue("cateVersion", "m cateVersion");
		item.addValue("limitCom", 250);
		item.addValue("version", "1");
		item.addValue("url", "http://www.baidu.com");
		return item;
	}
*/
	private static ResultItem getList() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("name", "name ");
		item.addValue("cateVersion", "m cateVersion");
		item.addValue("limitCom", 250);
		item.addValue("version", "1");
		item.addValue("url", "http://www.baidu.com");
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 5; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("name", "name ");
			item2.addValue("cateVersion", "m cateVersion");
			item2.addValue("limitCom", 250);
			item2.addValue("version", "1");
			item2.addValue("url", "http://www.baidu.com");
			items.add(item2);
		}
		item.addValue("item", items);
		return item;
	}

	/**
	 * @author 滕宏梦 2014年5月27日
	 * @return
	 * @description 高就获取首页职位信息
	 */
	private static ResultItem getHomeFragmentJobList() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		String[] checkS = {"已中标", "进行中", "已提交", "已完成", "企业未查看"};
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("positionId", i);
			item2.addValue("psitionName", "Java高级工程师" + i);
			item2.addValue("endtime", i + "天后截止");
			item2.addValue("rebate", "赏金500");
			item2.addValue("checkstatus", checkS[(i % 5)]);
			items.add(item2);
		}
		item.addValue("HomeFragmentJobList", items);
		return item;
	}
	/**
	 * @author 滕宏梦
	 * 2014年6月6日
	 * @description 高就在搜索页面点击一个三级分类的请求
	 */
	private static ResultItem getOneThreeTypeJobList() {
		thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("positionId", i);
			item2.addValue("psitionName", "Java高级工程师" + i);
			item2.addValue("positionAddr", "成都");
			item2.addValue("positionExperience", "经验1-3年");
			item2.addValue("educationalBackground", "大专");
			item2.addValue("companyName", "成都点明科技有限公司");
			item2.addValue("createTime", df.format(new Date(System.currentTimeMillis())));
			item2.addValue("positionSalary", "1100-5000元");
			item2.addValue("demandDescription", "我需要找个好手");
			item2.addValue("peopleSum", "1-3人");
			item2.addValue("endtime", i + "天后截止");
			item2.addValue("rebate", "500元");
			items.add(item2);
		}
		item.addValue("oneThreeTypeJobList", items);
		return item;
	}


	/**
	 * @author 滕宏梦
	 * 2014年6月5日
	 * @description 发送申请原因后的返回值
	 */
	private static ResultItem getApplyResult() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		return item;
	}
	
	
	/**
	 * @author 滕宏梦
	 * 2014年6月4日
	 * @description 获取搜索Fragment的职位
	 */
	private static ResultItem getRecuitTFragmentJobs() {
		ResultItem item = new ResultItem();
		List<ResultItem> oneTypeNum = new ArrayList<ResultItem>();
		item.addValue("status", true);
		
		int threeTypeNum = 0;
		String[] oneType   = {"技术", "产品", "设计", "金融", "市场"};
		String[] threeTypes = {"Java", "产品经理", "网页设计", "会计", "市场经理"};
		for (int i = 0; i < oneType.length; i++) {
			ResultItem resultItem = new ResultItem();
			resultItem.addValue(  "oneTypeId", i);
			resultItem.addValue("oneTypeName", oneType[i]);
			if (i % 2 ==0) {
				threeTypeNum = 10;
			} else {
				threeTypeNum = 8;
			}
			List<ResultItem> threeTypeList = new ArrayList<ResultItem>();
			for (int j = 0; j < threeTypeNum; j++) {
				ResultItem threeType = new ResultItem();
				threeType.addValue(  "threeTypeId", j);
				threeType.addValue("threeTypeName", threeTypes[i]);
				threeTypeList.add(threeType);
			}
			resultItem.addValue("threeTypes", threeTypeList);
			oneTypeNum.add(resultItem);
		}
		item.addValue("oneTypeList", oneTypeNum);
		return item;
	}
	/**
	 * @author 滕宏梦
	 * 2014年6月4日
	 * @description 获取搜索Fragment “更多” 职位信息
	 */
	private static ResultItem getRecuitTFragmentMoreJobs() {
		ResultItem item = new ResultItem();
		List<ResultItem> twoTypeNum = new ArrayList<ResultItem>();
		item.addValue("status", true);
		
		int threeTypeNum = 8;
		String[] twoType   = {"技术", "产品", "设计"};
		String[] threeTypes = {"Java", "产品经理", "网页设计"};
		for (int i = 0; i < twoType.length; i++) {
			ResultItem resultItem = new ResultItem();
			resultItem.addValue(  "twoTypeId", i);
			resultItem.addValue("twoTypeName", twoType[i]);

			List<ResultItem> threeTypeList = new ArrayList<ResultItem>();
			for (int j = 0; j < threeTypeNum; j++) {
				ResultItem threeType = new ResultItem();
				threeType.addValue(  "threeTypeId", j);
				threeType.addValue("threeTypeName", threeTypes[i]);
				threeTypeList.add(threeType);
			}
			resultItem.addValue("threeTypes", threeTypeList);
			twoTypeNum.add(resultItem);
		}
		item.addValue("twoTypeList", twoTypeNum);
		return item;
	}

	/**
	 * @author 滕宏梦 2014年5月27日
	 * @return
	 * @description 高就获取一条职位信息
	 */
	private static ResultItem getOnePosition() {
        thisThreadSleep();
        ResultItem item = new ResultItem();
		item.addValue("status", true);
		ResultItem item2 = new ResultItem();
		item2.addValue("positionId", 4);
		item2.addValue("positionName", "Java高级工程师");
		item2.addValue("positionAddr", "成都");
		item2.addValue("positionExperience", "1-3年");
		item2.addValue("educationalBackground", "本科");
		item2.addValue("companyName", "成都点名科技有限公司 ");
		item2.addValue("createTime",
				df.format(new Date(System.currentTimeMillis())));
		item2.addValue("positionSalary", "3000-5000");
		item2.addValue("rebate", "3000");
		item2.addValue("companyAddr", "成都市双流区西航港大道");
		item2.addValue("peopleSum", "3-5");
		item2.addValue("positionType", "兼职");
		item2.addValue(
				"positionDescription",
				"1、技术人员职位，在上级的领导和监督下定期完成量化的工作要求\n2、能独立处理和解决所负责的任务\n3、根据开发进度和任务分配，完成相应模块软件的设计、开发、编程任务\n4、进行程序单元、功能的测试，查出软件存在的缺陷并保证其质量\n5、进行编制项目文档和质量记录的工作\n");
		
		//企业版新增的数据---李雪莉
		item2.addValue("companyInfo", "成都点名科技有限公司");
		item2.addValue("recruitNum", 24);
		item2.addValue("positionCategory", "Java");
		
		item.addValue("PositionInfo", item2);
		return item;
	}

	/*************************************** Fesco ***************************************/

	/**
	 * @author qizhenghao
	 * @return fesco人员管理列表
	 */
	private static ResultItem getFescoPersonList(HttpRequestParams param) {

        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("pageSum", PAGESUM);
		item.addValue("currentPage", param.getParams().get("nextPageNum"));
		item.addValue("pageSize", 10);
		item.addValue("hasNextPage", true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 10; i++) {
			ResultItem item2 = new ResultItem();
			item2.addValue("staffNo", i);
			item2.addValue("staffName", param.getParams().get("nextPageNum")+"小薇" + i);
			item2.addValue("staffSex", 1);
			item2.addValue("staffAge", 20);
			item2.addValue("staffTelNum", "15300668888");
			item2.addValue("email", "15300668888@163.com");
			item2.addValue("staffHeadImg",
					"http://ww3.sinaimg.cn/mw600/8cd00fc4jw1dzhjk9etpxj.jpg");
			items.add(item2);
		}
		item.addValue("data", items);
		return item;
	}
	private static ResultItem getFescoApprovalState(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		return item;
	}
	private static ResultItem getFescoReqfLeaveModify(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		return item;
		
	}
	private static ResultItem getFescoReqfLeaveCommit(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		return item;
	}
	private static ResultItem getFescoApprovalList(){
		int random = (int)(Math.random()*100);
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		item.addValue("pageSum",5);
		item.addValue("pageSize", 10);
		item.addValue("currentPage",5);
		item.addValue("hasNextPage",false);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for(int i = 0 ; i < 10 ; i++){
			ResultItem temp = new ResultItem();
			temp.addValue("leaveNo", i);
			temp.addValue("staffName", "网名"+random);
			temp.addValue("staffHeadImg","http://ww3.sinaimg.cn/mw600/8cd00fc4jw1dzhjk9etpxj.jpg");
			temp.addValue("leaveType", 1);
			temp.addValue("releaseDate", "2014/5/30");
			temp.addValue("leaveState", 1);
			items.add(temp);
		}
		item.addValue("data", items);
		return item;
	}
	
	private static ResultItem getFescoReqfLeaveHistoryList(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		item.addValue("pageSum",7);
		item.addValue("pageSize", 10);
		item.addValue("currentPage", 4);
		item.addValue("hasNextPage",true);
		List<ResultItem> items = new ArrayList<ResultItem>();
		for(int i = 0 ; i < 10 ; i++){
			ResultItem temp = new ResultItem();
			temp.addValue("leaveNo", i);
			temp.addValue("staffHeadImg","http://ww3.sinaimg.cn/mw600/8cd00fc4jw1dzhjk9etpxj.jpg");
			temp.addValue("leaveType", i%3);
			temp.addValue("releaseDate", "2014/5/3"+i);
			temp.addValue("leaveState", i%3);
			temp.addValue("canBeModified",i%2==1);
			items.add(temp);
		}
		item.addValue("data", items);
		Log.d("testHistory","getTestData");
		return item;	
	}
	private static ResultItem getFescoReqfLeaveInfoModify(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		
		ResultItem temp = new ResultItem();
		temp.addValue("fromDate", "2014/6/4");
		temp.addValue("fromTime", "20:00");
		temp.addValue("toDate", "2014/6/8");
		temp.addValue("toTime", "8:00");
		temp.addValue("leaveType",0);
		temp.addValue("leaveReason","这个问题问的好乖的，我请个假而已，修改请假消息");		
		item.addValue("data", temp);
		return item;
	}
	private static ResultItem getFescoReqfLeaveDetail(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		
		ResultItem temp = new ResultItem();
		temp.addValue("leaveNo", 1);
		temp.addValue("staffName", "刘某");
		temp.addValue("staffAddr","双流县");
		temp.addValue("staffTelNum", "18200276322");
		temp.addValue("leaveAmountTime","3.6天");
		temp.addValue("leaveBegin", "2014/6/4 20:00");
		temp.addValue("leaveEnd", "2014/6/8 8:00");
		temp.addValue("leaveType",0);
		temp.addValue("leaveReason","这个问题问的好乖的，我请个假而已");
		temp.addValue("leaveStateByCM",1);
		temp.addValue("CMname","刘总");
		temp.addValue("leaveStateByAM", 2);
		temp.addValue("AMname", "王总");
		
		item.addValue("data", temp);
		return item;
	}
	
	private static ResultItem getFescoApprovalDetail(){
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("status_msg", "ok");
		
		ResultItem temp = new ResultItem();
		temp.addValue("leaveNo", 1);
		temp.addValue("staffName", "刘某");
		temp.addValue("staffAddr","双流县");
		temp.addValue("staffTelNum", "18200276322");
		temp.addValue("leaveAmountTime","3.6天");
		temp.addValue("leaveBegin", "2014/6/4 20:00");
		temp.addValue("leaveEnd", "2014/6/8 8:00");
		temp.addValue("leaveType",0);
		temp.addValue("leaveReason","这个问题问的好乖的，我请个假而已");
		temp.addValue("myApprovalState", 2);
		temp.addValue("leaveStateByCM",1);
		temp.addValue("CMname","刘总");
		temp.addValue("leaveStateByAM", 2);
		temp.addValue("AMname", "王总");
		item.addValue("data", temp);
		return item;
	}

	/**
	 * @author qizhenghao
	 * @return fesco修改密码
	 */
	private static ResultItem getFescoModifyPsd() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("error_msg", "这是错误信息");
		return item;
	}

	/**
	 * @author qizhenghao
	 * @return fesco修改资料
	 */
	private static ResultItem getFescoModifyProfile() {
        thisThreadSleep();
		ResultItem item = new ResultItem();
		item.addValue("status", "success");
		item.addValue("error_msg", "这是错误信息");
		return item;
	}

	
	
	

	private static ResultItem getOneJsonSingtask() {
        thisThreadSleep();
		String[] addresslist = new String[] { "校园超市", "全福缘", "学府海棠", "隆土基cafe",
				"电子科大急诊部", "电子科大医院", "清真餐厅", "水果超市", "满庭香", "三星桥" };
		double[] lonlist = { 103.936277, 103.935847, 103.99799, 103.942568,
				103.93608, 103.935136, 103.934804, 103.938276, 103.935817,
				103.939397 };
		double[] latlist = { 30.762938, 30.763004, 30.760242, 30.763605,
				30.763457, 30.76305, 30.76113, 30.762232, 30.762926, 30.759994 };
		ResultItem item = new ResultItem();
		item.addValue("status", true);
		item.addValue("pageContent", 17);
		item.addValue("pageSize", 9);
		item.addValue("pageNext", true);
		item.addValue("indexPage", 1);

		List<ResultItem> items = new ArrayList<ResultItem>();
		for (int i = 0; i < 20; i++) {
			int a = (int) (Math.random() * 10);
			ResultItem item2 = new ResultItem();

			item2.addValue("address", addresslist[i % 10]);
			item2.addValue("shopname", "电子店1");
			if (a > 5) {
				item2.addValue("State", 1);
				item2.addValue("startDate", (new Date(System.currentTimeMillis()+24*2*30 * 60 * 1000)));
				item2.addValue("EndDate", (new Date(System.currentTimeMillis() + 28*2*30 * 60 * 1000)));
				// 实际签到日期
				item2.addValue("signinDate",
						(new Date(System.currentTimeMillis())).toString());
			} else {
				item2.addValue("State", 0);
				item2.addValue("startDate", (new Date(System.currentTimeMillis())));
				item2.addValue("EndDate", (new Date(System.currentTimeMillis() + 4*2*30 * 60 * 1000)));
				item2.addValue("signinDate",
						(new Date(System.currentTimeMillis()+3*2*30 * 60 * 1000)).toString());
			}

			
//			Date date = new Date(System.currentTimeMillis() + 4*2*30 * 60 * 1000); //半小时时间
			
		
			// 实际签到日期
			item2.addValue("signinDate",
					(new Date(System.currentTimeMillis())).toString());
			item2.addValue("signinAddrLongitude", lonlist[i % 10]);
			item2.addValue("signinAddrLatitude", latlist[i % 10]);
			// taskNO就是地址存在的地方
			item2.addValue("TaskNo", Integer.parseInt(("10" + i)));
			items.add(item2);
		}
		item.addValue("List", items);
		return item;
	}

	private static ResultItem getOneJsonSingtaskDetail() {
        thisThreadSleep();
		String[] addresslist = new String[] { "校园超市", "全福缘", "学府海棠", "隆土基cafe",
				"电子科大急诊部", "电子科大医院", "清真餐厅", "水果超市", "满庭香", "三星桥" };
		double[] lonlist = { 103.936277, 103.935847, 103.99799, 103.942568,
				103.93608, 103.935136, 103.934804, 103.938276, 103.935817,
				103.939397 };
		double[] latlist = { 30.762938, 30.763004, 30.760242, 30.763605,
				30.763457, 30.76305, 30.76113, 30.762232, 30.762926, 30.759994 };
		ResultItem item = new ResultItem();
		item.addValue("TaskNo", 123);
//	    item.addValue("signinDate", true);
		item.addValue("State", true);
		
		item.addValue("markno", 0);
		item.addValue("note_content", "备注内容:签到具体hjdsfkdhfjkhsjkfhjkdshfjkhdsjkfhdsjkfh备注内容");
		item.addValue("sign_detail", "签到详情内容:签到具体地点的详情dsfhjkhdsjkhfjdshfjkhdsjkfhjkdh"
				+ "sdfkjhjkhfjkdshfjkhdsjkfhdsjkhdsjkfhkjhkdjf描述");
		
		item.addValue("address", addresslist[1]);
		item.addValue("signinAddrLongitude", lonlist[1]);
		item.addValue("signinAddrLatitude", latlist[1]);
		// 获取图片
		String[] images = new String[3];
		images = images();
		item.addValue("photoList", images);
		// 添加录音路径
		// ResultItem item2 = new ResultItem();

		// item2.addValue("voiceNo", 100);
		item.addValue("voicePath", generateFileName("1400651910768", ".amr"));
		item.addValue("voiceTime", "2s");
		// item.addValue("voicedata", item2);

		return item;
	}

	// 获取当前时间的图片
	public static String generateFileName(String filenamestr, String type) {
		String fileName = "" + filenamestr + type;
		return FileUtils.getCachePath("gen") + fileName;
	}

	public static String[] images() {
		String[] images = new String[3];
		images[0] = generateFileName("1400050530267", ".png");
		images[1] = generateFileName("1400653598217", ".png");
		images[2] = generateFileName("1400655231542", ".png");
		return images;
	}
    public static  void thisThreadSleep(){
        long beingTime = System.currentTimeMillis();
        Log.e("info" ,"beingTime==="+beingTime);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
	 * @author Little One
	 * @return fesco人事消息列表
	 */
    private static ResultItem getPersonnelNoticeList(HttpRequestParams param) {

		String[] type = new String[]{
			"病假", "事假", "其他"	
		};
		String[] date = new String[]{
				"2014.3.12", "2014.4.22", "2014,5,11", "2014.4.23", "2014.4.24"
		};
		
		Map<String, Object> params = param.getParams();
		int currentPage = (Integer) params.get("currentPage");
		int page_size = (Integer)params.get("pageSize");
		
		ResultItem item = new ResultItem();
		
		item.addValue("status", true);
		item.addValue("status_msg", null);
		item.addValue("currentPage", currentPage);
		item.addValue("pageSum", PAGESUM);
		item.addValue("pageSize", page_size);
		if(currentPage >= PAGESUM){
			item.addValue("hasNextPage", false);
		}else{
			item.addValue("hasNextPage", true);
		}
		
		List<ResultItem> items = new ArrayList<ResultItem>();
		for(int i = 0; i < page_size; i ++){
			ResultItem item1 = new ResultItem();
			
			item1.addValue("t_leaveNo", i);
			item1.addValue("t_leaveState", type[i%3]);
			item1.addValue("t_noticeTitle", type[i%3]);
			item1.addValue("t_noticeDate", date[i/5]);
			
			item1.addValue("t_staffName", "小" + i);
			item1.addValue("t_leaveType", "待审核");
			
			items.add(item1);
		}
		item.addValue("list", items);
		
		return item;		
	}
    
    /**
	 * @author Little One	
	 * @return fesco公告消息列表
	 */
    private static ResultItem getPublicNoticeList(HttpRequestParams param) {

		String[] date = new String[]{
				"2014.3.12", "2014.4.22", "2014,5,11", "2014.4.23", "2014.4.24"
		};
		
		Map<String, Object> params = param.getParams();
		int currentPage = (Integer) params.get("currentPage");
		int page_size = (Integer)params.get("pageSize");
		
		ResultItem item = new ResultItem();
		
		item.addValue("status", true);
		item.addValue("status_msg", null);
		item.addValue("currentPage", currentPage);
		item.addValue("pageSum", PAGESUM);
		item.addValue("pageSize", page_size);
		if(currentPage >= PAGESUM){
			item.addValue("hasNextPage", false);
		}else{
			item.addValue("hasNextPage", true);
		}
		
		List<ResultItem> items = new ArrayList<ResultItem>();
		for(int i = 0; i < page_size; i ++){
			ResultItem item1 = new ResultItem();
			
			item1.addValue("t_noticeNo", i);
			item1.addValue("t_noticeTitle", "标题" + i);
			item1.addValue("t_noticeDate", date[i/5]);
			item1.addValue("t_noticeTime", new Date(System.currentTimeMillis()).toString().substring(11, 19));
			item1.addValue("t_noticeIsread", false);
			
			items.add(item1);
		}
		item.addValue("list", items);
		
		return item;		
	}
    
    /**
	 * @author Little One
	 * @return fesco公告消息详情
	 */
    private static ResultItem getPublicNoticeDetail() {
		
		ResultItem item = new ResultItem();
		
		item.addValue("status", true);
		item.addValue("status_msg", null);
		ResultItem data = new ResultItem();
		data.addValue("t_noticeNo", 1);
		data.addValue("t_noticeTitle", "这是一个标题");
		data.addValue("t_notice_sender", "LisaRong");
		data.addValue("t_noticeDate", "2014.06.06");
		data.addValue("t_noticeTime", new Date(System.currentTimeMillis()).toString().substring(11, 19));
		data.addValue("t_noticeContent", "这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容");
		
		item.addValue("data", data);
		return item;		
	}
    
    /**
	 * @author Little One	
	 * @return 版本信息
	 */
    private static ResultItem getVersion(){
    	ResultItem item = new ResultItem();
    	
    	item.addValue("status", true);
    	item.addValue("status_msg", null);
    	item.addValue("app_version", 2);
    	item.addValue("app_url", "http://cdn.market.hiapk.com/data/upload/2013/10_08/11/secret.app_111957.apk");
    	
    	return item;
    }
}
