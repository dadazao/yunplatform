/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.autocomplete.dao;

import java.util.List;

import com.cloudstong.platform.resource.autocomplete.model.AutoComplete;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public interface AutoCompleteDao {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param string
	 * @param array
	 * @param currentIndex
	 * @param pageSize
	 * @return
	 */
	List queryForPageList(String string, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param compexId
	 * @return
	 */
	AutoComplete findByID(Long compexId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param belongTable
	 * @param columnName
	 * @return
	 */
	String findColumnValueList(String belongTable, String columnName);

}
