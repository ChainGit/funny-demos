package com.chain.easyshop.bean;

import com.chain.easyshop.extra.Money;

public class Account {

	private int accountId;
	private Money accountBalance;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAccountBalance() {
		return accountBalance.doubleValue();
	}

	public void setAccountBalance(double accountBalance) {
		if (this.accountBalance == null)
			this.accountBalance = new Money(accountBalance);
		else
			this.accountBalance.setValue(accountBalance);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [accountId=").append(accountId).append(", accountBalance=").append(accountBalance)
				.append("]");
		return builder.toString();
	}

}
