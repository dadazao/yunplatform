package com.cloudstong.platform.resource.ordermanage.action;

import javax.annotation.Resource;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.ordermanage.service.OrderManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列Action
 */
public class OrderManageAction extends BaseAction {

	private static final long serialVersionUID = -47675919524029858L;
	
	/**
	 * 操作序号列的服务接口,<code>orderManageService</code> 对象是OrderManageService接口的一个实例
	 */
	@Resource
	private OrderManageService orderManageService;

}
