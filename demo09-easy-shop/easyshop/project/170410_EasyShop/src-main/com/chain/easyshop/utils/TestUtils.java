package com.chain.easyshop.utils;

import java.util.Arrays;

public class TestUtils {

	private static final boolean FLAG = SettingsUtils.getSetting("DEBUG", Boolean.class);

	/**
	 * 打印数组元素
	 * 
	 * @param objs
	 */
	public static void println(Object[] objs) {
		if (FLAG)
			System.out.println(Arrays.asList(objs));
	}

	/**
	 * 打印单个元素
	 * 
	 * @param objs
	 */
	public static void println(Object obj) {
		if (FLAG)
			System.out.println(obj);
	}
}
