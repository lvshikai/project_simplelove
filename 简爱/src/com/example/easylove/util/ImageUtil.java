package com.example.easylove.util;

import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.easylove.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片工具类
 * 
 * 
 */
public class ImageUtil {

	/** 图片加载类 **/
	private static ImageLoader imageLoader;
	/** 图片加载设置 **/
	private static DisplayImageOptions options;

	/**
	 * 初始化图片工具类
	 */
	public static void initImageLoader(Context context) {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5加密
				.tasksProcessingOrder(QueueProcessingType.LIFO).build());

		options = new DisplayImageOptions.Builder()
				// 启用内存缓存
				.cacheInMemory(true)
				// 启用外存缓存
				.cacheOnDisk(true)
				// 没有图片资源时的默认图片
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// 加载失败时的图片
				.showImageOnFail(R.drawable.ic_launcher)
				// 加载图片时的图片
				.showImageOnLoading(R.drawable.ic_launcher)
				// 设置显示风格这里是圆角矩形
				// .displayer(new RoundedBitmapDisplayer(20))
				// 是否图片加载好后渐入的动画时间
				// .displayer(new FadeInBitmapDisplayer(100))
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public static void initImageLoaderCache(Context context) {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5加密
				.writeDebugLogs()
				.tasksProcessingOrder(QueueProcessingType.LIFO).build());

		options = new DisplayImageOptions.Builder()
				// 启用内存缓存
				.cacheInMemory(true)
				// 启用外存缓存
				.cacheOnDisk(true)
				// 没有图片资源时的默认图片
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// 加载失败时的图片
				.showImageOnFail(R.drawable.ic_launcher)
				// 加载图片时的图片
				.showImageOnLoading(R.drawable.ic_launcher)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true)
				// 设置显示风格这里是圆角矩形
				// .displayer(new RoundedBitmapDisplayer(20))
				// 是否图片加载好后渐入的动画时间
				// .displayer(new FadeInBitmapDisplayer(100))
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}
	
	public static Bitmap loadBitmap(String uri){
		return imageLoader.loadImageSync(uri);
	}
	
	public static Bitmap loadBitmap(String uri,DisplayImageOptions options){
		return imageLoader.loadImageSync(uri, options);
	}
	
	public static Bitmap loadBitmap(String uri,ImageSize targetImageSize){
		return imageLoader.loadImageSync(uri, targetImageSize);
	}
	
	public static Bitmap loadBitmap(String uri,ImageSize targetImageSize,DisplayImageOptions options){
		return imageLoader.loadImageSync(uri, targetImageSize,options);
	}

	/**
	 * 加载图片
	 */
	public static void load(String uri, ImageView imageView) {
		imageLoader.displayImage(uri, imageView, options);	
	}

	/**
	 * 加载图片
	 */
	public static void load(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		imageLoader.displayImage(uri, imageView, options, listener);
	}

	/**
	 * 加载图片
	 */
	public static void load(String uri, ImageView imageView, int imgRes) {
		imageLoader.displayImage(
				uri,
				imageView,
				new DisplayImageOptions.Builder().cacheInMemory(true)
						.cacheOnDisk(true).showImageOnFail(imgRes)
						.showImageOnLoading(imgRes)
						.showImageForEmptyUri(imgRes)
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
						.bitmapConfig(Bitmap.Config.RGB_565).build());
	}

}
