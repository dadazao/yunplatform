package com.cloudstong.platform.resource.ordermanage.service;

import com.cloudstong.platform.resource.ordermanage.model.OrderManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列服务接口
 */
public interface OrderManageService {
	/**
	 * Description:根据序号列构件ID查找序号列构件
	 * @param orderId 序号列构件ID
	 * @return 序号列构件
	 */
	public OrderManage findOrderManageById(String orderId);
}
