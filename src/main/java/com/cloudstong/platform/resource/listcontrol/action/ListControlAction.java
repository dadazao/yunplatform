package com.cloudstong.platform.resource.listcontrol.action;

import javax.annotation.Resource;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.listcontrol.service.ListControlService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件Action
 */
public class ListControlAction extends BaseAction {

	private static final long serialVersionUID = 1858070954895403379L;
	
	/**
	 * 操作列表组件的服务接口,<code>listControlService</code> 对象是ListControlService接口的一个实例
	 */
	@Resource
	private ListControlService listControlService;

}
