package com.mplus.mplus.paser.common;

import java.io.Serializable;

public class AppVersion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String url;//下载地址
	public int forceupdate;//是否强制更新  0：否；1：是
	public String versionname;//版本名称
	public int versioncode;//版本号
	public String content;//更新内容
	public String md5;//文件MD5值

}
