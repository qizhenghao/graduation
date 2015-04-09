package com.ultrawise.softwareproduct.idevplatform.view;


import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.ListView;

import com.ultrawise.softwareproduct.idevplatform.view.pulltorefresh.PullToRefreshBase;
import com.ultrawise.softwareproduct.idevplatform.view.pulltorefresh.PullToRefreshListView;

/**
 * 扩展 PullToRefreshListView
 * 
 * @author matti
 * 
 */
public class MRefreshListView extends PullToRefreshListView {

	private Handler handler = new Handler();

	private FreshCallBack callBack;
	private Mode oldMode;
	public boolean isLoading;

	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	public MRefreshListView(Context context) {
		super(context);
		initView();
	}

	public MRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MRefreshListView(Context context, Mode mode) {
		super(context, mode);
		initView();
	}

	public MRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		initView();
	}

	private void initView() {
		setMode(Mode.BOTH);
		setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				refreshData();
				resetLoading();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
//				if (!canLoadMore()) {
//					handler.postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							onRefreshComplete();
//							disableLoading();
//						}
//					}, 1000);
//				} else {
					String label = DateUtils.formatDateTime(getContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);
					getLoadingLayoutProxy().setLastUpdatedLabel(label);
					loadData();
//				}
			}
		});
		// setOnRefreshListener(new OnRefreshListener<ListView>() {
		//
		// @Override
		// public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		//
		// if (!canLoadMore()) {
		// handler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// onRefreshComplete();
		// disableLoading();
		// }
		// }, 1000);
		// } else {
		// String label = DateUtils.formatDateTime(
		// getContext(),
		// System.currentTimeMillis(),
		// DateUtils.FORMAT_SHOW_TIME
		// | DateUtils.FORMAT_SHOW_DATE
		// | DateUtils.FORMAT_ABBREV_ALL);
		//
		// getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// loadData();
		// }
		// }
		// });
		getRefreshableView().setFadingEdgeLength(0);
		getRefreshableView().setCacheColorHint(
				getResources().getColor(android.R.color.transparent));
	}

	private void refreshData() {
		if (callBack != null) {
			callBack.onRefresh();
		}
	}

	private void loadData() {
		if (callBack != null) {
			callBack.loadData();
		}
	}

	private boolean canLoadMore() {
		if (callBack != null) {
			return callBack.canLoadMore();
		} else {
			return true;
		}
	}
    public void dissable(){
    	setMode(Mode.DISABLED);
    }
    public void openable(){
    	setMode(Mode.BOTH);
    }
	public void disableLoading() {
		oldMode = getMode();
		if (oldMode == Mode.BOTH) {
			setMode(Mode.PULL_FROM_START);
		} else if (oldMode == Mode.PULL_FROM_END
				|| oldMode == Mode.PULL_UP_TO_REFRESH) {
			setMode(Mode.DISABLED);
		}
	}

	public void resetLoading() {
		if (isLoading) {
			if (oldMode != null) {
				setMode(oldMode);
			} else {
				setMode(Mode.BOTH);
			}
		}
	}
	public void setCallBack(FreshCallBack callBack) {
		this.callBack = callBack;
	}

}
