package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.inter.dao.GoodItemDao;

public class GoodItemDaoImpl extends BaseDaoImpl<GoodItem> implements GoodItemDao {

	@Override
	public GoodItem getGoodItem(int goodId) {
		String sql = "select store_goods_id.good_id goodId,good_title goodTitle,good_price goodPrice,good_sales_amount sellNums,good_store_number restNums "
				+ "from store_goods_id "
				+ "left join store_goods_base on store_goods_id.good_id = store_goods_base.good_id "
				+ "left join store_goods_trade on store_goods_id.good_id = store_goods_trade.good_id "
				+ "where store_goods_id.good_id = ?";
		return query(sql, goodId);
	}

	@Override
	public List<GoodItem> getGoodItemList() {
		String sql = "select store_goods_id.good_id goodId,good_title goodTitle,good_price goodPrice,good_sales_amount sellNums,good_store_number restNums "
				+ "from store_goods_id "
				+ "left join store_goods_base on store_goods_id.good_id = store_goods_base.good_id "
				+ "left join store_goods_trade on store_goods_id.good_id = store_goods_trade.good_id";
		return queryForList(sql);
	}

	@Override
	public List<GoodItem> getGoodItemListByRange(int fromIndex, int offset) {
		return getGoodItemListWithFilterByRange("", fromIndex, offset);
	}

	@Override
	public void sellGood(int goodId, int amount) {
		String sql = "update store_goods_trade set good_sales_amount = good_sales_amount + ? , good_store_number = good_store_number - ? where good_id = ?";
		update(sql, amount, amount, goodId);
	}

	@Override
	public List<GoodItem> getGoodItemListWithFilterByRange(String filter, int formIndex, int offset) {
		String sql = "select store_goods_id.good_id goodId,good_title goodTitle,good_price goodPrice,good_sales_amount sellNums,good_store_number restNums "
				+ "from store_goods_id "
				+ "left join store_goods_base on store_goods_id.good_id = store_goods_base.good_id "
				+ "left join store_goods_trade on store_goods_id.good_id = store_goods_trade.good_id " + filter
				+ " limit ?,?";
		return queryForList(sql, formIndex, offset);
	}

	@Override
	public long addGood(String goodTitle, double goodPrice, int goodStore) {
		String sql = "insert into store_goods_id (good_id) values (null)";
		long goodId1 = insert(sql);
		if (goodId1 == -1)
			return -1;
		sql = "insert into store_goods_base (good_id,good_title,good_price) values (?,?,?)";
		long goodId2 = insert(sql, goodId1, goodTitle, goodPrice);
		if (goodId2 == -1)
			return -1;
		sql = "insert into store_goods_trade (good_id,good_store_number) values (?,?)";
		long goodId3 = insert(sql, goodId1, goodStore);
		if (goodId3 == -1)
			return -1;
		if (goodId1 == goodId2 && goodId2 == goodId3)
			return goodId1;
		return -1;
	}

	@Override
	public void modifyGood(int goodId, String goodTitle, double goodPrice, int goodStore) {
		String sql = "update store_goods_base set good_title = ? , good_price = ? where good_id = ?";
		update(sql, goodTitle, goodPrice, goodId);
		sql = "update store_goods_trade set good_store_number = ? where good_id = ?";
		update(sql, goodStore, goodId);
	}

}
