package com.mplus.mplus.paser.common;

import java.io.Serializable;

import android.text.InputType;

public class IntentEditTextData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String etcontent;
	private int etlength = 0;
	private int inputtype = InputType.TYPE_CLASS_TEXT;
	private String hint;
	private String title;

	public IntentEditTextData(String etcontent, int etlength, int inputtype, String hint, String title) {
		super();
		this.etcontent = etcontent;
		this.etlength = etlength;
		this.inputtype = inputtype;
		this.hint = hint;
		this.title = title;
	}

	public String getEtcontent() {
		return etcontent;
	}

	public void setEtcontent(String etcontent) {
		this.etcontent = etcontent;
	}

	public int getEtlength() {
		return etlength;
	}

	public void setEtlength(int etlength) {
		this.etlength = etlength;
	}

	public int getInputtype() {
		return inputtype;
	}

	public void setInputtype(int inputtype) {
		this.inputtype = inputtype;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
