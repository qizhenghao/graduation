package com.bruce.phoneguard.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.*;
import com.bruce.phoneguard.android.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import com.bruce.phoneguard.android.activity.UnlockGesturePasswordDialogActivity;
import com.bruce.phoneguard.android.services.WatchDogService;

/**
 * 设置
 * 
 * @author qizhenghao date：2015年01月02日19:43:36
 * 
 */
public class SettingsFragment extends BaseFragment implements OnItemClickListener {

	private View parentView = null;
	private ListView listView_1, listView_2, listView_3;
	private ArrayList<Map<String, String>> listData, listData3;
    private List listData2;

    private SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.settings, container, false);

		return parentView;
	}

	@Override
	protected void initView() {
		listView_1 = (ListView) parentView.findViewById(R.id.listview_1);
		listView_2 = (ListView) parentView.findViewById(R.id.listview_2);
		listView_3 = (ListView) parentView.findViewById(R.id.listview_3);
	}

	@Override
	protected void initData() {
        sp = getActivity().getSharedPreferences("CONFIG", mContext.MODE_PRIVATE);

		listView_1.setAdapter(getSimpleAdapter_1());
		listView_2.setAdapter(getSimpleAdapter_2());
		listView_3.setAdapter(getSimpleAdapter_3());

		setListViewHeightBasedOnChildren(listView_1);
//		setListViewHeightBasedOnChildren(listView_2);
		setListViewHeightBasedOnChildren(listView_3);


	}

	@Override
	protected void initListener() {
		listView_1.setOnItemClickListener(this);
		listView_2.setOnItemClickListener(this);
		listView_3.setOnItemClickListener(this);
	}

	/**
	 * 设置第一列数据
	 */
	private SimpleAdapter getSimpleAdapter_1() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "账户设置");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "个人资料");
		listData.add(map);

		return new SimpleAdapter(getActivity(), listData, R.layout.setting_list_item,
				new String[] { "text" }, new int[] { R.id.tv_list_item });

	}

	/**
	 * 设置第二列数据
	 */
	private SettingAdapter getSimpleAdapter_2() {
		listData2 = new ArrayList<SettingModel>();

//		Map<String, String> map = new HashMap<String, String>();
        SettingModel model = new SettingModel();
        model.isOn = sp.getBoolean("ISLOCKSERVICEOPEN", false);
        model.name = "程序锁服务";
        listData2.add(model);
        SettingAdapter adapter = new SettingAdapter(mActivity.getLayoutInflater(), listData2);
        return adapter;
//        map.put("isOn", sp.getBoolean("ISLOCKSERVICEOPEN", false));
//        map.put("name", "程序锁服务");
//        listData2.add(map);

//
//		map = new HashMap<String, String>();
//		map.put("text", "会员介绍");
//		listData2.add(map);
//
//		map = new HashMap<String, String>();
//		map.put("text", "意见反馈");
//		listData2.add(map);
//
//		map = new HashMap<String, String>();
//		map.put("text", "小嗨帮助");
//		listData2.add(map);
//
//		return new SimpleAdapter(getActivity(), listData2, R.layout.setting_list_item,
//				new String[] { "text" }, new int[] { R.id.tv_list_item });


	}

	/**
	 * 设置第三列数据
	 */
	private SimpleAdapter getSimpleAdapter_3() {
		listData3 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "注销登录");
		listData3.add(map);

		return new SimpleAdapter(getActivity(), listData3, R.layout.setting_list_item1,
				new String[] { "text" }, new int[] { R.id.tv_list_item });

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (parent == listView_1) {
			Map<String, String> map = listData.get(position);
			Toast.makeText(getActivity(), map.get("text"), Toast.LENGTH_SHORT).show();
			if (position == 1) {
				// startActivity(new
				// Intent(getActivity(),PersonInfoActivity.class));
			}
		} else if (parent == listView_2) {
//			Map<String, String> map = listData2.get(position);
//			if (position == 0) {
//				// 屏蔽发现
//			} else if (position == 1) {
//				// 会员介绍
//			} else if (position == 2) {
//				// 意见反馈
//			} else {
//				// 小嗨帮助
//				// startActivity(AboutActivity.class);
//			}
//			Toast.makeText(getActivity(), map.get("text"), 1).show();
		} else if (parent == listView_3) {
			// mApplication.getHiBang().clearAccessToken();
			// mApplication.clearActivity();
		}
	}

	/***
	 * 动态设置listview的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}


    public class SettingModel {
        public String name = "";
        public boolean isOn;
    }


    public class SettingAdapter extends BaseAdapter {
        private List<SettingModel> data = null;
        private LayoutInflater inflater;

        public SettingAdapter(LayoutInflater inflater, List data) {
            this.data = data;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            ViewHolder holder = null;
            if (v == null) {
                holder = new ViewHolder();
                v = inflater.inflate(R.layout.setting_list_check_item, null);
                holder.aSwitch = (Switch) v.findViewById(R.id.setting_list_item_switch);
                holder.txt_name = (TextView) v.findViewById(R.id.tv_list_item);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.aSwitch.setChecked(data.get(position).isOn);
            holder.txt_name.setText(data.get(position).name);

            holder.aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        SharedPreferences.Editor editor = sp.edit();
                        boolean isOn = sp.getBoolean("ISLOCKSERVICEOPEN", false);
                        if (isOn) {
                            mActivity.stopService(new Intent(mActivity, WatchDogService.class));
                        } else {
                            mActivity.startService(new Intent(mActivity, WatchDogService.class));
                        }
                        editor.putBoolean("ISLOCKSERVICEOPEN", !isOn).commit();
                    }
            });
            return v;
        }

        class ViewHolder {
            public TextView txt_name;
            public Switch aSwitch;
        }
    }

}
