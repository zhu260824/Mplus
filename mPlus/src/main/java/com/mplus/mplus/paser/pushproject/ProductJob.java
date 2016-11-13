package com.mplus.mplus.paser.pushproject;

import java.io.Serializable;

public class ProductJob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String role;// 角色名称
	public String name;// 姓名
	public int action;// 动作(0:拟请、1:已签约)
	public String desc;// 角色描述
	public String nickname;//剧中称谓
	public ProductJob(String role, String name, int action, String desc,String nickname) {
		super();
		this.role = role;
		this.name = name;
		this.action = action;
		this.desc = desc;
		this.nickname = nickname;
	}
}
