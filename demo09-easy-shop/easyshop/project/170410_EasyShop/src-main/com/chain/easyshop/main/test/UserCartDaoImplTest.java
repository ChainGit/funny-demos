package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.UserCartDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class UserCartDaoImplTest {

	@Test
	public void testGetUserCart() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		UserCartDaoImpl ucdi = new UserCartDaoImpl(1001);
		TestUtils.println(ucdi.getUserCart());
		ucdi.doFirstPage();
		TestUtils.println(ucdi.getCurrentPageList());
		ucdi.doNextPage();
		ucdi.doNextPage();
		TestUtils.println(ucdi.getCurrentPageList());
		ucdi.doPrevPage();
		TestUtils.println(ucdi.getCurrentPageList());
		ucdi.doLastPage();
		TestUtils.println(ucdi.getCurrentPageList());
	}

}
