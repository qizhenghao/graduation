package com.bruce.phoneguard.android.fragment;

import android.app.Activity;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.activity.*;
import com.bruce.phoneguard.android.config.FescoConfig;
import com.bruce.phoneguard.android.model.GridItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @description 基本Fragment，其他Fraagmen要继承
 * 
 * @author qizhenghao
 * 
 *         data: 2014年12月27日10:55:40
 * */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;

	// /**
	// * 设置公用标题
	// *
	// * @param str
	// */
	// protected void setHeaderTitle(int str) {
	// View view = getView().findViewById(R.id.titleTv);
	// if (view instanceof TextView) {
	// ((TextView) view).setText(str);
	// }
	// }
	//
	// /**
	// * 设置公用标题
	// *
	// * @param str
	// */
	// protected void setHeaderTitle(String str) {
	// View view = getView().findViewById(R.id.titleTv);
	// if (view instanceof TextView) {
	// ((TextView) view).setText(str);
	// }
	// }


	/**
	 * 跳转到点击的activity
	 * 
	 * @author Qi Zhenghao
	 */
	public void toClickActivity(GridItem item) {
		switch (item.getId()) {
		case FescoConfig.SOFTWARE_MANA:
			Intent toAppMana = new Intent(getActivity(), AppManagerActivity.class);
			getActivity().startActivity(toAppMana);
			break;
		case FescoConfig.TASK_MANA:
			getActivity().startActivity(new Intent(getActivity(), TaskManagerActivity.class));
			break;
		case FescoConfig.GARBAGE_CLEAN:
			getActivity().startActivity(new Intent(getActivity(), CleanCacheActivity.class));
			break;
		case FescoConfig.ANTI_VIRUS:
			getActivity().startActivity(new Intent(getActivity(), AntiVirusActivity.class));
			break;
		case FescoConfig.TRAFFIC_MANAGE:
			Intent toTraffic = new Intent(getActivity(), TrafficManagerActivity.class);
			getActivity().startActivity(toTraffic);
			break;
		case FescoConfig.ADD_NEW:
			Intent toAddFreq = new Intent(getActivity(), AddFreqActivity.class);
			getActivity().startActivity(toAddFreq);
			break;
		case FescoConfig.AGAINST_THEFT:
			Intent toGesture = new Intent(getActivity(), UnlockGesturePasswordDialogActivity.class);
			getActivity().startActivity(toGesture);
            getActivity().overridePendingTransition(R.anim.open_from_bottom, 0);
			break;
		case FescoConfig.TEL_ENQUIRIES:
			Intent toTelEnquires = new Intent(getActivity(), NumberAddressQueryActivity.class);
			getActivity().startActivity(toTelEnquires);
			break;
		case FescoConfig.DATA_BAKEUP:
			Intent toDataBakeup = new Intent(getActivity(), DataBakeupActivity.class);
			getActivity().startActivity(toDataBakeup);
			break;
		case FescoConfig.PROGRAM_LOCK:
			Intent toAppLock = new Intent(getActivity(), AppLockActivity.class);
			getActivity().startActivity(toAppLock);
			break;
		case FescoConfig.BIG_FILE_MANAGE:
			Intent toBigFileManage = new Intent(getActivity(), BigFileManageActivity.class);
			getActivity().startActivity(toBigFileManage);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		init();
	}

	/**全部初始化，包括view、data、listener*/
	public void init() {
		initView();
		initData();
		initListener();
	}

	/** 初始化view，activity创建时调用 */
	abstract protected void initView();

	/** 初始化data，activity创建时调用，在initView方法之后调用 */
	abstract protected void initData();

	/** 设置监听事件，activity创建时调用，在initView方法之后调用 */
	abstract protected void initListener();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
    }
}
