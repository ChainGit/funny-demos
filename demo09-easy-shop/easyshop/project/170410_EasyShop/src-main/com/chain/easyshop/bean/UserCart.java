package com.chain.easyshop.bean;

import java.util.ArrayList;
import java.util.List;

public class UserCart {

	private int userId;
	private List<CartItem> cart = new ArrayList<>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CartItem> getCart() {
		return cart;
	}

	public void setCart(List<CartItem> cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserCart [userId=").append(userId).append(", cart=").append(cart).append("]");
		return builder.toString();
	}

}
