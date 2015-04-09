package com.bruce.phoneguard.android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;

/**
 * Created by Administrator on 2015/3/20.
 */
public class DataBakeupActivity extends BaseActivity implements View.OnClickListener {

    private Switch autoSynSwitch;
    private Switch autoWifiSynSwitch;
    private View contactLayout;
    private View messageLayout;
    private View recordLayout;
    private View albumLayout;
    private View filetLayout;
    private ImageView contactIV;
    private ImageView messageIV;
    private ImageView recordIV;
    private ImageView albumIV;
    private ImageView fileIV;
    private boolean isContactSelected;
    private boolean isMessageSelected;
    private boolean isRecordSelected;
    private boolean isAlbumSelected;
    private boolean isFileSelected;
    private boolean isAutoSyn;
    private boolean isAutoWifiSyn;
    private static final String isAutoStr = "isAutoStr";
    private static final String isAutoWifiStr = "isAutoWifiStr";
    private static final String isContactStr = "isContactStr";
    private static final String isMessageStr = "isMessageStr";
    private static final String isRecordStr = "isRecordStr";
    private static final String isAlbumStr = "isAlbumStr";
    private static final String isFileStr = "isFileStr";
    private TextView rightNowSynTv;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bakeup);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        autoSynSwitch.setOnClickListener(this);
        autoWifiSynSwitch.setOnClickListener(this);
        contactLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        recordLayout.setOnClickListener(this);
        albumLayout.setOnClickListener(this);
        filetLayout.setOnClickListener(this);
        rightNowSynTv.setOnClickListener(this);
    }

    @Override
    public void initView() {
        autoSynSwitch = (Switch) findViewById(R.id.auto_syn_switch);
        autoWifiSynSwitch = (Switch) findViewById(R.id.auto_wifi_syn_switch);
        contactLayout = findViewById(R.id.contact_select_layout);
        messageLayout = findViewById(R.id.message_select_layout);
        recordLayout = findViewById(R.id.record_select_layout);
        albumLayout = findViewById(R.id.album_select_layout);
        filetLayout = findViewById(R.id.file_select_layout);
        contactIV = (ImageView) findViewById(R.id.contact_select_iv);
        messageIV = (ImageView) findViewById(R.id.message_select_iv);
        recordIV = (ImageView) findViewById(R.id.record_select_iv);
        albumIV = (ImageView) findViewById(R.id.album_select_iv);
        fileIV = (ImageView) findViewById(R.id.file_select_iv);
        rightNowSynTv = (TextView) findViewById(R.id.right_now_syn_tv);
    }

    @Override
    public void initData() {
        sp = getPreferences(Context.MODE_PRIVATE);
        isAutoSyn = sp.getBoolean(isAlbumStr, false);
        isAutoWifiSyn = sp.getBoolean(isAutoWifiStr, false);
        isContactSelected = sp.getBoolean(isContactStr, false);
        isMessageSelected = sp.getBoolean(isMessageStr, false);
        isRecordSelected = sp.getBoolean(isRecordStr, false);
        isAlbumSelected = sp.getBoolean(isAlbumStr, false);
        isFileSelected = sp.getBoolean(isFileStr, false);

        autoSynSwitch.setChecked(isAutoSyn);
        autoWifiSynSwitch.setChecked(isAutoWifiSyn);
        contactIV.setImageResource(isContactSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
        messageIV.setImageResource(isMessageSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
        recordIV.setImageResource(isRecordSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
        albumIV.setImageResource(isAlbumSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
        fileIV.setImageResource(isFileSelected ? R.drawable.check_selected_green : R.drawable.uncheck_selected_gray);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_select_layout:
                isContactSelected = !isContactSelected;
                contactIV.setImageResource(isContactSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
                sp.edit().putBoolean(isContactStr, isContactSelected).commit();
                break;
            case R.id.message_select_layout:
                isMessageSelected = !isMessageSelected;
                messageIV.setImageResource(isMessageSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
                sp.edit().putBoolean(isMessageStr, isMessageSelected).commit();
                break;
            case R.id.record_select_layout:
                isRecordSelected = !isRecordSelected;
                recordIV.setImageResource(isRecordSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
                sp.edit().putBoolean(isRecordStr, isRecordSelected).commit();
                break;
            case R.id.album_select_layout:
                isAlbumSelected = !isAlbumSelected;
                albumIV.setImageResource(isAlbumSelected?R.drawable.check_selected_green:R.drawable.uncheck_selected_gray);
                sp.edit().putBoolean(isAlbumStr, isAlbumSelected).commit();
                break;
            case R.id.file_select_layout:
                isFileSelected = !isFileSelected;
                fileIV.setImageResource(isFileSelected ? R.drawable.check_selected_green : R.drawable.uncheck_selected_gray);
                sp.edit().putBoolean(isFileStr, isFileSelected).commit();
                break;
            case R.id.right_now_syn_tv:
                rightNowSyn();
                break;
            case R.id.auto_syn_switch:
                isAutoSyn = !isAutoSyn;
                sp.edit().putBoolean(isAutoStr, isAutoSyn).commit();
                break;
            case R.id.auto_wifi_syn_switch:
                isAutoWifiSyn = !isAutoWifiSyn;
                sp.edit().putBoolean(isAutoWifiStr, isAutoWifiSyn).commit();
                break;
            default:
                break;
        }
    }

    private void rightNowSyn() {

    }
}
