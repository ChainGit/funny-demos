package com.chain.easyshop.inter.dao;

import java.util.List;

import com.chain.easyshop.bean.TradeItem;

public interface TradeItemDao {

	public TradeItem getTradeItem(int userTradeId);

	public List<TradeItem> getTradeItemList(int userId);

	public List<TradeItem> getTradeItemListByRange(int userId, int fromIndex, int offset);

	public long addTradeItem(int userId);

}
