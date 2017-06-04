package com.cloudstong.platform.system.dao;

import java.util.List;

import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Privilege;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:权限管理数据库接口
 */
public interface IPrivilegeDao {
	/**
	 * Description:保存权限
	 * 
	 * Steps:
	 * 
	 * @param privilege
	 *                需要保存的权限对象
	 */
	public void savePrivilege(Privilege privilege);
	
	/**
	 * Description:查询权限状态缓存
	 * 
	 * Steps:
	 * 
	 * @return 权限状态缓存集合
	 */
	public List<FunctionTmp> queryFunctionTmp();
	
	/**
	 * Description:修改在权限生产中勾选的权限状态缓存
	 * 
	 * Steps:
	 * 
	 * @param ft
	 *              需要修改的缓存数据
	 * @param psId
	 *              需要修改的权限ID
	 */
	public void updateFunctionTmp(FunctionTmp ft, String psId);
	
	/**
	 * Description:直接修改在权限生产中勾选的权限状态缓存,不需要与原有的状态做对比
	 * 
	 * Steps:
	 * 
	 * @param ft
	 *              需要修改的缓存数据
	 */
	public void updateFunctionTmp(FunctionTmp ft);
	
	/**
	 * Description:根据权限ID查询权限数据
	 * 
	 * Steps:
	 * 
	 * @param plId
	 *             权限ID
	 * @return
	 */
	public List<Privilege> queryPrivilege(String plId);
	
	/**
	 * Description:根据所属目录,查询权限数据
	 * 
	 * Steps:
	 * 
	 * @param catalog
	 *            所属目录ID
	 * @return
	 */
	public List<Privilege> queryPrivilegeByCatalog(String catalog);
	
	/**
	 * Description:更新权限
	 * 
	 * Steps:
	 * 
	 * @param privilege
	 *           需要更新的权限对象
	 */
	public void updatePrivilege(Privilege privilege);
	
	/**
	 * Description:根据权限ID删除权限
	 * 
	 * Steps:
	 * 
	 * @param plId
	 *           权限ID
	 */
	public void deleltePrivilege(String plId);
	
	/**
	 * @param catalogId
	 * 				目录ID
	 * @return 该目录下的权限数量
	 */
	public int queryPrivilegeNum(String catalogId);
	
	/**
	 * 判断字段是否有删除的权限
	 * @param bianma 表单设计表的编码
	 * @return
	 */
	public boolean hasDelPrivilege(String bianma);
}
