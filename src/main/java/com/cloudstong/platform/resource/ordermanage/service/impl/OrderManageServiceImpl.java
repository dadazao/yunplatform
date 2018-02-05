package com.cloudstong.platform.resource.ordermanage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.ordermanage.dao.OrderManageDao;
import com.cloudstong.platform.resource.ordermanage.model.OrderManage;
import com.cloudstong.platform.resource.ordermanage.service.OrderManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列服务接口实现类
 */
@Repository("orderManageService")
public class OrderManageServiceImpl implements OrderManageService {

	@Resource
	private OrderManageDao orderManageDao;

	public OrderManageDao getOrderManageDao() {
		return orderManageDao;
	}

	public void setOrderManageDao(OrderManageDao orderManageDao) {
		this.orderManageDao = orderManageDao;
	}

	@Override
	public OrderManage findOrderManageById(String orderId) {
		return orderManageDao.findOrderManageById(orderId);
	}

}
