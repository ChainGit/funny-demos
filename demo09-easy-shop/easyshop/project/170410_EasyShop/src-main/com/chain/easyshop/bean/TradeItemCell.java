package com.chain.easyshop.bean;

public class TradeItemCell {

	private int goodTradeId;
	private int goodId;
	private int goodQuantity;
	private int userTradeId;

	public int getGoodQuantity() {
		return goodQuantity;
	}

	public void setGoodQuantity(int goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public int getGoodTradeId() {
		return goodTradeId;
	}

	public void setGoodTradeId(int goodTradeId) {
		this.goodTradeId = goodTradeId;
	}

	public int getUserTradeId() {
		return userTradeId;
	}

	public void setUserTradeId(int userTradeId) {
		this.userTradeId = userTradeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradeItemCell [goodTradeId=").append(goodTradeId).append(", goodId=").append(goodId)
				.append(", goodQuantity=").append(goodQuantity).append(", userTradeId=").append(userTradeId)
				.append("]");
		return builder.toString();
	}

}
