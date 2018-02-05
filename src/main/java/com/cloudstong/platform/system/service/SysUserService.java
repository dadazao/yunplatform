/**
 * 
 */
package com.cloudstong.platform.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * Created on 2014-8-23
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public interface SysUserService extends UserDetailsService{
	
	List<SysUser> findAllUser();

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryUser(String queryType,String relationId,QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryUser(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userId
	 * @return
	 */
	SysUser findUserById(Long userId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysUser
	 * @param user
	 * @return
	 */
	Long doSaveUser(SysUser sysUser, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	void doDeleteUsers(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userId
	 * @param orgIds
	 */
	void doAddUserOrg(Long userId, String orgIds);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userOrgId
	 */
	void doDeleteUserOrg(Long userOrgId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysUser
	 * @param user
	 */
	void doResetPassword(SysUser sysUser, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysUser
	 * @param user
	 */
	void doResetStatus(SysUser sysUser, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userId
	 * @param positionIds
	 */
	void doAddUserPosition(Long userId, String positionIds);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userPositionId
	 */
	void doDeleteUserPosition(Long userPositionId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userId
	 * @param roleIds
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddUserRole(Long userId, String roleIds);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userRoleId
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDeleteUserRole(Long userRoleId);

	List<SysUser> getByOrgParam(String cmpIds);

	SysUser getById(Long id);

	List<SysUser> getByUserIdAndUplow(long startUserId, long nodeUserId);

	List<SysUser> getByIdSet(Set uidSet);

	List<SysUser> getByUserParam(String cmpIds);

	SysUser getByAccount(String account);

	SysUser findUserByUsername(String username, String password);

	void updatePwd(Long id, String password);

	void doChangePassword(Long id, String encPassword);

}
