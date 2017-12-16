package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.bean.UserGoods;
import com.chain.easyshop.extra.MyInteger;
import com.chain.easyshop.inter.dao.UserGoodsDao;

public class UserGoodsDaoImpl extends BaseDaoWithPageDaoImpl<UserGoods, GoodItem> implements UserGoodsDao {

	private int userId = -1;

	public UserGoodsDaoImpl(int userId) {
		this();
		this.userId = userId;
	}

	public UserGoodsDaoImpl() {
		setTotalItemsNumber(getUserGoodsSize());
	}

	public UserGoodsDaoImpl(String filter) {
		super(filter);
		setTotalItemsNumber(getUserGoodsSize());
	}

	@Override
	public void setFilter(String filter) {
		super.setFilter(filter);
		setTotalItemsNumber(getUserGoodsSize());
	}

	@Override
	public void clearFilter() {
		setFilter("");
	}

	@Override
	public UserGoods getUserGoods(int userId) {
		UserGoods ug = new UserGoods();
		ug.setUserId(userId);
		ug.setGoods(new GoodItemDaoImpl().getGoodItemList());
		return ug;
	}

	@Override
	public UserGoods getUserGoods() {
		return getUserGoods(userId);
	}

	@Override
	public UserGoods getUserGoodsByRange(int userId, int fromIndex, int offset) {
		return getUserGoodsWithFilterByRange(userId, getFilter(), fromIndex, offset);
	}

	@Override
	public UserGoods getUserGoodsByRange(int fromIndex, int offset) {
		return getUserGoodsByRange(-1, fromIndex, offset);
	}

	@Override
	public long getUserGoodsSize(int userId) {
		String sql = "select count(*) value from store_goods_base " + getFilter();
		return new BaseDaoImpl<MyInteger>() {
		}.queryForValue(sql);
	}

	public long getUserGoodsSize() {
		return getUserGoodsSize(userId);
	}

	@Override
	public UserGoods getUserGoodsWithFilterByRange(int userId, String filter, int fromIndex, int offset) {
		UserGoods ug = new UserGoods();
		ug.setUserId(userId);
		ug.setGoods(new GoodItemDaoImpl().getGoodItemListWithFilterByRange(filter, fromIndex, offset));
		return ug;
	}

	@Override
	public UserGoods getUserGoodsWithFilterByRange(String filter, int fromIndex, int offset) {
		return getUserGoodsWithFilterByRange(userId, filter, fromIndex, offset);
	}

	@Override
	public void doNewPageList(String filter) {
		List<GoodItem> lst = getUserGoodsWithFilterByRange(filter, getFromIndex(), getOffset()).getGoods();
		setCurrentPageList(lst);
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
		setCurrentPageIndex(-1);
		setTotalPageAmount(-1);
		setTotalItemsNumber(getUserGoodsSize());
	}

	@Override
	public int getUserId() {
		return this.userId;
	}

}
