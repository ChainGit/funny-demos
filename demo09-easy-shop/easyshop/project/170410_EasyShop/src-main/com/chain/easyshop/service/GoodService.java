package com.chain.easyshop.service;

import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.inter.dao.impl.GoodItemDaoImpl;
import com.chain.easyshop.inter.dao.impl.GoodRemarksDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsFilterDaoImpl;

public class GoodService {

	private UserGoodsDaoImpl ugdi;
	private GoodItemDaoImpl gidi;
	private GoodRemarksDaoImpl grdi;
	private UserGoodsFilterDaoImpl ugfdi;

	public GoodService(int userId) {
		ugdi = new UserGoodsDaoImpl(userId);
		ugfdi = new UserGoodsFilterDaoImpl();
		gidi = new GoodItemDaoImpl();
	}

	public int getGoodId() {
		return grdi.getGoodId();
	}

	public void setGoodId(int goodId) {
		grdi.setGoodId(goodId);
	}

	public GoodItem getGoodItem(int goodId) {
		return gidi.getGoodItem(goodId);
	}

	public UserGoodsDaoImpl getUserGoodsDaoImpl() {
		return ugdi;
	}

	public void setUserGoodsDaoImpl(UserGoodsDaoImpl ugdi) {
		this.ugdi = ugdi;
	}

	public UserGoodsFilterDaoImpl getUserGoodsFilterDaoImpl() {
		return ugfdi;
	}

	public void setUserGoodsFilterDaoImpl(UserGoodsFilterDaoImpl ugfdi) {
		this.ugfdi = ugfdi;
	}

	public int getTotalPageAmout() {
		return ugdi.getTotalPageAmount();
	}

	public int getCurrentPageIndex() {
		return ugdi.getCurrentPageIndex();
	}

	public GoodRemarksDaoImpl getGoodRemarksDaoImpl() {
		return grdi;
	}

	public void setGoodRemarksDaoImpl(GoodRemarksDaoImpl grdi) {
		this.grdi = grdi;
	}

	public GoodItemDaoImpl getGoodItemDaoImpl() {
		return gidi;
	}

	public void setGoodItemDaoImpl(GoodItemDaoImpl gidi) {
		this.gidi = gidi;
	}

}
