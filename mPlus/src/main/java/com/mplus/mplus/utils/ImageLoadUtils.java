package com.mplus.mplus.utils;

import com.mplus.mplus.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;

public class ImageLoadUtils {

	public static DisplayImageOptions getImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.pic_default)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.pic_default)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.pic_default)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}

	public static DisplayImageOptions getPicOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.person_default)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.person_default)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.person_default)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}
	
	public static DisplayImageOptions getMainPersionOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.main_persion_def)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.main_persion_def)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.main_persion_def)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}
	
	public static DisplayImageOptions getActorHistoryOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.def_actor_history)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.def_actor_history)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.def_actor_history)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}
	
	
	
	public static DisplayImageOptions getImageProject() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.movie_project)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.movie_project)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.movie_project)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}
	public static DisplayImageOptions getLongImageProject() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.movie_project)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.movie_project)// 设置错误时候显示的图片
			.showImageOnLoading(R.drawable.movie_project)//设置图片在下载期间显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();
		return options;
	}
}
