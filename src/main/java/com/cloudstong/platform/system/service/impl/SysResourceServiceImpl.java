/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.system.dao.SysResourceDao;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysResourceService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-22
 * 
 *         Description:
 * 
 */
@Repository("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService {
	
	@Resource
	private SysResourceDao sysResourceDao;

	@Cacheable(value = "privilegeCache",key="'queryResourceSysRole:'+#systemId+#currentUser.id")
	public List<Catalog> queryResourceSysRole(Long systemId, SysUser currentUser) {
		Collection<GrantedAuthority> auths = currentUser.getAuthorities();
		List<Catalog> resourcesList = new ArrayList<Catalog>();

		if ((auths != null) && (auths.size() > 0) && (auths.contains(Constants.ROLE_GRANT_SUPER))) {
			resourcesList = sysResourceDao.getSuperResource(systemId);
		} else {
			String roles = "";
			for (GrantedAuthority role : auths) {
				roles = roles + "'" + role.getAuthority() + "'" + ",";
			}
			int index = roles.lastIndexOf(",");
			if (index >= 0) {
				roles = roles.substring(0, index);
			}
			if (StringUtil.isEmpty(roles)) {
				roles = "''";
			}
			resourcesList = sysResourceDao.getNormResourceByRole(systemId, roles);
		}

		return resourcesList;
	}
	
	@Override
	@Cacheable(value = "privilegeCache")
	public List<SysResourceExtend> getUrlRightMap(Long systemId) {
		if(systemId == Constants.DEFAULT_SYSTEM_ID) {
			String sql = "select r.*,c.tbl_alias as func,ro.tbl_bieming as roleAlias from sys_resource r left join sys_catalog c on r.tbl_module=c.id left join sys_privilege_resource pr on r.id=pr.tbl_resourceid left join sys_privilege p on pr.tbl_privilegeid=p.id left join sys_role_privilege rp on p.id = rp.tbl_privilegeid left join sys_role ro on rp.tbl_roleid = ro.id where p.tbl_enabled=1";
			List<SysResourceExtend> sres = sysResourceDao.selectSysResourceExtend(sql, null,new RowMapper<SysResourceExtend>(){
				@Override
				public SysResourceExtend mapRow(ResultSet rs, int rowNum) throws SQLException {
					SysResourceExtend sre = new SysResourceExtend();
					sre.setId(rs.getLong("id"));
					sre.setResourceName(rs.getString("tbl_resourcename"));
					sre.setResourceUrl(rs.getString("tbl_resourceurl"));
					sre.setType(rs.getString("tbl_type"));
					sre.setModule(rs.getLong("tbl_module"));
					sre.setComment(rs.getString("tbl_comment"));
					sre.setEnabled(rs.getInt("tbl_enabled"));	
					sre.setRole(rs.getString("roleAlias"));
					sre.setFunc(rs.getString("func"));
					return sre;
				}
			});
			return sres;
		}else{
			return null;
		}
	}
	
	@Override
	@Cacheable(value = "privilegeCache")
	public List<SysResourceExtend> getFunctionRoleList(Long systemId) {
		if(systemId == Constants.DEFAULT_SYSTEM_ID) {
			String sql = "select DISTINCT c.tbl_folderpackage as func,ro.tbl_bieming as roleAlias from sys_resource r left join sys_catalog c on r.tbl_module=c.id left join sys_privilege_resource pr on r.id=pr.tbl_resourceid left join sys_privilege p on pr.tbl_privilegeid=p.id left join sys_role_privilege rp on p.id = rp.tbl_privilegeid left join sys_role ro on rp.tbl_roleid = ro.id where p.tbl_enabled=1";
			List<SysResourceExtend> sres = sysResourceDao.selectSysResourceExtend(sql, null,new RowMapper<SysResourceExtend>(){
				@Override
				public SysResourceExtend mapRow(ResultSet rs, int rowNum) throws SQLException {
					SysResourceExtend sre = new SysResourceExtend();
					sre.setRole(rs.getString("roleAlias"));
					sre.setFunc(rs.getString("func"));
					return sre;
				}
			});
			return sres;
		}else{
			return null;
		}
	}


}
