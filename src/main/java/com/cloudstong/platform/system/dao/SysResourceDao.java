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
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysResourceExtend;

/**
 * @author Allan
 * 
 *         Created on 2014-10-22
 * 
 *         Description:
 * 
 */
public interface SysResourceDao extends BaseJdbcDao<SysResource, Long> {
	
	public List<SysResourceExtend> selectSysResourceExtend(String sql, Object object, RowMapper<SysResourceExtend> rowMapper);

	public List<String> selectRole(String sql, Object[] objects, RowMapper<String> rowMapper);

	public List<Catalog> getSuperResource(Long systemId);

	public List<Catalog> getNormResourceByRole(Long systemId, String roles);
	
}
