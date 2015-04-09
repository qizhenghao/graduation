package com.bruce.phoneguard.android.control;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce.phoneguard.android.InitData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.config.FescoConfig;
import com.bruce.phoneguard.android.model.GridItem;

public class AddFreqListAdapter extends BaseAdapter {

	private Context context = null;
	private List<GridItem> data = null;

	public AddFreqListAdapter(Context context, List<GridItem> data) {
		super();
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = LayoutInflater.from(context).inflate(R.layout.add_freq_item, null);
			holder.imagview_picture = (ImageView) v.findViewById(R.id.add_item_picture_imagview);
			holder.txt_name = (TextView) v.findViewById(R.id.add_item_name_txt);
			holder.checkBox = (CheckBox) v.findViewById(R.id.add_item_checkbox);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.imagview_picture.setImageResource(data.get(position).getImageId());
		holder.txt_name.setText(data.get(position).getName());

		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences sp = context.getSharedPreferences(FescoConfig.PHONEGUARD_FREQUENTLY_SP,
						Context.MODE_PRIVATE);
				if (isChecked) {
					sp.edit().putInt(data.get(position).getEngName(), data.get(position).getId()).commit();
				} else {
					sp.edit().remove(data.get(position).getEngName()).commit();
				}
				InitData.getInstance().getManamentList().get(position).setChecked(isChecked);
			}
		});

		holder.checkBox.setChecked(data.get(position).isChecked());

		return v;
	}

	class ViewHolder {
		public TextView txt_name;
		public ImageView imagview_picture;
		public CheckBox checkBox;
	}
}
