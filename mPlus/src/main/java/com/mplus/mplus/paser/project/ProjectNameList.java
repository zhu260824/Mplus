package com.mplus.mplus.paser.project;

import java.io.Serializable;
import java.util.ArrayList;

import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.project.JobList.contents;

public class ProjectNameList implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<contents> contents;
	private Page page;

	public ArrayList<contents> getContents() {
		return contents;
	}

	public void setContents(ArrayList<contents> contents) {
		this.contents = contents;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public class contents implements Serializable {

		private static final long serialVersionUID = 1L;
		private int needRelatedExp;//是否需要拍摄经验 0否1是
		private String planId;//职位id
		private String planName;//职位名称
		private String nickName;//剧中饰演
		private String planDesc;//人物小转
		private String planHeight;//职位身高
		private String planFeature;//职位类型
		private String scheduleStartTime;//档期开始
		private String scheduleEndTime;//档期结束
		private String planOptionNum;//备选影人数
		private String planApplyNum;//收到
		private String paycheck;//片酬
		private String planWeight;//体重
		private String planAge;//年纪
		public String getPaycheck() {
			return paycheck;
		}
		public void setPaycheck(String paycheck) {
			this.paycheck = paycheck;
		}
		public String getPlanWeight() {
			return planWeight;
		}
		public void setPlanWeight(String planWeight) {
			this.planWeight = planWeight;
		}
		public String getPlanAge() {
			return planAge;
		}
		public void setPlanAge(String planAge) {
			this.planAge = planAge;
		}
		private boolean isSelced=false;
		public int getNeedRelatedExp() {
			return needRelatedExp;
		}
		public void setNeedRelatedExp(int needRelatedExp) {
			this.needRelatedExp = needRelatedExp;
		}
		public String getPlanId() {
			return planId;
		}
		public void setPlanId(String planId) {
			this.planId = planId;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getPlanDesc() {
			return planDesc;
		}
		public void setPlanDesc(String planDesc) {
			this.planDesc = planDesc;
		}
		public String getPlanHeight() {
			return planHeight;
		}
		public void setPlanHeight(String planHeight) {
			this.planHeight = planHeight;
		}
		public String getPlanFeature() {
			return planFeature;
		}
		public void setPlanFeature(String planFeature) {
			this.planFeature = planFeature;
		}
		public String getScheduleStartTime() {
			return scheduleStartTime;
		}
		public void setScheduleStartTime(String scheduleStartTime) {
			this.scheduleStartTime = scheduleStartTime;
		}
		public String getScheduleEndTime() {
			return scheduleEndTime;
		}
		public void setScheduleEndTime(String scheduleEndTime) {
			this.scheduleEndTime = scheduleEndTime;
		}
		public String getPlanOptionNum() {
			return planOptionNum;
		}
		public void setPlanOptionNum(String planOptionNum) {
			this.planOptionNum = planOptionNum;
		}
		public String getPlanApplyNum() {
			return planApplyNum;
		}
		public void setPlanApplyNum(String planApplyNum) {
			this.planApplyNum = planApplyNum;
		}
		public boolean isSelced() {
			return isSelced;
		}
		public void setSelced(boolean isSelced) {
			this.isSelced = isSelced;
		}
		
	}
}
