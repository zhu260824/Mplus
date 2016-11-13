package com.mplus.mplus.paser.common;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class AppVersionPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static  AppVersion instance=null;
	
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =JsonUtils.fromJson(getData(), AppVersion.class);
					SerializableFactory.SaveData(SaveType.AppVersion, getData());
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
	public static AppVersion GetInstance() {
			try {
				String jsonstring= SerializableFactory.GetData(SaveType.AppVersion);
				if (jsonstring!=null) 
					instance =JsonUtils.fromJson(jsonstring,AppVersion.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return instance;
	}
	

}
