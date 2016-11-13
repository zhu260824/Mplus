package com.dtd365.library.Utils;

import java.util.HashMap;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;

public class MobclickAgentUtils {

	public static void startPage(Context mContext) {
		MobclickAgent.onResume(mContext);
		MobclickAgent.onPageStart(mContext.getClass().getName());
	}
	
	public static void endtPage(Context mContext) {
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd(mContext.getClass().getName());
	}
	
	public static void startFragment(Object object) {
		MobclickAgent.onPageStart(object.getClass().getName());
	}
	
	public static void endFragment(Object object) {
		MobclickAgent.onPageEnd(object.getClass().getName());
	}
	
	@SuppressWarnings("deprecation")
	public static void upDataToInternet(Context mContext) {
		MobclickAgent.updateOnlineConfig(mContext);
	}
	
	public static void MobclickEventName(Context mContext,String eventId,String actionName,String actionNum) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(actionName, actionNum);
		MobclickAgent.onEvent(mContext, eventId,map);
	}
	
	public static void MobclickProfilrSiginIn(String userId){
		MobclickAgent.onProfileSignIn(userId);
	}
	public static void MobclickProfilrSiginIn(String thirdName,String userId){
		MobclickAgent.onProfileSignIn(thirdName,userId);
	}
	
	public static void stopActivityMobclick() {
		MobclickAgent.openActivityDurationTrack(false);
	}
	
	public static void startActivityMobclick() {
		MobclickAgent.openActivityDurationTrack(true);
	}
}
