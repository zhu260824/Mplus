package com.mplus.mplus.paser.login;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class ProvincePaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Province_item> instance=null;
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
						instance=new ArrayList<Province_item>();
						JSONArray jsonArray=new JSONArray(getData());
						for (int i = 0; i < jsonArray.length(); i++) {
							Province_item province_item=new Province_item();
							JSONObject jsonObject=jsonArray.getJSONObject(i);
							province_item.setProvincename(jsonObject.getString("proname"));
							province_item.setList((ArrayList<String>)JsonUtils.json2List(jsonObject.getString("citys"), String.class));
							instance.add(province_item);
						}
						SerializableFactory.SaveData(SaveType.Province, getData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			parseError("");
		}
	}

	public static ArrayList<Province_item> GetInstance() {
//		if (instance==null) {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.Province);
			instance=new ArrayList<Province_item>();
			JSONArray jsonArray=new JSONArray(jsonstring);
			for (int i = 0; i < jsonArray.length(); i++) {
				Province_item province_item=new Province_item();
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				province_item.setProvincename(jsonObject.getString("proname"));
				province_item.setList((ArrayList<String>)JsonUtils.json2List(jsonObject.getString("citys"), String.class));
				instance.add(province_item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		return instance;
	}
}
