package com.mplus.mplus.paser.actor;

import java.util.ArrayList;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class ActorTagPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<ActorTag> instance=null;
	private int type;
	
	public ActorTagPaser(int type) {
		super();
		this.type = type;
	}
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					SerializableFactory.SaveData(SaveType.ActorTag+type, getData());
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
	public static ArrayList<ActorTag> GetInstance(int type) {
		try {
			String jsonstring= SerializableFactory.GetData(SaveType.ActorTag+type);
			if (jsonstring!=null) {
				instance=new ArrayList<ActorTag>();
				ArrayList<String> list=(ArrayList<String>) JsonUtils.json2List(jsonstring,String.class);
				for (String string : list) {
					ActorTag tag=new ActorTag(string, false);
					instance.add(tag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
}
