package com.chain.easyshop.inter.dao.impl;

import com.chain.easyshop.bean.User;
import com.chain.easyshop.inter.dao.UserDao;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User getUser(String username) {
		String sql = "select user_id userId,user_name userName,user_pass userPass,account_id accountId from users_base where user_name = ?";
		return query(sql, username);
	}

	@Override
	public void updateUserPassword(String username, String password) {
		String sql = "update users_base set user_pass = ? where user_name = ?";
		update(sql, password, username);
	}

	@Override
	public User getUserById(int userId) {
		String sql = "select user_id userId,user_name userName,user_pass userPass,account_id accountId from users_base where user_id = ?";
		return query(sql, userId);
	}

	@Override
	public long addUser(String username, String password, long accountId) {
		String sql = "insert into users_base (user_name,user_pass,account_id) values (?,?,?)";
		return insert(sql, username, password, accountId);
	}

}
