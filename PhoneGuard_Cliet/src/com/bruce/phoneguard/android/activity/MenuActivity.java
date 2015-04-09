package com.bruce.phoneguard.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.fragment.CalendarFragment;
import com.bruce.phoneguard.android.fragment.FrequentlyFragment;
import com.bruce.phoneguard.android.fragment.ManagementFragment;
import com.bruce.phoneguard.android.fragment.ProfileFragment;
import com.bruce.phoneguard.android.fragment.SettingsFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements
		View.OnClickListener {

	private ResideMenu resideMenu;
	private MenuActivity mContext;
	private ResideMenuItem itemFrequently;
	private ResideMenuItem itemManagement;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		getActionBar().hide();
		mContext = this;
		setUpMenu();
		if (savedInstanceState == null){
			changeFragment(new FrequentlyFragment());
			setName(getResources().getString(R.string.frequent_function));
		}
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		// create menu items;
		itemFrequently = new ResideMenuItem(this, R.drawable.icon_frequently, getResources().getString(R.string.frequent_function));
		itemManagement = new ResideMenuItem(this, R.drawable.icon_manage, getResources().getString(R.string.management_function));
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile,
				R.string.personal_profile);
		itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
				"Calendar");
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				R.string.personal_setting);

		itemFrequently.setOnClickListener(this);
		itemManagement.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemCalendar.setOnClickListener(this);
		itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFrequently, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemManagement, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

		// You can disable a direction by setting ->
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == itemFrequently) {
			changeFragment(new FrequentlyFragment());
			setName(getResources().getString(R.string.frequent_function));
		} else if (view == itemManagement) {
			changeFragment(new ManagementFragment());
			setName(getResources().getString(R.string.management_function));
		} else if (view == itemProfile) {
			changeFragment(new ProfileFragment());
			setName(getResources().getString(R.string.personal_profile));
		} else if (view == itemCalendar) {
			changeFragment(new CalendarFragment());
		} else if (view == itemSettings) {
			changeFragment(new SettingsFragment());
			setName(getResources().getString(R.string.personal_setting));
		}

		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
//			Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT)
//					.show();
		}

		@Override
		public void closeMenu() {
//			Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT)
//					.show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	/** 设置title */
	private void setName(String titleString) {
		((TextView) findViewById(R.id.menu_title_txt)).setText(titleString);
	}
}
