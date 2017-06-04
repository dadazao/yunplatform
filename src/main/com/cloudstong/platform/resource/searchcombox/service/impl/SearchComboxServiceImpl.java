package com.cloudstong.platform.resource.searchcombox.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.searchcombox.dao.SearchComboxDao;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;
import com.cloudstong.platform.resource.searchcombox.service.SearchComboxService;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:搜索下拉框服务接口实现类
 */
@Repository("searchComboxService")
public class SearchComboxServiceImpl implements SearchComboxService {

	@Resource
	private SearchComboxDao searchComboxDao;
	
	public SearchComboxDao getSearchComboxDao() {
		return searchComboxDao;
	}

	public void setSearchComboxDao(SearchComboxDao searchComboxDao) {
		this.searchComboxDao = searchComboxDao;
	}

	@Override
	public SearchCombox findByID(Long id) throws Exception {
		return searchComboxDao.findByID(id);
	}

	@Override
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception {
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
			queryForPageList = searchComboxDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}

	
}
