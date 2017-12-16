package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.UserTrades;

public interface UserTradesDao {

	public UserTrades getUserTrades(int userId);

	public UserTrades getUserTradesByRange(int userId, int fromIndex, int offset);

	public long getUserTradesSize(int userId);

	public void setUserId(int userId);

	public int getUserId();

	public long addUserTrade();

	public long addUserTradeItem(long userTradeId, int goodId, int goodAmount);
}
