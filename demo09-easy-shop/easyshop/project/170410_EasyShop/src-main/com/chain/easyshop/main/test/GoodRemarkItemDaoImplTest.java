package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.GoodRemarkItemDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class GoodRemarkItemDaoImplTest {

	@Test
	public void testGetGoodRemarkItemList() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		GoodRemarkItemDaoImpl gridi = new GoodRemarkItemDaoImpl();
		TestUtils.println(gridi.getGoodRemarkItemList(80001));
	}

}
