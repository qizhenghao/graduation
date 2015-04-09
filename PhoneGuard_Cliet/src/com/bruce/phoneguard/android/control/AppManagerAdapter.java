package com.bruce.phoneguard.android.control;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.model.AppInfo;

import java.util.List;

public class AppManagerAdapter extends BaseAdapter {

    private List<AppInfo> appInfos;
    private Context context;

    public AppManagerAdapter(List<AppInfo> taskInfos, Context context) {
        this.appInfos = taskInfos;
        this.context = context;
    }
    @Override
		public int getCount() {
			return appInfos.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AppInfo appInfo = appInfos.get(position);
			View view;
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(context,
						R.layout.item_app_manager, null);
				holder = new ViewHolder();
				holder.iv_app_icon = (ImageView) view
						.findViewById(R.id.iv_app_icon);
				holder.tv_app_name = (TextView) view
						.findViewById(R.id.tv_app_name);
				holder.tv_app_size = (TextView) view
						.findViewById(R.id.tv_app_size);
				holder.tv_app_location = (TextView) view
						.findViewById(R.id.tv_app_location);
				view.setTag(holder);
			}
			holder.iv_app_icon.setImageDrawable(appInfo.getIcon());
			holder.tv_app_name.setText(appInfo.getName());
			holder.tv_app_size.setText(Formatter.formatFileSize(
                    SysApplication.mContext, appInfo.getAppSize()));
			if (appInfo.isInRom()) {
				holder.tv_app_location.setText("手机内存");
			} else {
				holder.tv_app_location.setText("外部存储");
			}
			return view;
		}

		@Override
		public Object getItem(int position) {
			return appInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

    static class ViewHolder {
        ImageView iv_app_icon;
        TextView tv_app_name;
        TextView tv_app_size;
        TextView tv_app_location;
    }

}