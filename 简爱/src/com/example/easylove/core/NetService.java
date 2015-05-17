package com.example.easylove.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.apache.http.HttpEntity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.example.easylove.config.AppConfig;
import com.example.easylove.util.AESUtil;


/**
 * 网络连接Service
 * 
 * (预留)
 * 
 */
public class NetService {

	/** 网络连接监听 **/
	private ResultListener resultListener;
	private ExecutorService mExecutor;
	private Handler handler;
	private static final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";
	/**
	 * 构造方法
	 */
	public NetService() {

	}

	/**
	 * 异步请求构造方法
	 * 
	 * @param listener
	 *            接口响应监听
	 */
	public NetService(ResultListener resultListener) {
		this.handler = new Handler();
		this.resultListener = resultListener;
		this.mExecutor = ThreadManager.getInstance().getExecutorService();
	}

	/**
	 * 执行异步Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            请求内容
	 * 
	 * @return retContent 响应内容(null或"" 代表获取失败)
	 */
	public void doAsynGetRequest(final int id, final String url,
			final Map<String, String> parmMap) {
		mExecutor.submit(new Runnable() {
			@Override
			public void run() {
				String retContent = doGetRequest(id, url, parmMap);
				sendToMain(id, retContent);
			}
		});
	}

	/**
	 * 执行Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            请求内容
	 * @param files
	 *            需要上传的文件
	 * @return retContent 响应内容(null或"" 代表获取失败)
	 */
	public String doGetRequest(int id, String url, Map<String, String> parmMap) {
		String retContent = null;
		try {
			parmMap = addKey(parmMap);

			Log.i("--------", "-----------------------------------------------");
			Log.i("输入url   " + id, url);
			Log.i("输入参数  " + id, String.valueOf(parmMap));

			retContent = NetManager.getInstance().sendGetRequest(url, parmMap);
			Log.i("返回结果  " + id, TextUtils.isEmpty(retContent) ? "空"
					: retContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retContent;
	}

	/**
	 * 执行异步Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parmMap
	 *            请求内容
	 * 
	 * @return retContent 响应内容(null或"" 代表获取失败)
	 */
	public void doAsynPostRequest(final int id, final String url,
			final Map<String, String> parmMap) {
		mExecutor.submit(new Runnable() {
			@Override
			public void run() {
				String retContent = doPostRequest(id, url, parmMap);
				sendToMain(id, retContent);
			}
		});
	}

	/**
	 * 执行Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parmMap
	 *            请求内容
	 * @param files
	 *            需要上传的文件
	 * @return retContent 响应内容(null或"" 代表获取失败)
	 */
	public String doPostRequest(int id, String url, Map<String, String> parmMap) {
		String retContent = null;
		try {
			parmMap = addKey(parmMap);

			Log.i("--------", "-----------------------------------------------");
			Log.i("输入url   " + id, url);
			Log.i("输入参数  " + id, String.valueOf(parmMap));

			retContent = NetManager.getInstance().sendPostRequest(url, parmMap);
			Log.i("返回结果  " + id, TextUtils.isEmpty(retContent) ? "空"
					: retContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retContent;
	}

	/**
	 * 下载文件
	 * 
	 * @param sUrl
	 *            下载地址
	 * @param savePath
	 *            保存文件地址
	 * @param listener
	 *            下载监听
	 * @return File 下载的文件
	 */
	public File doDownloadFile(String sUrl, String savePath,
			DownloadListener listener) {

		HttpEntity pHttpEntity = NetManager.getInstance().doDownloadFile(sUrl);
		try {
			if (pHttpEntity != null) {
				long totalLength = pHttpEntity.getContentLength();
				listener.onTotalLength(totalLength);

				File file = new File(savePath);

				String parent = file.getParent();
				File dir = new File(parent);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(file);
				InputStream is = pHttpEntity.getContent();

				int len = 0;
				byte[] buffer = new byte[1024];
				long total = 0;

				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total += len;
					int percentage = (int) ((float) total / (float) totalLength * 100.0f);
					// int percentage = (int) (((float) total / max) * 100);
					listener.onDownloadLength(total, percentage);
				}
				fos.flush();
				fos.close();
				is.close();

				return file;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送返回结果到主线程
	 */
	private void sendToMain(final int id, final String retContent) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (resultListener != null)
					resultListener.onNetResult(id, retContent);
			}
		});
	}
	
	private Map<String, String> addKey(Map<String, String> parmMap) {
		if (parmMap == null)
			parmMap = new HashMap<String, String>();
		parmMap.put("clientid", AppConfig.CLIENT_ID);
		parmMap.put("clienttype", AppConfig.CLIENT_TYPE);
		parmMap.put("clientversion", ClientUtil.getAppVersion());
		parmMap.put("imei", ClientUtil.getIMEI());
		parmMap.put("version", AppConfig.NET_VERSION);
		try {
			parmMap.put("verifycode", URLEncoder.encode(AESUtil.encrypt(
					(AppConfig.VERIFY_CODE + System.currentTimeMillis())
							.getBytes("UTF-8"), AppConfig.PASSWORD), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}

		// parmMap.put("verifycode", AppConfig.VERIFY_CODE);

		return parmMap;
	}
	
	public interface ResultListener {
		/**
		 * 连接 回调方法
		 * 
		 * @param id
		 *            请求id
		 * @param retContent
		 *            返回内容
		 */
		void onNetResult(int id, String retContent);
	}

	/**
	 * 下载监听
	 * 
	 * @author wangxg
	 * 
	 */
	public interface DownloadListener {
		/**
		 * 总的长度
		 * 
		 * @param totalLength
		 */
		void onTotalLength(long totalLength);

		/**
		 * 
		 * @param downloadLength
		 *            已下载的长度
		 * @param percentage
		 *            百分比
		 */
		void onDownloadLength(long downloadLength, int percentage);
	}

}
