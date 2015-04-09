package com.bruce.phoneguard.android.fragment.traffic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.utils.DialogUtils;

/**
 * Created by Administrator on 2015/3/14.
 */
public class PlanSettingFragment extends BaseFragment implements View.OnClickListener{

    private static final String MOUTHTRAFFIC = "mouthTraffic";
    private static final String MOUTHACCOUNT = "mouthAccount";
    private static final String USEDTRAFFIC = "usedTraffic";
    private static final String WARNMINGVALUE = "warnmingValue";
    private View mContentView;
    private TextView mouthTraffic_tv;
    private TextView mouthAccount_tv;
    private TextView usedTraffic_tv;
    private TextView warnmingValue_tv;
    private TextView correction_to_operator_tv;
    private SeekBar seekBar;

    private StringBuilder stringBuilder;
    private String persent = "%";
    private String unit = "MB";
    private String empty = "  ";

    private SharedPreferences sp;
    private float mouthTraffic;
    private int mouthAccount;
    private float usedTraffic;
    private int warnmingValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_plan_setting, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mouthTraffic_tv = (TextView) mContentView.findViewById(R.id.showMouthTrafficUnit_TextView);
        mouthAccount_tv = (TextView) mContentView.findViewById(R.id.setDate_textView);
        usedTraffic_tv = (TextView) mContentView.findViewById(R.id.trafficUnit_textView);
        correction_to_operator_tv = (TextView) mContentView.findViewById(R.id.correction_to_operator_tv);
        warnmingValue_tv = (TextView) mContentView.findViewById(R.id.showWarnmingValue_textView);
        seekBar = (SeekBar) mContentView.findViewById(R.id.warningNetwork_seekBar);
    }

    @Override
    protected void initData() {
        stringBuilder = new StringBuilder();
        sp =  getActivity().getPreferences(Context.MODE_PRIVATE);
        mouthTraffic = sp.getFloat(MOUTHTRAFFIC, 0);
        mouthAccount = sp.getInt(MOUTHACCOUNT, 0);
        usedTraffic = sp.getFloat(USEDTRAFFIC, 0);
        warnmingValue = sp.getInt(WARNMINGVALUE, 0);
        mouthTraffic_tv.setText(mouthTraffic + "");
        mouthAccount_tv.setText(mouthAccount + "");
        usedTraffic_tv.setText(usedTraffic + "");
        warnmingValue_tv.setText((mouthAccount * warnmingValue / 100) + unit + empty + (warnmingValue) + persent);
        seekBar.setProgress(warnmingValue);
    }

    @Override
    protected void initListener() {
        mContentView.findViewById(R.id.setupMouthTraffic_LinearLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.setupDate_LinearLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.showUsedTraffic_LinearLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.correction_to_operator_tv).setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                warnmingValue = progress;
                warnmingValue_tv.setText((mouthAccount * progress / 100) + unit + empty + (progress) + persent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mContext, "停了", Toast.LENGTH_SHORT);
                sp.edit().putInt(WARNMINGVALUE, warnmingValue).commit();
                refreshUsePlanFragment();
            }
        });
    }

    private void refreshUsePlanFragment() {
        Intent intent = new Intent();
        intent.setAction(UsePlanFragment.REFRESH_RECEIVER_ACTION);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setupMouthTraffic_LinearLayout:
                setupmouthTraffic_tv();
                break;
            case R.id.setupDate_LinearLayout:
                setupmouthAccount_tv();
                break;
            case R.id.showUsedTraffic_LinearLayout:
                setupusedTraffic_tv();
                break;
            case R.id.correction_to_operator_tv:
                Toast.makeText(mContext, "向运营商校正", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }
    }

    private void setupusedTraffic_tv() {
        Dialog dialog = DialogUtils.createDialog(mContext, R.layout.traffic_setup_dialog, R.id.setupTraffic_edittext,
                usedTraffic_tv.getText().toString(), new DialogUtils.DialogCallBack() {
                    @Override
                    public void callBack(String str) {
                        if (str == null || "".equals(str.trim())) {
                        } else {
                            usedTraffic_tv.setText(str);
                            usedTraffic = Integer.valueOf(str);
                            sp.edit().putFloat(USEDTRAFFIC, usedTraffic).commit();
                            refreshUsePlanFragment();
                        }
                    }
                });
        dialog.show();
    }

    private void setupmouthAccount_tv() {
        Dialog dialog = DialogUtils.createDialog(mContext, R.layout.traffic_setup_dialog, R.id.setupTraffic_edittext,
                mouthAccount_tv.getText().toString(), new DialogUtils.DialogCallBack() {
                    @Override
                    public void callBack(String str) {
                        if (str == null || "".equals(str.trim())) {
                        } else {
                            mouthAccount_tv.setText(str);
                            mouthAccount = Integer.valueOf(str);
                            sp.edit().putInt(MOUTHACCOUNT, mouthAccount).commit();
                            refreshUsePlanFragment();
                        }
                    }
                });
        dialog.show();
    }

    private void setupmouthTraffic_tv() {
        Dialog dialog = DialogUtils.createDialog(mContext, R.layout.traffic_setup_dialog, R.id.setupTraffic_edittext,
                mouthTraffic_tv.getText().toString(), new DialogUtils.DialogCallBack() {
                    @Override
                    public void callBack(String str) {
                        if (str == null || "".equals(str.trim())) {
                        } else {
                            mouthTraffic_tv.setText(str);
                            mouthTraffic = Integer.valueOf(str);
                            sp.edit().putFloat(MOUTHTRAFFIC, mouthTraffic).commit();
                            refreshUsePlanFragment();
                        }
                    }
                });
        dialog.show();
    }

}
