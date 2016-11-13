package com.mplus.mplus.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class SystemsTool {
	/**
	 * 获取app版本名
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String  getVersionName(Context context){
		String  versionName = "1.0";
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	/**
	 * 获取app版本号
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static int  getVersionCode(Context context){
		int  versionCode = 1;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}
}
