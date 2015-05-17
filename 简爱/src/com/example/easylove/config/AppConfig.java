package com.example.easylove.config;

import java.io.File;

import android.os.Environment;

import com.example.easylove.R;
import com.example.easylove.base.EasyLoveApplication;

/**
 * 配置存储类，分为路径和常量
 * 
 * (预留)
 * 
 */
public class AppConfig {

	/** ------------------------Path start---------------------------- **/

	/** 应用名，用于日志以及文件存储路径 **/
	public final static String APP_NAME = EasyLoveApplication.context
			.getString(R.string.app_name);

	/** 服务器IP **/
	// public final static String SERVER_IP = "";//测试环境
	public final static String SERVER_IP = "";// 生产环境
	// public final static String SERVER_IP = "";//支付环境
	// public final static String SERVER_IP = "";//apiportal对应的环境
	/** 登录地址 通过该地址记录Cookies **/
	public final static String LOGIN_URL = "user/login";
	/** 服务器端口 **/
	public final static String SERVER_PORT = "8080";
	// public final static String SERVER_PORT = "80";//apiportal对应的端口

	/** 获取接口路径 **/
	public final static String SERVER_URL = "http:" + File.separator
			+ File.separator + SERVER_IP + ":" + SERVER_PORT + File.separator
			+ "wandaapi" + File.separator;

	/** SD卡路径 **/
	public final static String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	/** 项目路径 **/
	public final static String APP_PATH = SD_PATH + APP_NAME + File.separator;
	/** 日志存放路径 **/
	public final static String LOG_PATH = APP_PATH + "Log" + File.separator;

	/** ------------------------Path end---------------------------- **/

	/** ------------------------接口参数 start---------------------------- **/

	// public final static String CLIENT_ID = "1_6";//测试环境
	public final static String CLIENT_ID = "1";// 正式环境
	// public final static String VERIFY_CODE = "";//测试环境
	public final static String VERIFY_CODE = "";// 正式环境
	// public final static String CLIENT_TYPE = "2";// android 测试环境
	public final static String CLIENT_TYPE = "2";// android 正式环境
	public final static String NET_VERSION = "1";// 接口版本
	public final static String PASSWORD = "";// 接口版本

	/** ------------------------接口参数 end---------------------------- **/

	/**
	 * 轮循图片url
	 */
	public static String[] IMAGE_URLS = {
			"http://image.zcool.com.cn/56/35/1303967876491.jpg",
			"http://image.zcool.com.cn/59/54/m_1303967870670.jpg",
			"http://image.zcool.com.cn/47/19/1280115949992.jpg",
			"http://image.zcool.com.cn/59/11/m_1303967844788.jpg" };

	/** ------------------------常量 start---------------------------- **/

	/** 闪屏持续时间 单位：毫秒 **/
	public final static int TIME_DURATION_SPALSH = 2000;
	/** 全屏广告持续时间 单位：毫秒 **/
	public final static int TIME_DURATION_AD = 3000;
	/** 程序异常延迟时间 单位：毫秒 **/
	public final static int TIME_DURATION_EXCEPTION = 3000;
	/** 按钮连续点击最低间隔时间 单位：毫秒 **/
	public final static int TIME_INTERVAL_CLICK = 500;
	/** 连续点击退出最低间隔时间 单位：毫秒 **/
	public final static int TIME_INTERVAL_EXIT = 2500;
	/** 防止键盘露底延迟时间-单位：毫秒 **/
	public static final int TIME_DELAYED_KEYBOARD = 150;
	/** 分页加载时，每页显示个数 **/
	public final static int LIST_PAGE_SIZE = 10;

	/** ------------------------常量 end---------------------------- **/

}
