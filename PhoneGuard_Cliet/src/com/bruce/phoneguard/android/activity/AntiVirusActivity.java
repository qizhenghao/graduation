package com.bruce.phoneguard.android.activity;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.bruce.phoneguard.android.CacheData;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.dao.AntiVirusDao;
import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.utils.Md5Utils;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author qizhenghao
 * @version 1.0 date: 2015年01月01日20:51:04 function：杀毒activity
 */

public class AntiVirusActivity extends BaseActivity {
    protected static final int SCANNING = 1;
    protected static final int SCAN_FINISH = 2;
    protected static final int SCAN_BENGIN = 0;
    private ImageView iv_scan;
    private ProgressBar progressBar;
    private TextView tv_scan_status;
    private LinearLayout ll_container;
    private PackageManager pm;
    private boolean flag;
    private List<AppInfo> appInfos;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SCAN_BENGIN:
                    tv_scan_status.setText("正在初始化8核杀毒引擎...");
                    break;
                case SCANNING:
                    ScanInfo info = (ScanInfo) msg.obj;
                    tv_scan_status.setText("正在扫描:" + info.appname);
                    View view = View.inflate(getApplicationContext(), R.layout.scan_virus_item, null);
                    TextView appNameTV = (TextView) view.findViewById(R.id.tv_app_name);
                    TextView appSafeTV = (TextView) view.findViewById(R.id.tv_app_safe);
                    ImageView appIconIV = (ImageView) view.findViewById(R.id.iv_app_icon);
                    TextView appLocation = (TextView) view.findViewById(R.id.tv_app_location);
                    if (info.isVirus) {
                        appNameTV.setTextColor(Color.RED);
                        appSafeTV.setTextColor(Color.RED);
                    } else {
                        appNameTV.setTextColor(getResources().getColor(R.color.black));
                        appSafeTV.setTextColor(getResources().getColor(R.color.green_light));
                    }
                    appNameTV.setText(info.appname);
                    appSafeTV.setText(info.desc);
                    appIconIV.setImageDrawable(info.icon);
                    if (info.isRom) {
                        appLocation.setText("手机内存");
                    } else {
                        appLocation.setText("外置内存");
                    }
                    ll_container.addView(view, 0);
                    break;
                case SCAN_FINISH:
                    tv_scan_status.setText("恭喜，您的手机灰常安全！");
                    iv_scan.clearAnimation();
                    Toast.makeText(getApplicationContext(), "扫描完成", 0).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = getPackageManager();
        setContentView(R.layout.activity_anti_virus);
        initView();
        initData();

        scanVirus();
    }

    /**
     * 扫描病毒
     */
    private void scanVirus() {
        flag = true;
        new Thread() {
            public void run() {
                Message msg = Message.obtain();
                msg.what = SCAN_BENGIN;
                handler.sendMessage(msg);
                appInfos = CacheData.getAllApps();
                int max = appInfos.size();
                progressBar.setMax(max);
                int process = 0;
                for (AppInfo info : appInfos) {
                    if (!flag) {
                        return;
                    }
                    String apkpath = info.getApkpath();
                    // 检查获取这个文件的 特征码
                    String md5info = Md5Utils.getFileMd5(apkpath);
                    String result = AntiVirusDao.checkVirus(md5info);
                    msg = Message.obtain();
                    msg.what = SCANNING;
                    ScanInfo scanInfo = new ScanInfo();
                    if (result == null) {
                        scanInfo.desc = "扫描安全";
                        scanInfo.isVirus = false;
                    } else {
                        scanInfo.desc = result;
                        scanInfo.isVirus = true;
                    }
                    scanInfo.isRom = info.isInRom();
                    scanInfo.icon = info.getIcon();
                    scanInfo.appname = info.getName();
                    msg.obj = scanInfo;
                    handler.sendMessage(msg);
                    process++;
                    progressBar.setProgress(process);
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                msg = Message.obtain();
                msg.what = SCAN_FINISH;
                handler.sendMessage(msg);
            }

            ;
        }.start();
    }

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }

    class ScanInfo {
        Drawable icon;
        String appname;
        boolean isVirus;
        String desc;
        boolean isRom;
    }

    @Override
    public void initView() {
        tv_scan_status = (TextView) findViewById(R.id.tv_scan_status);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
    }

    @Override
    public void initData() {
        mActionBar.setTitle("病毒查杀");
        mActionBar.setIcon(R.drawable.selector_back_imagview);

        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        ra.setRepeatCount(Animation.INFINITE);
        ra.setDuration(2000);
        iv_scan.startAnimation(ra);
    }

}
