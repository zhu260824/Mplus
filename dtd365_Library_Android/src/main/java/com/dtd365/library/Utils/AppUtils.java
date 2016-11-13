package com.dtd365.library.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-25
 * 
 *         this utils is to handle with current app settings informations
 */
public class AppUtils {

	// get value of application versioncode
	public static long getAppVersionCode(Context context)
			throws NameNotFoundException {
		long versionCode = context.getPackageManager().getPackageInfo(
				getAppPackageName(context), 0).versionCode;
		return versionCode;
	}

	// get value of application versionname
	public static String getAppVersionName(Context context)
			throws NameNotFoundException {
		String versionName = context.getPackageManager().getPackageInfo(
				getAppPackageName(context), 0).versionName;
		return versionName;
	}

	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}
	// get value of mobile phone os versioncode
	public static String getPhoneOSVersionCode() {
		return android.os.Build.VERSION.RELEASE;
	}

	// get value of mobile device model
	public static String getClientModel() {
		return android.os.Build.MODEL;
	}

	// get value of packagename of app
	public static String getAppPackageName(Context context)
			throws NameNotFoundException {
		String packageName = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0).packageName;
		return packageName;
	}

	// get value of mobile device id
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	// get current device screen height
	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	// get current mobile phone number
	public static String getPhoneNumber(Context context) {
		TelephonyManager tel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mobile = tel.getLine1Number();
		if (ValueUtils.isStrNotEmpty(mobile)) {
			if (mobile.contains("+")) {
				mobile = mobile.substring(3);
			}

			if (RegexUtils.isMobile(mobile)) {
				return mobile;
			}
		}

		return mobile;
	}

	// get mobile sms code
	public static String getSMSVerifyCode(String smsBody, int digit) {
		String regEx = "[^0-9]{" + digit + "}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(smsBody.toString());
		smsBody = m.replaceAll("").trim().toString();
		return smsBody;
	}

	// get current device ip address
	public static String getPhoneMacAddress(Context context) {
		WifiManager mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}

	// get current user identifier by user's device
	public static String getUniquenessIdentifier(Context context) {
		String result = getPhoneMacAddress(context);
		if (ValueUtils.isStrNotEmpty(result)) {
			return result;
		}

		result = getPhoneNumber(context);
		if (ValueUtils.isStrNotEmpty(result)) {
			return result;
		}

		result = getIMEI(context);
		if (ValueUtils.isStrNotEmpty(result) && !RegexUtils.isIMEIEmpty(result)) {
			return result;
		}

		return result;
	}
}
