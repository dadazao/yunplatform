package com.cloudstong.platform.resource.pagemanage.action;

import javax.annotation.Resource;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.pagemanage.service.PageManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件Action
 */
public class PageManageAction extends BaseAction {

	private static final long serialVersionUID = -2073774461524142669L;
	
	/**
	 * 操作分页构件的服务接口,<code>pageManageService</code> 对象是PageManageService接口的一个实例
	 */
	@Resource
	private PageManageService pageManageService;

	public PageManageService getPageManageService() {
		return pageManageService;
	}

	public void setPageManageService(PageManageService pageManageService) {
		this.pageManageService = pageManageService;
	}
}
