package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.TradeItemCell;
import com.chain.easyshop.inter.dao.TradeItemCellDao;

public class TradeItemCellDaoImpl extends BaseDaoImpl<TradeItemCell> implements TradeItemCellDao {

	@Override
	public List<TradeItemCell> getTradeItemCellList(int userTradeId) {
		String sql = "select good_trade_id goodTradeId,good_id goodId,good_trade_quantity goodQuantity,user_trade_id userTradeId "
				+ "from goods_trade where user_trade_id = ?";
		return queryForList(sql, userTradeId);
	}

	@Override
	public long addTradeItemCell(long userTradeId, int goodId, int goodAmount) {
		String sql = "insert into goods_trade (good_id,good_trade_quantity,user_trade_id) values (?,?,?)";
		return insert(sql, goodId, goodAmount, userTradeId);
	}

}
