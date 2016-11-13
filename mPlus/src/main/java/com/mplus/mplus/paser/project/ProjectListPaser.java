package com.mplus.mplus.paser.project;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class ProjectListPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ProjectList instance=null;
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =(ProjectList) JsonUtils.fromJson(getData(),ProjectList.class);
					SerializableFactory.SaveData(SaveType.ProjectList, getData());
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
	public static ProjectList GetInstance() {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.ProjectList);
			if (jsonstring!=null) {
				instance =(ProjectList) JsonUtils.fromJson(jsonstring,ProjectList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}
