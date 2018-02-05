package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.RoleRoles;
import com.cloudstong.platform.system.model.RolesPrivilege;

/**
 * @author liutao
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public interface IRoleService {
	
	/**
	 * Description:根据指定的列名称与列值,查询角色
	 * 
	 * Steps:
	 * 
	 * @param psColumn
	 *              列名称
	 * @param psValue
	 *              列值
	 * @param psType
	 *              查询表类型,0:查询sys_role表,1:查询sys_roles
	 *
	 * @return 权限的集合
	 */
	public List<Role> queryRole(String psColumn, String psValue ,String psType);
	
	/**
	 * Description:根据角色ID,缓存角色在赋权时所选择的目录
	 * 
	 * Steps:
	 * 
	 * @param psCatalogs
	 *             被勾选的目录ID集合,以";"间隔
	 * @param psType
	 *              保存表类型,0:保存sys_role表,1:保存sys_roles
	 *              
	 * @param psId
	 */
	public void doUpdateRoleCP(String psCatalogs, String psId, String psType);
	
	/**
	 * Description:根据指定的列名称与列值,查询角色权限组
	 * 
	 * Steps:
	 * 
	 * @param psColumn
	 *              列名称
	 * @param plValue
	 *              列值
	 * @return 角色权限组表的集合
	 */
	public List<RoleRoles> queryRoleRoles(String psColumn, String psValue);
	/**
	 * Description:根据角色ID与权限ID,查询书当前角色是否已经有赋予的权限
	 * 
	 * Steps:
	 * 
	 * @param psMainid
	 * 				角色ID
	 * @param plSubid
	 * 				权限ID
	 * @return 该角色所有的权限
	 */
	public int getRolePrivilege(Long psMainid, Long plSubid);
	
	/**
	 * Description:根据指定的列名称与列值,查询角色权限
	 * 
	 * Steps:
	 * 
	 * @param psColumn
	 *              列名称
	 * @param plValue
	 *              列值
	 * @return 角色权限表的集合
	 */
	public List<RolesPrivilege> queryRolePrivilege(String psColumn, String plValue);
	
	/**
	 * Description:保存角色权限组
	 * 
	 * Steps:
	 * 
	 * @param plRoleId
	 * 				角色的ID
	 * @param plRolesId
	 * 				权限组的ID集合,以";"间隔
	 */
	public void doSaveRoleRoles(String plRoleId, String plRolesId);
	
	/**
	 * Description:保存权限权限组
	 * 
	 * Steps:
	 * 
	 * @param plRoleId
	 * 				角色的ID
	 * @param plPrivilegeId
	 * 				权限的ID集合,以";"间隔
	 */
	public void doSaveRolePrivilege(String plRoleId, String plPrivilegeId);
	
	/**
	 * Description:删除角色权限组表的数据
	 * 
	 * Steps:
	 * 1.如果psRoleId="",psRolesId=""时,清空角色权限组表
	 * 2.如果psRolesId=""时,删除psRoleId角色所拥有的权限组
	 * 3.如果都不为空串,则依据条件删除
	 * 
	 * @param psoleId
	 * 				角色的ID
	 * @param psRolesId
	 * 				权限组的ID
	 */
	public void doDeleteRoleRoles(String psRoleId, String psRolesId);
	
	/**
	 * Description:删除角色权限表的数据
	 * 
	 * Steps:
	 * 1.如果plRoleId="",plPrivilegeId=""时,清空权限角色表
	 * 2.如果plPrivilegeId=""时,删除plRoleId指定角色的所具有权限
	 * 3.如果都不为空串,则依据条件删除
	 * 
	 * @param plRoleId
	 * 				权限组的ID
	 * @param plPrivilegeId
	 * 				权限的ID
	 */
	public void doDeleteRolePrivilege(String plRoleId, String plPrivilegeId);
	
	/**
	 * Description:查询角色权限的缓存表
	 * 
	 * Steps:
	 * 
	 * @return 角色权限缓存
	 */
	public List<FunctionTmp> queryFunctionTmp();

	/**
	 * Description: 查找所有的角色
	 * @return
	 */
	public List<Role> findAllRole();

	/**
	 * Description:根据用户ID查找
	 * @param _userId 用户ID
	 * @return
	 */
	public List<Role> selectRolesByUserId(Long _userId);

	/**
	 * Description:保存用户的角色
	 * @param _userId 用户ID
	 * @param roleIds 角色ID集合
	 */
	public void doSaveUserRoles(Long _userId, String roleIds);
}
