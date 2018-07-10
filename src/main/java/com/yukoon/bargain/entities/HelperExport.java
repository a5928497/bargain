package com.yukoon.bargain.entities;

public class HelperExport {


	//活动宿主手机号
	private String username;
	//帮助者手机号
	private String helperName;
	//帮助者砍价价值
	private Double bargainPrice;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHelperName() {
		return helperName;
	}

	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	public Double getBargainPrice() {
		return bargainPrice;
	}

	public void setBargainPrice(Double bargainPrice) {
		this.bargainPrice = bargainPrice;
	}
}
