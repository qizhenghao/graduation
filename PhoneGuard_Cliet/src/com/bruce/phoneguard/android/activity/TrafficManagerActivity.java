package com.bruce.phoneguard.android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.bruce.phoneguard.android.R;
import android.os.Bundle;
import com.bruce.phoneguard.android.control.ViewPagerFramentAdapter;
import com.bruce.phoneguard.android.fragment.traffic.PlanSettingFragment;
import com.bruce.phoneguard.android.fragment.traffic.TrafficSortFragment;
import com.bruce.phoneguard.android.fragment.traffic.UsePlanFragment;
import com.bruce.phoneguard.android.ui.TabViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class TrafficManagerActivity extends BaseActivity {

    private ViewPager viewPager;
    private ViewPagerFramentAdapter viewPagerAdapter;
    private TabViewPagerIndicator pageIndicator;
    private List<Fragment> fragmentList;
    private UsePlanFragment usePlanFragment;
    private TrafficSortFragment trafficSortFragment;
    private PlanSettingFragment planSettingFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_manager);
		initView();
		initData();
	}

    @Override
	public void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageIndicator = (TabViewPagerIndicator) findViewById(R.id.tab_viewpager_indicator);
        pageIndicator.setViewIds(new int[]{R.id.line_viewpager_indicator,
                R.id.tab_one, R.id.tab_two, R.id.tab_three
        });
	}

	@Override
	public void initData() {
		mActionBar.setTitle("流量管理");
		mActionBar.setIcon(R.drawable.selector_back_imagview);

        viewPager.setOffscreenPageLimit(3);

        fragmentList = new ArrayList<Fragment>();
        usePlanFragment = new UsePlanFragment();
        trafficSortFragment = new TrafficSortFragment();
        planSettingFragment = new PlanSettingFragment();
        fragmentList.add(usePlanFragment);
        fragmentList.add(trafficSortFragment);
        fragmentList.add(planSettingFragment);

        viewPagerAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        pageIndicator.setViewPager(viewPager);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
