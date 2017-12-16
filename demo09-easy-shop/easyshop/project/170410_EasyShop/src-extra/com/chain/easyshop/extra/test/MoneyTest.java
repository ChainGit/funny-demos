package com.chain.easyshop.extra.test;

import org.junit.Test;

import com.chain.easyshop.extra.Money;
import com.chain.easyshop.utils.TestUtils;

public class MoneyTest {

	@Test
	public void test() {
		Money m = new Money("123.34");
		TestUtils.println(m);
	}

}
