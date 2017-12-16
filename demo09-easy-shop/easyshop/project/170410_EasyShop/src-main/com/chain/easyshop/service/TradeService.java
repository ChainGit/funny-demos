package com.chain.easyshop.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chain.easyshop.apps.pc.jsp.UserCartSelectCache;
import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.inter.dao.impl.GoodItemDaoImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TradeService {

	private int userId;
	private UserService us;
	private GoodService gs;
	private UserCartSelectCache ucsc;

	public TradeService(int userId, UserService us, GoodService gs) {
		setUserId(userId);
		setUserService(us);
		setGoodService(gs);
		ucsc = us.getUserCartSelectCache();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> checkCart(ObjectMapper mapper, String userCart)
			throws JsonParseException, JsonMappingException, IOException {
		UserCartSelectCache cache = new UserCartSelectCache(userId);
		us.setUserCartSelectCache(cache);
		Map<String, List<Map<String, Object>>> smap = new HashMap<>();
		smap = mapper.readValue(userCart, Map.class);
		List<Map<String, Object>> lstm = smap.get("s");
		for (Map<String, Object> m : lstm) {
			Set<Map.Entry<String, Object>> sm = m.entrySet();
			int goodId = 0;
			int goodAmount = 0;
			for (Map.Entry<String, Object> e : sm) {
				if (e.getKey().equals("goodId"))
					goodId = Integer.parseInt((String) e.getValue());
				if (e.getKey().equals("goodAmount"))
					goodAmount = Integer.parseInt((String) e.getValue());
			}
			if (goodId != 0 && goodAmount != 0)
				cache.put(goodId, goodAmount);
		}

		// System.out.println(cache);

		Map<String, Object> map = new HashMap<>();

		List<Object> cc = new ArrayList<>();
		for (Map.Entry<Integer, Integer> c : cache.getCart().entrySet()) {
			GoodItem gi = gs.getGoodItem(c.getKey());
			if (gi != null) {
				if (c.getValue() > gi.getRestNums()) {
					Map<String, Object> ccitem = new HashMap<>();
					ccitem.put("mid", 1001);
					ccitem.put("gi", gi.getGoodId());
					ccitem.put("gt", gi.getGoodTitle());
					ccitem.put("gsn", gi.getRestNums());
					cc.add(ccitem);
				}
				cache.addGoodItem(gi.getGoodId(), gi);
			}
		}

		if (cc.size() == 0)
			map.put("cc", 1);
		else
			map.put("cc", cc);
		return map;
	}

	public List<Map<String, Object>> confirm(ObjectMapper mapper) {
		UserCartSelectCache cache = getUserCartSelectCache();
		Map<Integer, Integer> mpc = cache.getCart();
		Set<Map.Entry<Integer, Integer>> mpce = mpc.entrySet();
		Map<Integer, GoodItem> mpd = cache.getData();

		List<Map<String, Object>> lst = new ArrayList<>();
		for (Map.Entry<Integer, Integer> x : mpce) {
			int goodId = x.getKey();
			int goodAmount = x.getValue();

			GoodItem gi = mpd.get(goodId);
			String goodTitle = gi.getGoodTitle();
			double goodPrice = gi.getGoodPrice();

			Map<String, Object> mp = new HashMap<>();
			mp.put("gt", goodTitle);
			mp.put("gp", goodPrice);
			mp.put("ga", goodAmount);
			lst.add(mp);
		}

		return lst;
	}

	public int checkAccount(String password) {
		if (password == null || !us.getUser().getUserPass().equals(password))
			return 1003;

		BigDecimal cb = new BigDecimal(getUserCartSelectCache().getTotalMoney());
		BigDecimal cu = new BigDecimal(us.getAccountById().getAccountBalance());
		if (cu.subtract(cb).doubleValue() < BigDecimal.ZERO.doubleValue())
			return 1002;

		return 1;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserCartSelectCache getUserCartSelectCache() {
		return ucsc;
	}

	public void setUserCartSelectCache(UserCartSelectCache ucsc) {
		this.ucsc = ucsc;
	}

	public UserService getUserService() {
		return us;
	}

	public void setUserService(UserService us) {
		this.us = us;
	}

	public GoodService getGoodService() {
		return gs;
	}

	public void setGoodService(GoodService gs) {
		this.gs = gs;
	}

	public double getTotalMoney() {
		return getUserCartSelectCache().getTotalMoney();
	}

	public void cash() {
		UserCartSelectCache cache = getUserCartSelectCache();
		GoodItemDaoImpl gidi = new GoodItemDaoImpl();

		long userTradeId = us.getUserTradesDaoImpl().addUserTrade();

		Map<Integer, Integer> mpc = cache.getCart();
		Set<Map.Entry<Integer, Integer>> mpce = mpc.entrySet();
		for (Map.Entry<Integer, Integer> x : mpce) {
			int goodId = x.getKey();
			int goodAmount = x.getValue();
			gidi.sellGood(goodId, goodAmount);
			us.getUserTradesDaoImpl().addUserTradeItem(userTradeId, goodId, goodAmount);
			us.getUserCartDaoImpl().deleteFromCart(userId, goodId);
		}

		us.getAccountDaoImpl().pay(us.getAccountById().getAccountId(), cache.getTotalMoney());
	}

}
