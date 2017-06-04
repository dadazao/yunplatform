/**
 * 
 */
package com.cloudstong.platform.system.dao;

import java.util.List;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.system.model.SysOrg;

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
public interface SysOrgDao extends BaseJdbcDao<SysOrg, Long> {

	public SysOrg getById(Long id);

	public List<SysOrg> getOrgsByUserId(Long userId);

	public SysOrg getPrimaryOrgByUserId(Long userId);

	/**
	 * Description:删除机构时删除用户机构中间表的数据
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deleteMiddleTable(Long id);

}
