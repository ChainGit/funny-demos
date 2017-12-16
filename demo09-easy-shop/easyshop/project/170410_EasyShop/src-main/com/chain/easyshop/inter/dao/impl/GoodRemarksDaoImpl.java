package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.GoodRemarkItem;
import com.chain.easyshop.bean.GoodRemarks;
import com.chain.easyshop.extra.MyInteger;
import com.chain.easyshop.inter.dao.GoodRemarksDao;

public class GoodRemarksDaoImpl extends BaseDaoWithPageDaoImpl<GoodRemarks, GoodRemarkItem> implements GoodRemarksDao {

	private int goodId;

	public GoodRemarksDaoImpl(int goodId) {
		this.goodId = goodId;
		setTotalItemsNumber(getGoodRemarksSize(goodId));
	}

	public GoodRemarks getGoodRemarks() {
		return getGoodRemarks(goodId);
	}

	@Override
	public GoodRemarks getGoodRemarks(int goodId) {
		GoodRemarkItemDaoImpl gridi = new GoodRemarkItemDaoImpl();
		List<GoodRemarkItem> lst = gridi.getGoodRemarkItemList(goodId);
		GoodRemarks gr = new GoodRemarks();
		gr.setGoodId(goodId);
		gr.setRemarks(lst);
		return gr;
	}

	public GoodRemarks getGoodRemarksByRange(int fromIndex, int offset) {
		return getGoodRemarksByRange(goodId, fromIndex, offset);
	}

	@Override
	public GoodRemarks getGoodRemarksByRange(int goodId, int fromIndex, int offset) {
		GoodRemarkItemDaoImpl gridi = new GoodRemarkItemDaoImpl();
		List<GoodRemarkItem> lst = gridi.getGoodRemarkItemListByRange(goodId, fromIndex, offset);
		GoodRemarks gr = new GoodRemarks();
		gr.setGoodId(goodId);
		gr.setRemarks(lst);
		return gr;
	}

	@Override
	public long getGoodRemarksSize(int goodId) {
		String sql = "select count(*) value from store_goods_remark where good_id = ?";
		return new BaseDaoImpl<MyInteger>() {
		}.queryForValue(sql, goodId);
	}

	@Override
	public void doNewPageList() {
		List<GoodRemarkItem> lst = getGoodRemarksByRange(getFromIndex(), getOffset()).getRemarks();
		setCurrentPageList(lst);
	}

	@Override
	public void doNewPageList(String filter) {

	}

	@Override
	public void setGoodId(int goodId) {
		this.goodId = goodId;
		setTotalPageAmount(-1);
		setCurrentPageIndex(-1);
		setTotalItemsNumber(getGoodRemarksSize(goodId));
	}

	@Override
	public int getGoodId() {
		return this.goodId;
	}

	@Override
	public void addGoodRemarkItem(int userId, int goodId, String remarkContent) {
		GoodRemarkItemDaoImpl gridi = new GoodRemarkItemDaoImpl();
		gridi.insertGoodRemark(userId, goodId, remarkContent);
	}

}
