package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.User;

public interface UserDao {

	public User getUserById(int userId);

	public User getUser(String username);

	public void updateUserPassword(String username, String password);

	public long addUser(String username, String password, long accountId);

}
