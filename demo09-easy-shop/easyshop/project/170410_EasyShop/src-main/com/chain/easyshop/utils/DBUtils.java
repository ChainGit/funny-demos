package com.chain.easyshop.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类
 * 
 * @author CHAIN
 *
 */
public class DBUtils {

	public static DataSource ds;

	static {
		ds = new ComboPooledDataSource("easyshop");
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static void release(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	public static void release(Connection conn, Statement stat) {
		try {
			if (stat != null)
				stat.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		} finally {
			release(conn);
		}
	}

	public static void release(Connection conn, Statement stat, ResultSet res) {
		try {
			if (res != null)
				res.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		} finally {
			release(conn, stat);
		}
	}
}
