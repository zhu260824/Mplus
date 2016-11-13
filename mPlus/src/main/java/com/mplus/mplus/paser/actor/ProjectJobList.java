package com.mplus.mplus.paser.actor;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectJobList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mproductName;
	private ArrayList<plans> plans;
	private String mproductId;

	public String getMproductName() {
		return mproductName;
	}

	public void setMproductName(String mproductName) {
		this.mproductName = mproductName;
	}

	public ArrayList<plans> getPlans() {
		return plans;
	}

	public void setPlans(ArrayList<plans> plans) {
		this.plans = plans;
	}

	public String getMproductId() {
		return mproductId;
	}

	public void setMproductId(String mproductId) {
		this.mproductId = mproductId;
	}

	public class plans implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String planName;
		private String planId;
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getPlanId() {
			return planId;
		}
		public void setPlanId(String planId) {
			this.planId = planId;
		}
		

	}

}
