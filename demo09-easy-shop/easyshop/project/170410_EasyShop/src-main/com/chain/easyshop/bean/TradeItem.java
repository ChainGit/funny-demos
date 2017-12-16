package com.chain.easyshop.bean;

import java.util.ArrayList;
import java.util.List;

public class TradeItem {

	private int userTradeId;
	private int userId;
	private String tradeTime;
	private List<TradeItemCell> trade = new ArrayList<>();

	public int getUserTradeId() {
		return userTradeId;
	}

	public void setUserTradeId(int userTradeId) {
		this.userTradeId = userTradeId;
	}

	public List<TradeItemCell> getTrade() {
		return trade;
	}

	public void setTrade(List<TradeItemCell> trade) {
		this.trade = trade;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
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
		builder.append("TradeItem [userTradeId=").append(userTradeId).append(", userId=").append(userId)
				.append(", tradeTime=").append(tradeTime).append(", trade=").append(trade).append("]");
		return builder.toString();
	}

}
