package com.chain.easyshop.bean;

import java.util.ArrayList;
import java.util.List;

public class UserGoods {

	private int userId;
	private List<GoodItem> goods = new ArrayList<>();

	public List<GoodItem> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodItem> goods) {
		this.goods = goods;
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
		builder.append("UserGoods [userId=").append(userId).append(", goods=").append(goods).append("]");
		return builder.toString();
	}

}
