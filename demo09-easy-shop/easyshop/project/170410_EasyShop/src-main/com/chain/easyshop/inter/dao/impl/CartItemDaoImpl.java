package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.CartItem;
import com.chain.easyshop.inter.dao.CartItemDao;

public class CartItemDaoImpl extends BaseDaoImpl<CartItem> implements CartItemDao {

	@Override
	public CartItem getCartItem(int userId, int goodId) {
		String sql = "select user_cart_id userCartId,user_id userId,good_id goodId,goods_amount goodAmount from users_cart where user_id = ? and good_id = ?";
		return query(sql, userId, goodId);
	}

	@Override
	public List<CartItem> getCartItemList(int userId) {
		String sql = "select user_cart_id userCartId,user_id userId,good_id goodId,goods_amount goodAmount from users_cart where user_id = ?";
		return queryForList(sql, userId);
	}

	@Override
	public List<CartItem> getCartItemListByRange(int userId, int fromIndex, int offset) {
		String sql = "select user_cart_id userCartId,user_id userId,good_id goodId,goods_amount goodAmount from users_cart where user_id = ? limit ?,?";
		return queryForList(sql, userId, fromIndex, offset);
	}

	@Override
	public long addCartItem(int userId, int goodId, int goodAmount) {
		String sql = "insert into users_cart (user_id,good_id,goods_amount) values (?,?,?)";
		return insert(sql, userId, goodId, goodAmount);
	}

	@Override
	public void deleteCartItem(int userId, int goodId) {
		String sql = "delete from users_cart where user_id = ? and good_id = ?";
		update(sql, userId, goodId);
	}

	@Override
	public void modifyCartItem(int userId, int goodId, int goodAmount) {
		String sql = "update users_cart set goods_amount = ? where user_id = ? and good_id = ?";
		update(sql, goodAmount, userId, goodId);
	}

	@Override
	public void autoPlusOneCartItem(int userId, int goodId) {
		String sql = "update users_cart set goods_amount = goods_amount + 1 where user_id = ? and good_id = ?";
		update(sql, userId, goodId);
	}

}
