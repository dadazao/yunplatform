package com.cloudstong.platform.resource.selectmanage.service;

import com.cloudstong.platform.resource.selectmanage.model.SelectManage;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选择列服务接口
 */
public interface SelectManageService {
	/**
	 * Description:根据选择列构件ID查找选择列构件
	 * @param selectId 选择列构件ID
	 * @return 选择列构件
	 */
	public SelectManage findSelectManageById(String selectId);
}
