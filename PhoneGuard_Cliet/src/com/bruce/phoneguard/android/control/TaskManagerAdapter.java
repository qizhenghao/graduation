package com.bruce.phoneguard.android.control;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.model.TaskInfo;

import java.util.List;

public class TaskManagerAdapter extends BaseAdapter {
    private List<TaskInfo> taskInfos;
    private Context context;

    public TaskManagerAdapter(List<TaskInfo> taskInfos, Context context) {
        this.taskInfos = taskInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return taskInfos.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TaskInfo info = taskInfos.get(position);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(context,
                    R.layout.item_task_manager, null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) view
                    .findViewById(R.id.iv_task_icon);
            holder.tv_name = (TextView) view
                    .findViewById(R.id.tv_task_name);
            holder.tv_size = (TextView) view
                    .findViewById(R.id.tv_task_size);
            holder.cb_status = (CheckBox) view
                    .findViewById(R.id.cb_task_status);
            view.setTag(holder);
        }
        holder.iv_icon.setImageDrawable(info.getIcon());
        holder.tv_name.setText(info.getAppname());
        holder.tv_size.setText("占用内存："
                + Formatter.formatFileSize(SysApplication.mContext,
                info.getMemsize()));
        holder.cb_status.setChecked(info.isChecked());
        if (info.getPackname().equals(SysApplication.mPackageName)) {
            // 就是我们自己。
            holder.cb_status.setVisibility(View.INVISIBLE);
        } else {
            holder.cb_status.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return taskInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_size;
        public CheckBox cb_status;
    }

}
