/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.dao.SysPrivilegeDao;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
@Repository("sysPrivilegeDao")
public class SysPrivilegeDaoImpl extends BaseJdbcDaoImpl<SysPrivilege, Long> implements SysPrivilegeDao {

	@Resource
	private CatalogDao catalogDao;
	
	@Override
	public String getTable() {
		return "sys_privilege";
	}

	@Override
	public void deleteAuthResourceData(Long id) {
		String sql = "delete from sys_privilege_resource where tbl_resourceid = ?";
		Object [] args = new Object[]{id};
		super.jdbcTemplate.update(sql, args);
	}

	@Override
	public Long addModuleAuth(Catalog _catalog,SysUser user) {
		Long buttonid = -1L;
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_privilege (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_privilegename,tbl_code,tbl_comment,tbl_enabled,tbl_module,tbl_buttonid)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"访问权","",_catalog.getName()+"访问权",1,_catalog.getId(),buttonid};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addModuleResource(Catalog _catalog,SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_resource (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_resourcename,tbl_resourceurl,tbl_comment,tbl_type,tbl_enabled,tbl_module)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName(),_catalog.getPath(),_catalog.getName(),1,1,_catalog.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public void addModuleRelation(Long authId, Long resourceId) {
		String sql = "insert into sys_privilege_resource (id,tbl_privilegeid,tbl_resourceid)values(?,?,?)";
		Object[]args = new Object[]{UniqueIdUtil.genId(),authId,resourceId};
		insert(sql, args);
	}

	@Override
	public void deleteModuleAuth(SysPrivilege _sysPrivilege) {
		String sql = "delete from sys_privilege where id = ?";
		Object[]args = new Object[]{_sysPrivilege.getId()};
		update(sql,args);
	}

	@Override
	public void deleteModuleResource(SysPrivilege _sysPrivilege) {
		String sql = "delete from sys_resource where id in (select tbl_resourceid from sys_privilege_resource where tbl_privilegeid = ?)";
		Object[]args = new Object[]{_sysPrivilege.getId()};
		update(sql,args);
	}

	@Override
	public void deleteModuleRelation(SysPrivilege _sysPrivilege) {
		String sql = "delete from sys_privilege_resource where tbl_privilegeid = ?";
		Object[]args = new Object[]{_sysPrivilege.getId()};
		update(sql,args);
		
		String rpSql = "delete from sys_role_privilege where tbl_privilegeid = ?";
		Object[] rpArgs = new Object[]{_sysPrivilege.getId()};
		update(rpSql,rpArgs);
	}

	@Override
	public Long addTabulationButtonAuth(Catalog _catalog, TabulationButton _tabulationButton, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_privilege (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_privilegename,tbl_code,tbl_comment,tbl_enabled,tbl_module,tbl_buttonid)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_tabulationButton.getShowName()+"按钮","",_catalog.getName()+"模块："+_tabulationButton.getButtonName()+"按钮",1,_catalog.getId(),_tabulationButton.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addTabulationButtonResource(Catalog _catalog, TabulationButton _tabulationButton, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_resource (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_resourcename,tbl_resourceurl,tbl_comment,tbl_type,tbl_enabled,tbl_module)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		String resourceurl = null;
		String buttonUrl = _tabulationButton.getUrl();
		if(buttonUrl != null) {
			String table = catalogDao.getMaintable(_catalog.getId());
			if(table != null) {
				resourceurl = buttonUrl.replace("{param}", EncryptUtil.Md5(table));
			}else{
				resourceurl = buttonUrl.replace("{param}","");
			}
		}
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_tabulationButton.getShowName()+"按钮",resourceurl,_catalog.getName()+"模块的"+_tabulationButton.getShowName()+"按钮",4,1,_catalog.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addFormButtonAuth(Catalog _catalog, FormButton _formButton, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_privilege (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_privilegename,tbl_code,tbl_comment,tbl_enabled,tbl_module,tbl_buttonid)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_formButton.getShowName()+"按钮","",_catalog.getName()+"模块："+_formButton.getButtonName()+"按钮",1,_catalog.getId(),_formButton.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addFormButtonResource(Catalog _catalog, FormButton _formButton, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_resource (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_resourcename,tbl_resourceurl,tbl_comment,tbl_type,tbl_enabled,tbl_module)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		String resourceurl = null;
		String buttonUrl = _formButton.getUrl();
		if(buttonUrl != null) {
			String table = catalogDao.getMaintable(_catalog.getId());
			if(table != null) {
				resourceurl = buttonUrl.replace("{param}", EncryptUtil.Md5(table));
			}else{
				resourceurl = buttonUrl.replace("{param}","");
			}
		}
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_formButton.getShowName()+"按钮",resourceurl,_catalog.getName()+"模块的"+_formButton.getShowName()+"按钮",4,1,_catalog.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addTabulationOptAuth(Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_privilege (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_privilegename,tbl_code,tbl_comment,tbl_enabled,tbl_module,tbl_buttonid)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_tabulationOpt.getShowName()+"按钮","",_catalog.getName()+"模块："+_tabulationOpt.getButtonName()+"按钮",1,_catalog.getId(),_tabulationOpt.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public Long addTabulationOptResource(Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_resource (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_resourcename,tbl_resourceurl,tbl_comment,tbl_type,tbl_enabled,tbl_module)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		String resourceurl = null;
		String buttonUrl = _tabulationOpt.getUrl();
		if(buttonUrl != null) {
			String table = catalogDao.getMaintable(_catalog.getId());
			if(table != null) {
				resourceurl = buttonUrl.replace("{param}", EncryptUtil.Md5(table));
			}else{
				resourceurl = buttonUrl.replace("{param}","");
			}
		}
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,_catalog.getName()+"模块："+_tabulationOpt.getShowName()+"按钮",resourceurl,_catalog.getName()+"模块的"+_tabulationOpt.getShowName()+"按钮",4,1,_catalog.getId()};
		insert(sql, args);
		return uuid;
	}

	@Override
	public void deletePrivilegeResource(Long id) {
		String sql = "delete from sys_resource where id in (select tbl_resourceid from sys_privilege_resource where tbl_privilegeid = ?)";
		Object[]args = new Object[]{id};
		update(sql,args);
	}

	@Override
	public void deletePrivilegeRelation(Long id) {
		String sql = "delete from sys_privilege_resource where tbl_privilegeid = ?";
		Object[]args = new Object[]{id};
		update(sql,args);
		
		String rpSql = "delete from sys_role_privilege where tbl_privilegeid = ?";
		Object[] rpArgs = new Object[]{id};
		update(rpSql,rpArgs);
	}
	
	public void deletePrivilegeRole(Long id) {
		String sql = "delete from sys_role_privilege where tbl_privilegeid = ?";
		Object[]args = new Object[]{id};
		update(sql,args);
	}

	@Override
	public Long findCatalogIdByPrivileId(Long id) {
		String sql = "select p.tbl_module as tbl_module from sys_privilege p where p.id = ? and tbl_buttonid = ?";
		Object[] args = new Object[]{id,-1L};
		return jdbcTemplate.queryForLong(sql, args);
	}
	

}
