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
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.dao.SysPrivilegeDao;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.rowmap.SysResourceRowMapper;
import com.cloudstong.platform.system.rowmap.SysprivilegeRowMapper;
import com.cloudstong.platform.system.service.SysPrivilegeService;
import com.cloudstong.platform.system.util.SecurityUtil;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
@Repository("sysPrivilegeService")
public class SysPrivilegeServiceImpl implements SysPrivilegeService {
	
	private final Log log = LogFactory.getLog(getClass());

	@Resource
	private SysPrivilegeDao sysPrivilegeDao;
	
	@Resource
	private CatalogDao catalogDao;

	@Override
	public PageResult queryPrivilegeResource(Long privilegeId,QueryCriteria queryCriteria) {
		String sql = "select m.* from sys_resource m left join sys_privilege_resource pr on m.id = pr.tbl_resourceid where pr.tbl_privilegeid = ?";
		return sysPrivilegeDao.query(sql,new Object[]{privilegeId},queryCriteria,new SysResourceRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public Long doSaveResource(SysResource sysResource, Long privilegeId, SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(sysResource.getId()==null||sysResource.getId().equals(0l)){
			//向资源表插入记录
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_resource (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_resourcename,tbl_resourceurl,tbl_comment,tbl_type,tbl_enabled,tbl_module,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysResource.getResourceName(),sysResource.getResourceUrl(),sysResource.getComment(),sysResource.getType(),sysResource.getEnabled(),sysResource.getModule(),sysResource.getOptCounter()};
			sysPrivilegeDao.insert(sql, args);
			//向权限和资源的中间表插入记录
			String msql = "insert into sys_privilege_resource (id,tbl_privilegeid,tbl_resourceid)values(?,?,?)";
			Object[]margs = new Object[]{UniqueIdUtil.genId(),privilegeId,uuid};
			sysPrivilegeDao.insert(msql, margs);
			id = uuid;
		}else{
			//更新资源表记录
			id = sysResource.getId();
			String sql = "update sys_resource set comm_updateBy=?,comm_updateDate=?,tbl_resourcename=?,tbl_resourceurl=?,tbl_comment=?,tbl_type=?,tbl_enabled=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,sysResource.getResourceName(),sysResource.getResourceUrl(),sysResource.getComment(),sysResource.getType(),sysResource.getEnabled(),sysResource.getId()};
			sysPrivilegeDao.update(sql, args);
		}
		
		SecurityUtil.removeAll();
		
		return id;
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public Long doSavePrivilege(SysPrivilege sysPrivilege, SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(sysPrivilege.getId()==null||sysPrivilege.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_privilege (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_privilegename,tbl_code,tbl_comment,tbl_enabled,tbl_module,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysPrivilege.getPrivilegeName(),sysPrivilege.getCode(),sysPrivilege.getComment(),sysPrivilege.getEnabled(),sysPrivilege.getModule(),sysPrivilege.getOptCounter()};
			sysPrivilegeDao.insert(sql, args);
			id = uuid;
		}else{
			id = sysPrivilege.getId();
			String sql = "update sys_privilege set comm_updateBy=?,comm_updateDate=?,tbl_privilegename=?,tbl_code=?,tbl_comment=?,tbl_enabled=?,tbl_module=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,sysPrivilege.getPrivilegeName(),sysPrivilege.getCode(),sysPrivilege.getComment(),sysPrivilege.getEnabled(),sysPrivilege.getModule(),sysPrivilege.getId()};
			sysPrivilegeDao.update(sql, args);
		}
		
		SecurityUtil.removeAll();
		
		return id;
	}

	@Override
	public PageResult queryPrivilege(QueryCriteria queryCriteria) {
		return sysPrivilegeDao.query(queryCriteria, new SysprivilegeRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysPrivilege findPrivilegeById(Long privilegeId) {
		return sysPrivilegeDao.selectById(privilegeId, new SysprivilegeRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDeletePrivileges(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除权限
			sysPrivilegeDao.delete(id);
			//删除权限下的所有资源及关联表记录
			sysPrivilegeDao.deletePrivilegeResource(id);
			sysPrivilegeDao.deletePrivilegeRelation(id);
			sysPrivilegeDao.deletePrivilegeRole(id);
			
			try {
				Long catalogId = sysPrivilegeDao.findCatalogIdByPrivileId(id);
				catalogDao.updateAuth(catalogId,1);
			} catch (Exception e) {
				log.debug("the privile is not a module auth.");
			}
		}
		
		SecurityUtil.removeAll();
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doEnablePrivileges(Long[] selectedIDs) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update sys_privilege set tbl_enabled = 1 where id in (");
		for (int i = 0; i < selectedIDs.length; i++) {
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
			params.add(selectedIDs[i]);
		}
		sql.append(")");
		sysPrivilegeDao.update(sql.toString(),params.toArray());
		
		SecurityUtil.removeAll();
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDisablePrivileges(Long[] selectedIDs) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update sys_privilege set tbl_enabled = 0 where id in (");
		for (int i = 0; i < selectedIDs.length; i++) {
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
			params.add(selectedIDs[i]);
		}
		sql.append(")");
		sysPrivilegeDao.update(sql.toString(),params.toArray());
		
		SecurityUtil.removeAll();
	}

	@Override
	public SysResource findResourceById(Long resourceId) {
		return (SysResource)sysPrivilegeDao.selectById("sys_resource",resourceId, new SysResourceRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDeleteResources(Long[] selectedSubIDs) {
		for (Long id : selectedSubIDs) {
			//删除资源
			sysPrivilegeDao.delete("sys_resource",id);
			//删除权限和资源的中间表记录
			sysPrivilegeDao.deleteAuthResourceData(id);
		}
		
		SecurityUtil.removeAll();
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doEnableResources(Long[] selectedSubIDs) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update sys_resource set tbl_enabled = 1 where id in (");
		for (int i = 0; i < selectedSubIDs.length; i++) {
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
			params.add(selectedSubIDs[i]);
		}
		sql.append(")");
		sysPrivilegeDao.update(sql.toString(),params.toArray());
		
		SecurityUtil.removeAll();
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDisableResources(Long[] selectedSubIDs) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update sys_resource set tbl_enabled = 0 where id in (");
		for (int i = 0; i < selectedSubIDs.length; i++) {
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
			params.add(selectedSubIDs[i]);
		}
		sql.append(")");
		sysPrivilegeDao.update(sql.toString(),params.toArray());
		
		SecurityUtil.removeAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysPrivilege> selectCatalogAuthList(String module) {
		return sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ?", new Object[]{module}, new SysprivilegeRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysPrivilege> selectHasAuthList(Long roleId) {
		return sysPrivilegeDao.select("select * from sys_privilege p left join sys_role_privilege rp on p.id = rp.tbl_privilegeid where rp.tbl_roleid = ?", new Object[]{roleId}, new SysprivilegeRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddModuleAuth(String status, Catalog _catalog,SysUser user) {
		//判断是否勾中复选框，若为true则添加权限和资源，否则删除权限和资源
		if(status.equals("true")){
			addModuleAndParentAuth(_catalog, user);
		}else{
			List<SysPrivilege> _list = sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ? and tbl_buttonid = ?", new Object[]{_catalog.getId(),-1L}, new SysprivilegeRowMapper());
			if(_list.size()>0){
				SysPrivilege _sysPrivilege = _list.get(0);
				//删除权限
				sysPrivilegeDao.deleteModuleAuth(_sysPrivilege);
				//删除资源
				sysPrivilegeDao.deleteModuleResource(_sysPrivilege);
				//删除权限和资源的对应关系
				sysPrivilegeDao.deleteModuleRelation(_sysPrivilege);
				//模块去掉权
				catalogDao.updateAuth(_catalog.getId(),0);
			}
		}
		
		SecurityUtil.removeAll();
	}

	@SuppressWarnings("unchecked")
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	private void addModuleAndParentAuth(Catalog _catalog, SysUser user) {
		//查询该模块是否已经添加过访问权限，若添加过则不重复添加
		List<SysPrivilege> _list = sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ? and tbl_buttonid = ?", new Object[]{_catalog.getId(),-1L}, new SysprivilegeRowMapper());
		if(_list.size()==0){
			//添加权限
			Long authId = sysPrivilegeDao.addModuleAuth(_catalog,user);
			//添加资源
			Long resourceId = sysPrivilegeDao.addModuleResource(_catalog,user);
			//添加权限和资源的对应关系
			sysPrivilegeDao.addModuleRelation(authId,resourceId);
			//表示模块赋权
			catalogDao.updateAuth(_catalog.getId(),1);
		}
		if(!_catalog.getParentId().equals(1L)){
			Catalog _tmpCatalog = catalogDao.selectById(_catalog.getParentId());
			addModuleAndParentAuth(_tmpCatalog,user);
		}
		
		SecurityUtil.removeAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddTabulationButtonAuth(String status, Catalog _catalog, TabulationButton _tabulationButton, SysUser user) {
		//判断是否勾中复选框，若为true则添加权限和资源，否则删除权限和资源
		if(status.equals("true")){
			//添加权限
			Long authId = sysPrivilegeDao.addTabulationButtonAuth(_catalog,_tabulationButton,user);
			//添加资源
			Long resourceId = sysPrivilegeDao.addTabulationButtonResource(_catalog,_tabulationButton,user);
			//添加权限和资源的对应关系
			sysPrivilegeDao.addModuleRelation(authId,resourceId);
		}else{
			List<SysPrivilege> _list = sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ? and tbl_buttonid = ?", new Object[]{_catalog.getId(),_tabulationButton.getId()}, new SysprivilegeRowMapper());
			if(_list.size()>0){
				SysPrivilege _sysPrivilege = _list.get(0);
				//删除权限
				sysPrivilegeDao.deleteModuleAuth(_sysPrivilege);
				//删除资源
				sysPrivilegeDao.deleteModuleResource(_sysPrivilege);
				//删除权限和资源的对应关系
				sysPrivilegeDao.deleteModuleRelation(_sysPrivilege);
			}
		}
		
		SecurityUtil.removeAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddTabulationOptAuth(String status, Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user) {
		//判断是否勾中复选框，若为true则添加权限和资源，否则删除权限和资源
		if(status.equals("true")){
			//添加权限
			Long authId = sysPrivilegeDao.addTabulationOptAuth(_catalog,_tabulationOpt,user);
			//添加资源
			Long resourceId = sysPrivilegeDao.addTabulationOptResource(_catalog,_tabulationOpt,user);
			//添加权限和资源的对应关系
			sysPrivilegeDao.addModuleRelation(authId,resourceId);
		}else{
			List<SysPrivilege> _list = sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ? and tbl_buttonid = ?", new Object[]{_catalog.getId(),_tabulationOpt.getId()}, new SysprivilegeRowMapper());
			if(_list.size()>0){
				SysPrivilege _sysPrivilege = _list.get(0);
				//删除权限
				sysPrivilegeDao.deleteModuleAuth(_sysPrivilege);
				//删除资源
				sysPrivilegeDao.deleteModuleResource(_sysPrivilege);
				//删除权限和资源的对应关系
				sysPrivilegeDao.deleteModuleRelation(_sysPrivilege);
			}
		}
		
		SecurityUtil.removeAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddFormButtonAuth(String status, Catalog _catalog, FormButton _formButton, SysUser user) {
		//判断是否勾中复选框，若为true则添加权限和资源，否则删除权限和资源
		if(status.equals("true")){
			//添加权限
			Long authId = sysPrivilegeDao.addFormButtonAuth(_catalog,_formButton,user);
			//添加资源
			Long resourceId = sysPrivilegeDao.addFormButtonResource(_catalog,_formButton,user);
			//添加权限和资源的对应关系
			sysPrivilegeDao.addModuleRelation(authId,resourceId);
		}else{
			List<SysPrivilege> _list = sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ? and tbl_buttonid = ?", new Object[]{_catalog.getId(),_formButton.getId()}, new SysprivilegeRowMapper());
			if(_list.size()>0){
				SysPrivilege _sysPrivilege = _list.get(0);
				//删除权限
				sysPrivilegeDao.deleteModuleAuth(_sysPrivilege);
				//删除资源
				sysPrivilegeDao.deleteModuleResource(_sysPrivilege);
				//删除权限和资源的对应关系
				sysPrivilegeDao.deleteModuleRelation(_sysPrivilege);
			}
		}
		
		SecurityUtil.removeAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPrivilege> findAllPrivilegeByModule(Long module) {
		return sysPrivilegeDao.select("select * from sys_privilege where tbl_module = ?", new Object[]{module}, new SysprivilegeRowMapper());
	}

	@Override
	public List<SysPrivilege> selectCatalogAuthListByModules(String modules) {
		String [] mods = modules.split(";");
		StringBuffer sql = new StringBuffer("select * from sys_privilege where tbl_module in (");
		for(int i=0;i<mods.length;i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		sql.append(")");
		return sysPrivilegeDao.select(sql.toString(), mods, new SysprivilegeRowMapper());
	}

}
