package com.chain.easyshop.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;

import com.chain.easyshop.exception.EasyShopRuntimeException;

public class SettingsUtils {

	private static Properties prop;

	static {
		try {
			prop = new Properties();
			prop.load(SettingsUtils.class.getClassLoader().getResourceAsStream("easyshop.properties"));
			// prop.load(new FileInputStream("easyshop.properties"));
		} catch (IOException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	public static <T> T getSetting(String key, Class<T> clz) {
		Constructor<T> c;
		try {
			c = clz.getConstructor(String.class);
			return c.newInstance(prop.getProperty(key));
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

}
