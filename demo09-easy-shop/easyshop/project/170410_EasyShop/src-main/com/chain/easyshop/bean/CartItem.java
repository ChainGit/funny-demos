package com.chain.easyshop.bean;

public class CartItem {

	private int userCartId;
	private int userId;
	private int goodId;
	private int goodAmount;

	public int getGoodAmount() {
		return goodAmount;
	}

	public void setGoodAmount(int goodAmount) {
		this.goodAmount = goodAmount;
	}

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public int getUserCartId() {
		return userCartId;
	}

	public void setUserCartId(int userCartId) {
		this.userCartId = userCartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartItem [userCartId=").append(userCartId).append(", userId=").append(userId)
				.append(", goodId=").append(goodId).append(", goodAmount=").append(goodAmount).append("]");
		return builder.toString();
	}

}
