package com.bruce.phoneguard.android.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TaskFramentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;


        public TaskFramentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

    @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            if (fragmentList != null) {
                return fragmentList.size();
            }
            return 0;
        }
    }