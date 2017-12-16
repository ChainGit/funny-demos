package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.UserTradesDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class UserTradesDaoImplTest {

	@Test
	public void testGetUserTrades() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		UserTradesDaoImpl urdi = new UserTradesDaoImpl(1001);
		TestUtils.println(urdi.getUserTrades());
		urdi.doFirstPage();
		TestUtils.println(urdi.getCurrentPageList());
		urdi.doNextPage();
		urdi.doNextPage();
		TestUtils.println(urdi.getCurrentPageList());
		urdi.doPrevPage();
		TestUtils.println(urdi.getCurrentPageList());
		urdi.doLastPage();
		TestUtils.println(urdi.getCurrentPageList());
	}

}
