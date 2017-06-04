package com.cloudstong.platform.resource.operationmanage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.operationmanage.dao.OperationManageDao;
import com.cloudstong.platform.resource.operationmanage.model.OperationManage;
import com.cloudstong.platform.resource.operationmanage.service.OperationManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列服务接口实现类
 */
@Repository("operationManageService")
public class OperationManageServiceImpl implements OperationManageService {

	@Resource
	private OperationManageDao operationManageDao;

	public OperationManageDao getOperationManageDao() {
		return operationManageDao;
	}

	public void setOperationManageDao(OperationManageDao operationManageDao) {
		this.operationManageDao = operationManageDao;
	}

	@Override
	public OperationManage findOperationManageById(String optId) {
		return this.operationManageDao.findOperationManageById(optId);
	}

}
