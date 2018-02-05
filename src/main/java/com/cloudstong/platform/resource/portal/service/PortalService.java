package com.cloudstong.platform.resource.portal.service;

import java.util.Map;

import com.cloudstong.platform.resource.portal.model.Portal;
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
 * 门户业务接口
 */
public interface PortalService {
	/**
	 * 根据门户id查找相应门户内容
	 * 
	 * @param user
	 * 
	 * @param portalId
	 * @return
	 * @throws Exception
	 */
	public Portal findPortalByPortalId(SysUser user, String portalId)
			throws Exception;

	/**
	 * 获取当前默认的门户
	 * 
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public Portal doDefaultPortal(SysUser user) throws Exception;
	/**
	 * 
	 * Description:
	 * 查找当前用户的门户
	 * Steps:
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, String> findPortalIds(SysUser user);
}
