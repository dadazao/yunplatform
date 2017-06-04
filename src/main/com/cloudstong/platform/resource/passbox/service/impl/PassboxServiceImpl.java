package com.cloudstong.platform.resource.passbox.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.passbox.dao.PassboxDao;
import com.cloudstong.platform.resource.passbox.model.Passbox;
import com.cloudstong.platform.resource.passbox.service.PassboxService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:密码框服务接口实现类
 */
@Repository("passboxService")
public class PassboxServiceImpl implements PassboxService {

	@Resource
	private PassboxDao passboxDao; 
	
	public PassboxDao getPassboxDao() {
		return passboxDao;
	}

	public void setPassboxDao(PassboxDao passboxDao) {
		this.passboxDao = passboxDao;
	}

	@Override
	public Passbox findByID(Long id) throws Exception {
		return passboxDao.findByID(id);
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
			queryForPageList = passboxDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}
	
}
