package com.chain.easyshop.bean;

import java.util.ArrayList;
import java.util.List;

public class GoodRemarks {

	private int goodId;
	private List<GoodRemarkItem> remarks = new ArrayList<>();

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public List<GoodRemarkItem> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<GoodRemarkItem> remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodRemarks [goodId=").append(goodId).append(", remarks=").append(remarks).append("]");
		return builder.toString();
	}

}
