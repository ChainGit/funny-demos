package com.chain.easyshop.bean;

public class User {

	private int userId;
	private String userName;
	private String userPass;
	private int accountId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=").append(userId).append(", userName=").append(userName).append(", userPass=")
				.append(userPass).append(", accountId=").append(accountId).append("]");
		return builder.toString();
	}

}
