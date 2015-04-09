package com.bruce.phoneguard.android.activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @description 基本Activity，其他的Activity要继承。
 * 
 * @author qizhenghao
 * date: 2014年12月27日16:02:51
 * @version 1.0
 * */

public abstract class BaseActivity extends SherlockFragmentActivity {
	private Toast mToast;
	protected ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setLogo(R.drawable.selector_back_imagview);
		SysApplication.getInstance().addActivity(this); // 添加Activity

	}

	/**
	 * 显示Toast
	 * 
	 * @param string
	 *            需要显示的文字
	 */
	public void showToast(String string) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), string,
					Toast.LENGTH_SHORT);
		}
		mToast.setText(string);
		mToast.show();
	}

	/**
	 * 显示Toast
	 * 
	 * @param resId
	 *            需要显示的文字String资源Id
	 */
	public void showToast(int resId) {
		showToast(getString(resId));
	}

	/**
	 * 设置公用标题
	 * 
	 * @param str
	 */
	protected void setHeaderTitle(int str) {
		View view = findViewById(R.id.titleTv);
		if (view instanceof TextView) {
			((TextView) view).setText(str);
		}
	}

	/**
	 * 设置公用标题
	 * 
	 * @param str
	 */
	protected void setHeaderTitle(String str) {
		View view = findViewById(R.id.titleTv);
		if (view instanceof TextView) {
			((TextView) view).setText(str);
		}
	}

	public abstract void initView();

	public abstract void initData();

	/** 默认退出 **/
	protected void defaultFinish() {
		super.finish();
	}

	/**
	 * 设置ActionBar的布局
	 * 
	 * @param layoutId
	 *            布局Id
	 * @param title
	 * @author qizhenghao
	 * */
	@SuppressWarnings("deprecation")
	public void setActionBarLayout(int layoutId, String title) {
		if (null != mActionBar) {
			mActionBar.setDisplayShowHomeEnabled(false);
			mActionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			((TextView) v.findViewById(R.id.header_title_txt)).setText(title);
			LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			mActionBar.setCustomView(v, layout);
		}
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
