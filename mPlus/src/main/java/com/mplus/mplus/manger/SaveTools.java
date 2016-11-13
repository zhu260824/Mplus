package com.mplus.mplus.manger;

import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.utils.LogTool;

public class SaveTools {

	public static void upDateUser (User user){
		LogTool.showMsgILog(JsonUtils.toJson(user));
		SerializableFactory.SaveData(SaveType.UserData, JsonUtils.toJson(user));
	}
	
}
