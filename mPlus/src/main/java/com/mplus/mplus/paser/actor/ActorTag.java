package com.mplus.mplus.paser.actor;

import java.io.Serializable;

public class ActorTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private boolean isSelced = false;

	public ActorTag(String name, boolean isSelced) {
		super();
		this.name = name;
		this.isSelced = isSelced;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelced() {
		return isSelced;
	}

	public void setSelced(boolean isSelced) {
		this.isSelced = isSelced;
	}

}
