package com.ultrawise.softwareproduct.idevplatform.view;


import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.GridView;
import com.ultrawise.softwareproduct.idevplatform.view.FreshCallBack;
import com.ultrawise.softwareproduct.idevplatform.view.pulltorefresh.PullToRefreshBase;
import com.ultrawise.softwareproduct.idevplatform.view.pulltorefresh.PullToRefreshGridView;

/**
 * 
 * 
 * @category author XiongXinJiang
 * @version 创建时间：2013年9月5日 上午11:25:30
 * 
 * 
 **/
public class MRefreshGridView extends PullToRefreshGridView {

	private Handler handler = new Handler();

	private FreshCallBack callBack;

	public MRefreshGridView(Context context) {
		super(context);
		initView();
	}

	public MRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MRefreshGridView(Context context, Mode mode) {
		super(context, mode);
		initView();
	}

	public MRefreshGridView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		initView();
	}

	private void initView() {
		setMode(Mode.BOTH);
		setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				if (!canLoadMore()) {
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							onRefreshComplete();
							disableLoading();
						}
					}, 1000);
				} else {
					String label = DateUtils.formatDateTime(getContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					getLoadingLayoutProxy().setLastUpdatedLabel(label);
					loadData();
				}
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

	public void disableLoading() {
		setMode(Mode.DISABLED);
	}

	public void resetLoading() {
		setMode(Mode.PULL_FROM_END);
	}

	public void setCallBack(FreshCallBack callBack) {
		this.callBack = callBack;
	}

}
