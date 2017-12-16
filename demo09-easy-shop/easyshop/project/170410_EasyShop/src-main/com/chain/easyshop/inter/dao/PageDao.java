package com.chain.easyshop.inter.dao;

import java.util.List;

public interface PageDao<T> {

	public static final int DEFAUT_SCALE = 3;

	// ��ȡ��ǰ�ڼ�ҳ
	public int getCurrentPageIndex();

	public void setCurrentPageIndex(int currentPageIndex);

	public int doPrevPageIndex();

	public int doNextPageIndex();

	// ��ȡ�ܹ�ҳ��
	public int getTotalPageAmount();

	public void setTotalPageAmount(int totalPageAmount);

	// ��ȡÿһҳ��ʾ�ĸ���
	public int getEachPageSize();

	public void setEachPageSize(int eachPageSize);

	// �����ܹ��ж���������
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
