package com.mplus.mplus.paser.login;

import java.io.Serializable;
import java.util.ArrayList;

public class Province_item implements Serializable {

	/**
	 * 封装类，省级类里面嵌套个市级的
	 */
	private static final long serialVersionUID = -7396379205345176528L;

	private String provincename;
	private ArrayList<String> list;


	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
}
