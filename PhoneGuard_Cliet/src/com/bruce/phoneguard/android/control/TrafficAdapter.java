package com.bruce.phoneguard.android.control;

import android.content.Context;
import android.net.TrafficStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.utils.FileUtils;

import java.util.List;

public class TrafficAdapter extends BaseAdapter {

    private List<AppInfo> infos;
    private Context context;
    private LayoutInflater inflater;

    public TrafficAdapter(List<AppInfo> infos, Context context, LayoutInflater inflater) {
        this.infos = infos;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return infos.get(position).getUid();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_traffic_info, null);
            holder.imgView = (ImageView) v.findViewById(R.id.iv_app_icon);
            holder.titleTxt = (TextView) v.findViewById(R.id.tv_app_name);
            holder.trafficTxt = (TextView) v.findViewById(R.id.tv_traffic_size);
            holder.uploadTxt = (TextView) v.findViewById(R.id.tv_upload);
            holder.downloadTxt = (TextView) v.findViewById(R.id.tv_download);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        AppInfo info = infos.get(position);
        long tx = TrafficStats.getUidTxBytes(info.getUid());
        long rx = TrafficStats.getUidRxBytes(info.getUid());
        holder.imgView.setImageDrawable(info.getIcon());
        holder.titleTxt.setText(info.getName());
        holder.trafficTxt.setText(FileUtils.getFileSize((tx + rx)));
        holder.uploadTxt.setText(FileUtils.getFileSize(tx));
        holder.downloadTxt.setText(FileUtils.getFileSize(rx));
        return v;
    }

    public class ViewHolder {
        public ImageView imgView;
        public TextView titleTxt;
        public TextView trafficTxt;
        public TextView uploadTxt;
        public TextView downloadTxt;
    }

}
