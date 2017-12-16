package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.TradeItemDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class TradeItemDaoImplTest {

	@Test
	public void testGetTradeItem() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		TradeItemDaoImpl tidi = new TradeItemDaoImpl();
		TestUtils.println(tidi.getTradeItem(3));
	}
}
