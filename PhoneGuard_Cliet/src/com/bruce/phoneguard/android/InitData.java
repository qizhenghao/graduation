package com.bruce.phoneguard.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bruce.phoneguard.android.config.FescoConfig;
import com.bruce.phoneguard.android.model.GridItem;
import com.bruce.phoneguard.android.utils.FileUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * @author qizhenghao data: 2014年12月27日15:30:45
 * @version 1.0 function： 初始化数据
 */

public class InitData {

	private static List<GridItem> frequentlyList = null;

	private static List<GridItem> manamentList = null;

	private static List<GridItem> allList = null;

	private static InitData instance = null;

	private InitData() {
		init();
	}

	private void init() {
		frequentlyList = new ArrayList<GridItem>();
		manamentList = new ArrayList<GridItem>();
		allList = new ArrayList<GridItem>();
		initAllList();
	}
	
	/**初始化DB文件，从assets目录下加载*/
	public void initDBFile(final AssetManager assetManager,
			final String filePath, final String dbname) {
		new Thread() {
			public void run() {
				try {
					File file = new File(filePath, dbname);
					if (file.exists() && file.length() > 0) {
						Log.i("SplashActivity", "数据库是存在的。无需拷贝");
						return;
					}
					FileUtils.saveFile(assetManager.open(dbname), filePath
							+ dbname);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** 添加 */
	private void initAllList() {
		allList.add(add_new);
		addGridItems(allList);
	}

	/** 将现有的功能加到list中 */
	private void addGridItems(List<GridItem> list) {
		list.add(software_mana);
		list.add(task_mana);
		list.add(garbage_clean);
		list.add(anti_virus);
		list.add(traffic_manage);
		list.add(against_theft);
		list.add(tel_enquiries);
		list.add(data_bakeup);
		list.add(program_lock);
        list.add(big_file_manage);
	}

	public static InitData getInstance() {
		if (instance == null) {
			instance = new InitData();
		}
		return instance;
	}

	/**
	 * @author qizhenghao
	 *            当前activity date: 2014年12月27日19:16:22
	 * @return 所有功能List
	 */
	public List<GridItem> getManamentList() {
		if (manamentList.size() == 0) {
			addGridItems(manamentList);
		}
		return manamentList;
	}

	/**
	 * @author qizhenghao
	 * @param context
	 *            当前activity date: 2014年12月27日19:16:22
	 * @return 常用功能List
	 */
	public List<GridItem> getFrequentlyList(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				FescoConfig.PHONEGUARD_FREQUENTLY_SP, Context.MODE_PRIVATE);

		Map<String, ?> allContent = sp.getAll();
		frequentlyList.clear();
		for (Map.Entry<String, ?> entry : allContent.entrySet()) {
			allList.get((Integer) entry.getValue()).setChecked(true);
			frequentlyList.add(allList.get((Integer) entry.getValue()));
		}
		frequentlyList.add(allList.get(FescoConfig.ADD_NEW));
		return frequentlyList;
	}

	GridItem add_new = new GridItem(FescoConfig.ADD_NEW, "添加",
			FescoConfig.ADD_NEW_STR, R.drawable.selector_add_new_item, true);
	GridItem software_mana = new GridItem(FescoConfig.SOFTWARE_MANA, "软件管理",
			FescoConfig.APPROVAL_STR, R.drawable.selector_software_mana_item,
			false);
	GridItem task_mana = new GridItem(FescoConfig.TASK_MANA, "进程管理",
			FescoConfig.TRAIL_STR, R.drawable.selector_task_mana_item, false);
	GridItem garbage_clean = new GridItem(FescoConfig.GARBAGE_CLEAN, "垃圾清理",
			FescoConfig.PERSONAL_MANAGE_STR,
			R.drawable.selector_garbage_clean_item, false);
	GridItem anti_virus = new GridItem(FescoConfig.ANTI_VIRUS, "病毒查杀",
			FescoConfig.SIGN_IN_STR, R.drawable.virus_cleaning, false);
	GridItem traffic_manage = new GridItem(FescoConfig.TRAFFIC_MANAGE, "流量管理",
			FescoConfig.HUMAN_RESOURCES_STR, R.drawable.selector_off_item,
			false);
	GridItem against_theft = new GridItem(FescoConfig.AGAINST_THEFT, "防盗管理",
			FescoConfig.AGAINST_THEFT_STR, R.drawable.against_theft,
			false);
	GridItem tel_enquiries = new GridItem(FescoConfig.TEL_ENQUIRIES, "归属地查询",
			FescoConfig.TEL_ENQUIRIES_STR, R.drawable.tel_enquiries,
			false);
	GridItem data_bakeup = new GridItem(FescoConfig.DATA_BAKEUP, "数据备份",
			FescoConfig.DATA_BAKEUP_STR, R.drawable.data_bakeup,
			false);
	GridItem program_lock = new GridItem(FescoConfig.PROGRAM_LOCK, "程序加锁",
			FescoConfig.PROGRAM_LOCK_STR, R.drawable.permision_management,
			false);
    GridItem big_file_manage = new GridItem(FescoConfig.BIG_FILE_MANAGE, "大文件管理",
            FescoConfig.BIG_FILE_MANAGE_STR, R.drawable.large_file_management, false);

}
