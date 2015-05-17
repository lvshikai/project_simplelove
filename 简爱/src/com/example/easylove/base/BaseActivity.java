package com.example.easylove.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Activity 基类
 * 
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements
		OnClickListener {

	/** 上下文 **/
	protected Activity mActivity;

	/** 初始化，替代onCreate方法 **/
	protected abstract void initActivity(Bundle savedInstanceState);

	/** 吐司 **/
	protected Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = BaseActivity.this;
		initActivity(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

	}
	
	
	/**
	 * 取消toast
	 */
	public void toastDismis() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	/**
	 * 获取string
	 * 
	 * @param mRid
	 * @return
	 */
	protected String getStringMethod(int mRid) {
		return this.getResources().getString(mRid);
	}

	/**
	 * 获取demin
	 * 
	 * @param mRid
	 * @return
	 */
	protected int getDemonIntegerMethod(int mRid) {
		return (int) this.getResources().getDimension(mRid);
	}

	

	/**
	 * 显示长Toast并且修改文字但不累加
	 */
	public void lToast(String msg) {
		showToast(msg, Toast.LENGTH_LONG);
	}

	public void lToast(int msg) {
		showToast(msg, Toast.LENGTH_LONG);
	}

	/**
	 * 显示短Toast并且修改文字但不累加
	 */
	public void sToast(String msg) {
		showToast(msg, Toast.LENGTH_SHORT);
	}

	public void sToast(int msg) {
		showToast(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示Toaset
	 * 
	 * @param msg
	 *            String
	 * @param duration
	 */
	public void showToast(String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg, duration);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}

	/**
	 * 显示Toaset
	 * 
	 * @param msg
	 *            int
	 * @param duration
	 */
	public void showToast(int msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg, duration);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}

	/**
	 * 长Toast
	 */
	protected void showLongToast(String msg) {
		Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 短Toast
	 */
	protected void showShortToast(String msg) {
		Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
	}

}
