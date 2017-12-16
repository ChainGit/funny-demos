package com.chain.easyshop.inter.dao.impl;

import java.util.List;

import com.chain.easyshop.inter.dao.PageDao;

public abstract class BaseDaoWithPageDaoImpl<P, I> extends BaseDaoImpl<P> implements PageDao<I> {

	// ��Ʒ��������
	private String filter = "";

	// ��ǰ�ڼ�ҳ
	private int currentPageIndex = -1;

	// �ܹ��ж���ҳ
	private int totalPageAmount = -1;

	// ��ǰҳ�� List
	private List<I> currentPageList;

	// ÿҳ��ʾ��������¼
	private int eachPageSize = DEFAUT_SCALE;

	// ���ж�������¼
	private long totalItemsNumber = -1;

	public BaseDaoWithPageDaoImpl() {

	}

	public BaseDaoWithPageDaoImpl(String filter) {
		setFilter(filter);
	}

	public String getFilter() {
		return this.filter;
	}

	public void clearFilter() {
		setFilter("");
		// ��Ҫ������super��ˢ��totalItemsNumber
	}

	public void setFilter(String filter) {
		if (this.filter.equals(filter))
			return;
		this.filter = filter;
		this.totalPageAmount = -1;
		this.totalItemsNumber = -1;
		this.currentPageIndex = -1;
		// ��Ҫ������super��ˢ��totalItemsNumber
	}

	@Override
	public int getCurrentPageIndex() {
		if (currentPageIndex < 1)
			currentPageIndex = 1;
		else if (currentPageIndex > getTotalPageAmount())
			currentPageIndex = getTotalPageAmount();

		return currentPageIndex;
	}

	@Override
	public void setCurrentPageIndex(int currentPageIndex) {
		if (currentPageIndex < 1)
			currentPageIndex = 1;
		else if (currentPageIndex > getTotalPageAmount())
			currentPageIndex = getTotalPageAmount();

		this.currentPageIndex = currentPageIndex;
	}

	@Override
	public int getTotalPageAmount() {
		if (this.totalPageAmount < 0) {
			int totalPageNumber = (int) totalItemsNumber / eachPageSize;
			if (totalItemsNumber % eachPageSize != 0)
				totalPageNumber++;
			this.totalPageAmount = totalPageNumber;
		}
		return totalPageAmount;
	}

	@Override
	public void setTotalPageAmount(int totalPageAmount) {
		this.totalPageAmount = totalPageAmount;
	}

	@Override
	public int getEachPageSize() {
		return eachPageSize;
	}

	@Override
	public void setEachPageSize(int eachPageSize) {
		this.eachPageSize = eachPageSize;
	}

	@Override
	public void setTotalItemsNumber(long totalItemsNumber) {
		this.totalItemsNumber = totalItemsNumber;
	}

	@Override
	public long getTotalItemsNumber() {
		return totalItemsNumber;
	}

	@Override
	public void setCurrentPageList(List<I> list) {
		this.currentPageList = list;
	}

	@Override
	public List<I> getCurrentPageList() {
		return currentPageList;
	}

	@Override
	public boolean isHasNextPage() {
		if (getCurrentPageIndex() < getTotalPageAmount())
			return true;

		return false;
	}

	@Override
	public boolean isHasPrevPage() {
		if (getCurrentPageIndex() > 1)
			return true;

		return false;
	}

	@Override
	public int doPrevPageIndex() {
		if (isHasPrevPage())
			setCurrentPageIndex(getCurrentPageIndex() - 1);
		return getCurrentPageIndex();
	}

	@Override
	public int doNextPageIndex() {
		if (isHasNextPage())
			setCurrentPageIndex(getCurrentPageIndex() + 1);
		return getCurrentPageIndex();
	}

	@Override
	public int getFromIndex() {
		int index = (getCurrentPageIndex() - 1) * getEachPageSize();
		return index < 0 ? 0 : index;
	}

	@Override
	public int getOffset() {
		return getEachPageSize();
	}

	@Override
	public void doPrevPage() {
		doPrevPage(filter);
	}

	@Override
	public void doNextPage() {
		doNextPage(filter);
	}

	@Override
	public void doFirstPage() {
		doFirstPage(filter);
	}

	@Override
	public void doLastPage() {
		doLastPage(filter);
	}

	@Override
	public void doPrevPage(String filter) {
		doPrevPageIndex();
		doNewPageList(filter);
	}

	@Override
	public void doNextPage(String filter) {
		doNextPageIndex();
		doNewPageList(filter);
	}

	@Override
	public void doFirstPage(String filter) {
		setCurrentPageIndex(1);
		doNewPageList(filter);
	}

	@Override
	public void doLastPage(String filter) {
		setCurrentPageIndex(getTotalPageAmount());
		doNewPageList(filter);
	}

	@Override
	public void doNewPageList() {
		doNewPageList(filter);
	}

	public abstract void doNewPageList(String filter);

}
