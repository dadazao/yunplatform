package com.cloudstong.platform.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询返回结果
 */
public class PageResult implements java.io.Serializable {

	private static final long serialVersionUID = 29288145087138579L;

	/**
	 * 总记录条数
	 */
	private int totalCount = 0;

	/**
	 * 总页数
	 */
	private int countPage = 0;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	/**
	 * 目前页数
	 */
	private int currentPage = 1;

	/**
	 * 页面显示页数
	 */
	private int pageCountShow = Constants.PAGE_PAGECOUNTSHOW;

	/**
	 * 返回的结果集
	 */
	private List content = new ArrayList();

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @deprecated
	 * @return
	 */
	public int getFirstPage() {
		return 1;
	}

	/**
	 * 
	 * @deprecated
	 * @return
	 */
	public int getPrePage() {
		if (currentPage > 1) {
			return currentPage - 1;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * @deprecated
	 * @return
	 */
	public int getNextPage() {
		if (currentPage < countPage) {
			return currentPage + 1;
		} else {
			return countPage;
		}
	}

	/**
	 * 
	 * @deprecated
	 * @return
	 */
	public int getLastPage() {
		return countPage;
	}

	/**
	 * @deprecated
	 * @return
	 */
	public boolean getFirstFlag() {
		if (currentPage == 1 || countPage == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @deprecated
	 * @return
	 */
	public boolean getPreFlag() {
		if (currentPage == 1 || countPage == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @deprecated
	 * @return
	 */
	public boolean getNextFlag() {
		if (currentPage == countPage || countPage == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @deprecated
	 * @return
	 */
	public boolean getLastFlag() {
		if (currentPage == countPage || countPage == 1) {
			return false;
		} else {
			return true;
		}
	}

	public List getContent() {
		return content;
	}

	public void setContent(List content) {
		if (content == null)
			this.content = new ArrayList();
		else
			this.content = content;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getPagingList() {
		List pageList = new ArrayList();
		int start = currentPage - 3 >= 1 ? currentPage - 3 : 1;
		for (int i = start; i <= currentPage; i++) {
			pageList.add(i + "");
		}
		int end = currentPage + 3 > countPage ? countPage : currentPage + 3;
		for (int i = currentPage + 1; i <= end; i++) {
			pageList.add(i + "");
		}
		return pageList;
	}

	public int getPageCountShow() {
		return pageCountShow;
	}

	public void setPageCountShow(int pageCountShow) {
		this.pageCountShow = pageCountShow;
	}

}
