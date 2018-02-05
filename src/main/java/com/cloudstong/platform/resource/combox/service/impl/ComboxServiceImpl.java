package com.cloudstong.platform.resource.combox.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.combox.dao.ComboxDao;
import com.cloudstong.platform.resource.combox.model.Combox;
import com.cloudstong.platform.resource.combox.service.ComboxService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:下拉框服务接口实现类
 */
@Repository("comboxService")
public class ComboxServiceImpl implements ComboxService {

	@Resource
	private ComboxDao comboxDao;
	
	public ComboxDao getComboxDao() {
		return comboxDao;
	}

	public void setComboxDao(ComboxDao comboxDao) {
		this.comboxDao = comboxDao;
	}
	
	@Override
	public Combox findByID(Long id) throws Exception {
		return comboxDao.findByID(id);
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
			queryForPageList = comboxDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}

}
