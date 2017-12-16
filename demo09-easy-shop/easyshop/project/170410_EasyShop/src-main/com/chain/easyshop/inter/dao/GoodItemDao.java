package com.chain.easyshop.inter.dao;

import java.util.List;

import com.chain.easyshop.bean.GoodItem;

public interface GoodItemDao {

	public GoodItem getGoodItem(int goodId);

	public List<GoodItem> getGoodItemList();

	public List<GoodItem> getGoodItemListByRange(int formIndex, int offset);

	public List<GoodItem> getGoodItemListWithFilterByRange(String filter, int formIndex, int offset);

	public void sellGood(int goodId, int amount);

	public long addGood(String goodTitle, double goodPrice, int goodStore);

	public void modifyGood(int goodId, String goodTitle, double goodPrice, int goodStore);

}
