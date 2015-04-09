package com.bruce.phoneguard.android.fragment.traffic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.*;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.fragment.BaseFragment;
import com.bruce.phoneguard.android.ui.CircularProgressDrawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/3/14.
 */
public class UsePlanFragment extends BaseFragment {

    public static final String REFRESH_RECEIVER_ACTION = "refresh_receiver_action";
    private static final String MOUTHTRAFFIC = "mouthTraffic";
    private static final String MOUTHACCOUNT = "mouthAccount";
    private static final String USEDTRAFFIC = "usedTraffic";
    private static final String WARNMINGVALUE = "warnmingValue";
    private View mContentView;

    private SharedPreferences sp;
    private float mouthTraffic;
    private int mouthAccount;
    private float usedTraffic;
    // Views
    private ImageView ivDrawable;
    private TextView TrafficNumber_textView;
    private TextView MTrafficNumber_textView;
    private TextView AllTrafficNumber_textView;
    private TextView ToCountDate_textView;


    private String unit = "MB";
    private CircularProgressDrawable drawable;
    private Animator currentAnimation;

    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_use_plan, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        ivDrawable = (ImageView) mContentView.findViewById(R.id.iv_drawable);
        TrafficNumber_textView = (TextView) mContentView.findViewById(R.id.TrafficNumber_textView);
        MTrafficNumber_textView = (TextView) mContentView.findViewById(R.id.MTrafficNumber_textView);
        AllTrafficNumber_textView = (TextView) mContentView.findViewById(R.id.AllTrafficNumber_textView);
        ToCountDate_textView = (TextView) mContentView.findViewById(R.id.ToCountDate_textView);

    }

    @Override
    protected void initData() {
        currentAnimation = prepareStyle1Animation();
        drawable = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        ivDrawable.setImageDrawable(drawable);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH_RECEIVER_ACTION);
        getActivity().registerReceiver(refreshBroadCast, intentFilter);

        sp =  getActivity().getPreferences(Context.MODE_PRIVATE);
        mouthTraffic = sp.getFloat(MOUTHTRAFFIC, 0);
        mouthAccount = sp.getInt(MOUTHACCOUNT, 0);
        usedTraffic = sp.getFloat(USEDTRAFFIC, 0);

        TrafficNumber_textView.setText("2.25" + unit);
        MTrafficNumber_textView.setText(usedTraffic + unit);
        AllTrafficNumber_textView.setText(mouthTraffic + unit);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        int year = calendar.get(Calendar.YEAR);
//        int mouth = calendar.get(Calendar.MONTH);
//        int day = mouthAccount;
//        if (mouth == 12) {
//            mouth = 1;
//            year++;
//        }
        ToCountDate_textView.setText((31+mouthAccount-calendar.get(Calendar.DAY_OF_MONTH)) + "天");


    }

    @Override
    protected void initListener() {
        ivDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAnimation.start();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(refreshBroadCast);
    }

    /**
     * Style 1 animation will simulate a indeterminate loading while taking advantage of the inner
     * circle to provide a progress sense
     *
     * @return Animation
     */
    private Animator prepareStyle1Animation() {
        AnimatorSet animation = new AnimatorSet();

        final Animator indeterminateAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0, 3600);
        indeterminateAnimation.setDuration(3600);

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        innerCircleAnimation.setDuration(3600);
        innerCircleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                drawable.setIndeterminate(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                indeterminateAnimation.end();
                drawable.setIndeterminate(false);
                drawable.setProgress(0);
            }
        });

        animation.playTogether(innerCircleAnimation, indeterminateAnimation);
        return animation;
    }

    /**
     * This animation will make a pulse effect to the inner circle
     *
     * @return Animation
     */
    private Animator preparePulseAnimation() {
        AnimatorSet animation = new AnimatorSet();

        Animator firstBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.88f);
        firstBounce.setDuration(300);
        firstBounce.setInterpolator(new CycleInterpolator(1));
        Animator secondBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.83f);
        secondBounce.setDuration(300);
        secondBounce.setInterpolator(new CycleInterpolator(1));
        Animator thirdBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.80f);
        thirdBounce.setDuration(300);
        thirdBounce.setInterpolator(new CycleInterpolator(1));

        animation.playSequentially(firstBounce, secondBounce, thirdBounce);
        return animation;
    }

    private BroadcastReceiver refreshBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REFRESH_RECEIVER_ACTION)) {
                mouthTraffic = sp.getFloat(MOUTHTRAFFIC, 0);
                mouthAccount = sp.getInt(MOUTHACCOUNT, 0);
                usedTraffic = sp.getFloat(USEDTRAFFIC, 0);

                TrafficNumber_textView.setText("2.25" + unit);
                MTrafficNumber_textView.setText(usedTraffic + unit);
                AllTrafficNumber_textView.setText(mouthTraffic + unit);
                ToCountDate_textView.setText((31+mouthAccount-calendar.get(Calendar.DAY_OF_MONTH)) + "天");
            }
        }
    };
}
