package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.CartItem;
import com.chain.easyshop.bean.UserCart;
import com.chain.easyshop.extra.MyInteger;
import com.chain.easyshop.inter.dao.UserCartDao;

public class UserCartDaoImpl extends BaseDaoWithPageDaoImpl<UserCart, CartItem> implements UserCartDao {

	private int userId;

	public UserCartDaoImpl(int userId) {
		this.userId = userId;
		setTotalItemsNumber(getUserCartSize(userId));
	}

	public UserCart getUserCart() {
		return getUserCart(userId);
	}

	public UserCart getUserCartByRange(int fromIndex, int offset) {
		return getUserCartByRange(userId, fromIndex, offset);
	}

	@Override
	public UserCart getUserCart(int userId) {
		UserCart uc = new UserCart();
		CartItemDaoImpl ci = new CartItemDaoImpl();
		uc.setCart(ci.getCartItemList(userId));
		uc.setUserId(userId);
		return uc;
	}

	@Override
	public UserCart getUserCartByRange(int userId, int fromIndex, int offset) {
		UserCart uc = new UserCart();
		CartItemDaoImpl ci = new CartItemDaoImpl();
		uc.setCart(ci.getCartItemListByRange(userId, fromIndex, offset));
		uc.setUserId(userId);
		return uc;
	}

	@Override
	public long getUserCartSize(int userId) {
		String sql = "select count(*) value from users_cart where user_id = ?";
		return new BaseDaoImpl<MyInteger>() {
		}.queryForValue(sql, userId);
	}

	@Override
	public void doNewPageList() {
		List<CartItem> lst = getUserCartByRange(getFromIndex(), getOffset()).getCart();
		setCurrentPageList(lst);
	}

	@Override
	public void doNewPageList(String filter) {

	}

	@Override
	public void insertToCart(int userId, int goodId) {
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		CartItem ci = cidi.getCartItem(userId, goodId);
		if (ci != null)
			cidi.autoPlusOneCartItem(userId, goodId);
		else
			// cidi.addCartItem(userId, goodId, ci.getGoodAmount() + 1);
			cidi.addCartItem(userId, goodId, 1);
	}

	@Override
	public void deleteFromCart(int userId, int goodId) {
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		cidi.deleteCartItem(userId, goodId);
	}

	@Override
	public void updateInCart(int userId, int goodId, int newGoodAmount) {
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		cidi.modifyCartItem(userId, goodId, newGoodAmount);
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
		setTotalPageAmount(-1);
		setCurrentPageIndex(-1);
		setTotalItemsNumber(getUserCartSize(userId));
	}

	@Override
	public int getUserId() {
		return this.userId;
	}

	@Override
	public void clearUserCart(int userId) {
		String sql = "delete from users_cart where user_id = ?";
		update(sql, userId);
	}

}
