package com.cloudstong.platform.system.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.IRoleDao;
import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.RoleRoles;
import com.cloudstong.platform.system.model.RolesPrivilege;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:角色管理数据库接口的实现类
 */
@Repository("roleDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RoleDaoImpl implements IRoleDao{

	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public List<Role> queryRole(String psColumn, String psValue, String psType){
		if(psType.equals("0")){
			return jdbcTemplate.query("select * from sys_role where "+psColumn+"=?", new Object[] { psValue }, new BeanPropertyRowMapper(Role.class));
		}else{
			return jdbcTemplate.query("select * from sys_roles where "+psColumn+"=?", new Object[] { psValue }, new BeanPropertyRowMapper(Role.class));
		}
	}
	
	public void updateRoleCP(String psCatalogs, String psId, String psType){
		if(psType.equals("0")){
			jdbcTemplate.update("update sys_role set tbl_catalogs=? where id=?", new Object[] { psCatalogs, psId});
		}else{
			jdbcTemplate.update("update sys_roles set tbl_catalogs=? where id=?", new Object[] { psCatalogs, psId});
		}
	}
	
	public List<RoleRoles> queryRoleRoles(String psColumn, String psValue){
		return jdbcTemplate.query("select * from sys_role_roles where "+psColumn+"=?", new Object[] { psValue }, new BeanPropertyRowMapper(RoleRoles.class));
	}
	
	public int getRolePrivilege(Long psMainid, Long plSubid){
		return jdbcTemplate.queryForInt("select count(*) from sys_roles_privilege where tbl_mainid=? and tbl_subid=?", new Object[] { psMainid, plSubid});
	}
	
	public List<RolesPrivilege> queryRolePrivilege(String psColumn, String plValue){
		return jdbcTemplate.query("select * from sys_roles_privilege where "+psColumn+"=?", new Object[] { plValue }, new BeanPropertyRowMapper(RolesPrivilege.class));
	}
	
	public void saveRoleRoles(String plRoleId, String plRolesId){
		String sql = "insert into sys_role_roles(id, tbl_mainid, tbl_subid) values (?, ?,?)";
		Object[] _objParams = new Object[] {UniqueIdUtil.genId(), plRoleId, plRolesId};
		jdbcTemplate.update(sql, _objParams);
	}
	
	public void saveRolePrivilege(String plRoleId, String plPrivilegeId){
		String sql = "insert into sys_roles_privilege(id, tbl_mainid, tbl_subid) values (?, ?,?)";
		Object[] _objParams = new Object[] {UniqueIdUtil.genId(), plRoleId, plPrivilegeId};
		jdbcTemplate.update(sql, _objParams);
	}
	
	public void deleteRoleRoles(String psRoleId, String psRolesId){
		if(psRoleId.equals("") && psRolesId.equals("")){
			String sql = "delete from sys_role_roles";
			jdbcTemplate.update(sql);
		}else if(psRolesId.equals("")){
			String sql = "delete from sys_role_roles where tbl_mainid=?";
			Object[] _objParams = new Object[] { psRoleId};
			jdbcTemplate.update(sql, _objParams);
		}else{
			String sql = "delete from sys_role_roles where tbl_mainid=? and tbl_subid=?";
			Object[] _objParams = new Object[] { psRoleId, psRolesId};
			jdbcTemplate.update(sql, _objParams);
		}
	}
	
	public void deleteRolePrivilege(String plRoleId, String plPrivilegeId){
		if(plRoleId.equals("") && plPrivilegeId.equals("")){
			String sql = "delete from sys_roles_privilege";
			jdbcTemplate.update(sql);
		}else if(plPrivilegeId.equals("")){
			String sql = "delete from sys_roles_privilege where tbl_mainid=?";
			Object[] _objParams = new Object[] { plRoleId};
			jdbcTemplate.update(sql, _objParams);
		}else{
			String sql = "delete from sys_roles_privilege where tbl_mainid=? and tbl_subid=?";
			Object[] _objParams = new Object[] { plRoleId, plPrivilegeId};
			jdbcTemplate.update(sql, _objParams);
		}
	}

	@Override
	public List<FunctionTmp> queryFunctionTmp() {
		return jdbcTemplate.query("select * from sys_function_tmp where id=?", new Object[] {"2" }, new BeanPropertyRowMapper(FunctionTmp.class));
	}

	@Override
	public List<Role> findAllRole() {
		return jdbcTemplate.query("select * from sys_role", new BeanPropertyRowMapper(Role.class));
	}

	@Override
	public List<Role> selectRolesByUserId(Long _userId) {
		String sql = "select * from sys_role where id in (select tbl_subid from sys_user_role where tbl_mainid = ?)";
		Object[]params = new Object[]{_userId};
		return jdbcTemplate.query(sql,params,new BeanPropertyRowMapper(Role.class));
	}

	@Override
	public void saveUserRoles(Long _userId, String roleIds) {
		String dsql = "delete from sys_user_role where tbl_mainid = ?";
		Object []params=new Object[]{_userId};
		jdbcTemplate.update(dsql, params);
		String ids[]=roleIds.split(";");
		for(int i=0;i<ids.length;i++){
			String sql="insert into sys_user_role values(?,?,?)";
			params=new Object[]{UniqueIdUtil.genId(),_userId,Long.valueOf(ids[i])};
			jdbcTemplate.update(sql, params);
		}
	}
}
