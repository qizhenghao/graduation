package com.bruce.phoneguard.android.activity;

import com.bruce.phoneguard.android.InitData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.control.AddFreqListAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class AddFreqActivity extends BaseActivity implements OnClickListener {

	private ListView addListView;
	private AddFreqListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_frequently);
		initView();
		initData();
	}

	@Override
	public void initView() {
		addListView = (ListView) findViewById(R.id.add_freq_listview);

	}

	@Override
	public void initData() {
		mActionBar.setTitle("添加常用");
        mActionBar.setIcon(R.drawable.selector_back_imagview);
		
		adapter = new AddFreqListAdapter(AddFreqActivity.this, InitData
				.getInstance()
				.getManamentList());
		addListView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_imagbtn:
			defaultFinish();
			break;
		case R.id.commit_btn:
			defaultFinish();
			break;
		default:
			break;
		}
	}

}
