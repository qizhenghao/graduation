package com.bruce.phoneguard.android.fragment.traffic;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.control.TaskManagerAdapter;
import com.bruce.phoneguard.android.control.TrafficAdapter;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.model.TaskInfo;
import com.bruce.phoneguard.android.model.parser.AppInfoParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2015/3/14.
 */
public class TrafficSortFragment extends BaseFragment{
    private View mContentView;
    private ListView listView;
    private View loadingView;
    private TrafficAdapter adapter;
    private List<AppInfo> appInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_lv, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        listView = (ListView) mContentView.findViewById(R.id.lv);
        loadingView = mContentView.findViewById(R.id.ll_loading);
    }

    @Override
    protected void initData() {
        if (appInfos == null) {
            appInfos = new ArrayList<AppInfo>();
        }
        adapter = new TrafficAdapter(appInfos, getActivity(), getActivity().getLayoutInflater());
        listView.setAdapter(adapter);
        fillData();
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getItemAtPosition(position);
                if (obj != null && obj instanceof AppInfo) {
                    AppInfo info = (AppInfo) obj;
                    TrafficAdapter.ViewHolder holder = (TrafficAdapter.ViewHolder) view.getTag();
//                    if (info.isChecked()) {
//                        holder.aSwitch.setChecked(false);
//                        info.setChecked(false);
//                    } else {
//                        holder.aSwitch.setChecked(true);
//                        info.setChecked(true);
//                    }
                }
            }
        });
    }


    private void fillData() {
        loadingView.setVisibility(View.VISIBLE);
        new Thread() {
            public void run() {
                appInfos = AppInfoParser.getAppInfos(mContext);
                Object[] infoArr = appInfos.toArray();
                Arrays.sort(infoArr, new Comparator<Object>() {
                    @Override
                    public int compare(Object lhs, Object rhs) {
                        long left = TrafficStats.getUidTxBytes(((AppInfo) lhs).getUid()) + TrafficStats.getUidRxBytes(((AppInfo) lhs).getUid());
                        long right = TrafficStats.getUidTxBytes(((AppInfo) rhs).getUid()) + TrafficStats.getUidRxBytes(((AppInfo) rhs).getUid());
                        if (left >= right) {
                            return 1;
                        }
                        return -1;
                    }
                });
                appInfos.clear();
                for (int i=infoArr.length-1;i>=0;i--) {
                    appInfos.add((AppInfo) infoArr[i]);
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new TrafficAdapter(appInfos, mContext, getActivity().getLayoutInflater()));
                        loadingView.setVisibility(View.INVISIBLE);
                    }
                });
            };
        }.start();
    }

}
