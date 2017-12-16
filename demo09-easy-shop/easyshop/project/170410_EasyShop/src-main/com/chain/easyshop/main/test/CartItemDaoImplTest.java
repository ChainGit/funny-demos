package com.chain.easyshop.main.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.bean.CartItem;
import com.chain.easyshop.inter.dao.impl.CartItemDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class CartItemDaoImplTest {

	// @Test
	public void testGetCartItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		CartItem c = cidi.getCartItem(1001, 80000);
		TestUtils.println(c);
	}

	@Test
	public void testGetCartItemListByRange() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		List<CartItem> lstc = cidi.getCartItemListByRange(1001, 1, 3);
		TestUtils.println(lstc);
	}

	// @Test
	public void testAddCartItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		TestUtils.println(cidi.addCartItem(1001, 80000, 10));
	}

	// @Test
	public void testDeleteCartItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		cidi.deleteCartItem(1001, 80000);
	}

	// @Test
	public void testModifyCartItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		CartItemDaoImpl cidi = new CartItemDaoImpl();
		cidi.modifyCartItem(1001, 80000, 5);
	}

}
