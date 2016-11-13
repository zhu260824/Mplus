package com.mplus.mplus.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class LogTool {

	private final static String TAG = "mplus";
	
	public static String getTag() {
		return TAG;
	}

	public static void showMsgILog(String msg) {
		Log.i(TAG, msg);
	}

	public static void showMsgDLog(String msg) {
		Log.d(TAG, msg);
	}

	public static void showMsgELog(String msg) {
		Log.e(TAG, msg);
	}

	public static void showMsgWLog(String msg) {
		Log.w(TAG, msg);
	}

	public static void showMsgVLog(String msg) {
		Log.v(TAG, msg);
	}

	public static void showMapILog(String key, String valuse) {
		Log.i(TAG, key + "--->" + valuse);
	}

	public static void showMapDLog(String key, String valuse) {
		Log.d(TAG, key + "--->" + valuse);
	}

	public static void showMapELog(String key, String valuse) {
		Log.e(TAG, key + "--->" + valuse);
	}

	public static void showMapWLog(String key, String valuse) {
		Log.w(TAG, key + "--->" + valuse);
	}

	public static void showMapVLog(String key, String valuse) {
		Log.v(TAG, key + "--->" + valuse);
	}

	public static void showArrayListILog(List<String> dataList) {
		String log = "";
		for (Object object : dataList) {
			log = log + object.toString() + ",";
		}
		Log.i(TAG, log);
	}

	public static void showArrayListDLog(ArrayList<Object> list) {
		String log = "";
		for (Object object : list) {
			log = log + object.toString() + ",";
		}
		Log.d(TAG, log);
	}

	public static void showArrayListELog(ArrayList<Object> list) {
		String log = "";
		for (Object object : list) {
			log = log + object.toString() + ",";
		}
		Log.e(TAG, log);
	}

	public static void showArrayListWLog(ArrayList<Object> list) {
		String log = "";
		for (Object object : list) {
			log = log + object.toString() + ",";
		}
		Log.w(TAG, log);
	}

	public static void showArrayListVLog(ArrayList<Object> list) {
		String log = "";
		for (Object object : list) {
			log = log + object.toString() + ",";
		}
		Log.v(TAG, log);
	}

	public static void showMapsListILog(Map<Object, Object>  maps) {
		String log = "";
		for (Map.Entry<Object, Object> entry : maps.entrySet()) {
			log=log+"<--"+entry.getKey().toString()+"--->"+entry.getValue().toString()+",";
		}
		Log.i(TAG, log);
	}
	
	public static void showMapsListDLog(Map<Object, Object>  maps) {
		String log = "";
		for (Map.Entry<Object, Object> entry : maps.entrySet()) {
			log=log+"<--"+entry.getKey().toString()+"--->"+entry.getValue().toString()+",";
		}
		Log.d(TAG, log);
	}
	
	public static void showMapsListELog(Map<Object, Object>  maps) {
		String log = "";
		for (Map.Entry<Object, Object> entry : maps.entrySet()) {
			log=log+"<--"+entry.getKey().toString()+"--->"+entry.getValue().toString()+",";
		}
		Log.e(TAG, log);
	}
	
	public static void showMapsListWLog(Map<Object, Object>  maps) {
		String log = "";
		for (Map.Entry<Object, Object> entry : maps.entrySet()) {
			log=log+"<--"+entry.getKey().toString()+"--->"+entry.getValue().toString()+",";
		}
		Log.w(TAG, log);
	}
	
	public static void showMapsListVLog(Map<Object, Object>  maps) {
		String log = "";
		for (Map.Entry<Object, Object> entry : maps.entrySet()) {
			log=log+"<--"+entry.getKey().toString()+"--->"+entry.getValue().toString()+",";
		}
		Log.v(TAG, log);
	}
}
