package com.chain.easyshop.inter.dao.impl;

import java.math.BigDecimal;

import com.chain.easyshop.inter.dao.UserGoodsFilterDao;

public class UserGoodsFilterDaoImpl implements UserGoodsFilterDao {

	@Override
	public String GetFilterByPriceRange(double from, double to) {
		String filter = null;

		if (from < 0)
			from = 0.0d;
		if (to < 0)
			to = 0.0d;

		BigDecimal xfrom = new BigDecimal(from);
		BigDecimal xto = new BigDecimal(to);
		BigDecimal xzero = new BigDecimal(0);

		if (xfrom.equals(xzero) && xto.equals(xzero))
			filter = "";
		else if (xto.equals(xzero))
			filter = "where store_goods_base.good_price >= " + from;
		else if (xfrom.equals(xzero))
			filter = "where store_goods_base.good_price <= " + to;
		else
			filter = "where store_goods_base.good_price >= " + from + " and store_goods_base.good_price <= " + to;
		return filter;
	}
}
