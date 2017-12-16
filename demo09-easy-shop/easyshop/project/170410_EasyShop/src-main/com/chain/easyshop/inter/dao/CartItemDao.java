package com.chain.easyshop.inter.dao;

import java.util.List;

import com.chain.easyshop.bean.CartItem;

public interface CartItemDao {

	public CartItem getCartItem(int userId, int goodId);

	public List<CartItem> getCartItemList(int userId);

	public List<CartItem> getCartItemListByRange(int userId, int fromIndex, int offset);

	public long addCartItem(int userId, int goodId, int goodAmount);

	public void deleteCartItem(int userId, int goodId);

	public void modifyCartItem(int userId, int goodId, int goodAmount);

	public void autoPlusOneCartItem(int userId, int goodId);

}
