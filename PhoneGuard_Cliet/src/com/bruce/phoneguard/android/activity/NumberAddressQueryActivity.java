package com.bruce.phoneguard.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.dao.NumberAddressDao;

public class NumberAddressQueryActivity extends BaseActivity {
    private EditText et_phone_number;
    private TextView tv_address_info;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到系统的震动服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        setContentView(R.layout.activity_address_query);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                String location = NumberAddressDao.getLocation(text);
                tv_address_info.setText(location);
            }
        });
    }

    @Override
    public void initView() {
        tv_address_info = (TextView) findViewById(R.id.tv_address_info);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
    }

    @Override
    public void initData() {
        mActionBar.setTitle("归属地查询");
        mActionBar.setIcon(R.drawable.selector_back_imagview);
    }

    public void query(View view) {
        String phone = et_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_x);
            et_phone_number.startAnimation(shake);
            vibrator.vibrate(new long[]{200, 100, 300, 100, 300, 100}, -1);
            Toast.makeText(this, "号码不能为空", 0).show();
            return;
        }
        String location = NumberAddressDao.getLocation(phone);
        tv_address_info.setText(location);
    }
}
