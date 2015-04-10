package com.bruce.phoneguard.android.activity;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.bruce.phoneguard.android.R;

public class Setup3Activity extends BaseSetupActivity {
    private EditText et_setup3_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et_setup3_phone = (EditText) findViewById(R.id.et_setup3_phone);
        et_setup3_phone.setText(sp.getString("safenumber", ""));
    }
    @Override
    public void showNext() {
        String phone = et_setup3_phone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "请先设置安全号码", Toast.LENGTH_SHORT).show();
            return;
        }
        Editor editor = sp.edit();
        editor.putString("safenumber", phone);
        editor.commit();

        startActivityAndFinishSelf(Setup4Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(Setup2Activity.class);
    }

    public void selectContact(View view){
        Intent intent = new Intent(this,SelectContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            String phone = data.getStringExtra("phone");
            et_setup3_phone.setText(phone);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
