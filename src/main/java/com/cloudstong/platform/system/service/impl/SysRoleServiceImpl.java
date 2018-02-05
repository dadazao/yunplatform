/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysRoleDao;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserPosition;
import com.cloudstong.platform.system.model.SysUserRole;
import com.cloudstong.platform.system.service.SysRoleService;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysPositionRowMapper;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysUserPositionRowMapper;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysUserRowMapper;

/**
 * @author liuqi
 * 
 *         Created on 2014-8-28
 * 
 *         Description:
 * 
 */
@Repository("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;

	@Override
	@Cacheable(value = "privilegeCache")
	public PageResult queryRole(QueryCriteria queryCriteria) {
		return sysRoleDao.query(queryCriteria, new SysRoleRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "privilegeCache")
	public SysRole findRoleById(Long roleId) {
		return sysRoleDao.selectById(roleId, new SysRoleRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public Long doSaveRole(SysRole sysRole, SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(sysRole.getId()==null||sysRole.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_role (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_rolename,tbl_bieming,tbl_rolecomment,tbl_yunxushanchu,tbl_yunxubianji,tbl_shifouqiyong,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysRole.getRoleName(),sysRole.getRoleAliss(),sysRole.getComment(),sysRole.getCanDelete(),sysRole.getCanEdit(),sysRole.getEnable(),sysRole.getOptCounter()};
			sysRoleDao.insert(sql, args);
			id = uuid;
		}else{
			id = sysRole.getId();
			String sql = "update sys_role set comm_updateBy=?,comm_updateDate=?,tbl_rolename=?,tbl_bieming=?,tbl_rolecomment=?,tbl_yunxushanchu=?,tbl_yunxubianji=?,tbl_shifouqiyong=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,sysRole.getRoleName(),sysRole.getRoleAliss(),sysRole.getComment(),sysRole.getCanDelete(),sysRole.getCanEdit(),sysRole.getEnable(),sysRole.getId()};
			sysRoleDao.update(sql, args);
		}
		return id; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doCopyRole(SysRole sysRole, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		SysRole _sysRole = sysRoleDao.selectById(sysRole.getId(), new SysRoleRowMapper());
		//复制角色
		Long uuid = UniqueIdUtil.genId();
		String sql = "insert into sys_role (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
				"tbl_rolename,tbl_bieming,tbl_rolecomment,tbl_yunxushanchu,tbl_yunxubianji,tbl_shifouqiyong)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysRole.getRoleName(),sysRole.getRoleAliss(),_sysRole.getComment(),_sysRole.getCanDelete(),_sysRole.getCanEdit(),_sysRole.getEnable()};
		sysRoleDao.insert(sql, args);
		//复制角色下的用户
		List<Map<String,Object>> _userRoles = sysRoleDao.select("select * from sys_user_role where tbl_roleid = ?", new Object[]{sysRole.getId()});
		for (Map<String, Object> map : _userRoles) {
			Long sub_uuid = UniqueIdUtil.genId();
			sysRoleDao.insert("insert into sys_user_role (id,tbl_userid,tbl_roleid)values(?,?,?)", new Object[]{sub_uuid,map.get("tbl_userid"),uuid});
		}
		
	}
	
	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDeleteRoles(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除角色
			sysRoleDao.delete(id);
			//删除用户角色中间表记录
			sysRoleDao.deleteMiddleTable(id);
		}
	}
	
	@Override
	@Cacheable(value = "privilegeCache")
	public PageResult queryUserRole(QueryCriteria queryCriteria) {
		String sql = "select up.*,u.tbl_yonghuxingming as fullname,p.tbl_rolename as rolename from sys_user_role up left join sys_user u on up.tbl_userid=u.id left join sys_role p on up.tbl_roleid=p.id where 1=1 ";
		return sysRoleDao.query(sql,queryCriteria,new SysUserRoleRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddUser(String userIds, Long roleId) {
		if(userIds!=null && !userIds.equals("") && roleId!=null && !roleId.equals(0l)){
			String []uids = userIds.split(";");
			for(int i=0;i<uids.length;i++){
				String sql = "select * from sys_user_role where tbl_userid = ? and tbl_roleid = ?";
				Object[]args = new Object[]{uids[i],roleId};
				List<Map<String,Object>> _list =  sysRoleDao.select(sql, args);
				if(_list.size()==0){
					String insertSql = "insert into sys_user_role (id,tbl_userid,tbl_roleid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),uids[i],roleId};
					sysRoleDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDeleteUser(Long[] selectedSubIDs) {
		for(int i=0;i<selectedSubIDs.length;i++){
			sysRoleDao.delete("sys_user_role",selectedSubIDs[i]);
		}
	}

	@SuppressWarnings("rawtypes")
	class SysRoleRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysRole sysRole = new SysRole();
			sysRole.setId(rs.getLong("id"));
			sysRole.setRoleName(rs.getString("tbl_rolename"));
			sysRole.setRoleAliss(rs.getString("tbl_bieming"));
			sysRole.setComment(rs.getString("tbl_rolecomment"));
			sysRole.setCanDelete(rs.getInt("tbl_yunxushanchu"));
			sysRole.setCanEdit(rs.getInt("tbl_yunxubianji"));
			sysRole.setEnable(rs.getInt("tbl_shifouqiyong"));
			sysRole.setCreateBy(rs.getLong("comm_createBy"));
			sysRole.setCreateDate(rs.getTimestamp("comm_createDate"));
			sysRole.setUpdateBy(rs.getLong("comm_updateBy"));
			sysRole.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			sysRole.setOptCounter(rs.getLong("comm_opt_counter"));
			sysRole.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return sysRole;
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysUserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUser sysUser = new SysUser();
			sysUser.setId(rs.getLong("id"));
			sysUser.setUsername(rs.getString("tbl_username"));
			sysUser.setPassword(rs.getString("tbl_password"));
			sysUser.setFullname(rs.getString("tbl_yonghuxingming"));
			sysUser.setMobile(rs.getString("tbl_mobile"));
			sysUser.setEmail(rs.getString("tbl_email"));
			sysUser.setPhone(rs.getString("tbl_phone"));
			sysUser.setSex(rs.getString("tbl_sex"));
			sysUser.setLock(rs.getInt("tbl_suoding"));
			sysUser.setOverdue(rs.getInt("tbl_guoqi"));
			sysUser.setActive(rs.getInt("tbl_jihuo"));
			return sysUser;
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysUserRoleRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setId(rs.getLong("id"));
			
			SysUser sysUser = new SysUser();
			sysUser.setId(rs.getLong("tbl_userid"));
			sysUser.setFullname(rs.getString("fullname"));
			
			SysRole sysRole = new SysRole();
			sysRole.setId(rs.getLong("tbl_roleid"));
			sysRole.setRoleName(rs.getString("rolename"));
			
			sysUserRole.setSysUser(sysUser);
			sysUserRole.setSysRole(sysRole);
			return sysUserRole;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "privilegeCache")
	public List<SysRole> selectAllRole() {
		return sysRoleDao.getAll(new SysRoleRowMapper());
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddAuth(String authIds, Long roleId) {
		//先删除该角色的所有权限
		String dsql = "delete from sys_role_privilege where tbl_roleid = ?";
		Object[]dargs = new Object[]{roleId};
		sysRoleDao.update(dsql,dargs);
		//添加权限
		if(authIds!=null && !authIds.equals("") && roleId!=null && !roleId.equals(0l)){
			String []aids = authIds.split(";");
			for(int i=0;i<aids.length;i++){
				String insertSql = "insert into sys_role_privilege (id,tbl_roleid,tbl_privilegeid) values(?,?,?)";
				Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),roleId,aids[i]};
				sysRoleDao.insert(insertSql,insertArgs);
			}
		}
		CacheUtil.clearAllICache();
	}

	@Override
	public List findNotCanDeleteRole(Long[] selectedIDs) {
		StringBuffer sql = new StringBuffer("select * from sys_role where tbl_yunxushanchu = 0 and id in(");
		for(int i=0;i<selectedIDs.length;i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		sql.append(")");
		return sysRoleDao.select(sql.toString(), selectedIDs);
	}

	@Override
	@Cacheable(value = "privilegeCache")
	public List<SysRole> getRoleBySystemId(Long systemId) {
		if(systemId == Constants.DEFAULT_SYSTEM_ID) {
			String sql = "select * from sys_role";
			List<SysRole> roles = sysRoleDao.select(sql,new Object[]{},new SysRoleRowMapper());
			return roles;
		}
		return null;
	}


	@Override
	@Cacheable(value = "privilegeCache")
	public List<String> getRolesByUserId(Long userId) {
		String sql = "select r.tbl_bieming as alias from sys_user_role ur left join sys_role r on ur.tbl_roleid=r.id where ur.tbl_userid=?";
		List<String> roles = sysRoleDao.selectRole(sql, new Object[]{userId}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String alias = rs.getString("alias");
				if(alias != null) {
					return alias;
				}
				return null;
			}
		});
		return roles;
	}

}
