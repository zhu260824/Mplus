package com.mplus.mplus.utils;

import java.util.ArrayList;

public class StringUtils {

	public static String ListToString(ArrayList<String> lists) {
		String restule = "";
		for (String string : lists) {
			restule += string + ",";
		}
		if (restule.length() > 1) {
			restule = restule.substring(0, restule.length() - 1);
		}
		return restule;
	}

	public static ArrayList<String> StringToList(String resule) {
		ArrayList<String> lists = new ArrayList<String>();
		if (resule != null && resule.length() > 1) {
			String[] resules = resule.split(",");
			for (String string : resules) {
				lists.add(string);
			}
		}
		return lists;
	}

	/**
	 * 
	 * @param type
	 *            0：省、市 1：角色 2:影片时代背景 3:影片类型 4:影片特征 5:角色模板 6:角色标签 7:投资期限 8:身高
	 *            9:合意条款支付方式 10:奖项名称 11:回款权益 12:民族 13:艺人标签 14:体重 15:年龄 16:项目阶段
	 * @param value
	 * @return
	 */
	public static String FormatDict(int type, String value) {
		switch (type) {
		case 8:
		value=	value.replace("~", ",");
		value=	value.replace("cm", "");
		value=	value.replace("以上", ",1000");
			break;
		case 14:
			value=	value.replace("~", ",");
			value=	value.replace("kg", "");
			value=	value.replace("以上", ",1000");
			break;
		case 15:
			value=	value.replace("~", ",");
			value=	value.replace("以上", ",1000");
			break;
		default:
			break;
		}
		return value;
	}
}
