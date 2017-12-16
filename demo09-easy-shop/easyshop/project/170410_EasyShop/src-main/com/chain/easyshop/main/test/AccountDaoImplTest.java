package com.chain.easyshop.main.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.bean.Account;
import com.chain.easyshop.inter.dao.impl.AccountDaoImpl;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.TestUtils;

public class AccountDaoImplTest {

	@Test
	public void testGetAccountById() throws SQLException {
		Connection conn = DBUtils.getConnection();
		ConnectionContext.getInstance().bindConnection(conn);
		AccountDaoImpl adi = new AccountDaoImpl();
		TestUtils.println(adi.getGenericSuperTypeName());
		Account a = adi.getAccountById(11001);
		TestUtils.println(a);
	}

	@Test
	public void updateBalance() throws SQLException {
		Connection conn = DBUtils.getConnection();
		ConnectionContext.getInstance().bindConnection(conn);
		AccountDaoImpl adi = new AccountDaoImpl();
		TestUtils.println(adi.getGenericSuperTypeName());
		adi.updateBalance(11001, 11233.44);
	}
}
