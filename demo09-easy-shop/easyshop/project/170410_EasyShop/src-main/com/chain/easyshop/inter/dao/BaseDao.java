package com.chain.easyshop.inter.dao;

import java.util.List;

public interface BaseDao<T> {

	long insert(String sql, Object... params);

	void delete(String sql, Object... params);

	void update(String sql, Object... params);

	T query(String sql, Object... params);

	List<T> queryForList(String sql, Object... params);

	<V> V queryForValue(String sql, Object... params);

	void batch(String sql, Object[]... params);
}
