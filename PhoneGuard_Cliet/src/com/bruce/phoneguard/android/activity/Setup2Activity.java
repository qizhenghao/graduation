package com.bruce.phoneguard.android.activity;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bruce.phoneguard.android.R;

public class Setup2Activity extends BaseSetupActivity {
    private TelephonyManager tm;
    private ImageView iv_setup2_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        setContentView(R.layout.activity_setup2);
        iv_setup2_status = (ImageView) findViewById(R.id.iv_setup2_status);
        // 判断是否绑定过，
        String savedSim = sp.getString("sim", null);
        if (TextUtils.isEmpty(savedSim)) {
            iv_setup2_status.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
        } else {
            iv_setup2_status.setImageResource(R.drawable.strongbox_app_lock_ic_locked);
        }
    }

    /**
     * 绑定或者解绑sim卡
     *
     * @param view
     */
    public void bindUnbindSim(View view) {
        // 判断是否绑定过，
        String savedSim = sp.getString("sim", null);
        if (TextUtils.isEmpty(savedSim)) {
            // 唯一的标识
            String simserial = tm.getSimSerialNumber();
            Editor editor = sp.edit();
            editor.putString("sim", simserial);
            editor.commit();
            Toast.makeText(this, "绑定sim卡成功", Toast.LENGTH_SHORT).show();
            iv_setup2_status.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
        } else {
            Editor editor = sp.edit();
            editor.putString("sim", null);
            editor.commit();
            Toast.makeText(this, "解除绑定sim卡成功", Toast.LENGTH_SHORT).show();
            iv_setup2_status.setImageResource(R.drawable.strongbox_app_lock_ic_locked);
        }
    }

    @Override
    public void showNext() {
        // 判断是否绑定过，
        String savedSim = sp.getString("sim", null);
        if (TextUtils.isEmpty(savedSim)) {
            Toast.makeText(this, "请先绑定sim卡", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivityAndFinishSelf(Setup3Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(Setup1Activity.class);
    }
}
