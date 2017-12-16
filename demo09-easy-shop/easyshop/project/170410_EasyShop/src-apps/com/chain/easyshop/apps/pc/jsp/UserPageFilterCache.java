package com.chain.easyshop.apps.pc.jsp;

public class UserPageFilterCache {

	private int userId;

	private double fromPrice;
	private double toPrice;

	public UserPageFilterCache(int userId) {
		this.userId = userId;
	}

	public double getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(double fromPrice) {
		this.fromPrice = fromPrice;
	}

	public double getToPrice() {
		return toPrice;
	}

	public void setToPrice(double toPrice) {
		this.toPrice = toPrice;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
