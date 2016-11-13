package com.dtd365.library.Utils;

import java.util.List;

import android.content.Context;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-25
 */
public class ValueUtils {

	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isStrEmpty(String value) {
		if (null == value || "".equals(value.trim())) {
			return true;
		} else {
			// to determine whether all full width space
			value = value.replaceAll(" ", "").trim();
			if (null == value || "".equals(value.trim())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * string  null   ‘null’时返回“”；
	 * @param value
	 * @return
	 */
	public static String StrEmpty(String value){
		if (value==null||value.equals("null")) {
			value="";
		}
		return value;
	}
	public static boolean isStrNotEmpty(String value) {
		if (null == value || "".equals(value.trim())) {
			return false;
		} else {
			// to determine whether all full width space
			value = value.replaceAll(" ", "").trim();
			if (null == value || "".equals(value.trim())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param List<?>
	 * @return boolean
	 */
	public static boolean isListNotEmpty(List<?> noteList) {
		return null != noteList && noteList.size() > 0;
	}

	public static boolean isListEmpty(List<?> noteList) {
		return null == noteList || noteList.size() == 0;
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param Object
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object object) {
		return null != object;
	}

	public static boolean isEmpty(Object object) {
		return null == object;
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param text : such as 1、hahah|2、eheheh|3、ghghhg ...
	 * @param reg : such as '|'
	 * @return string
	 */
	public static String getHtmlParamText(String text, String reg) {
		String result = "";
		if (ValueUtils.isStrEmpty(text)) {
			return result;
		}
		String[] texts = text.split("\\" + reg);
		for (int i = 0; i < texts.length; i++) {
			result += "<p>";
			result += texts[i];
			result += "</p>";
		}
		return result;
	}

	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param resId
	 * @return string
	 */
	public static String getString(Context context,int resId) {
		return null == context ? "" : context.getString(resId);
	}
}