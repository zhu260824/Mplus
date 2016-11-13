package com.dtd365.library.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dtd365.library.app.DtdApplication;
/**
 * @author ZL
 * 保存接口数据
 * */
public class SerializableFactory {
	public final static String MPLUS="mplus";

	/**
	 * 存数据
	 * */
	public static  void SaveData(String name,String data){
		SharedPreferences sp = DtdApplication.getInstance().getSharedPreferences(MPLUS, Context.MODE_PRIVATE);
		Editor editor= sp.edit();
		editor.putString(name,System.currentTimeMillis()+data);  
		editor.commit();  
		editor= null;
		sp= null;
	}
	
	/**
	 * 取时间
	 * */
	public static long GetTime(String name){
		long systemtime=0;
		SharedPreferences sp = DtdApplication.getInstance().getSharedPreferences(MPLUS, Context.MODE_PRIVATE);
		String data= sp.getString(name, null);
		if (data!=null && data.length()>13) {
			try {
				systemtime=Long.valueOf(data.substring(0, 13));
			} catch (Exception e) {
			}
			
		}
		return systemtime;
	}
	
	/**
	 * 取数据
	 * */
	public static String GetData(String name)
	{
		SharedPreferences sp = DtdApplication.getInstance().getSharedPreferences(MPLUS, Context.MODE_PRIVATE);
		String data= sp.getString(name, null);
		if (data!=null && data.length()>13) {
			try {
				data=data.substring(13, data.length());
			} catch (Exception e) {
			}
			
		}
		return data;
	}
	/**
	 * SharedPreferences保存数据
	 * */
	public static void sharedPreferencesSave(String name,String key,String value){
			//获得SharedPreferences对象
			SharedPreferences settings = DtdApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
			//获得可编辑对象
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(key, value);
			
			editor.commit();
		}
		/**
		 * SharedPreferences读取数据（String）
		 * */
	public static String sharedPreferencesReadString(String name,String key){
			SharedPreferences settings = DtdApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
			//第二个参数为如果SharedPreferences中没有保存就赋一个默认值
			return settings.getString(key,"");
		}
		/**
		 * SharedPreferences读取数据（int）
		 * */
	public static int sharedPreferencesReadInt(String name,String key){
			SharedPreferences settings = DtdApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
			//第二个参数为如果SharedPreferences中没有保存就赋一个默认值
			return settings.getInt(key, 0);
		}
		/**
		 * SharePreferences删除数据 1为固定值 0 为所有
		 * name 文件名称
		 * key  对象名称（可为空）
		 * type 删除类型
		 */
	public static void sharedPreferencesRemove(String name,String key,int type){
			//获得SharedPreferences对象
			SharedPreferences settings = DtdApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
			//获得可编辑对象
			SharedPreferences.Editor editor = settings.edit();
			if(type==1){
				editor.remove(key); 
			}else{
				editor.clear(); 
			}
			  editor.commit();  
		}
}
