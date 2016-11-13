package com.mplus.mplus.paser.project;

import java.io.Serializable;
import java.util.ArrayList;

import com.mplus.mplus.paser.common.Page;

public class JobList implements Serializable {

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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String planid;
		private String role;
		private String height;
		private String starttime;
		private String endtime;
		private String roledesc;
		private String nickname;
		private String version;
		private String feature;
		private String mproductid;
		private String mproductname;
		private String shortcut;
		private String points;
		private String weight;
		private String age;
		private String paycheck;
		
		private int needrelatedexp;//1需要0不需要

		public String getPlanid() {
			return planid;
		}

		public void setPlanid(String planid) {
			this.planid = planid;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getStarttime() {
			return starttime;
		}

		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public String getRoledesc() {
			return roledesc;
		}

		public void setRoledesc(String roledesc) {
			this.roledesc = roledesc;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getFeature() {
			return feature;
		}

		public void setFeature(String feature) {
			this.feature = feature;
		}

		public String getMproductid() {
			return mproductid;
		}

		public void setMproductid(String mproductid) {
			this.mproductid = mproductid;
		}

		public String getMproductname() {
			return mproductname;
		}

		public void setMproductname(String mproductname) {
			this.mproductname = mproductname;
		}

		public String getShortcut() {
			return shortcut;
		}

		public void setShortcut(String shortcut) {
			this.shortcut = shortcut;
		}

		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public int getNeedrelatedexp() {
			return needrelatedexp;
		}

		public void setNeedrelatedexp(int needrelatedexp) {
			this.needrelatedexp = needrelatedexp;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getPaycheck() {
			return paycheck;
		}

		public void setPaycheck(String paycheck) {
			this.paycheck = paycheck;
		}
		
	}
}
