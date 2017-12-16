package com.chain.easyshop.bean;

import java.util.ArrayList;
import java.util.List;

public class UserTrades {

	private int userId;
	private List<TradeItem> trades = new ArrayList<>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<TradeItem> getTrades() {
		return trades;
	}

	public void setTrades(List<TradeItem> trades) {
		this.trades = trades;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserTrades [userId=").append(userId).append(", trades=").append(trades).append("]");
		return builder.toString();
	}

}
