package com.bruce.phoneguard.android.ui;

import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VincentChen on 2014/12/19.
 * <p/>
 * 发现多个tab切换效果
 */
public class TabViewPagerIndicator extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private final String TAG = "DiscoverTabPageIndicator";

    private List<TextView> mTextItems;

    private LineViewPagerIndicator mLineIndicator;

    private Activity mParentActivity;

    private int[] mViewIds;

    public TabViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * set the view ids and initialize the tab page indicator.
     * NOTE: this method MUST be called in initializing, otherwise the view would not be functional.
     * @param ids an array containing the ids of the line indicator (index 0 in the array)
     *            and the text items (index from 1).
     */
    public void setViewIds(int[] ids) {
        if (ids != null && ids.length > 1) {
            mViewIds = ids;
            initViews();
        } else {
            Log.e(TAG, "error in setViewIds(): the given \"ids\" is invalid!!!");
        }
    }

    public void setViewPager(ViewPager pager) {
        mLineIndicator.setViewPager(pager);
        mLineIndicator.setOnPageChangeListener(this);
    }

    public void setParentActivty(Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    private void initViews() {
        if (mViewIds != null && mViewIds.length > 1) {
            mTextItems = new ArrayList<TextView>(mViewIds.length - 1);
            mLineIndicator = (LineViewPagerIndicator) findViewById(mViewIds[0]);
            for (int i = 1; i < mViewIds.length; i++) {
                mTextItems.add((TextView) findViewById(mViewIds[i]));
            }
        } else {
            Log.e(TAG, "error in initViews(): mViewIds is invalid!!!");
        }
        bindListener();
    }


    private void bindListener() {
        if (mTextItems != null && mTextItems.size() > 0) {
            for (int i = 0; i < mTextItems.size(); i++) {
                final int currTab = i;
                mTextItems.get(currTab).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View dv) {
                        if (mLineIndicator.getCurrentPage() == currTab) {
                            onTabClicked(currTab);
                        }
                        mLineIndicator.setCurrentItem(currTab);
                    }
                });
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int index) {
//        triggerUmengEvent();
    }

    private void onTabClicked(int index) {

    }

//    /**
//     * trigger the statistics event on the given page
//     */
//    public void triggerUmengEvent() {
//        int pageIndex = mLineIndicator.getCurrentPage();
//        String umengEventId;
//        switch (mViewIds[pageIndex + 1]) { // plus 1 because the 1st element in mViewIds is line indicator
//            case R.id.discover_tab_one:
//                umengEventId = "AD-1001";
//                break;
//            case R.id.discover_tab_two:
//                umengEventId = "AD-1002";
//                break;
//            case R.id.discover_tab_three:
//                umengEventId = "AD-1003";
//                break;
//            default:
//                umengEventId = "AD-1001";
//                break;
//        }
//    }
}
