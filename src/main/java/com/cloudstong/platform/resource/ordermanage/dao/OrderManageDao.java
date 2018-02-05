package com.cloudstong.platform.resource.ordermanage.dao;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.ordermanage.model.OrderManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列操作数据库接口
 */
public interface OrderManageDao {
	/**
	 * Description:根据序号列ID查找序号列
	 * @param orderManageId 序号列ID
	 * @return 序号列
	 */
	@Cacheable(value="resourceCache",key="'findOrderManageById:'+#orderManageId")
	public OrderManage findOrderManageById(String orderManageId);
}
