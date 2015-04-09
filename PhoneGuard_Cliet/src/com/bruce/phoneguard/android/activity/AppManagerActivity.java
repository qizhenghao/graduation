package com.bruce.phoneguard.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.control.ViewPagerFramentAdapter;
import com.bruce.phoneguard.android.fragment.appmanage.AdviceAppFragment;
import com.bruce.phoneguard.android.fragment.appmanage.SystemAppFragment;
import com.bruce.phoneguard.android.fragment.appmanage.UserAppFragment;
import com.bruce.phoneguard.android.fragment.traffic.PlanSettingFragment;
import com.bruce.phoneguard.android.fragment.traffic.TrafficSortFragment;
import com.bruce.phoneguard.android.fragment.traffic.UsePlanFragment;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.model.parser.AppInfoParser;
import com.bruce.phoneguard.android.ui.TabViewPagerIndicator;
import com.bruce.phoneguard.android.utils.DensityUtil;
import com.stericson.RootTools.RootTools;

public class AppManagerActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = "AppManagerActivity";

    private ViewPager viewPager;
    private ViewPagerFramentAdapter viewPagerAdapter;
    private TabViewPagerIndicator pageIndicator;
    private List<Fragment> fragmentList;
    private AdviceAppFragment adviceAppFragment;
    private UserAppFragment userAppFragment;
    private SystemAppFragment systemAppFragment;

	private TextView tv_avail_rom;
	private TextView tv_avail_sd;
    private View loadingView;
	/**
	 * 使用的应用程序信息集合
	 */
	private List<AppInfo> infos;
	/**
	 * 用户程序集合
	 */
	private List<AppInfo> userAppInfos;

	/**
	 * 系统程序集合
	 */
	private List<AppInfo> systemAppInfos;

	/**
	 * 悬浮窗体
	 */
	private PopupWindow popupwindow;

	private UninstallReceiver receiver;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		initView();
		initData();
		setListener();

		receiver = new UninstallReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);

	}

	private void setListener() {


	}

	/** 填充userAppInfos、 systemAppInfos 列表数据 */
	private void fillData() {
        loadingView.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				infos = AppInfoParser.getAppInfos(AppManagerActivity.this);
				for (AppInfo info : infos) {
					if (info.isUserApp()) {
						userAppInfos.add(info);
					} else {
						systemAppInfos.add(info);
					}
				}
				runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setVisibility(View.INVISIBLE);
                        refreshFragment();
                    }
                });
			};
		}.start();
	}

    private void refreshFragment() {
        adviceAppFragment.onRefresh();
        systemAppFragment.onRefresh();
        userAppFragment.onRefresh();
    }

    private void dismissPopupWindow() {
		if (popupwindow != null && popupwindow.isShowing()) {
			popupwindow.dismiss();
			popupwindow = null;
		}
	}




	@Override
	protected void onDestroy() {
		dismissPopupWindow();
		unregisterReceiver(receiver);
		receiver = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.ll_share:
//			Log.i(TAG, "分享：" + clickedAppInfo.getPackname());
//			shareApplication();
//			break;
//		case R.id.ll_uninstall:
//			Log.i(TAG, "卸载：" + clickedAppInfo.getPackname());
////			uninstallApplication();
//			break;
//		case R.id.ll_start:
//			Log.i(TAG, "开启：" + clickedAppInfo.getPackname());
//			startApplication();
//			break;
//		case R.id.ll_setting:
//			Log.i(TAG, "设置：" + clickedAppInfo.getPackname());
//			viewAppDetail();
//			break;
		}
		dismissPopupWindow();
	}



	private class UninstallReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String info = intent.getData().toString();
			System.out.println(info);
			initData();
		}
	}

	@Override
	public void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageIndicator = (TabViewPagerIndicator) findViewById(R.id.tab_viewpager_indicator);
        pageIndicator.setViewIds(new int[]{R.id.line_viewpager_indicator,
                R.id.tab_one, R.id.tab_two, R.id.tab_three
        });
		tv_avail_rom = (TextView) findViewById(R.id.tv_avail_rom);
		tv_avail_sd = (TextView) findViewById(R.id.tv_avail_sd);

        tv_avail_rom.setVisibility(View.GONE);
        tv_avail_sd.setVisibility(View.GONE);

        loadingView = findViewById(R.id.ll_loading);
	}

	// 填充数据的业务方法
	@Override
	public void initData() {

		mActionBar.setTitle("软件管理");
		mActionBar.setIcon(R.drawable.selector_back_imagview);
		long avail_sd = Environment.getExternalStorageDirectory()
				.getFreeSpace();
		long avail_rom = Environment.getDataDirectory().getFreeSpace();
		String str_avail_sd = Formatter.formatFileSize(this, avail_sd);
		String str_avail_rom = Formatter.formatFileSize(this, avail_rom);
		tv_avail_rom.setText("剩余手机内部：" + str_avail_rom);
		tv_avail_sd.setText("剩余SD卡：" + str_avail_sd);

        viewPager.setOffscreenPageLimit(3);

        userAppInfos = new ArrayList<AppInfo>();
        systemAppInfos = new ArrayList<AppInfo>();

        fragmentList = new ArrayList<Fragment>();
        adviceAppFragment = new AdviceAppFragment(userAppInfos);
        userAppFragment = new UserAppFragment(userAppInfos);
        systemAppFragment = new SystemAppFragment(systemAppInfos);
        fragmentList.add(adviceAppFragment);
        fragmentList.add(userAppFragment);
        fragmentList.add(systemAppFragment);

        viewPagerAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        pageIndicator.setViewPager(viewPager);

		fillData();
	}
}
