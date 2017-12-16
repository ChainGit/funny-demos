package com.chain.easyshop.bean;

public class GoodRemarkItem {

	private int goodRemarkId;
	private int goodId;
	private String remarkContent;
	private String remarkDate;
	private int remarkUserId;

	public String getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(String remarkDate) {
		this.remarkDate = remarkDate;
	}

	public String getRemarkContent() {
		return remarkContent;
	}

	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}

	public int getRemarkUserId() {
		return remarkUserId;
	}

	public void setRemarkUserId(int remarkUserId) {
		this.remarkUserId = remarkUserId;
	}

	public int getGoodRemarkId() {
		return goodRemarkId;
	}

	public void setGoodRemarkId(int goodRemarkId) {
		this.goodRemarkId = goodRemarkId;
	}

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodRemarkItem [goodRemarkId=").append(goodRemarkId).append(", goodId=").append(goodId)
				.append(", remarkContent=").append(remarkContent).append(", remarkDate=").append(remarkDate)
				.append(", remarkUserId=").append(remarkUserId).append("]");
		return builder.toString();
	}

}
