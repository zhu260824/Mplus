package com.mplus.mplus.paser.actor;

import java.util.ArrayList;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.common.Page;

public class ProjectJobListPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<ProjectJobList> instance=null;

	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					instance =(ArrayList<ProjectJobList>) JsonUtils.json2List(getData(),ProjectJobList.class);
					SerializableFactory.SaveData(SaveType.ProjectJobList, getData());
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
	public static ArrayList<ProjectJobList> GetInstance() {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.ProjectJobList);
			if (jsonstring!=null) {
				instance =(ArrayList<ProjectJobList>) JsonUtils.json2List(jsonstring,ProjectJobList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	
	
	

}
