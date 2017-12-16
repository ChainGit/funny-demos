package com.chain.easyshop.inter.dao;

import java.util.List;

import com.chain.easyshop.bean.TradeItemCell;

public interface TradeItemCellDao {

	public List<TradeItemCell> getTradeItemCellList(int userTradeId);
	
	public long addTradeItemCell(long userTradeId, int goodId, int goodAmount);

}
