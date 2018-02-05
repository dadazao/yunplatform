package com.cloudstong.platform.resource.pagemanage.service;

import com.cloudstong.platform.resource.pagemanage.model.PageManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件服务接口
 */
public interface PageManageService {
	/**
	 * Description:根据分页构件ID查找分页构件
	 * @param pageId 分页构件ID
	 * @return 分页构件
	 */
	public PageManage findPageManageById(String pageId);
}
