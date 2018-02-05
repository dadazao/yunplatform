package com.cloudstong.platform.resource.selectmanage.dao;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.selectmanage.model.SelectManage;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选择列操作数据库接口
 */
public interface SelectManageDao {
	/**
	 * Description:根据选择列ID查找选择列
	 * @param selectManageId 选择列ID
	 * @return 选择列
	 */
	@Cacheable(value="resourceCache",key="'findSelectManageById:'+#selectManageId")
	public SelectManage findSelectManageById(String selectManageId);
}
