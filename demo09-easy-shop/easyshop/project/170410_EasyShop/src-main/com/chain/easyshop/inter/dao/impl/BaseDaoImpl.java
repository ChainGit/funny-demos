package com.chain.easyshop.inter.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.inter.dao.BaseDao;
import com.chain.easyshop.utils.DBUtils;
import com.chain.easyshop.utils.ReflectionUtils;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private QueryRunner qr = new QueryRunner();

	private Class<T> tclz;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		tclz = (Class<T>) ReflectionUtils.getFirstGenericSuperType(this.getClass());
	}

	public String getGenericSuperTypeName() {
		return tclz.getName();
	}

	@Override
	public long insert(String sql, Object... params) {
		long id = 0;

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;

		try {
			conn = ConnectionContext.getInstance().getConnection();
			stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (params != null)
				for (int i = 0; i < params.length; i++)
					stat.setObject(i + 1, params[i]);

			stat.executeUpdate();

			res = stat.getGeneratedKeys();
			if (res.next())
				id = res.getLong(1);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		} finally {
			DBUtils.release(null, stat, res);
		}

		// 插入操作返回主键的值便于后续操作
		return id;
	}

	@Override
	public void delete(String sql, Object... params) {

	}

	@Override
	public void update(String sql, Object... params) {
		Connection conn = null;

		try {
			conn = ConnectionContext.getInstance().getConnection();
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	@Override
	public T query(String sql, Object... params) {
		Connection conn = null;

		try {
			conn = ConnectionContext.getInstance().getConnection();
			return qr.query(conn, sql, new BeanHandler<>(tclz), params);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	@Override
	public List<T> queryForList(String sql, Object... params) {

		Connection conn = null;

		try {
			conn = ConnectionContext.getInstance().getConnection();
			return qr.query(conn, sql, new BeanListHandler<>(tclz), params);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	@Override
	public <V> V queryForValue(String sql, Object... params) {
		Connection conn = null;

		try {
			conn = ConnectionContext.getInstance().getConnection();
			return (V) qr.query(conn, sql, new ScalarHandler<V>(), params);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;

		try {
			connection = ConnectionContext.getInstance().getConnection();
			qr.batch(connection, sql, params);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

}
