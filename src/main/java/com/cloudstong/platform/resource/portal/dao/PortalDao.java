package com.cloudstong.platform.resource.portal.dao;

import java.util.List;

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
 * 门户数据操作接口
 */
public interface PortalDao {

	/**
	 * 根据portal id查找出对应的Portal
	 * 
	 * @throws Exception
	 */
	public Portal findPortalById(String portalId) throws Exception;

	public List<Long> findPortletIdListByPortalId(Long id) throws Exception;

	public Portal findDefaultPortal(SysUser user) throws Exception;

	public List<String> findCurrentPortletIdListByParams(String string, SysUser user);

}
