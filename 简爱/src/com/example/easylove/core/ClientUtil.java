package com.example.easylove.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.example.easylove.base.EasyLoveApplication;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

/**
 * 客户端信息获取工具类
 * 
 * (预留)
 * 
 */
public class ClientUtil {

	public static Context context = EasyLoveApplication.context;

	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return 版本号
	 */
	public static String getAppVersion() {
		// 获取手机的包管理者
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(),
					0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取当前应用名称
	 */
	public static String getAppName() {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(),
					0);
			String appName = packInfo.applicationInfo.loadLabel(pm).toString();
			return appName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取IMEI号
	 */
	public static String getIMEI() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}

	/**
	 * 获取电话号(有可能为空)
	 */
	public static String getPhone() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String num = mTelephonyMgr.getLine1Number();

		if (TextUtils.isEmpty(num)) {
			return "";
		} else {
			return num;
		}
	}

	/**
	 * 获取手机制造商
	 */
	public static String getManufacturers() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 获取手机品牌
	 */
	public static String getBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机型号
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机操作系统
	 */
	public static String getOs() {
		return "Android " + android.os.Build.VERSION.INCREMENTAL;
	}

	/**
	 * 获取操作系统版本
	 */
	public static String getOsVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获得屏幕分辨率x(宽)
	 */
	public static int getScreenX() {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获得屏幕分辨率y(高)
	 */
	public static int getScreenY() {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 是否有蓝牙
	 */
	public static boolean getBluetooth() {
		BluetoothAdapter bluetoothadapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothadapter == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否有摄像头
	 */
	public static boolean getCamera() {
		boolean has = true;
		try {
			Camera camera = android.hardware.Camera.open();
			camera.stopPreview();
			camera.release();
		} catch (RuntimeException e) {
			e.printStackTrace();
			has = false;
		}
		return has;
	}

	/**
	 * 连接状态-暂时取不到
	 */
	public static String getConnecttype() {
		return "";
	}

	/**
	 * CPU最大频率
	 */
	public static String getMaxCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = "N/A";
		}
		return result.trim();
	}

	/**
	 * 获取CPU名字
	 */
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {

			}
			br.close();
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	/**
	 * MAC地址
	 */
	public static String getMacPath() {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();

		return info.getMacAddress();
	}

	/**
	 * 内存空间
	 */
	public static String totalRAM() {
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 0; i < arrayOfString.length; i++) {
				initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
				localBufferedReader.close();
			}
		} catch (IOException e) {

		}
		return Formatter.formatFileSize(context, initial_memory);
	}

	/**
	 * 网络制式
	 */
	public static String getNetType() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String result = "UNKNOWN";
		switch (mTelephonyMgr.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_CDMA:
			result = "CDMA";
			break;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			result = "EDGE";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			result = "EVDO_0";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			result = "EVDO_A";
			break;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			result = "GPRS";
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			result = "HSDPA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			result = "HSPA";
			break;
		case TelephonyManager.NETWORK_TYPE_IDEN:
			result = "IDEN";
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			result = "UMTS";
			break;
		}
		return result;
	}

	/**
	 * 手机制式
	 */
	public static String phoneType() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String type = "";
		switch (mTelephonyMgr.getPhoneType()) {
		case TelephonyManager.PHONE_TYPE_NONE:
			type = "NONE";
			break;
		case TelephonyManager.PHONE_TYPE_CDMA:
			type = "CDMA";
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			type = "GSM";
			break;
		}
		return type;
	}

	/**
	 * SD卡是否可用
	 */
	public static boolean existSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * SD卡可用大小(MB)
	 */
	@SuppressWarnings("deprecation")
	public static String availableBlock() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (path == null) {
			return "0";
		}
		android.os.StatFs statfs = new android.os.StatFs(path);
		// 获取可供程序使用的Block的数量
		long nAvailaBlock = statfs.getAvailableBlocks();
		// 获取SDCard上每个block的SIZE
		long nBlocSize = statfs.getBlockSize();

		return Formatter.formatFileSize(context, nAvailaBlock * nBlocSize);
	}

	/**
	 * 内部空间可用大小(MB)
	 */
	@SuppressWarnings("deprecation")
	public static String availableRom() {
		File root = Environment.getDataDirectory();
		StatFs sf = new StatFs(root.getPath());
		long blockSize = sf.getBlockSize();
		long availCount = sf.getAvailableBlocks();

		return Formatter.formatFileSize(context, blockSize * availCount);
	}

}
