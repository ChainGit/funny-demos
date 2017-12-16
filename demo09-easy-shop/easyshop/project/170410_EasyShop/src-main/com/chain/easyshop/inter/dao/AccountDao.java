package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.Account;

public interface AccountDao {

	public Account getAccountById(int accountId);

	public void updateBalance(int accountId, double money);

	public long addAccount(double accountBalance);

	public void pay(int accountId, double subMoney);

}
