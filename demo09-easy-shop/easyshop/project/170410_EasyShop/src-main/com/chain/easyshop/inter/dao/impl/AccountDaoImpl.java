package com.chain.easyshop.inter.dao.impl;

import com.chain.easyshop.bean.Account;
import com.chain.easyshop.inter.dao.AccountDao;

public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {

	@Override
	public Account getAccountById(int accountId) {
		String sql = "select account_id accountId,account_balance accountBalance from users_account where account_id = ?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(int accountId, double money) {
		String sql = "update users_account set account_balance = ? where account_id = ?";
		update(sql, money, accountId);
	}

	@Override
	public long addAccount(double accountBalance) {
		String sql = "insert into users_account (account_balance) values (?)";
		return insert(sql, accountBalance);
	}

	@Override
	public void pay(int accountId, double payMoney) {
		String sql = "update users_account set account_balance = account_balance - ? where account_id = ?";
		update(sql, payMoney, accountId);
	}

}
