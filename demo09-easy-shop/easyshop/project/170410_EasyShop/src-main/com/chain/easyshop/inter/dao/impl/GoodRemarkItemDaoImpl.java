package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.bean.GoodRemarkItem;
import com.chain.easyshop.inter.dao.GoodRemarkItemDao;

public class GoodRemarkItemDaoImpl extends BaseDaoImpl<GoodRemarkItem> implements GoodRemarkItemDao {

	@Override
	public List<GoodRemarkItem> getGoodRemarkItemList(int goodId) {
		String sql = "select good_remark_id goodRemarkId,good_id goodId,good_remark remarkContent,good_remark_time remarkDate,good_remark_user_id remarkUserId "
				+ "from store_goods_remark where good_id = ?";
		return queryForList(sql, goodId);
	}

	@Override
	public List<GoodRemarkItem> getGoodRemarkItemListByRange(int goodId, int fromIndex, int offset) {
		String sql = "select good_remark_id goodRemarkId,good_id goodId,good_remark remarkContent,good_remark_time remarkDate,good_remark_user_id remarkUserId "
				+ "from store_goods_remark where good_id = ? limit ?,?";
		return queryForList(sql, goodId, fromIndex, offset);
	}

	@Override
	public void insertGoodRemark(int userId, int goodId, String remarkContent) {
		String sql = "insert into store_goods_remark (good_id,good_remark,good_remark_user_id) values (?,?,?)";
		insert(sql, goodId, remarkContent, userId);
	}

}
