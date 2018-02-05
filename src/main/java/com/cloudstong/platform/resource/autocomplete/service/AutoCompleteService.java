/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.autocomplete.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public interface AutoCompleteService {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param qc
	 * @return
	 * @throws Exception 
	 */
	List<Component> queryForPageList(QueryCriteria qc) throws Exception;

}
