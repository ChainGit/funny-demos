package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.TradeItem;
import com.chain.easyshop.bean.UserTrades;
import com.chain.easyshop.extra.MyInteger;
import com.chain.easyshop.inter.dao.UserTradesDao;

public class UserTradesDaoImpl extends BaseDaoWithPageDaoImpl<UserTrades, TradeItem> implements UserTradesDao {

	private int userId;

	public UserTradesDaoImpl(int userId) {
		this.userId = userId;
		setTotalItemsNumber(getUserTradesSize(userId));
	}

	public UserTrades getUserTrades() {
		return getUserTrades(userId);
	}

	public UserTrades getUserTradesByRange(int fromIndex, int offset) {
		return getUserTradesByRange(userId, fromIndex, offset);
	}

	@Override
	public UserTrades getUserTrades(int userId) {
		UserTrades ut = new UserTrades();
		ut.setUserId(userId);
		ut.setTrades(new TradeItemDaoImpl().getTradeItemList(userId));
		return ut;
	}

	@Override
	public UserTrades getUserTradesByRange(int userId, int fromIndex, int offset) {
		UserTrades ut = new UserTrades();
		ut.setUserId(userId);
		ut.setTrades(new TradeItemDaoImpl().getTradeItemListByRange(userId, fromIndex, offset));
		return ut;
	}

	@Override
	public long getUserTradesSize(int userId) {
		String sql = "select count(*) value from users_trade where user_id = ?";
		return new BaseDaoImpl<MyInteger>() {
		}.queryForValue(sql, userId);
	}

	@Override
	public void doNewPageList() {
		List<TradeItem> lst = getUserTradesByRange(getFromIndex(), getOffset()).getTrades();
		setCurrentPageList(lst);
	}

	@Override
	public void doNewPageList(String filter) {

	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
		setTotalPageAmount(-1);
		setCurrentPageIndex(-1);
		setTotalItemsNumber(getUserTradesSize(userId));
	}

	@Override
	public int getUserId() {
		return this.userId;
	}

	@Override
	public long addUserTrade() {
		return new TradeItemDaoImpl().addTradeItem(userId);
	}

	@Override
	public long addUserTradeItem(long userTradeId, int goodId, int goodAmount) {
		return new TradeItemCellDaoImpl().addTradeItemCell(userTradeId, goodId, goodAmount);
	}

}
