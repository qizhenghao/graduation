package com.bruce.phoneguard.android;

import com.bruce.phoneguard.android.model.AppInfo;
import com.bruce.phoneguard.android.model.parser.AppInfoParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/18.
 */
public class CacheData {

    private CacheData() {

    }

    private static List<AppInfo> lockedApps = null;

    private static List<AppInfo> unlockdApps = null;

    private static List<AppInfo> allApps = null;

    private static List<AppInfo> userTasks = null;

    private static List<AppInfo> sysTasks = null;

    /**
     * 从内存中获取未锁的所有apps
     * @param lockPacks
     * @return
     */
    public static List<AppInfo> getUnlockdApps(List<String> lockPacks) {
        if (unlockdApps == null) {
            lockedApps = new ArrayList<AppInfo>();
            unlockdApps = new ArrayList<AppInfo>();
            AppInfoParser.getAppInfos(SysApplication.mContext, lockPacks, lockedApps, unlockdApps, allApps);
        }
        return unlockdApps;
    }

    /**
     * 从内存中获取已锁的所有apps
     * @param lockPacks
     * @return
     */
    public static List<AppInfo> getLockedApps(List<String> lockPacks) {
        if (lockedApps == null) {
            lockedApps = new ArrayList<AppInfo>();
            unlockdApps = new ArrayList<AppInfo>();
            AppInfoParser.getAppInfos(SysApplication.mContext, lockPacks, lockedApps, unlockdApps, allApps);
        }
        return lockedApps;
    }

    /**
     * 从内存中获取已所有apps
     * @return
     */
    public static List<AppInfo> getAllApps() {
        if (allApps == null) {
            allApps = new ArrayList<AppInfo>();
            if (unlockdApps != null && lockedApps != null) {
                allApps.addAll(lockedApps);
                allApps.addAll(unlockdApps);
            } else {
                AppInfoParser.getAppInfos(SysApplication.mContext, allApps);
            }
        }
        return allApps;
    }

    public static List<AppInfo> getUserTasks() {
        return userTasks;
    }

    public static List<AppInfo> getSysTasks() {
        return sysTasks;
    }
}
