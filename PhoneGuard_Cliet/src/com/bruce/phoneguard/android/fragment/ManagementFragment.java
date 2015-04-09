package com.bruce.phoneguard.android.fragment;

import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.bruce.phoneguard.android.InitData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.control.GridViewAdapter;
import com.bruce.phoneguard.android.model.GridItem;

/**
 * @author qizhenghao Date: 2014年12月27日19:52:51
 * @version 1.0
 * 
 * @description: 展示所有功能
 */
public class ManagementFragment extends BaseFragment {

	private View parentView;
	private GridView gridView_menu = null;

	private LayoutInflater inflater;
	private GridViewAdapter adapter;
	private List<GridItem> managementList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.functionfragment, container, false);
		this.inflater = inflater;
		return parentView;
	}

	@Override
	protected void initView() {
		gridView_menu = (GridView) parentView.findViewById(R.id.function_gridView);
		gridView_menu.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	protected void initData() {
		managementList = InitData.getInstance().getManamentList();
		adapter = new GridViewAdapter(inflater, managementList);
		gridView_menu.setAdapter(adapter);
	}

	@Override
	protected void initListener() {
		gridView_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				toClickActivity(managementList.get(arg2));
			}

		});
	}
}
