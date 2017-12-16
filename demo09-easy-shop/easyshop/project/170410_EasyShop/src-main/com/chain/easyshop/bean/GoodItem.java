package com.chain.easyshop.bean;

import com.chain.easyshop.extra.Money;

public class GoodItem {

	private int goodId;
	private String goodTitle;
	private Money goodPrice;
	private int restNums;
	private int sellNums;

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public String getGoodTitle() {
		return goodTitle;
	}

	public void setGoodTitle(String goodTitle) {
		this.goodTitle = goodTitle;
	}

	public double getGoodPrice() {
		return goodPrice.doubleValue();
	}

	public void setGoodPrice(double goodPrice) {
		if (this.goodPrice == null)
			this.goodPrice = new Money(goodPrice);
		else
			this.goodPrice.setValue(goodPrice);
	}

	public int getRestNums() {
		return restNums;
	}

	public void setRestNums(int restNums) {
		this.restNums = restNums;
	}

	public int getSellNums() {
		return sellNums;
	}

	public void setSellNums(int sellNums) {
		this.sellNums = sellNums;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodItem [goodId=").append(goodId).append(", goodTitle=").append(goodTitle)
				.append(", goodPrice=").append(goodPrice).append(", restNums=").append(restNums).append(", sellNums=")
				.append(sellNums).append("]");
		return builder.toString();
	}

}
