package com.mplus.mplus.paser.project;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class ProjectNameListPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ProjectNameList instance = null;
	private int type = 1;

	public ProjectNameListPaser(int type) {
		super();
		this.type = type;
	}

	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					if (getData() != null) {
//						instance = (ProjectNameList) JsonUtils.fromJson(getData(),ProjectNameList.class);
						SerializableFactory.SaveData(SaveType.ProjectNameList+ type, getData());
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
	public static ProjectNameList GetInstance(int type) {
		try {
			String jsonstring = SerializableFactory.GetData(SaveType.ProjectNameList + type);
			if (jsonstring != null) {
				instance = (ProjectNameList) JsonUtils.fromJson(jsonstring,ProjectNameList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}
