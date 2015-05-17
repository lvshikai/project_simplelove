package com.example.easylove.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;

import com.example.easylove.config.AppConfig;
import com.example.easylove.core.ClientUtil;
import com.example.easylove.util.ActivityUtil;

/**
 * 异常处理类
 * 
 * 
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = ExceptionHandler.class.getSimpleName();

	/** 本类实例 **/
	private static ExceptionHandler instance;

	/**
	 * 获取本类实例
	 */
	public synchronized static ExceptionHandler getInstance() {
		if (instance == null) {
			instance = new ExceptionHandler();
		}
		return instance;
	}

	/**
	 * 初始化
	 */
	public void init() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 处理异常，收集异常信息
		if (ex != null) {
			handleException(ex);
		}
		closeApplication(AppConfig.TIME_DURATION_EXCEPTION);
	}

	/**
	 * 异常处理
	 */
	private void handleException(Throwable e) {
		Log.e(TAG, "<----------- Exception start --------->");
		e.printStackTrace();
		Throwable cause = e.getCause();
		if (cause != null) {
			cause.printStackTrace();
		}
		Log.e(TAG, "<----------- Exception end  --------->");

		if (ClientUtil.existSDCard()) {
			saveToFile(e);
		}
	}

	/**
	 * 保存到文件中
	 * 
	 * @param e
	 * @return
	 */
	private void saveToFile(Throwable e) {
		try {
			Time time = new Time();
			time.setToNow();
			time.normalize(true);

			// 文件名格式：andorid_uid_time.txt
			/**
			 * 此处uid为登陆用户的id ,预留
			 */
			String uid = "";
			
			
			String fileName = "android_" + uid + "_"
					+ time.format("%Y%m%d%H%M%S") + ".txt";

			String mod = "手机型号:-->" + ClientUtil.getModel();
			String mos = "操作系统:-->" + ClientUtil.getOs();
			String imei = "IMEI:-->" + ClientUtil.getIMEI();
			String version = "应用版本:-->" + ClientUtil.getAppVersion();

			String msg = "原因:-->" + e.toString();
			String date = "时间:-->" + time.format("%Y-%m-%d %H:%M:%S");

			String details = "StackTrace:-------开始------->";

			String dirPath = AppConfig.LOG_PATH;
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dirPath, fileName);
			FileOutputStream fos = new FileOutputStream(file);

			BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(
					fos, "utf-8"));
			bufw.write(date);
			bufw.newLine();
			bufw.write(mod);
			bufw.newLine();
			bufw.write(mos);
			bufw.newLine();
			bufw.write(imei);
			bufw.newLine();
			bufw.write(version);
			bufw.newLine();
			bufw.write(msg);
			bufw.newLine();
			bufw.write(details);
			bufw.newLine();

			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				String line = i + "-->" + stackTrace[i].toString();
				bufw.write(line);
				bufw.newLine();
			}

			String causeInfo = "CauseBy:-------开始------->";
			bufw.write(causeInfo);
			bufw.newLine();

			Throwable cause = e.getCause();
			if (cause != null) {
				StackTraceElement[] trace1 = cause.getStackTrace();
				for (int j = 0; j < trace1.length; j++) {
					String line = j + "-->" + trace1[j].toString();
					bufw.write(line);
					bufw.newLine();
				}
			} else {
				bufw.write("无");
				bufw.newLine();
			}

			bufw.flush();
			bufw.close();
			fos.close();

			upLoadErrorLog(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 延时关闭应用，单位：毫秒
	 */
	private void closeApplication(long delayTime) {
		SystemClock.sleep(delayTime);
		ActivityUtil.getInstance().exit();
	}

	/** 上传错误日志 */
	private void upLoadErrorLog(File... files) {

	}

}
