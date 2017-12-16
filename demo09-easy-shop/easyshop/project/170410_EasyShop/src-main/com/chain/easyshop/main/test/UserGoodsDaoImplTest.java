package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.inter.dao.impl.UserDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsFilterDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class UserGoodsDaoImplTest {

	@Test
	public void testGetUserGoods() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		UserGoodsDaoImpl ugdi = new UserGoodsDaoImpl();
		TestUtils.println(ugdi.getUserGoods(new UserDaoImpl().getUser("test").getUserId()));
		test(ugdi);

		ugdi.setFilter(new UserGoodsFilterDaoImpl().GetFilterByPriceRange(20, 70));
		test(ugdi);

		ugdi.clearFilter();
		test(ugdi);
	}

	private void test(UserGoodsDaoImpl ugdi) {
		ugdi.doFirstPage();
		TestUtils.println(ugdi.getCurrentPageList());
		ugdi.doNextPage();
		ugdi.doNextPage();
		TestUtils.println(ugdi.getCurrentPageList());
		ugdi.doPrevPage();
		TestUtils.println(ugdi.getCurrentPageList());
		ugdi.doLastPage();
		TestUtils.println(ugdi.getCurrentPageList());
	}

}
