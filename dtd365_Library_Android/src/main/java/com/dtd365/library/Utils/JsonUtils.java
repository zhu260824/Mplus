package com.dtd365.library.Utils;

import java.util.List;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
/**
 * @author ZL
 * @param JSON解析工具类
 * */
public class JsonUtils {
	/**
	 * @param <T>
	 *            泛型声明
	 * @param bean
	 *            类的实例
	 * @return JSON字符串
	 */
	public static String toJson(Object bean) {
		if (null == bean) {
			return null;
		}
		return JSON.toJSONString(bean);
	}

	/**
	 * @param <T>
	 *            泛型声明
	 * @param json
	 *            JSON字符串
	 * @param clzz
	 *            要转换对象的类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clzz) throws Exception {

		if (TextUtils.isEmpty(json)) {
			return null;
		}

		return JSON.parseObject(json, clzz);
	}

	/**
	 * 解析json使用泛型转换为对应List
	 * 
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> mclass)
			throws Exception {
		return JSON.parseArray(json, mclass);

	}
}
