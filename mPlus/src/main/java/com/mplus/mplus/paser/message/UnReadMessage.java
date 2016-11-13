package com.mplus.mplus.paser.message;

import java.io.Serializable;

public class UnReadMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unReadMsgNum;
	private String msgSpec;

	public String getUnReadMsgNum() {
		return unReadMsgNum;
	}

	public void setUnReadMsgNum(String unReadMsgNum) {
		this.unReadMsgNum = unReadMsgNum;
	}

	public String getMsgSpec() {
		return msgSpec;
	}

	public void setMsgSpec(String msgSpec) {
		this.msgSpec = msgSpec;
	}

}
