package com.bruce.phoneguard.android.fragment.task;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.control.TaskManagerAdapter;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.model.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/11.
 */
public class UserTaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private View mContentView;
    private ListView listView;
    private TaskManagerAdapter adapter;
    private List<TaskInfo> userTasks;
    public UserTaskFragment(List<TaskInfo> userTasks) {
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
            userTasks = new ArrayList<TaskInfo>();
        }
        adapter = new TaskManagerAdapter(userTasks, getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getItemAtPosition(position);
                if (obj != null && obj instanceof TaskInfo) {
                    TaskInfo info = (TaskInfo) obj;
                    if (info.getPackname().equals(SysApplication.mPackageName)) {
                        return;
                    }
                    TaskManagerAdapter.ViewHolder holder = (TaskManagerAdapter.ViewHolder) view.getTag();
                    if (info.isChecked()) {
                        holder.cb_status.setChecked(false);
                        info.setChecked(false);
                    } else {
                        holder.cb_status.setChecked(true);
                        info.setChecked(true);
                    }
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        if (adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
    }

    public void onRefresh(List<TaskInfo> userTasks) {
        this.userTasks = userTasks;
        adapter.notifyDataSetInvalidated();
    }


}