package com.chain.easyshop.main.test;

import org.junit.Test;

import com.chain.easyshop.inter.dao.impl.UserDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsDaoImpl;
import com.chain.easyshop.utils.TestUtils;

public class BaseDaoImplTest {

	@Test
	public void test() {
		UserDaoImpl udi = new UserDaoImpl();
		TestUtils.println(udi.getGenericSuperTypeName());
		UserGoodsDaoImpl bdi = new UserGoodsDaoImpl();
		TestUtils.println(bdi.getGenericSuperTypeName());
	}

}
