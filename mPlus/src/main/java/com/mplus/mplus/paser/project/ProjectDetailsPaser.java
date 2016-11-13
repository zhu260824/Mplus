package com.mplus.mplus.paser.project;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.SimpleProject;

public class ProjectDetailsPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static SimpleProject instance=null;
	
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =JsonUtils.fromJson(getData(),SimpleProject.class);
					SerializableFactory.SaveData(SaveType.ProjectDetails, getData());
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
	public static SimpleProject GetInstance() {
//		if (instance==null) {
			try {
				String jsonstring= SerializableFactory.GetData(SaveType.ProjectDetails);
				instance =JsonUtils.fromJson(jsonstring,SimpleProject.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return instance;
	}

}
