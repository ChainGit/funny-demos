package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.TradeItemCellDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class TradeItemCellDaoImplTest {

	@Test
	public void testGetTradeItemCellList() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		TradeItemCellDaoImpl ticdi = new TradeItemCellDaoImpl();
		TestUtils.println(ticdi.getTradeItemCellList(2));
	}
}
