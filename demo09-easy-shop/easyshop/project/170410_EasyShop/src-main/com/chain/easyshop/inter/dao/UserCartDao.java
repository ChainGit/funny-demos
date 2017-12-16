package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.UserCart;

public interface UserCartDao {

	public UserCart getUserCart(int userId);

	public UserCart getUserCartByRange(int userId, int fromIndex, int offset);

	public long getUserCartSize(int userId);

	public void insertToCart(int userId, int goodId);

	public void deleteFromCart(int userId, int goodId);

	public void updateInCart(int userId, int goodId, int newGoodAmount);

	public void setUserId(int userId);

	public int getUserId();

	public void clearUserCart(int userId);

}
