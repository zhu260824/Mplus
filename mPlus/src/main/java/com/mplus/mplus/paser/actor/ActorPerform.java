package com.mplus.mplus.paser.actor;

import java.io.Serializable;

public class ActorPerform implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String materialUrl;
	private String time;
	private String id;
	private String version;
	private String detailDesc;
	private String role;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMaterialUrl() {
		return materialUrl;
	}

	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDetailDesc() {
		return detailDesc;
	}

	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
