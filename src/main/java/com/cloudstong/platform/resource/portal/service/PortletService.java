package com.cloudstong.platform.resource.portal.service;

import java.util.List;

import com.cloudstong.platform.resource.portal.model.Portlet;
import com.cloudstong.platform.system.model.SysUser;

/**
 * 
 * @author Jason
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * portlet(门户组件)业务接口
 */
public interface PortletService {

	public List<Portlet> findPortletByIds(SysUser user, Long[] ids)
			throws Exception;

}
