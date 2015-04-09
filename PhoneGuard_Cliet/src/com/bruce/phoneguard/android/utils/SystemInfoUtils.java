package com.bruce.phoneguard.android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;
import com.bruce.phoneguard.android.model.AppInfo;
import com.stericson.RootTools.RootTools;

public class SystemInfoUtils {
	/**
	 * 判断一个服务是否处于运行状态
	 * @param context 上下文
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String className){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(200);
		for(RunningServiceInfo info:infos){
			String serviceClassName = info.service.getClassName();
			if(className.equals(serviceClassName)){
				return true;
			}
		}
		return false;
	}

    /**
     * 得到当前正在运行程序的包名
     返回系统里面的任务栈的信息 , taskinfos的集合里面只有一个元素
     内容就是当前正在运行的进程对应的任务栈
     */
    public static String getCurrentTask(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskinfos = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo currenttask = taskinfos.get(0);
        // 获取当前用户可见的activity 所在的程序的包名
        return currenttask.topActivity.getPackageName();
    }

    /**
	 * 获取手机的总内存大小 单位byte
	 * @return
	 */
	public static long getTotalMem(){
		try {
			FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String totalInfo  = br.readLine();
			//MemTotal:         513000 kB
			StringBuffer sb = new StringBuffer();
			for(char c : totalInfo.toCharArray()){
				if(c>='0'&&c<='9'){
					sb.append(c);
				}
			}
			long bytesize = Long.parseLong(sb.toString())*1024;
			return bytesize;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取可用的内存信息。
	 * @param context
	 * @return
	 */
	public static long getAvailMem(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//获取内存大小
		MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(outInfo);
		long availMem = outInfo.availMem;
		return availMem;
	}
	/**
	 * 得到正在运行的进程的数量
	 * @param context
	 * @return
	 */
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcessInfos = am.getRunningAppProcesses();
		int count = runningAppProcessInfos.size();
		return count;
	}

    /**
     * 打开系统应用程序信息界面
     * @param context
     * @param packageName
     */
    public static void viewAppDetail(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }

    /**
     * 卸载软件
     * @param context
     * @param packageName
     */
    public static void uninstallUserApp(Context context, String packageName) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
    }

    /**
     * 卸载系统应用 ，需要root权限 利用linux命令删除文件。
     * @param context
     * @param apkPath
     */
    public static void uninstallSysApp(Context context, String apkPath) {
            if (!RootTools.isRootAvailable()) {
                Toast.makeText(context, "卸载系统应用，必须要root权限", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (!RootTools.isAccessGiven()) {
                    Toast.makeText(context, "请授权root权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                RootTools.sendShell("mount -o remount ,rw /system", 3000);
                RootTools.sendShell("rm -r " + apkPath,
                        30000);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    /**
     * 开启应用程序
     * @param context
     * @param packageName
     */
    public static void startApp(Context context, String packageName) {
        // 打开这个应用程序的入口activity。
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "该应用没有启动界面", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 分享应用
     * @param context
     * @param packageName
     * @param appName
     */
    public static void shareApp(Context context, String packageName, String appName) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                "推荐您使用一款软件，名称叫：" + packageName
                        + "下载路径：https://play.google.com/store/apps/details?id="
                        + appName);
        context.startActivity(intent);
    }
	
}
