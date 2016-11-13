package com.dtd365.library.Utils;

import android.R.integer;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * @author zhaochaoyue
 * @create 2014-6-26
 * 
 * @description : this utils is used to deal with network such as net is
 *              available or not and so.
 */
public class NetworkUtils {

	/**
	 * @author zcy
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param
	 * @return boolean
	 * @description : 当前网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivity) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (null != info) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param
	 * @return boolean
	 * @description : WIFI是否可用
	 */
	public static boolean isWifiEnabled(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param
	 * @return boolean
	 * @description : 当前网络是否是wifi类型的
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (null != activeNetInfo
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param
	 * @return boolean
	 * @description : 当前网络是否是移动网络的
	 */
	public static boolean is3G(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/*
	 * List of Common HTTP Status Codes 200 OK 300 Multiple Choices 301 Moved
	 * Permanently 302 Found 304 Not Modified 307 Temporary Redirect 400 Bad
	 * Request 401 Unauthorized 403 Forbidden 404 Not Found 410 Gone 500
	 * Internal Server Error 501 Not Implemented 503 Service Unavailable 550
	 * Permission denied
	 */
	public static String VolleyErrorMessage(int StatusCodes) {
		String messageString = "";
		switch (StatusCodes) {
		case 300:

			break;
		case 301:

			break;
		case 302:

			break;
		case 304:

			break;
		case 307:

			break;
		case 400:

			break;
		case 401:

			break;
		case 403:

			break;
		case 404:

			break;

		case 410:

			break;

		case 500:

			break;
		case 501:

			break;
		case 503:

			break;
		case 510:

			break;

		default:
			break;
		}

		return "";
	}

}
