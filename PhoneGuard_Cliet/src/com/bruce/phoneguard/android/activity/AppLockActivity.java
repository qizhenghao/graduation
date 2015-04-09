package com.bruce.phoneguard.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import com.bruce.phoneguard.android.CacheData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.control.ViewPagerFramentAdapter;
import com.bruce.phoneguard.android.dao.AppLockDao;
import com.bruce.phoneguard.android.fragment.applock.LockedAppFragment;
import com.bruce.phoneguard.android.fragment.applock.UnlockAppFragment;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.ui.TabViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class AppLockActivity extends BaseActivity {

    private ViewPager viewPager;
    private ViewPagerFramentAdapter viewPagerAdapter;
    private TabViewPagerIndicator pageIndicator;
    private List<Fragment> fragmentList;
    private LockedAppFragment lockedAppFragment;
    private UnlockAppFragment unlockAppFragment;

	private List<AppInfo> unlockApps;
	private List<AppInfo> lockedApps;
	private AppLockDao dao;
	private LinearLayout loadingView;
	
	private List<String> lockPacks;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loadingView.setVisibility(View.INVISIBLE);
            refreshFragments();
		}

	};

    private void refreshFragments() {
        lockedAppFragment.onRefresh(lockedApps);
        unlockAppFragment.onRefresh(unlockApps);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_lock);
        initView();
        initData();
        setListener();
	}

    private void setListener() {
//        lv.setOnItemClickListener(new OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                TranslateAnimation ta = new TranslateAnimation(
//                        Animation.RELATIVE_TO_SELF, 0.0f,
//                        Animation.RELATIVE_TO_SELF, 0.5f,
//                        Animation.RELATIVE_TO_SELF, 0.0f,
//                        Animation.RELATIVE_TO_SELF, 0.0f);
//                ta.setDuration(500);
//                view.startAnimation(ta);
//                ImageView iv = (ImageView) view.findViewById(R.id.iv_app_lock_status);
//
//                // 传递当前要锁定程序的包名
//                AppInfo info = (AppInfo) lv.getItemAtPosition(position);
//                String packname = info.getPackname();
//                if(dao.find(packname)){
//                    // 移除这个条目
//                    //dao.delete(packname);
//                    getContentResolver().delete(Uri.parse("content://com.bruce.applockprovider/delete"), null, new String[]{packname});
//                    lockPacks.remove(packname);
//                    iv.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
//                }else{
//                    //dao.add(packname);
//                    lockPacks.add(packname);
//                    ContentValues values = new ContentValues();
//                    values.put("packname", packname);
//                    getContentResolver().insert(Uri.parse("content://com.bruce.applockprovider/insert"), values);
//                    iv.setImageResource(R.drawable.strongbox_app_lock_ic_locked);
//                }
//
//            }
//        });
    }

    @Override
    public void initView() {
        loadingView = (LinearLayout) this.findViewById(R.id.ll_app_manager_loading);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageIndicator = (TabViewPagerIndicator) findViewById(R.id.discover_tab_page_indicator);
        pageIndicator.setViewIds(new int[]{R.id.line1_viewpager_indicator,
                R.id.tab_one, R.id.tab_two});
    }

    @Override
    public void initData() {
        mActionBar.setTitle("程序加锁");
        mActionBar.setIcon(R.drawable.selector_back_imagview);
        dao = new AppLockDao(this);

        lockedApps = new ArrayList<AppInfo>();
        unlockApps = new ArrayList<AppInfo>();

        viewPager.setOffscreenPageLimit(2);

        fragmentList = new ArrayList<Fragment>();
        lockedAppFragment = new LockedAppFragment(lockedApps, onRemoveDataListener);
        unlockAppFragment = new UnlockAppFragment(unlockApps, onRemoveDataListener);
        fragmentList.add(lockedAppFragment);
        fragmentList.add(unlockAppFragment);

        viewPagerAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        pageIndicator.setViewPager(viewPager);

        fillData();
    }

    private void fillData() {
		loadingView.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {
                lockPacks = dao.getAllApps();
                unlockApps = CacheData.getUnlockdApps(lockPacks);
                lockedApps = CacheData.getLockedApps(lockPacks);
				handler.sendEmptyMessage(0);
			}
		}.start();

	}

    private OnRemoveDataListener onRemoveDataListener = new OnRemoveDataListener() {
        @Override
        public void onRemove(AppInfo appInfo, boolean isLock) {
             if (isLock) {
                 lockedApps.remove(appInfo);
                 unlockApps.add(appInfo);
             } else {
                 lockedApps.add(appInfo);
                 unlockApps.remove(appInfo);
             }
            refreshFragments();
        }
    };

    public interface OnRemoveDataListener {
        void onRemove(AppInfo appInfo, boolean isLock);
    }
}
