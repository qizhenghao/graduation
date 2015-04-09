package com.bruce.phoneguard.android.fragment.appmanage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.control.AppManagerAdapter;
import com.bruce.phoneguard.android.control.TaskManagerAdapter;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.model.TaskInfo;
import com.bruce.phoneguard.android.utils.DensityUtil;
import com.bruce.phoneguard.android.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/11.
 */
public class UserAppFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private View mContentView;
    private ListView listView;
    private AppManagerAdapter adapter;
    private List<AppInfo> userTasks;

    private LinearLayout ll_start;
    private LinearLayout ll_share;
    private LinearLayout ll_uninstall;
    private LinearLayout ll_setting;

    /**
     * 悬浮窗体
     */
    private PopupWindow popupwindow;
    private AppInfo clickedAppInfo;

    public UserAppFragment(List<AppInfo> userTasks) {
        this.userTasks = userTasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_lv, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        listView = (ListView) mContentView.findViewById(R.id.lv);
    }

    @Override
    protected void initData() {
        if (userTasks == null) {
            userTasks = new ArrayList<AppInfo>();
        }
        adapter = new AppManagerAdapter(userTasks, getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getItemAtPosition(position);
                if (obj != null && obj instanceof AppInfo) {
                    clickedAppInfo = (AppInfo) obj;
                    View contentView = View.inflate(mContext,
                            R.layout.popup_item, null);
                    ll_uninstall = (LinearLayout) contentView
                            .findViewById(R.id.ll_uninstall);
                    ll_start = (LinearLayout) contentView
                            .findViewById(R.id.ll_start);
                    ll_share = (LinearLayout) contentView
                            .findViewById(R.id.ll_share);
                    ll_setting = (LinearLayout) contentView
                            .findViewById(R.id.ll_setting);
                    ll_share.setOnClickListener(clickListener);
                    ll_start.setOnClickListener(clickListener);
                    ll_uninstall.setOnClickListener(clickListener);
                    ll_setting.setOnClickListener(clickListener);
                    dismissPopupWindow();
                    popupwindow = new PopupWindow(contentView, -2, -2);
                    // 动画播放有一个前提条件： 窗体必须要有背景资源。 如果窗体没有背景，动画就播放不出来。
                    popupwindow.setBackgroundDrawable(new ColorDrawable(
                            Color.TRANSPARENT));
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    // 显示弹出气泡效果。
                    // 在代码里面所有的长度单位都是 像素。
                    int dip = 60;
                    int px = DensityUtil.dip2px(mContext, dip);
                    System.out.println(px);
                    // 把dip转化成 像素
                    popupwindow.showAtLocation(parent, Gravity.LEFT
                            + Gravity.TOP, px, location[1]);
                    ScaleAnimation sa = new ScaleAnimation(0.5f, 1.0f, 0.5f,
                            1.0f, Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(200);
                    AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
                    aa.setDuration(200);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(aa);
                    set.addAnimation(sa);
                    contentView.startAnimation(set);
                }

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                dismissPopupWindow();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    @Override
    public void onRefresh() {
        if (adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
    }

    public void onRefresh(List<AppInfo> userTasks) {
        this.userTasks = userTasks;
        adapter.notifyDataSetInvalidated();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_share:
                    SystemInfoUtils.shareApp(mContext, clickedAppInfo.getPackname(), clickedAppInfo.getName());
                    break;
                case R.id.ll_uninstall:
                    SystemInfoUtils.uninstallUserApp(mContext, clickedAppInfo.getPackname());
                    break;
                case R.id.ll_start:
                    SystemInfoUtils.startApp(mContext, clickedAppInfo.getPackname());
                    break;
                case R.id.ll_setting:
                    SystemInfoUtils.viewAppDetail(mContext, clickedAppInfo.getPackname());
                    break;
            }
            dismissPopupWindow();
        }
    };

    private void dismissPopupWindow() {
        if (popupwindow != null && popupwindow.isShowing()) {
            popupwindow.dismiss();
            popupwindow = null;
        }
    }

}