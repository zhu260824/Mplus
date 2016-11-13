package com.mplus.mplus.paser.actor;

import java.io.Serializable;

public class ActorList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String customerId;
	private String name;
	private String sex;
	private String height;
	private String weight;
	private String photo;
	private String avatar;
	private String age;
	private String starFaceId;
	private String starAvatar;
	private String favoriteId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStarFaceId() {
		return starFaceId;
	}

	public void setStarFaceId(String starFaceId) {
		this.starFaceId = starFaceId;
	}

	public String getStarAvatar() {
		return starAvatar;
	}

	public void setStarAvatar(String starAvatar) {
		this.starAvatar = starAvatar;
	}
}
