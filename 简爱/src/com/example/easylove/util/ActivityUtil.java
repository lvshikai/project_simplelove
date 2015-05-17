package com.example.easylove.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Activity工具类
 * 
 * 
 */
public class ActivityUtil {

	/** 此类实例 **/
	private static ActivityUtil mInstance;
	/** 记录打开的Activity的集合 **/
	private List<Activity> mActivityList = new ArrayList<Activity>();

	public ActivityUtil() {

	}

	/**
	 * 获取实例，单例模式
	 */
	public synchronized static ActivityUtil getInstance() {
		if (mInstance == null) {
			mInstance = new ActivityUtil();
		}
		return mInstance;
	}

	/**
	 * 添加Activity到集合中
	 */
	public void add(Activity activity) {
		mActivityList.add(activity);
	}

	/**
	 * 从集合中移除Activity
	 */
	public void remove(Activity activity) {
		mActivityList.remove(activity);
	}
	/**
	 * 退出除了首页的界面
	 */
	public void exitOthers() {
		for (Activity activity : mActivityList) {
			if (activity != null)
				if (mActivityList.size() == 1) {

				} else {
					activity.finish();
				}

		}
	}

	/**
	 * 退出程序，遍历所有Activity然后finish
	 */
	public void exit() {
		for (Activity activity : mActivityList) {
			if (activity != null)
				activity.finish();
		}
			
		System.exit(0);
	}

}
