package com.mplus.mplus.paser.message;

import java.util.ArrayList;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.common.Page;

public class MessageBeanPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<MessageBean> instance=null;
	private static Page page;
	private int type=1;
	
	public MessageBeanPaser(int type) {
		super();
		this.type = type;
	}
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					JSONObject jsObject=new JSONObject(getData());
					if (jsObject!=null) {
						instance =(ArrayList<MessageBean>) JsonUtils.json2List(jsObject.getString("contents"),MessageBean.class);
						page=JsonUtils.fromJson(jsObject.getString("page"), Page.class);
						SerializableFactory.SaveData(SaveType.MessageBean+type, getData());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			parseError("");
		}
	}
	/*
	 * 代码中如果需要获取到启动对象返回值时，通过此方法获取
	 */
	public static ArrayList<MessageBean> GetInstance(int type) {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.MessageBean+type);
			JSONObject jsObject=new JSONObject(jsonstring);
			if (jsObject!=null) {
				instance =(ArrayList<MessageBean>) JsonUtils.json2List(jsObject.getString("contents"),MessageBean.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static Page GetPage(int type){
		try {
			String jsonstring= SerializableFactory.GetData("MessageBean"+type);
			JSONObject jsObject=new JSONObject(jsonstring);
			if (jsObject!=null) {
				page=JsonUtils.fromJson(jsObject.getString("page"), Page.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}


}
