package com.chain.easyshop.extra.test;

import org.junit.Test;

import com.chain.easyshop.utils.SettingsUtils;

public class SettingsUtilsTest {

	@Test
	public void test() {
		boolean b = SettingsUtils.getSetting("DEBUG", Boolean.class);
		System.out.println(b);
	}

}
