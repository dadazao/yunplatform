package com.cloudstong.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.system.dao.IRoleDao;
import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.RoleRoles;
import com.cloudstong.platform.system.model.RolesPrivilege;
import com.cloudstong.platform.system.service.IRoleService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:角色管理的实现类
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Resource
	private IRoleDao rolehDao;
	
	public List<Role> queryRole(String psColumn, String psValue ,String psType){
		return rolehDao.queryRole(psColumn, psValue, psType);
	}
	
	public void doUpdateRoleCP(String psCatalogs, String psId, String psType){
		rolehDao.updateRoleCP(psCatalogs, psId, psType);
	}
	
	public List<RoleRoles> queryRoleRoles(String psColumn, String psValue){
		return rolehDao.queryRoleRoles(psColumn, psValue);
	}
	
	public int getRolePrivilege(Long psMainid, Long plSubid){
		return rolehDao.getRolePrivilege(psMainid, plSubid);
	}
	
	public List<RolesPrivilege> queryRolePrivilege(String psColumn, String psValue){
		return rolehDao.queryRolePrivilege(psColumn, psValue);
	}
	
	public void doSaveRoleRoles(String plRoleId, String plRolesId){
		rolehDao.saveRoleRoles(plRoleId, plRolesId);
	}
	
	public void doSaveRolePrivilege(String plRoleId, String plPrivilegeId){
		rolehDao.saveRolePrivilege(plRoleId, plPrivilegeId);
	}
	
	public void doDeleteRoleRoles(String psRoleId, String psRolesId){
		rolehDao.deleteRoleRoles(psRoleId, psRolesId);
	}
	
	public void doDeleteRolePrivilege(String plRoleId, String plPrivilegeId){
		rolehDao.deleteRolePrivilege(plRoleId, plPrivilegeId);
	}

	@Override
	public List<FunctionTmp> queryFunctionTmp() {
		// TODO Auto-generated method stub
		return rolehDao.queryFunctionTmp();
	}

	@Override
	public List<Role> findAllRole() {
		return rolehDao.findAllRole();
	}

	@Override
	public List<Role> selectRolesByUserId(Long _userId) {
		return rolehDao.selectRolesByUserId(_userId);
	}

	@Override
	public void doSaveUserRoles(Long _userId, String roleIds) {
		rolehDao.saveUserRoles(_userId,roleIds);
	}
}
