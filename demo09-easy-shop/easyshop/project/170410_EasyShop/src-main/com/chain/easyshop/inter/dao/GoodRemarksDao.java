package com.chain.easyshop.inter.dao;

import com.chain.easyshop.bean.GoodRemarks;

public interface GoodRemarksDao {

	public GoodRemarks getGoodRemarks(int goodId);

	public GoodRemarks getGoodRemarksByRange(int goodId, int fromIndex, int offset);

	public void setGoodId(int goodId);

	public int getGoodId();

	public long getGoodRemarksSize(int goodId);

	public void addGoodRemarkItem(int userId, int goodId, String remarkContent);

}
