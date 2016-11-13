package com.mplus.mplus.paser.projectdetail;

import java.io.Serializable;

public class ProjectConferList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String status;
	public String shortcut;
	public String time;
	public boolean isHeard=false;
	public boolean isEnd=false;
	public ProjectConferList(String name, String status, String shortcut,String time, boolean isHeard, boolean isEnd) {
		super();
		this.name = name;
		this.status = status;
		this.shortcut = shortcut;
		this.time = time;
		this.isHeard = isHeard;
		this.isEnd = isEnd;
	}
	
	

}
