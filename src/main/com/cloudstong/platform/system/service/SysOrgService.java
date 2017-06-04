/**
 * 
 */
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysOrgType;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * Created on 2014-8-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public interface SysOrgService {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	List<SysOrg> selectAllOrg();

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryOrg(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param orgId
	 * @return
	 */
	SysOrg findOrgById(Long orgId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysOrg
	 * @return
	 */
	Long doSaveOrg(SysOrg sysOrg,SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	void doDeleteOrgs(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryUserOrg(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userIds
	 * @param orgId
	 */
	void doAddUser(String userIds, Long orgId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedSubIDs
	 */
	void doDeleteUser(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userOrgId
	 */
	void doSetupMainOrg(Long userOrgId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userOrgId
	 */
	void doSetupPrincipal(Long userOrgId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userOrgId
	 */
	void doCancelPrincipal(Long userOrgId);

	SysOrg getDefaultOrgByUserId(Long startUserId);

	SysOrg getById(Long orgId);

	SysOrg getParentWithTypeLevel(SysOrg sysOrg, SysOrgType sysOrgType);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 * @return
	 */
	List findSubOrgs(Long[] selectedIDs);

}
