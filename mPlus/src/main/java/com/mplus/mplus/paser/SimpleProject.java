package com.mplus.mplus.paser;

import java.io.Serializable;

public class SimpleProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String mproductid;// 项目ID
	public String mproductname;// 项目名称
	public String shortcut;// 项目封面
	public String filmtimes;// 时代背景
	public String filmtype;// 影片类别
	public String filmtag;// 影片特征
	public String stage;// 作品状态
	public String projectnum;// 合意数
	public String funds;// 募集资金
	public String confernum;// 合意人数
	public String filmformat;// 影片类型
	public String license;// 许可证编号
	public String summary;// 故事概括
	public String points;// 项目亮点
	public String budget;// 投资预算
	public String shootingtime;// 开机时间
	public String wraptime;// 杀青时间
	public String completetime;// 成片时间
	public String showtime;// 上映时间
	public String location;// 拍摄地点
	public String status;// 审核状态
	public boolean attention=false;// 是否已关注
	public String sharelink;// 分享链接
	public String invests;// 投资总额
	public String jobs;// 职位数
	public String age;// 年龄
	public String sex;// 性别
	public String horoscope;// 星座
	public String city;// 市
	public String province;// 省
	public String roles;// 角色
	public String customerid;// 艺人ID
	public String initiatorname;// 发起人名称
	public String usershortcut;// 发起人图像
	public String productUserId;// 发起人id
	public String actorSignCount;//已签约人数
	public String actorConsensusCount;//合意人数
	public String fundConsensusCount;//投资总额
	public String director;//导演
	public String producer;//制片人
	public String actorteam;//演员阵容    男一号、女一号、男二号、女二号） 多个,分隔
	public String applyfornumber;//申请数
	public String plannum;
	public String applynum;
	
}
