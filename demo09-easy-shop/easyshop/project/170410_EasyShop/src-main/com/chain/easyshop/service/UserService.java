package com.chain.easyshop.service;

import com.chain.easyshop.apps.pc.jsp.UserCartSelectCache;
import com.chain.easyshop.apps.pc.jsp.UserPageFilterCache;
import com.chain.easyshop.bean.Account;
import com.chain.easyshop.bean.User;
import com.chain.easyshop.inter.dao.impl.AccountDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserCartDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserTradesDaoImpl;

public class UserService {

	private User user;
	private UserDaoImpl udi;
	private AccountDaoImpl adi;
	private UserCartDaoImpl ucdi;
	private UserTradesDaoImpl utdi;
	private UserPageFilterCache upfc;
	private UserCartSelectCache ucsc;

	public UserService(String username) {
		udi = new UserDaoImpl();
		user = udi.getUser(username);
		adi = new AccountDaoImpl();
		ucdi = new UserCartDaoImpl(user.getUserId());
		utdi = new UserTradesDaoImpl(user.getUserId());
		upfc = new UserPageFilterCache(user.getUserId());
		ucsc = new UserCartSelectCache(getUserId());
	}

	public static long addUser(String username, String password, double balance) {
		UserDaoImpl udix = new UserDaoImpl();
		User user = udix.getUser(username);
		if (user != null)
			return -1;
		long accountId = new AccountDaoImpl().addAccount(balance);
		long userId = udix.addUser(username, password, accountId);
		return userId;
	}

	public long getUserCartSize() {
		return ucdi.getUserCartSize(user.getUserId());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserCartSelectCache getUserCartSelectCache() {
		return ucsc;
	}

	public void setUserCartSelectCache(UserCartSelectCache ucsc) {
		this.ucsc = ucsc;
	}

	public User getUserById(int userId) {
		return udi.getUserById(userId);
	}

	public String getUserName() {
		return user.getUserName();
	}

	public int getUserId() {
		return user.getUserId();
	}

	public Account getAccountById() {
		return adi.getAccountById(getUser().getAccountId());
	}

	public void updateUserPassword(String password) {
		udi.updateUserPassword(getUserName(), password);
	}

	public void updateUserAccountBalance(double accountBalance) {
		adi.updateBalance(getAccountById().getAccountId(), accountBalance);
	}

	public long getTotalTradesAmount() {
		return utdi.getTotalItemsNumber();
	}

	public long getTotalCartsAmount() {
		return ucdi.getTotalItemsNumber();
	}

	public UserDaoImpl getUserDaoImpl() {
		return udi;
	}

	public void setUserDaoImpl(UserDaoImpl udi) {
		this.udi = udi;
	}

	public UserCartDaoImpl getUserCartDaoImpl() {
		return ucdi;
	}

	public void setUserCartDaoImpl(UserCartDaoImpl ucdi) {
		this.ucdi = ucdi;
	}

	public UserTradesDaoImpl getUserTradesDaoImpl() {
		return utdi;
	}

	public void setUserTradesDaoImpl(UserTradesDaoImpl utdi) {
		this.utdi = utdi;
	}

	public UserPageFilterCache getUserPageFilterCache() {
		return upfc;
	}

	public void setUserPageFilterCache(UserPageFilterCache upfc) {
		this.upfc = upfc;
	}

	public AccountDaoImpl getAccountDaoImpl() {
		return adi;
	}

	public void setAccountDaoImpl(AccountDaoImpl adi) {
		this.adi = adi;
	}

}
