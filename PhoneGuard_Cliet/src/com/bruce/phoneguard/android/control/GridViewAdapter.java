package com.bruce.phoneguard.android.control;

import java.util.List;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.model.GridItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author qizhenghao
 * data: 2014年12月27日10:38:43
 * @version 1.0
 * function: gridview adapter
 */

public class GridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private List<GridItem> data = null;

	public GridViewAdapter(LayoutInflater inflater, List<GridItem> data) {
		super();
		this.inflater = inflater;
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
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		if(v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.gridview_item, null);
			holder.imagBtn_picture = (ImageView) v.findViewById(R.id.picture_imagBtn);
			holder.txt_name = (TextView) v.findViewById(R.id.name_txt);
			
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		holder.imagBtn_picture.setImageResource(data.get(position).getImageId());
		holder.txt_name.setText(data.get(position).getName());
		
//		holder.imagBtn_picture.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
		return v;
	}

	class ViewHolder {
		public TextView txt_name;
		public ImageView imagBtn_picture;	
	}
}
