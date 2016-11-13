package com.mplus.mplus.paser.login;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String province;// 省
	public String city;// 市
	public String persign;// 个性签名
	public String sex;// 性别
	public String alias;// 昵称
//	public String messages;//
	public String userid;// 用户id
	public String portraitpath;// 头像路径
	public String token;// 安全认证
	public String username;// 用户名
//	public String horoscope;// 星座
//	public String birthday;// 出生日期
	public String resume;// 个人简介
	public String realname;//真实姓名
	public String age;// 年龄
	public String email;// 邮箱
	public String emailstatus;// 邮箱状态
	public String mobile;// 手机号
	public String height;//身高cm
	public String weight;//体重kg
	public String stageName;//艺名
	public String feature;//个人标签
	public String customerid;
	public int unReadMessages;//未都信息数
//	public String roles;// 角色
//	public int veritystatus;// 认证状态
	public String version;// 版本号
	/**
	 * 1201 个人用户实名认证未通过 
	 * 1202 个人用户实名认证已通过 
	 * 1203 未进行个人用户实名认证
	 * 1204 个人用户实名认证审核中
	 * 1205个人用户行业认证未通过 1206 个人用户行业认证已通过 1207 未进行个人用户行业认证 1208 个人用户行业认证审核中
	 * 
	 * 
	 * 1203 个人用户实名认证未通过 1204 个人用户实名认证已通过 1201 未进行个人用户实名认证 1202 个人用户实名认证审核中 1207
	 * 个人用户行业认证未通过 1208 个人用户行业认证已通过 1205 未进行个人用户行业认证 1206 个人用户行业认证审核中
	 * */
}
