package com.cloudstong.platform.resource.selectmanage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.selectmanage.dao.SelectManageDao;
import com.cloudstong.platform.resource.selectmanage.model.SelectManage;
import com.cloudstong.platform.resource.selectmanage.service.SelectManageService;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选择列服务接口实现类
 */
@Repository("selectManageService")
public class SelectManageServiceImpl implements SelectManageService {

	@Resource
	private SelectManageDao selectManageDao;

	public SelectManageDao getSelectManageDao() {
		return selectManageDao;
	}


	public void setSelectManageDao(SelectManageDao selectManageDao) {
		this.selectManageDao = selectManageDao;
	}


	@Override
	public SelectManage findSelectManageById(String selectId) {
		return this.selectManageDao.findSelectManageById(selectId);
	}

}
