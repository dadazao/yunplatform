package com.cloudstong.platform.resource.listcontrol.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.listcontrol.dao.ListControlDao;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.listcontrol.service.ListControlService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件服务接口实现类
 */
@Repository("listControlService")
public class ListControlServiceImpl implements ListControlService {

	@Resource
	private ListControlDao listControlDao;

	public ListControlDao getListControlDao() {
		return listControlDao;
	}

	public void setListControlDao(ListControlDao listControlDao) {
		this.listControlDao = listControlDao;
	}

	@Override
	public List<ListControl> findAllListControl() throws Exception {
		return this.listControlDao.selectAllListControl();
	}

	@Override
	public ListControl getListControlById(Long listControlId)
			throws Exception {
		return this.listControlDao.findListControlById(listControlId);
	}

}
