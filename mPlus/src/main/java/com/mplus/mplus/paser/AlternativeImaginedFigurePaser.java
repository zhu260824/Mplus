package com.mplus.mplus.paser;

import org.json.JSONObject;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.manger.SaveType;

public class AlternativeImaginedFigurePaser extends BaseParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AlternativeImaginedFigureList instance = null;
	private int type = 1;

	public AlternativeImaginedFigurePaser(int type) {
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
						SerializableFactory.SaveData(SaveType.AlternativeImaginedFigure+ type, getData());
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
	public static AlternativeImaginedFigureList GetInstance(int type) {
		try {
			String jsonstring = SerializableFactory.GetData(SaveType.AlternativeImaginedFigure + type);
			if (jsonstring != null) {
				instance = (AlternativeImaginedFigureList) JsonUtils.fromJson(jsonstring,AlternativeImaginedFigureList.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}

