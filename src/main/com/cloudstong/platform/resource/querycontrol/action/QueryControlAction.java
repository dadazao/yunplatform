package com.cloudstong.platform.resource.querycontrol.action;

import javax.annotation.Resource;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.querycontrol.service.QueryControlService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询组件Action
 */
public class QueryControlAction extends BaseAction {

	private static final long serialVersionUID = 4324493472792667402L;
	
	/**
	 * 操作查询组件的服务接口,<code>queryControlService</code> 对象是QueryControlService接口的一个实例
	 */
	@Resource
	private QueryControlService queryControlService;

	public QueryControlService getQueryControlService() {
		return queryControlService;
	}

	public void setQueryControlService(QueryControlService queryControlService) {
		this.queryControlService = queryControlService;
	}

}
