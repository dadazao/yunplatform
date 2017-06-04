package com.cloudstong.platform.resource.querycontrol.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.querycontrol.dao.QueryControlDao;
import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;
import com.cloudstong.platform.resource.querycontrol.service.QueryControlService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询组件服务接口实现类
 */
@Repository("queryControlService")
public class QueryControlServiceImpl implements QueryControlService {

	@Resource
	private QueryControlDao queryControlDao;

	public QueryControlDao getQueryControlDao() {
		return queryControlDao;
	}

	public void setQueryControlDao(QueryControlDao queryControlDao) {
		this.queryControlDao = queryControlDao;
	}

	@Override
	public List<QueryControl> findAllQueryControl() throws Exception {
		return this.queryControlDao.selectAllQueryControl();
	}

	@Override
	public QueryControl getQueryControlById(String queryControlId)
			throws Exception {
		return this.queryControlDao.findQueryControlById(queryControlId);
	}

	@Override
	public List<AdvanceQueryControl> findAllAdvanceQueryControl() {
		return this.queryControlDao.selectAllAdvanceQueryControl();
	}

	@Override
	public AdvanceQueryControl getAdvanceQueryControlById(Long id) {
		return this.queryControlDao.findAdvanceQueryControlById(id);
	}

}
