package com.mplus.mplus.paser.message;

/**
 * @Title:消息中心数据
 * @Description:
 * @author zhaochaoyue
 * 
 * @date 2015年9月14日
 */
public class MessageBean {
	public String msgId;// id
	public String content;// 内容
	public String title;
	public String createTime;// 消息发布时间
	public String openStatus;// 状态（0：已读，1：未读）
	private Boolean isShow = false;// 是否展开，默认没有展开
	private Boolean check = false;// 是否选中，默认没有选中

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

}
