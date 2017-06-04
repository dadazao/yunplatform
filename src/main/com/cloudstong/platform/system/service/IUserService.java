package com.cloudstong.platform.system.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户管理接口
 */
public interface IUserService {
	
	/**
	 * Description:获得登录用户的信息
	 * 
	 * Steps:
	 * 
	 * @param username
	 *               用户姓名
	 * @param password
	 *               用户密码
	 * @param pbSuperUser
	 *               是否为超级用户
	 *               
	 * @return 用户对象
	 */
	public SysUser getUser(String username, String password, boolean pbSuperUser);

	/**
	 * Description:判断用户是否存在
	 * 
	 * Steps:
	 * 
	 * @param psUsername 
	 *              用户姓名
	 * @return
	 */
	public boolean username(String psUsername);

	/**
	 * Description:修改用户密码
	 * 
	 * Steps:
	 * 
	 * @param id
	 *               用户ID
	 * @param password
	 *               用户新密码
	 */
	public void doChangePassword(Long id, String password);

	/**
	 * Description:获得组织部门信息
	 * 
	 * Steps:
	 * 
	 * @param parameter
	 *                部门ID
	 * @return
	 */
	public Org getOrgById(String psOrgId);
	
	/**
	 * Description:删除用户所具有的角色信息,如果用户ID为空串,那么以角色ID为条件;如果角色ID为空串,那么以用户ID为条件
	 * 				如果用户ID和角色ID都为空串,那么删除所有的用户角色信息
	 * 
	 * Steps:
	 * 
	 * @param plUserId
	 *                用户ID
	 * @param plRoleId
	 *                角色ID
	 */
	public void doDeleteUserRole(String plUserId, String plRoleId);
	
	/**
	 * Description:根据用户表的查询条件,获得用户信息
	 * 
	 * Steps:
	 * 
	 * @param psColumn
	 * 				用户表查询字段,如按照用户姓名查可输入"tbl_username",按照ID查可以输入"id"
	 * @param psUserName
	 * 				用户表字段值
	 * @param piFlag
	 * 				信息标识
	 * 				0:获取用户对象(不包含关联信息);1:获取用户的人员信息;2:获取用户部门信息;3:获取用户角色信息;
	 * 				4获取用户权限信息;5:获取用户资源信息;6:获取用户对象(包含关联信息)
	 * 				
	 */
	public Object getUserResource(String psColumn, String psValue, int piFlag);

	/**
	 * Description:根据人员Id查找人员信息
	 * @param personId
	 * @return 人员信息
	 */
	public Person findPersonById(String personId);
	
	/**
	 * Description:根据人员ID集合查找人员名称
	 * @param ids 人员ID集合
	 * @return
	 */
	public List<Person> selectPersonNameByIds(String ids);

	/**
	 * Description:根据用户ID集合查找用户名称
	 * @param ids 用户ID集合
	 * @return
	 */
	public List<SysUser> selectUserNameByIds(String ids);
	
	/**
	 * @param session
	 * 				处理http请求会话
	 * @param seqcode
	 * 				编码对象
	 */
	public void addOrUpdateUserSeq(HttpSession session, Seqcode seqcode);
	
	
	/**
	 * 缓存数据时使用项目开发人员
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param username
	 * @return
	 */
	public SysUser getUser(String username);
}
