package com.mplus.mplus.paser;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class MyGetApplyPaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MyGetApplyList instance = null;
	private int type = 1;

	public MyGetApplyPaser(int type) {
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
						SerializableFactory.SaveData(SaveType.MyGetApply+ type, getData());
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
	public static MyGetApplyList GetInstance(int type) {
		try {
			String jsonstring = SerializableFactory.GetData(SaveType.MyGetApply + type);
			if (jsonstring != null) {
				instance = (MyGetApplyList) JsonUtils.fromJson(jsonstring,MyGetApplyList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}

