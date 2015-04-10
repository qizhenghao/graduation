package com.bruce.phoneguard.android.activity;

import android.os.Bundle;
import com.bruce.phoneguard.android.R;

/**
 * Created by Administrator on 2015/4/10.
 */
public class Setup1Activity extends BaseSetupActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showNext() {
        startActivityAndFinishSelf(Setup2Activity.class);
    }

    @Override
    public void showPre() {

    }
}
