package com.mplus.mplus.paser.actor;

import java.io.Serializable;

public class ActorDetails implements Serializable {

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
	private String avatar;
	private String age;
	private String signature;
	private String resume;
	private String actorFeature;// 标签
	private String introVideoName;// 视频名称
	private String introVideoCover;// 视频封面
	private String introVideoLink;// 视频链接
	private String actorShareLink;
	private String isFavorite;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}



	public String getActorFeature() {
		return actorFeature;
	}

	public void setActorFeature(String actorFeature) {
		this.actorFeature = actorFeature;
	}

	public String getIntroVideoName() {
		return introVideoName;
	}

	public void setIntroVideoName(String introVideoName) {
		this.introVideoName = introVideoName;
	}

	public String getIntroVideoCover() {
		return introVideoCover;
	}

	public void setIntroVideoCover(String introVideoCover) {
		this.introVideoCover = introVideoCover;
	}

	public String getIntroVideoLink() {
		return introVideoLink;
	}

	public void setIntroVideoLink(String introVideoLink) {
		this.introVideoLink = introVideoLink;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getActorShareLink() {
		return actorShareLink;
	}

	public void setActorShareLink(String actorShareLink) {
		this.actorShareLink = actorShareLink;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}
}
