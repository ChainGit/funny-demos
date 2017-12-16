package com.chain.easyshop.inter.dao;

import java.util.List;

public interface PageDao<T> {

	public static final int DEFAUT_SCALE = 3;

	// 获取当前第几页
	public int getCurrentPageIndex();

	public void setCurrentPageIndex(int currentPageIndex);

	public int doPrevPageIndex();

	public int doNextPageIndex();

	// 获取总共页数
	public int getTotalPageAmount();

	public void setTotalPageAmount(int totalPageAmount);

	// 获取每一页显示的个数
	public int getEachPageSize();

	public void setEachPageSize(int eachPageSize);

	// 设置总共有多少条数据
	public void setTotalItemsNumber(long totalItemsNumber);

	public long getTotalItemsNumber();

	public void setCurrentPageList(List<T> list);

	public List<T> getCurrentPageList();

	public boolean isHasNextPage();

	public boolean isHasPrevPage();

	public int getFromIndex();

	public int getOffset();

	public void doFirstPage();

	public void doPrevPage();

	public void doNextPage();

	public void doLastPage();

	public void doFirstPage(String filter);

	public void doPrevPage(String filter);

	public void doNextPage(String filter);

	public void doLastPage(String filter);

	public void doNewPageList();

	public void doNewPageList(String filter);

}
