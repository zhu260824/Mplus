package com.mplus.mplus.paser.common;

import java.util.ArrayList;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class DictStringPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<String> instance=null;
	private int type=1;
	
	public DictStringPaser(int type) {
		super();
		this.type = type;
	}

	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
						instance =(ArrayList<String>) JsonUtils.json2List(getData(),String.class);
						SerializableFactory.SaveData(SaveType.Dicts+type, getData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			parseError("");
		}
	}

	public static ArrayList<String> GetInstance(int type) {
			try {
				String jsonstring= SerializableFactory.GetData(SaveType.Dicts+type);
				instance =(ArrayList<String>) JsonUtils.json2List(jsonstring,String.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return instance;
	}
}
