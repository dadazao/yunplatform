/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.autocomplete.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.autocomplete.dao.AutoCompleteDao;
import com.cloudstong.platform.resource.autocomplete.service.AutoCompleteService;
import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
@Repository("autoCompleteService")
public class AutoCompleteServiceImpl implements AutoCompleteService {
	@Resource
	private AutoCompleteDao autoCompleteDao;

	@Override
	public List<Component> queryForPageList(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		List queryForPageList = null;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			where.append(" AND  " + entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		// where.append(" ORDER BY comm_createDate");
		try {
			queryForPageList = autoCompleteDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}
	
}
