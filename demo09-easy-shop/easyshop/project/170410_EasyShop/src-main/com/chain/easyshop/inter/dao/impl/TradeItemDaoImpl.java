package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.TradeItem;
import com.chain.easyshop.bean.TradeItemCell;
import com.chain.easyshop.inter.dao.TradeItemDao;

public class TradeItemDaoImpl extends BaseDaoImpl<TradeItem> implements TradeItemDao {

	@Override
	public TradeItem getTradeItem(int userTradeId) {
		String sql = "select user_trade_id userTradeId,user_id userId,user_trade_time tradeTime "
				+ "from users_trade where user_trade_id = ?";
		TradeItem ti = query(sql, userTradeId);
		List<TradeItemCell> lst = new TradeItemCellDaoImpl().getTradeItemCellList(userTradeId);
		ti.setTrade(lst);
		return ti;
	}

	@Override
	public List<TradeItem> getTradeItemList(int userId) {
		String sql = "select user_trade_id userTradeId,user_id userId,user_trade_time tradeTime "
				+ "from users_trade where user_trade_id in (select user_trade_id from users_trade where user_id = ?)";
		List<TradeItem> lstTradeItem = queryForList(sql, userId);
		for (TradeItem ti : lstTradeItem)
			ti.setTrade(new TradeItemCellDaoImpl().getTradeItemCellList(ti.getUserTradeId()));
		return lstTradeItem;
	}

	@Override
	public List<TradeItem> getTradeItemListByRange(int userId, int fromIndex, int offset) {
		String sql = "select user_trade_id userTradeId,user_id userId,user_trade_time tradeTime "
				+ "from ( select user_trade_id,user_id,user_trade_time "
				+ "from users_trade where user_trade_id in ( select user_trade_id from users_trade where user_id = ? ) ) as tmp limit ?,?";
		List<TradeItem> lstTradeItem = queryForList(sql, userId, fromIndex, offset);
		for (TradeItem ti : lstTradeItem)
			ti.setTrade(new TradeItemCellDaoImpl().getTradeItemCellList(ti.getUserTradeId()));
		return lstTradeItem;
	}

	@Override
	public long addTradeItem(int userId) {
		String sql = "insert into users_trade (user_id) values (?)";
		return insert(sql, userId);
	}

}
