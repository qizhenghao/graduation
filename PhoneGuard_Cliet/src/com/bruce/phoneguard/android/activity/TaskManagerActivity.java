package com.bruce.phoneguard.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.control.ViewPagerFramentAdapter;
import com.bruce.phoneguard.android.fragment.task.AdviceTaskFragment;
import com.bruce.phoneguard.android.fragment.task.SystemTaskFragment;
import com.bruce.phoneguard.android.fragment.task.UserTaskFragment;
import com.bruce.phoneguard.android.model.TaskInfo;
import com.bruce.phoneguard.android.model.parser.TaskInfoParser;
import com.bruce.phoneguard.android.ui.TabViewPagerIndicator;
import com.bruce.phoneguard.android.utils.SystemInfoUtils;

public class TaskManagerActivity extends BaseActivity {
    private TextView tv_running_prcesscount;
    private TextView tv_ram_info;
    private TextView clean_tv;

    private ViewPager viewPager;
    private ViewPagerFramentAdapter viewPagerAdapter;
    private TabViewPagerIndicator pageIndicator;
    private List<Fragment> fragmentList;
    private AdviceTaskFragment adviceTaskFragment;
    private UserTaskFragment userTaskFragment;
    private SystemTaskFragment systemTaskFragment;
    /**
     * 所有进程信息的集合
     */
    private List<TaskInfo> infos;

    private List<TaskInfo> adviceTaskInfos;
    private List<TaskInfo> userTaskInfos;
    private List<TaskInfo> systemTaskInfos;
    private LinearLayout ll_loading;
    /**
     * 正在运行的进程数量
     */
    private int runningProcessCount;

    /**
     * 总的可用内存
     */
    private long totalAvailMem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        clean_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killProcess();
            }
        });
    }

    /**
     * 填充userTaskInfos 、systemTaskInfos列表数据
     */
    private void fillData() {
        ll_loading.setVisibility(View.VISIBLE);
        new Thread() {
            public void run() {
                infos = TaskInfoParser
                        .getRunningTaskInfos(TaskManagerActivity.this);
                for (TaskInfo info : infos) {
                    if (info.isUsertask()) {
                        userTaskInfos.add(info);
                        if (info.getPackname() != SysApplication.mPackageName) {
                            adviceTaskInfos.add(info);
                        }
                    } else {
                        systemTaskInfos.add(info);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll_loading.setVisibility(View.INVISIBLE);
                        viewPager.setVisibility(View.VISIBLE);
                        refreshFragments();
                    }
                });
            }

            ;
        }.start();

    }

    private void refreshFragments() {
        adviceTaskFragment.onRefresh();
        userTaskFragment.onRefresh();
        systemTaskFragment.onRefresh();
    }

    @Override
    protected void onStart() {
        if (adviceTaskFragment != null) {
            // 通知界面刷新
            refreshFragments();
        }
        super.onStart();
    }

    /**
     * 杀死后台进程
     */
    public void killProcess() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int count = 0;
        long savemem = 0;
        List<TaskInfo> killedTaskInfos = new ArrayList<TaskInfo>();

        // 在遍历集合的时候 不可以修改集合的大小
        for (TaskInfo info : adviceTaskInfos) {
            if (info.isChecked()) {
                count++;
                savemem += info.getMemsize();
                am.killBackgroundProcesses(info.getPackname());
                killedTaskInfos.add(info);
            }
        }

        for (TaskInfo info : userTaskInfos) {
            if (info.isChecked()) {
                count++;
                savemem += info.getMemsize();
                am.killBackgroundProcesses(info.getPackname());
                killedTaskInfos.add(info);
            }
        }

        for (TaskInfo info : systemTaskInfos) {
            if (info.isChecked()) {
                count++;
                savemem += info.getMemsize();
                am.killBackgroundProcesses(info.getPackname());
                killedTaskInfos.add(info);
            }
        }
        for (TaskInfo info : killedTaskInfos) {
            if (info.isUsertask()) {
                userTaskInfos.remove(info);
                adviceTaskInfos.remove(info);
            } else {
                systemTaskInfos.remove(info);
            }
        }
        runningProcessCount -= count;
        totalAvailMem += savemem;
        // 更新标题
        tv_running_prcesscount.setText("进程：" + runningProcessCount);
        tv_ram_info
                .setText("内存："
                        + Formatter.formatFileSize(this, totalAvailMem)
                        + "/"
                        + Formatter.formatFileSize(this,
                        SystemInfoUtils.getTotalMem()));
        Toast.makeText(
                this,
                "杀死了" + count + "个进程,释放了"
                        + Formatter.formatFileSize(this, savemem) + "的内存", Toast.LENGTH_SHORT)
                .show();
        // 刷新界面
        refreshFragments();
    }

    @Override
    public void initView() {
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        tv_running_prcesscount = (TextView) findViewById(R.id.tv_running_prcesscount);
        tv_ram_info = (TextView) findViewById(R.id.tv_ram_info);
        clean_tv = (TextView) findViewById(R.id.clean_tv);
        viewPager = (ViewPager) findViewById(R.id.discover_view_pager);
        pageIndicator = (TabViewPagerIndicator) findViewById(R.id.discover_tab_page_indicator);
        pageIndicator.setViewIds(new int[]{R.id.discover_tab_line_layout,
                R.id.discover_tab_one, R.id.discover_tab_two, R.id.discover_tab_three
        });
    }

    @Override
    public void initData() {
        mActionBar.setTitle("进程管理");
        mActionBar.setIcon(R.drawable.selector_back_imagview);

        userTaskInfos = new ArrayList<TaskInfo>();
        adviceTaskInfos = new ArrayList<TaskInfo>();
        systemTaskInfos = new ArrayList<TaskInfo>();

        // 设置内存空间的大小 和 正在运行的进程的数量
        totalAvailMem = SystemInfoUtils.getAvailMem(this);
        long totalMem = SystemInfoUtils.getTotalMem();
        tv_ram_info.setText("内存："
                + Formatter.formatFileSize(this, totalAvailMem) + "/"
                + Formatter.formatFileSize(this, totalMem));
        runningProcessCount = SystemInfoUtils.getRunningProcessCount(this);
        tv_running_prcesscount.setText("进程：" + runningProcessCount);

        viewPager.setOffscreenPageLimit(3);

        fragmentList = new ArrayList<Fragment>();
        adviceTaskFragment = new AdviceTaskFragment(adviceTaskInfos);
        userTaskFragment = new UserTaskFragment(userTaskInfos);
        systemTaskFragment = new SystemTaskFragment(systemTaskInfos);
        fragmentList.add(adviceTaskFragment);
        fragmentList.add(userTaskFragment);
        fragmentList.add(systemTaskFragment);

        viewPagerAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        pageIndicator.setViewPager(viewPager);
        fillData();
    }
}
