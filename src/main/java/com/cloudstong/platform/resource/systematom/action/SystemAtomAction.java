package com.cloudstong.platform.resource.systematom.action;

import javax.annotation.Resource;

import com.cloudstong.platform.resource.systematom.service.SystemAtomService;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统元素Action
 */
public class SystemAtomAction {

	/**
	 * 操作系统元素的服务接口,<code>systemAtomService</code> 对象是SystemAtomService接口的一个实例
	 */
	@Resource
	private SystemAtomService systemAtomService;

	public SystemAtomService getSystemAtomService() {
		return systemAtomService;
	}

	public void setSystemAtomService(SystemAtomService systemAtomService) {
		this.systemAtomService = systemAtomService;
	}

}
