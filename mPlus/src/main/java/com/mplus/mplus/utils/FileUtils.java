package com.mplus.mplus.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

public class FileUtils {
	/**
	 * Get MD5 of one file:hex string,test OK!
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/***
	 * Get MD5 of one file！test ok!
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileMD5(String filepath) {
		File file = new File(filepath);
		return getFileMD5(file);
	}

	/***
	 * compare two file by Md5
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static boolean isSameMd5(File file1, File file2) {
		String md5_1 = getFileMD5(file1);
		String md5_2 = getFileMD5(file2);
		return md5_1.equals(md5_2);
	}

	/***
	 * compare two file by Md5
	 * 
	 * @param filepath1
	 * @param filepath2
	 * @return
	 */
	public static boolean isSameMd5(String filepath1, String filepath2) {
		File file1 = new File(filepath1);
		File file2 = new File(filepath2);
		return isSameMd5(file1, file2);
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean isExternalStorageAvaliable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}

	}

	/* 在手机上打开文件 */
	public static void openFile(Context context, File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		/* 调用getMIMEType()来取得MimeType */
		String type = getMIMEType(f);
		/* 设定intent的file与MimeType */
		intent.setDataAndType(Uri.fromFile(f), type);
		context.startActivity(intent);
	}

	/* 判断文件MimeType的method */
	@SuppressLint("DefaultLocale")
	public static String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		/* 取得扩展名 */
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
		/* 按扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
				|| end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
				|| end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		/* 如果无法直接打开，就跳出软件清单给使用者选择 */
		if (!end.equals("apk")) {
			type += "/*";
		}
		return type;
	}

	public static String savaBitmapToFile(Context mContext,Bitmap bitmap, String filename) {
		if (TextUtils.isEmpty(filename))
			filename = getAppSaveImageOnSDCard(mContext);
		File fe = new File(filename);
		FileOutputStream fOut = null;
		try {
			fe.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			fOut = new FileOutputStream(fe);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public static void deleteFile(String filename) {
		if (TextUtils.isEmpty(filename)) 
			return;
		File file=new File(filename);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i].getAbsolutePath()); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			//文件不存在
		}
	}

	
	@SuppressLint("NewApi")
	public static String getUriPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}
	
	public static String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}
	
	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getAppSaveImage(Context mContext){
		String appPath=mContext.getCacheDir().getAbsolutePath();
		String appDCIM=appPath+"/DCIM";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
		String cutnameString = dateFormat.format(date);
		String imageParh="/"+cutnameString+".jpg";
		File file = new File(appDCIM);  
	    if (!file.exists()) {  
	        try {  
	            //按照指定的路径创建文件夹  
	            file.mkdirs();  
	         } catch (Exception e) {  
	               // TODO: handle exception  
	         }  
	     }  
	    file = new File(appDCIM, imageParh); 
        try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getAppSaveImageOnSDCard(Context mContext){
		String appPath=Environment.getExternalStorageDirectory().getAbsolutePath();
		String appDCIM=appPath+"/Android/data/"+mContext.getApplicationContext().getPackageName()+"/files/DCIM";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
		String cutnameString = dateFormat.format(date);
		String imageParh="/"+cutnameString+".jpg";
		File file = new File(appDCIM);  
	    if (!file.exists()) {  
	        try {  
	            //按照指定的路径创建文件夹  
	            file.mkdirs();  
	         } catch (Exception e) {  
	               // TODO: handle exception  
	         }  
	     }  
	    file = new File(appDCIM, imageParh); 
        try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
}
