package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.GoodRemarksDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class GoodRemarksDaoImplTest {

	@Test
	public void testGetGoodRemarks() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		GoodRemarksDaoImpl grdi = new GoodRemarksDaoImpl(80000);
		TestUtils.println(grdi.getGoodRemarks());
		grdi.doFirstPage();
		TestUtils.println(grdi.getCurrentPageList());
		grdi.doNextPage();
		grdi.doNextPage();
		TestUtils.println(grdi.getCurrentPageList());
		grdi.doPrevPage();
		TestUtils.println(grdi.getCurrentPageList());
		grdi.doLastPage();
		TestUtils.println(grdi.getCurrentPageList());
	}

}
