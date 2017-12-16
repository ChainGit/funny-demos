package com.chain.easyshop.inter.dao;

import java.util.List;

import com.chain.easyshop.bean.GoodRemarkItem;

public interface GoodRemarkItemDao {

	public List<GoodRemarkItem> getGoodRemarkItemList(int goodId);

	public List<GoodRemarkItem> getGoodRemarkItemListByRange(int goodId, int fromIndex, int offset);

	public void insertGoodRemark(int userId, int goodId, String remarkContent);

}
