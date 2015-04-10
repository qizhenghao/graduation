package com.bruce.phoneguard.android.activity;

import com.bruce.phoneguard.android.R;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AgainstTheftActivity extends BaseActivity {

    private static final String TAG = "LostFindActivity";
    private SharedPreferences sp;
    private TextView tv_lostfind_number;
    private ImageView iv_lostfind_status;
    private RelativeLayout rl_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 得到系统的配置
        sp = getSharedPreferences("config", MODE_PRIVATE);
        // setContentView(R.layout.activity_lost_find);
        // 判断用户是否完成过 设置向导，如果没有完成过 就进入设置向导界面。
        if (isFinishSetup()) {
            setContentView(R.layout.activity_against_theft);
            rl_menu = (RelativeLayout) findViewById(R.id.rl_menu);
            Log.i(TAG, "完成过设置向导，进入正常的界面");
            // 安全号码
            tv_lostfind_number = (TextView) findViewById(R.id.tv_lostfind_number);
            // 保护的状态
            iv_lostfind_status = (ImageView) findViewById(R.id.iv_lostfind_status);
            tv_lostfind_number.setText(sp.getString("safenumber", ""));
            boolean protecting = sp.getBoolean("protecting", false);
            if (protecting) {
                iv_lostfind_status.setImageResource(R.drawable.strongbox_app_lock_ic_locked);
            } else {
                iv_lostfind_status.setImageResource(R.drawable.strongbox_app_lock_ic_unlock);
            }

        } else {
            // 进入设置向导界面。
            Log.i(TAG, "进入设置向导界面");
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            // 关闭当前手机防盗的界面
            finish();
        }

    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    /**
     * 判断用户是否完成过设置向导
     *
     * @return
     */
    private boolean isFinishSetup() {
        return sp.getBoolean("finishsetup", false);
    }

    public void reEntrySetup(View view) {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        // 关闭当前手机防盗的界面
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.lost_find_menu, menu);
//        return true;
//    }

    // 当菜单被用户打开的时候调用的方法。
//	@Override
//	public boolean onMenuOpened(int featureId, Menu menu) {
//		if (rl_menu.getVisibility() == View.VISIBLE) {
//			rl_menu.setVisibility(View.INVISIBLE);
//		} else if (rl_menu.getVisibility() == View.INVISIBLE) {
//			rl_menu.setVisibility(View.VISIBLE);
//		}
//		return super.onMenuOpened(featureId, menu);
//	}

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (R.id.item_change_name == menuItem.getItemId()) {
//            AlertDialog.Builder builder = new Builder(this);
//            builder.setTitle("请输入新的手机防盗名称");
//            final EditText et = new EditText(this);
//            builder.setView(et);
//            builder.setPositiveButton("确定", new OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int arg1) {
//                    String newname = et.getText().toString().trim();
//                    Editor editor = sp.edit();
//                    editor.putString("newname", newname);
//                    editor.commit();
//                }
//            });
//            builder.show();
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
}
