package com.mplus.mplus.paser.actor;

import java.io.Serializable;

public class ActorPhotoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String materialUrl;
	private String id;
	private String version;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterialUrl() {
		return materialUrl;
	}

	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
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

}
