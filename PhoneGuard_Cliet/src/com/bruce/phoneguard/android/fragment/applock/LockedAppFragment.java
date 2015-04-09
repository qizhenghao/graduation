package com.bruce.phoneguard.android.fragment.applock;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.*;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.activity.AppLockActivity;
import com.bruce.phoneguard.android.control.AppLockAdapter;
import com.bruce.phoneguard.android.dao.AppLockDao;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/11.
 */
public class LockedAppFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View mContentView;
    private ListView listView;
    private AppLockAdapter adapter;
    private List<AppInfo> lockedApps;
    private AppLockActivity.OnRemoveDataListener onRemoveDataListener;

    public LockedAppFragment(List<AppInfo> appInfos, AppLockActivity.OnRemoveDataListener onRemoveDataListener) {
        this.lockedApps = appInfos;
        this.onRemoveDataListener = onRemoveDataListener;
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
        if (lockedApps == null) {
            lockedApps = new ArrayList<AppInfo>();
        }
        adapter = new AppLockAdapter(lockedApps, getActivity(), true);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TranslateAnimation ta = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                ta.setDuration(500);
                view.startAnimation(ta);
                ImageView iv = (ImageView) view.findViewById(R.id.iv_app_lock_status);

                // 传递当前要锁定程序的包名
                AppInfo info = lockedApps.get(position);
                String packname = info.getPackname();
                mContext.getContentResolver().delete(Uri.parse("content://com.bruce.applockprovider/delete"), null, new String[]{packname});
                onRemoveDataListener.onRemove(info, true);
                iv.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
            }
        });
    }


    @Override
    public void onRefresh() {
        if (adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
    }

    public void onRefresh(List<AppInfo> lockedApps) {
        this.lockedApps = lockedApps;
        adapter = new AppLockAdapter(lockedApps, mContext, true);
        listView.setAdapter(adapter);
    }
}