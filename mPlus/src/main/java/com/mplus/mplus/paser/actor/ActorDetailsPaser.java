package com.mplus.mplus.paser.actor;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class ActorDetailsPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ActorDetails instance=null;
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =(ActorDetails) JsonUtils.fromJson(getData(),ActorDetails.class);
					SerializableFactory.SaveData(SaveType.ActorDetails, getData());
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
	public static ActorDetails GetInstance() {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.ActorDetails);
			if (jsonstring!=null) {
				instance =(ActorDetails) JsonUtils.fromJson(jsonstring,ActorDetails.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
}
