package com.cloudstong.platform.resource.operationmanage.action;

import javax.annotation.Resource;

import com.cloudstong.platform.resource.operationmanage.service.OperationManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列Action
 */
public class OperationManageAction {

	/**
	 * 操作操作列的服务接口,<code>operationManageService</code> 对象是OperationManageService接口的一个实例
	 */
	@Resource
	private OperationManageService operationManageService;

	public OperationManageService getOperationManageService() {
		return operationManageService;
	}

	public void setOperationManageService(
			OperationManageService operationManageService) {
		this.operationManageService = operationManageService;
	}

}
