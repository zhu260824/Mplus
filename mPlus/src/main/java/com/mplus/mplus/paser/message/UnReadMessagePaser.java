package com.mplus.mplus.paser.message;

import java.util.ArrayList;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class UnReadMessagePaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<UnReadMessage> instance=null;
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					if (getData()!=null) {
						instance =(ArrayList<UnReadMessage>) JsonUtils.json2List(getData(),UnReadMessage.class);
						SerializableFactory.SaveData(SaveType.UnReadMessage, getData());
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
	public static ArrayList<UnReadMessage> GetInstance() {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.UnReadMessage);
			if (jsonstring!=null) {
				instance =(ArrayList<UnReadMessage>) JsonUtils.json2List(jsonstring,UnReadMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}
