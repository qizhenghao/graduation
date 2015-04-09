package com.bruce.phoneguard.android.fragment;

import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.bruce.phoneguard.android.InitData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.activity.MenuActivity;
import com.bruce.phoneguard.android.control.GridViewAdapter;
import com.bruce.phoneguard.android.model.GridItem;
import com.special.ResideMenu.ResideMenu;

/**
 * @author qizhenghao Date: 2014年12月27日10:00:41
 * @version 1.0
 * 
 * @description: 展示常用功能，支持自定义增删
 */
public class FrequentlyFragment extends BaseFragment {

	private View parentView;
	private ResideMenu resideMenu;
	private MenuActivity menuActivity;
	private GridView gridView_menu = null;

	private LayoutInflater inflater;
	private GridViewAdapter adapter;
	private List<GridItem> frequetList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.functionfragment, container, false);
		this.inflater = inflater;
		return parentView;
	}

	@Override
	protected void initView() {
		menuActivity = (MenuActivity) getActivity();
		resideMenu = menuActivity.getResideMenu();

		// add gesture operation's ignored views
//		FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
//		resideMenu.addIgnoredView(ignored_view);

		gridView_menu = (GridView) parentView.findViewById(R.id.function_gridView);
		gridView_menu.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	protected void initData() {
		frequetList = InitData.getInstance().getFrequentlyList(getActivity());
		adapter = new GridViewAdapter(inflater, frequetList);
		gridView_menu.setAdapter(adapter);
	}

	@Override
	protected void initListener() {
//		parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//			}
//		});

		gridView_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				toClickActivity(frequetList.get(arg2));
			}

		});
	}
}
