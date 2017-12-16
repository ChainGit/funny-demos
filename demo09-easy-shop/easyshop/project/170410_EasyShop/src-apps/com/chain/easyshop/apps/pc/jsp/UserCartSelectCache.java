package com.chain.easyshop.apps.pc.jsp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.chain.easyshop.bean.GoodItem;

public class UserCartSelectCache {

	private int userId;
	private double totalMoney;
	private Map<Integer, Integer> cart = new HashMap<>();
	private Map<Integer, GoodItem> data = new HashMap<>();

	public UserCartSelectCache(int userId) {
		setUserId(userId);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Map<Integer, Integer> getCart() {
		return cart;
	}

	public void setCart(Map<Integer, Integer> cart) {
		this.cart = cart;
	}

	public void put(int key, int value) {
		cart.put(key, value);
	}

	public void get(int key) {
		cart.get(key);
	}

	public void addGoodItem(int goodId, GoodItem gi) {
		data.put(goodId, gi);
	}

	public GoodItem getGoodItem(int goodId) {
		return data.get(goodId);
	}

	public double getTotalMoney() {
		Map<Integer, Integer> mpc = getCart();
		Set<Map.Entry<Integer, Integer>> mpce = mpc.entrySet();
		Map<Integer, GoodItem> mpd = getData();

		double tm = 0.0d;
		for (Map.Entry<Integer, Integer> x : mpce) {
			int goodId = x.getKey();
			int goodAmount = x.getValue();

			GoodItem gi = mpd.get(goodId);
			double goodPrice = gi.getGoodPrice();

			tm += goodPrice * goodAmount;
		}

		totalMoney = tm;
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Map<Integer, GoodItem> getData() {
		return data;
	}

	public void setData(Map<Integer, GoodItem> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserCartSelectCache [userId=").append(userId).append(", totalMoney=").append(totalMoney)
				.append(", cart=").append(cart).append(", data=").append(data).append("]");
		return builder.toString();
	}

}
