package com.cloudstong.platform.resource.operationmanage.dao;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.operationmanage.model.OperationManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列操作数据库接口
 */
public interface OperationManageDao {

	/**
	 * Description:根据操作列ID查找操作列
	 * @param operationManageId 操作列ID
	 * @return 操作列
	 */
	@Cacheable(value="resourceCache",key="'findOperationManageById:'+#operationManageId")
	public OperationManage findOperationManageById(String operationManageId);

}
