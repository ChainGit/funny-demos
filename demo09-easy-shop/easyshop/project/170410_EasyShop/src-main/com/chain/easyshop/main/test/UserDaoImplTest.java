package com.chain.easyshop.main.test;

import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.bean.User;
import com.chain.easyshop.inter.dao.impl.UserDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class UserDaoImplTest {

	@Test
	public void test() throws SQLException {
		ConnectionContext.getInstance().bindConnection(DBUtils.getConnection());
		UserDaoImpl udi = new UserDaoImpl();
		User u = udi.getUser("test");
		TestUtils.println(u);
	}

}
