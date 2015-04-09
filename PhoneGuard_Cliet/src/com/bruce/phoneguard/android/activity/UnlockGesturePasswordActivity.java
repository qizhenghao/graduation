package com.bruce.phoneguard.android.activity;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.bruce.phoneguard.android.services.IService;
import com.bruce.phoneguard.android.services.WatchDogService;
import com.bruce.phoneguard.android.ui.LockPatternView;
import com.bruce.phoneguard.android.ui.LockPatternView.Cell;
import com.bruce.phoneguard.android.utils.LockPatternUtils;


public class UnlockGesturePasswordActivity extends Activity {
    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private Handler mHandler = new Handler();
    private TextView mHeadTextView;
    private ImageView mUnlockAppImage;
    private Animation mShakeAnim;

    private Toast mToast;
    private IService iService;
    private String packname;
    private MyConn myconn;


    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }

        mToast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturepassword_unlock);
        myconn = new MyConn();
        Intent intent = new Intent(this,WatchDogService.class);
        bindService(intent, myconn, BIND_AUTO_CREATE);
        mLockPatternView = (LockPatternView) this
                .findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mUnlockAppImage = (ImageView) findViewById(R.id.gesturepwd_unlock_face);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);

        packname = getIntent().getStringExtra("packname");

        // 完成界面的初始化
        ApplicationInfo appinfo;
        try {
            appinfo = getPackageManager().getPackageInfo(packname, 0).applicationInfo;
            Drawable appicon = appinfo.loadIcon(getPackageManager());
            String appname = appinfo.loadLabel(getPackageManager()).toString();
            mUnlockAppImage.setImageDrawable(appicon);
            mHeadTextView.setText(appname);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SysApplication.getInstance().getLockPatternUtils().savedPatternExists()) {
            startActivity(new Intent(this, GuideGesturePasswordActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
            if (SysApplication.getInstance().getLockPatternUtils().checkPattern(pattern)) {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Correct);
                // 通知看门狗 临时的取消对这个程序的保护
                iService.callAppProtectStop(packname);
                showToast("解锁成功");
                finish();
            } else {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0)
                            showToast("您已5次输错密码，请30秒后再试");
                        mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast("输入长度不够，请重试");
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    mHandler.postDelayed(attemptLockout, 2000);
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        public void onPatternCellAdded(List<Cell> pattern) {

        }

        private void patternInProgress() {
        }
    };
    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(
                    LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText(secondsRemaining + " 秒后重试");
                    } else {
                        mHeadTextView.setText("请绘制手势密码");
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

    private class MyConn implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            iService = (IService)service;
        }
        public void onServiceDisconnected(ComponentName name) {
        }
    }
}
