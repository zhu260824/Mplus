package com.mplus.mplus.paser.project;

import java.io.Serializable;
import java.util.ArrayList;

import com.mplus.mplus.paser.common.Page;

public class UserAddJobList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page page;
	private ArrayList<contents> contents;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ArrayList<contents> getContents() {
		return contents;
	}

	public void setContents(ArrayList<contents> contents) {
		this.contents = contents;
	}

	public class contents implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String planName;
		private String location;
		private String nickname;
		private String mproductId;
		private String shootingTime;
		private String filmType;
		private String planId;
		private String mproductName;
		private String planDesc;
		private String shortcut;
		private String planHeight;
		private String planWeight;
		private String planAge;
		private String stage;
		private String screenwriter;
		private String director;

		public String getPlanName() {
			return planName;
		}

		public void setPlanName(String planName) {
			this.planName = planName;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getMproductId() {
			return mproductId;
		}

		public void setMproductId(String mproductId) {
			this.mproductId = mproductId;
		}

		public String getShootingTime() {
			return shootingTime;
		}

		public void setShootingTime(String shootingTime) {
			this.shootingTime = shootingTime;
		}

		public String getFilmType() {
			return filmType;
		}

		public void setFilmType(String filmType) {
			this.filmType = filmType;
		}

		public String getPlanId() {
			return planId;
		}

		public void setPlanId(String planId) {
			this.planId = planId;
		}

		public String getMproductName() {
			return mproductName;
		}

		public void setMproductName(String mproductName) {
			this.mproductName = mproductName;
		}

		public String getPlanDesc() {
			return planDesc;
		}

		public void setPlanDesc(String planDesc) {
			this.planDesc = planDesc;
		}

		public String getShortcut() {
			return shortcut;
		}

		public void setShortcut(String shortcut) {
			this.shortcut = shortcut;
		}

		public String getPlanHeight() {
			return planHeight;
		}

		public void setPlanHeight(String planHeight) {
			this.planHeight = planHeight;
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

		public String getStage() {
			return stage;
		}

		public void setStage(String stage) {
			this.stage = stage;
		}

		public String getScreenwriter() {
			return screenwriter;
		}

		public void setScreenwriter(String screenwriter) {
			this.screenwriter = screenwriter;
		}

		public String getDirector() {
			return director;
		}

		public void setDirector(String director) {
			this.director = director;
		}

	}

}
