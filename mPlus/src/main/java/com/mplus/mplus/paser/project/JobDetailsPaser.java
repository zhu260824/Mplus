package com.mplus.mplus.paser.project;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class JobDetailsPaser extends BaseParser{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JobDetails instance=null;
	
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
//					instance =JsonUtils.fromJson(getData(),JobDetails.class);
					SerializableFactory.SaveData(SaveType.JobDetails, getData());
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
	public static JobDetails GetInstance() {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.JobDetails);
			instance =JsonUtils.fromJson(jsonstring,JobDetails.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
}
