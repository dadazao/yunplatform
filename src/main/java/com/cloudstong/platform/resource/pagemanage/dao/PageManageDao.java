package com.cloudstong.platform.resource.pagemanage.dao;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.pagemanage.model.PageManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件操作数据库接口
 */
public interface PageManageDao {
	/**
	 * Description:根据分页构件ID查找分页构件
	 * @param pageManageId 分页构件ID
	 * @return 分页构件
	 */
	@Cacheable(value="resourceCache",key="'findPageManageById:'+#pageManageId")
	public PageManage findPageManageById(String pageManageId);
}
