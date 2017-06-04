package com.cloudstong.platform.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询条件，包括简单查询和高级查询条件
 */
public class QueryCriteria implements java.io.Serializable {
	/**
	 * 升序
	 */
	public static final String SORT_DIRECTION_ASC = "ASC";

	/**
	 * 降序
	 */
	public static final String SORT_DIRECTION_DESC = "DESC";

	/**
	 * 每页条数.
	 */
	private int pageSize = 20;

	/**
	 * 目前页号.
	 */
	private int currentPage = 1;

	/**
	 * 目前页的记录号.
	 */
	private int currentIndex = 0;

	/**
	 * 排序字段，默认为更新时间
	 */
	private String orderField = "";

	/**
	 * 排序顺序，默认为降序.
	 */
	private String orderDirection = "desc";

	/**
	 * 查询条件集合
	 */
	private Map<String, Object> queryCondition = new HashMap<String, Object>();

	/**
	 * 排序字段集合
	 */
	private List<String> sortCondition = new ArrayList<String>();
	
	/**
	 * 主表
	 */
	private String domain;
	
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryCriteria other = (QueryCriteria) obj;
        if (this.queryCondition.size() != other.queryCondition.size())
            return false;
        Iterator iterator = other.queryCondition.keySet().iterator();
        while(iterator.hasNext()) {
        	if(!this.queryCondition.containsKey(iterator.next())) {
        		return false;
        	}
        }
        return true;
    }

    @Override
    public String toString() {
        return this.queryCondition.toString().replace(" ","") + currentIndex + pageSize;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void addQueryCondition(String key, Object value) {
		this.queryCondition.put(key, value);
	}

	public void removeQueryCondition(String key) {
		this.queryCondition.remove(key);
	}
	
	public void addSortCondition(String orderField) {
		this.sortCondition.add(orderField);
	}

	public void removeSortCondition(String orderField) {
		this.sortCondition.remove(orderField);
	}
	
	public Map getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(Map queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	public List<String> getSortCondition() {
		return sortCondition;
	}

	public void setSortCondition(List<String> sortCondition) {
		this.sortCondition = sortCondition;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentIndex() {
		if (this.currentPage > 1) {
			this.currentIndex = (this.currentPage - 1) * this.pageSize;
		} else {
			this.currentPage = 1;
		}
		return this.currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * Description:复制查询条件
	 * 
	 * @return
	 */
	public QueryCriteria copy() {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setCurrentIndex(this.getCurrentIndex());
		criteria.setOrderField(orderField);
		criteria.setPageSize(pageSize);
		criteria.setOrderDirection(orderDirection);
		criteria.getSortCondition().addAll(this.getSortCondition());
		criteria.getQueryCondition().putAll(this.getQueryCondition());
		return criteria;
	}

	/**
	 * Description:重置查询条件和排序字段
	 * 
	 */
	public void reset() {
		currentIndex = 0;
		orderField = "";
		orderDirection = "ASC";
		queryCondition.clear();
		sortCondition.clear();
	}
}
