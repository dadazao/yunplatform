package com.cloudstong.platform.resource.operationmanage.service;

import com.cloudstong.platform.resource.operationmanage.model.OperationManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列服务接口
 */
public interface OperationManageService {
	/**
	 * Description:根据操作列构件ID查找操作列构件
	 * @param optId 操作列构件ID
	 * @return 操作列构件
	 */
	public OperationManage findOperationManageById(String optId);
}
