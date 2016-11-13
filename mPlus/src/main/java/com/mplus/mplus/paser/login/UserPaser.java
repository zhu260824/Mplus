package com.mplus.mplus.paser.login;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.SaveType;

public class UserPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static User instance=null;
	
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =JsonUtils.fromJson(getData(),User.class);
					SerializableFactory.SaveData(SaveType.UserData, getData());
					MPlusApplication.isLogin=true;
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
	public static User GetInstance() {
//		if (instance==null) {
			try {
				String jsonstring= SerializableFactory.GetData(SaveType.UserData);
				instance =JsonUtils.fromJson(jsonstring,User.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return instance;
	}
}
