/**
 * 
 */
package com.cloudstong.platform.system.dao;

import java.util.List;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;

/**
 * @author liuqi Created on 2014-8-23
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 */
public interface SysUserDao extends BaseJdbcDao<SysUser, Long> {

	public SysUser getById(Long id);
	
	public SysUser getByAccount(String username);

	public List getByOrgId(Long orgId);

	public List getByPosId(Long posId);

	public List getByRoleId(Long roleId);

	public List<SysUserOrg> getChargeByOrgId(Long orgId);

	/**
	 * 
	 * Description: 根据组织id获取其下所有用户的id列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> getUserIdsByOrgId(Long orgId);

	/**
	 * Description:删除用户机构中间表记录
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deleteOrgMiddle(Long id);

	/**
	 * Description:删除用户岗位中间表记录
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deletePositionMiddle(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deleteRoleMiddle(Long id);

	public void changePassword(Long id, String encPassword);

}
