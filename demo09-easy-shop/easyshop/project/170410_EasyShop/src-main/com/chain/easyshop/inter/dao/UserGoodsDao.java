package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.UserGoods;

public interface UserGoodsDao {

	public UserGoods getUserGoods(int userId);

	public UserGoods getUserGoods();

	public UserGoods getUserGoodsByRange(int userId, int fromIndex, int offset);

	public UserGoods getUserGoodsByRange(int fromIndex, int offset);

	public UserGoods getUserGoodsWithFilterByRange(int userId, String filter, int fromIndex, int offset);

	public UserGoods getUserGoodsWithFilterByRange(String filter, int fromIndex, int offset);

	public long getUserGoodsSize(int userId);

	public void setUserId(int userId);

	public int getUserId();

}
