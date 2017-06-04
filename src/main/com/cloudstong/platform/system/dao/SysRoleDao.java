/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysRole;

/**
 * @author liuqi
 * 
 *         Created on 2014-8-28
 * 
 *         Description:
 * 
 */
public interface SysRoleDao extends BaseJdbcDao<SysRole, Long> {

	public SysRole getById(Long id);

	public List<SysRole> getByUserId(Long lUserId);

	/**
	 * Description:删除用户角色中间表记录
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deleteMiddleTable(Long id);

	public List<String> selectRole(String sql, Object[] objects, RowMapper<String> rowMapper);


}
