package com.example.easylove.core;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.example.easylove.core.NetService.ResultListener;


/**
 * 
 * (预留)
 *
 */
public abstract class BaseEngine implements ResultListener {

	protected NetService mNetService;
	// 请求ID，每次请求时变化
	private int requestId;
	// 接口返回成功后表示
	public String RESULT_SUCCEED = "加载成功";

	protected BaseEngine() {
		mNetService = new NetService(this);
	}

	/**
	 * 返回body
	 * 
	 * @param body
	 *            null连接失败
	 */
	protected abstract void onHandleComplete(String result);

	protected String verifyResult(String result) {
		if (TextUtils.isEmpty(result)) {
			return "连接服务器失败";
		} else {
			try {
				JSONObject jo = new JSONObject(result);

				int status = jo.getInt("status");
				if (status != 0) {
					String msg = jo.getString("msg");
					return TextUtils.isEmpty(msg) ? "未知失败原因" : msg;
				}

			} catch (JSONException e) {
				e.printStackTrace();
				return "数据解析失败";
			}
		}

		return RESULT_SUCCEED;
	}

	protected int verifyStatus(String result) {
		if (TextUtils.isEmpty(result)) {
			return -1;
		} else {
			try {
				JSONObject jo = new JSONObject(result);

				int status = jo.getInt("status");
				return status;

			} catch (JSONException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

	/**
	 * 发送异步请求
	 * 
	 * @param url
	 * @param content
	 */
	protected void doAsynPostRequest(String url, Map<String, String> parmMap) {
		mNetService.doAsynPostRequest(++requestId, url, parmMap);
	}

	/**
	 * 发送异步请求
	 * 
	 * @param url
	 * @param content
	 */
	protected void doAsynGetRequest(String url, Map<String, String> parmMap) {
		mNetService.doAsynGetRequest(++requestId, url, parmMap);
	}

	@Override
	public void onNetResult(int id, String retContent) {
		onHandleComplete(retContent);
	}

}
