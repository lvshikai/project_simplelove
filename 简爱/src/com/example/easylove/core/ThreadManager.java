package com.example.easylove.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程沲管理
 * 
 * (预留)
 * 
 */
public class ThreadManager {
	
	/** 本类实例 **/
	public static ThreadManager instance;
	/** 线程池线程个数 **/
	private final int mThreadCount = 10;
	/** 线程池 **/
	private ExecutorService mThreadPool;

	/** 私有构造方法 **/
	private ThreadManager() {
		mThreadPool = Executors.newFixedThreadPool(mThreadCount);
	}

	/**
	 * 返回实例
	 * 
	 * @return
	 */
	public synchronized static ThreadManager getInstance() {
		if (instance == null) {
			instance = new ThreadManager();
		}
		return instance;
	}

	/**
	 * 返回线程池
	 * 
	 * @return
	 */
	public ExecutorService getExecutorService() {
		return mThreadPool;
	}
}
