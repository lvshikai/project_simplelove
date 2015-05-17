package com.example.easylove.base;

import android.app.Application;
import android.content.Context;

import com.example.easylove.exception.ExceptionHandler;
import com.example.easylove.util.ImageUtil;

/**
 * Application
 */
public class EasyLoveApplication extends Application {
	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		
		// 全局异常处理
		ExceptionHandler.getInstance().init();
		// 初始化图片加载
		ImageUtil.initImageLoader(context);

	}
}
