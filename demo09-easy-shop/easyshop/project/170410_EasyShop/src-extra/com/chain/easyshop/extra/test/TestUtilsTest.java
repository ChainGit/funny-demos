package com.chain.easyshop.extra.test;

import org.junit.Test;

import com.chain.easyshop.utils.TestUtils;

public class TestUtilsTest {

	@Test
	public void test() {
		TestUtils.println("TEST");
		TestUtils.println(new String[] { "AAA", "BBB", "CCC" });
	}

}
