package com.mplus.mplus.paser.project;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class UserAddJobListPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserAddJobList instance = null;
	private int type=1;
		
	public UserAddJobListPaser(int type) {
		super();
		this.type = type;
	}

	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					// instance =(JobList)
					// JsonUtils.fromJson(getData(),JobList.class);
					SerializableFactory.SaveData(SaveType.UserAddJobList+type, getData());
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
	public static UserAddJobList GetInstance(int type) {
		try {
			String jsonstring = SerializableFactory.GetData(SaveType.UserAddJobList+type);
			if (jsonstring != null) {
				instance = (UserAddJobList) JsonUtils.fromJson(jsonstring, UserAddJobList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	
}
