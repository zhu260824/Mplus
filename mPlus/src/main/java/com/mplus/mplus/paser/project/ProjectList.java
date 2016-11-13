package com.mplus.mplus.paser.project;

import java.io.Serializable;
import java.util.ArrayList;

import com.mplus.mplus.paser.common.Page;

public class ProjectList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Page page;
	private ArrayList<contents> contents;
	private ArrayList<top> top;

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

	public ArrayList<top> getTop() {
		return top;
	}

	public void setTop(ArrayList<top> top) {
		this.top = top;
	}

	public class contents implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String mproductname;
		private String shootingTime;
		private String stage;
		private String shortcut;
		private String filmFormat;
		private String jobCount;
		private String mproductid;
		private String director;

		public String getMproductname() {
			return mproductname;
		}

		public void setMproductname(String mproductname) {
			this.mproductname = mproductname;
		}

		public String getShootingTime() {
			return shootingTime;
		}

		public void setShootingTime(String shootingTime) {
			this.shootingTime = shootingTime;
		}

		public String getStage() {
			return stage;
		}

		public void setStage(String stage) {
			this.stage = stage;
		}

		public String getShortcut() {
			return shortcut;
		}

		public void setShortcut(String shortcut) {
			this.shortcut = shortcut;
		}

		public String getFilmFormat() {
			return filmFormat;
		}

		public void setFilmFormat(String filmFormat) {
			this.filmFormat = filmFormat;
		}

		public String getJobCount() {
			return jobCount;
		}

		public void setJobCount(String jobCount) {
			this.jobCount = jobCount;
		}

		public String getMproductid() {
			return mproductid;
		}

		public void setMproductid(String mproductid) {
			this.mproductid = mproductid;
		}

		public String getDirector() {
			return director;
		}

		public void setDirector(String director) {
			this.director = director;
		}

	}

	public class top implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String mproductid;
		private String shortcut;

		public String getMproductid() {
			return mproductid;
		}

		public void setMproductid(String mproductid) {
			this.mproductid = mproductid;
		}

		public String getShortcut() {
			return shortcut;
		}

		public void setShortcut(String shortcut) {
			this.shortcut = shortcut;
		}

	}
}
