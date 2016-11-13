package com.mplus.mplus.paser;

import java.io.Serializable;
import java.util.ArrayList;

import com.mplus.mplus.paser.common.Page;

public class AlternativeImaginedFigureList implements Serializable {
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
		private String name;
		private String height;
		private String weight;
		private String customerId;
		private String userId;
		private String avatar;
		private String planName;
		private String sex;
		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getPlanName() {
			return planName;
		}

		public void setPlanName(String planName) {
			this.planName = planName;
		}
	}
}
