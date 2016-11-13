package com.mplus.mplus.paser.pushproject;

import java.io.Serializable;

public class RoleType  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String role;
	public String type;
	public boolean isselced=false;
	
	public RoleType() {
		super();
	}
	
	public RoleType(String role, String type, boolean isselced) {
		super();
		this.role = role;
		this.type = type;
		this.isselced = isselced;
	}

}
