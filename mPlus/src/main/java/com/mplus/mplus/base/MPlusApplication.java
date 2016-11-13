package com.mplus.mplus.base;

import java.io.File;

import com.dtd365.library.app.DtdApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

public class MPlusApplication extends DtdApplication {
	public static DisplayImageOptions options;
	public static boolean isLogin = false;
	public static int Width = 0;
	public static int Height = 0;
	private static String accessToken;
	private static String LoginToken;

	@Override
	public void onCreate() {
		super.onCreate();
		System.gc();
		initImageLoader(getApplicationContext());
		initScreenSize();
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		MPlusApplication.accessToken = accessToken;
	}

	public static String getLoginToken() {
		return LoginToken;
	}

	public static void setLoginToken(String loginToken) {
		LoginToken = loginToken;
	}

	@SuppressWarnings("deprecation")
	private void initScreenSize() {
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Width = wm.getDefaultDisplay().getWidth();
		Height = wm.getDefaultDisplay().getHeight();
	}

	/**
	 * 初始化ImageLoader
	 * 
	 * @param context
	 */

	private void initImageLoader(Context context) {
		// 缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,"imageloader/Cache");
		// 初始化图片加载库
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true)
				// 图片存本地
				.displayer(new FadeInBitmapDisplayer(50))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.cacheInMemory(false)// 启用内存缓存
				.build(); // 启用硬盘缓存

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
//				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
				.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 由原先的discCache -> diskCache
				.diskCache(new UnlimitedDiskCache(cacheDir))
				.diskCacheFileCount(100)
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				.defaultDisplayImageOptions(displayImageOptions) // default 
//				.writeDebugLogs() // Remove for release app
				.build();
		// 全局初始化此配置
		ImageLoader.getInstance().init(config);

	}
	
}
