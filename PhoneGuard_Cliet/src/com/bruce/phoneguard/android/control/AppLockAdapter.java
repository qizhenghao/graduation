package com.bruce.phoneguard.android.control;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.model.AppInfo;

import java.util.List;

public class AppLockAdapter extends BaseAdapter {

    private List<AppInfo> appinfos;
    private Context mContext;
    private boolean isLock;

    public AppLockAdapter(List<AppInfo> appInfos, Context context, boolean isLock) {
        this.appinfos = appInfos;
        this.mContext = context;
        this.isLock =isLock;
    }

		public int getCount() {
			return appinfos.size();
        }

		public Object getItem(int position) {
			return appinfos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View v, ViewGroup parent) {
            ViewHolder holder = null;
			if (v == null) {
                holder = new ViewHolder();
				v = View.inflate(mContext, R.layout.lock_app_item, null);
                holder.iconIV = (ImageView) v.findViewById(R.id.iv_app_icon);
                holder.nameTV = (TextView) v.findViewById(R.id.tv_app_name);
                holder.lockIV = (ImageView) v.findViewById(R.id.iv_app_lock_status);
                holder.packNameTV = (TextView) v.findViewById(R.id.tv_app_packname);
                v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}
			AppInfo info = appinfos.get(position);
            holder.iconIV.setImageDrawable(info.getIcon());
            holder.nameTV.setText(info.getName());
            if(isLock){
                holder.lockIV.setImageResource(R.drawable.strongbox_app_lock_ic_locked);
            }else{
                holder.lockIV.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
            }
            holder.packNameTV.setText(info.getPackname());
            return v;
		}

    public class ViewHolder {
        public ImageView iconIV;
        public TextView nameTV;
        public ImageView lockIV;
        public TextView packNameTV;
    }

	}