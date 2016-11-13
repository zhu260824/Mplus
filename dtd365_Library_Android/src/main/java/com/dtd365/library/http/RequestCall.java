package com.dtd365.library.http;

import java.io.Serializable;
import java.util.HashMap;

import android.content.Context;
/**
 * @author ZL
 * 请求参数
 * */
import com.dtd365.library.Utils.AppUtils;

public class RequestCall implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String UrlAction;
	private HashMap<String, Object> parms;
	private BaseParser Parser;
	private String hearder;
	private int method;

	public RequestCall() {
		super();
	}

	public RequestCall(String urlAction, HashMap<String, Object> parms,
			BaseParser parser) {
		super();
		UrlAction = urlAction;
		this.parms = parms;
		Parser = parser;
	}

	public RequestCall(String urlAction, HashMap<String, Object> parms,
			BaseParser parser, int method) {
		super();
		UrlAction = urlAction;
		this.parms = parms;
		Parser = parser;
		this.method = method;
	}

	public RequestCall(String urlAction, HashMap<String, Object> parms,
			BaseParser parser, String hearder) {
		super();
		UrlAction = urlAction;
		this.parms = parms;
		Parser = parser;
		this.hearder = hearder;
	}

	public String getUrl(Context context) {
		return AppUtils.getMetaValue(context, "APP_URL") + UrlAction;
	}

	public String getUrlAction() {
		return UrlAction;
	}

	public void setUrlAction(String urlAction) {
		UrlAction = urlAction;
	}

	public HashMap<String, Object> getParms() {
		return parms;
	}

	public void setParms(HashMap<String, Object> parms) {
		this.parms = parms;
	}

	public BaseParser getParser() {
		return Parser;
	}

	public void setParser(BaseParser parser) {
		Parser = parser;
	}

	public String getHearder() {
		return hearder;
	}

	public void setHearder(String hearder) {
		this.hearder = hearder;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

}
