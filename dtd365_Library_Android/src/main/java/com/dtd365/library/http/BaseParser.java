package com.dtd365.library.http;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * @author ZL
 * 解析基础类
 * */
public class BaseParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String SUCCESS="success";
	public final static String MSG="msg";
	public final static String DATA="data";
	/**
	 * 服务器端返回信息
	 */
	private String Msg;
	private String Data;
	private boolean Success=false;

	/**
	 * 判断接口调用是否成功
	 */
	public boolean getResultSuccess() {
		return Success;
	}
	
	/**
	 * 如果调用失败，通过此方法获取错误消息
	 */
	public String getResponseMsg(){
		return Msg;
	}
	
	public String getData() {
		return Data;
	}

	public void parse(JSONObject jsonObject){
		if (jsonObject==null) {
			return;
		}
		try {
			Msg=jsonObject.isNull(MSG)?null:jsonObject.getString(MSG);
			Success=jsonObject.isNull(SUCCESS)?null:jsonObject.getBoolean(SUCCESS);
			Data=jsonObject.isNull(DATA)?null:jsonObject.getString(DATA);
		} catch (JSONException e) {
			e.printStackTrace();
			parseError(jsonObject.toString());
		}
	}
	
	/**
	 * 解析出现错误
	 */
	protected void parseError(String jsonStr) {
		Success = false;
		Msg = "数据解析异常，"+jsonStr;
	}
	
}
