package com.chain.easyshop.main.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.inter.dao.impl.GoodItemDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class GoodItemDaoImplTest {

	// @Test
	public void testGetGoodItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		GoodItemDaoImpl gidi = new GoodItemDaoImpl();
		GoodItem gi = gidi.getGoodItem(80000);
		TestUtils.println(gi);
	}

	@Test
	public void testGetGoodItemListByRange() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		GoodItemDaoImpl gidi = new GoodItemDaoImpl();
		List<GoodItem> lstgi = gidi.getGoodItemListByRange(2, 5);
		TestUtils.println(lstgi);
	}

	// @Test
	public void testSellGood() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		GoodItemDaoImpl gidi = new GoodItemDaoImpl();
		gidi.sellGood(80000, 5);
	}

}
