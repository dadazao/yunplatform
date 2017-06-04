package com.cloudstong.platform.system.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.system.dao.IRoleDao;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.Privilege;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.RoleRoles;
import com.cloudstong.platform.system.model.RolesPrivilege;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.UserRole;
import com.cloudstong.platform.system.rowmap.SysResourceRowMapper;
import com.cloudstong.platform.system.rowmap.SysUserRowMapper;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户管理数据库接口的实现类
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@Repository("userDao")
public class UserDaoImpl implements IUserDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	@Resource
	private IRoleDao roleDao;
	
	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#getUser(java.lang.String, java.lang.String)
	 */
	public SysUser getUser(String username, String password, boolean pbSuperUser) {
		SysUser user = getUserByParams("tbl_username,tbl_password",new Object[]{username, password});
		if(user == null){
			return null;
		}		
		List<Org> _lstResultJ = getUserOrg(user.getDepartment());
		user.setOrgs(_lstResultJ);
		
		//得到用户的角色信息
//		Set<Role> _setRole = getRole(String.valueOf(user.getId()));
//		
//		//得到用户的权限组信息
//		Set<Role> _setRoles = getRoles(_setRole);
//		
//		//得到用户的权限信息
//		Set<Privilege> _setPrivilege = getUserPrivilege(_setRoles);
//		
//		user.setRole(_setRole);
//		user.setRoles(_setRoles);
//		user.setPrivileges(_setPrivilege);
		
		//得到用户的资源信息
		List<Seqcode> _lstSeqcode = new LinkedList<Seqcode>();
//		if(pbSuperUser){//如果为超级用户,赋予其所有权限
			_lstSeqcode = getSuperUserSeqcode();
//		}else{
//			_lstSeqcode = getUserSeqcode(_setPrivilege);
//		}
		Map<String, Seqcode> curUserSeqcode = new HashMap<String, Seqcode>();
		for (Seqcode seqcode : _lstSeqcode) {
			curUserSeqcode.put(seqcode.getTblValue(), seqcode);
		}
//		user.setCurUserSeqcode(curUserSeqcode);
//		user.setSeqcode(_lstSeqcode);
		
		//查询当前用户所有的资源信息
		List<SysResource> _lstResource = new LinkedList<SysResource>();
		_lstResource = getUserResource(user);
		Map<String, SysResource> curUserResource = new HashMap<String, SysResource>();
		for (SysResource sysResource : _lstResource) {
			curUserResource.put(sysResource.getResourceUrl(), sysResource);
		}
//		user.setCurUserResource(curUserResource);
		return user;
	}
	
	/**
	 * Description:查询当前用户所有的资源
	 * 
	 * Steps:
	 * 
	 * @param user
	 * @return
	 */
	private List<SysResource> getUserResource(SysUser user) {
		List<SysResource> _resourceList = new ArrayList<SysResource>();
		//根据用户获取角色
		List<Map<String,Object>> _roleList = jdbcTemplate.queryForList("select tbl_roleid from sys_user_role where tbl_userid = ?", new Object[]{user.getId()});
		if(_roleList.size()>0){
			//根据角色查找权限
			List<Object> pparams = new ArrayList<Object>();
			StringBuffer psql = new StringBuffer("select tbl_privilegeid from sys_role_privilege where tbl_roleid in (");
			for(int i =0;i<_roleList.size();i++){
				if(i==0){
					psql.append("?");
				}else{
					psql.append(",?");
				}
				pparams.add(_roleList.get(i).get("tbl_roleid"));
			}
			psql.append(")");
			List<Map<String,Object>> _privilegeList = jdbcTemplate.queryForList(psql.toString(), pparams);
			if(_privilegeList.size()>0){
				//根据权限查找资源
				List<Object> rparams = new ArrayList<Object>();
				StringBuffer rsql = new StringBuffer("select r.* from sys_resource r left join sys_privilege_resource pr on r.id = pr.tbl_resourceid where pr.tbl_privilegeid in (");
				for(int j=0;j<_privilegeList.size();j++){
					if(j==0){
						rsql.append("?");
					}else{
						rsql.append(",?");
					}
					rparams.add(_privilegeList.get(j).get("tbl_privilegeid"));
				}
				rsql.append("");
				_resourceList = jdbcTemplate.query(rsql.toString(), rparams.toArray(), new SysResourceRowMapper());
			}
		}
		return _resourceList;
	}

	public SysUser getUser(String username) {
		if("admin".equals(username)) {
			SysUser user = getUserByParams("tbl_username",new Object[]{username});
			if(user == null){
				return null;
			}		
			List<Org> _lstResultJ = getUserOrg(user.getDepartment());
			user.setOrgs(_lstResultJ);
			
			//得到用户的角色信息
			Set<Role> _setRole = getRole(String.valueOf(user.getId()));
			
			//得到用户的权限组信息
			Set<Role> _setRoles = getRoles(_setRole);
			
			//得到用户的权限信息
			Set<Privilege> _setPrivilege = getUserPrivilege(_setRoles);
			
//			user.setRole(_setRole);
//			user.setRoles(_setRoles);
//			user.setPrivileges(_setPrivilege);
			
			//得到用户的资源信息
			List<Seqcode> _lstSeqcode = new LinkedList<Seqcode>();
			_lstSeqcode = getSuperUserSeqcode();//超级用户
			Map<String, Seqcode> curUserSeqcode = new HashMap<String, Seqcode>();
			for (Seqcode seqcode : _lstSeqcode) {
				curUserSeqcode.put(seqcode.getTblValue(), seqcode);
			}
//			user.setCurUserSeqcode(curUserSeqcode);
//			user.setSeqcode(_lstSeqcode);
			user.setSuper(true);
			return user;
		}
		return new SysUser();
		
	}
	
	private SysUser getUserByParams(String psColumns, Object[] psValues){
		List<SysUser> _lstResult = new ArrayList<SysUser>();
		String sColumn = "1=1";
		for (String str : psColumns.split(",")) {
			sColumn += " and "+str+"=?";
		}
		_lstResult = jdbcTemplate.query(
				"SELECT * from sys_user where "+sColumn,psValues,new SysUserRowMapper());
		if(_lstResult != null && _lstResult.size()>0){
			return _lstResult.get(0);
		}else{
			return null;
		}
	}
	/**
	 * Description:得到用户的人员信息
	 * 
	 * Steps:
	 * 
	 * @param psPersonId
	 *                 人员ID
	 * @return 人员集合
	 */
	private List<Person> getUserPerson(String psPersonId){
		return jdbcTemplate.query(
				"select * from sys_renyuan where id=?",
				new Object[] { psPersonId },
				new BeanPropertyRowMapper(Person.class));
	}
	
	/**
	 * Description:得到用户的部门信息
	 * 
	 * Steps:
	 * 
	 * @param psOrgId
	 *                 部门ID
	 * @return 部门集合
	 */
	private List<Org> getUserOrg(String psOrgId){
		return jdbcTemplate.query(
				"select * from sys_org where id=?",
				new Object[] { psOrgId },
				new BeanPropertyRowMapper(Org.class));
	}
	/**
	 * Description:得到用户的角色信息
	 * 
	 * Steps:
	 * 
	 * @param psUserId
	 *                 用户ID
	 * @return 角色集合
	 */
	private Set<Role> getRole(String psUserId){
		List<UserRole> _lstUserRole = jdbcTemplate.query("select * from sys_user_role where tbl_mainid=?", new Object[] { psUserId }, new BeanPropertyRowMapper(UserRole.class));
		List<Role> _lstRole= roleDao.queryRole("1", "1", "0");
		List<RoleRoles> _lstRoleRoles= roleDao.queryRoleRoles("1", "1");
		List<Role> _lstRoles= roleDao.queryRole("1", "1", "1");
		//查询当前用户的所有角色(_lstRoleTmp)
		Set<Role> _setRoleTmp = new HashSet<Role>();
		for (UserRole usereRole : _lstUserRole) {
			for (Role role : _lstRole) {
				if(role.getId().equals(usereRole.getTblSubid())){
					_setRoleTmp.add(role);
				}
			}
		}
		
		return _setRoleTmp;
	}
	
	/**
	 * Description:得到用户的权限组信息
	 * 
	 * Steps:
	 * 
	 * @param psUserId
	 *                 用户ID
	 * @return 权限组集合
	 */
	private Set<Role> getRoles(Set<Role> psetRole){
		List<RoleRoles> _lstRoleRoles= roleDao.queryRoleRoles("1", "1");
		List<Role> _lstRoles= roleDao.queryRole("1", "1", "1");
		
		//查询当前用户的所有角色权限组中间表(_lstRoleRolesTmp)
		Set<RoleRoles> _lstRoleRolesTmp = new HashSet<RoleRoles>();
		for (Role role : psetRole) {
			for (RoleRoles roleRoles : _lstRoleRoles) {
				if(role.getId().equals(roleRoles.getTblMainid())){
					_lstRoleRolesTmp.add(roleRoles);
				}
			}
		}
		
		//查询当前用户的所有权限组(_lstRolesTmp)
		Set<Role> _setRoleTmp = new HashSet<Role>();
		for (RoleRoles roleRoles : _lstRoleRolesTmp){
			for (Role role : _lstRoles) {
				if(role.getId().equals(roleRoles.getTblSubid())){
					_setRoleTmp.add(role);
				}
			}
		}
		
		return _setRoleTmp;
	}
	
	/**
	 * Description:查询角色所有的权限
	 * 
	 * Steps:
	 * 
	 * @param psetRole
	 *                  角色集合
	 * @return 权限集合
	 */
	private Set<Privilege> getUserPrivilege(Set<Role> psetRoles){
		List<RolesPrivilege> _lstRolesPrivilege= roleDao.queryRolePrivilege("1", "1");
		List<Privilege> _lstPrivilege = jdbcTemplate.query("select * from sys_privilege", new BeanPropertyRowMapper(Privilege.class));
		
		//查询当前用户的所有权限组权限中间表(_lstRolesTmp)
		Set<RolesPrivilege> _setRolesPrivilegeTmp = new HashSet<RolesPrivilege>();
		for (Role Role : psetRoles){
			for (RolesPrivilege rolesPrivilege : _lstRolesPrivilege) {
				if(Role.getId().equals(rolesPrivilege.getTblMainid())){
					_setRolesPrivilegeTmp.add(rolesPrivilege);
				}
			}
		}
		
		Set<Privilege> _setPrivilegeTmp = new HashSet<Privilege>();
		for (RolesPrivilege rolesPrivilege : _setRolesPrivilegeTmp) {
			for (Privilege privilege : _lstPrivilege) {
				if(privilege.getId().equals(rolesPrivilege.getTblSubid())){
					_setPrivilegeTmp.add(privilege);
				}
			}
		}
		
		return _setPrivilegeTmp;
	}
	
	/**
	 * Description:查询用户所有的资源
	 * 
	 * Steps:
	 * 
	 * @param psetPrivilege
	 *                   权限集合
	 * @return 资源集合
	 */
	private List<Seqcode> getUserSeqcode( Set<Privilege> psetPrivilege){
		List<Seqcode> _lstSeqcodeTmp = new ArrayList<Seqcode>();
		List<Seqcode> _lstSeqcode = jdbcTemplate.query("select * from sys_bianma where tbl_usestatus=?",new Object[] {"1"}, new BeanPropertyRowMapper(Seqcode.class));
		for (Seqcode seqcode : _lstSeqcode) {
			for (Privilege privilege : psetPrivilege) {
				if(privilege.getTblSeq().equals(seqcode.getId())){
					_lstSeqcodeTmp.add(seqcode);
				}
			}
		}
		_lstSeqcode = jdbcTemplate.query("select * from sys_bianma where tbl_usestatus=?",new Object[] {"0"}, new BeanPropertyRowMapper(Seqcode.class));
		for (Seqcode seqcode : _lstSeqcode) {
			_lstSeqcodeTmp.add(seqcode);
		}
		return _lstSeqcodeTmp;
	}
	
	/**
	 * Description:为超级用户赋权
	 * 
	 * Steps:
	 * 
	 * @return 所有的编码资源
	 */
	private List<Seqcode> getSuperUserSeqcode(){
		return jdbcTemplate.query("select * from sys_bianma", new BeanPropertyRowMapper(Seqcode.class));
	}
	
	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#username(java.lang.String)
	 */
	public boolean username(String psUsername) {
		int i = jdbcTemplate.queryForInt(
				"select count(*) from sys_user where tbl_username=?",
				new Object[] { psUsername });
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#changePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void changePassword(Long id, String password) {
		String sql = "update sys_user set tbl_password=? where id=?";
		Object[] params = new Object[] { password, id };
		jdbcTemplate.update(sql, params);
	}

	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#getOrgById(java.lang.String)
	 */
	@Override
	public Org getOrgById(String psOrgId) {
		String sql = "select * from sys_org where id = ? ";
		Object[] params = new Object[] { psOrgId };
		return (Org)jdbcTemplate.queryForObject(sql, params,
				new BeanPropertyRowMapper(Org.class));
	}

	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#doDeleteUserRole(java.lang.String, java.lang.String)
	 */
	public void doDeleteUserRole(String plUserId, String plRoleId){
		if(plUserId.equals("") && plRoleId.equals("")){
			String sql = "delete from sys_user_roles";
			jdbcTemplate.update(sql);
		}else if(plUserId.equals("")){
			String sql = "delete from sys_user_roles where tbl_subid=?";
			Object[] _objParams = new Object[] { plRoleId};
			jdbcTemplate.update(sql, _objParams);
		}else if(plRoleId.equals("")){
			String sql = "delete from sys_user_roles where tbl_mainid=?";
			Object[] _objParams = new Object[] { plUserId};
			jdbcTemplate.update(sql, _objParams);
		}else{
			String sql = "delete from sys_user_roles where tbl_mainid=? and tbl_subid=?";
			Object[] _objParams = new Object[] { plUserId, plRoleId};
			jdbcTemplate.update(sql, _objParams);
		}
	}

	/* 
	 * @see com.cloudstongplatform.system.dao.IUserDao#getUserResource(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Object getUserResource(String psColumn, String psValue, int piFlag) {
		SysUser user = getUserByParams(psColumn,new Object[]{psValue});
		List<Person> lstPerson = new ArrayList<Person>();
		List<Org> lstOrg = new ArrayList<Org>();
		Set<Role> setRole = new HashSet<Role>();
		Set<Privilege> setPrivilege = new HashSet<Privilege>();
		List<Seqcode> setSeqcode = new ArrayList<Seqcode>();
		
		List _lstResult = new ArrayList();
		if(piFlag == 0){
			return user;
		}else if(piFlag == 1){
			lstPerson = getUserPerson(user.getFullname());
			if(lstPerson !=null && lstPerson.size()>0){
				return lstPerson.get(0);
			}else{
				return new Person();
			}
		}else if(piFlag == 2){
			lstOrg = getUserOrg(user.getDepartment());
			if(lstOrg !=null && lstOrg.size()>0){
				return lstOrg.get(0);
			}else{
				return new Org();
			}
		}else if(piFlag == 3){
			return getRole(String.valueOf(user.getId()));
		}else if(piFlag == 4){
			setRole = getRole(String.valueOf(user.getId()));
			return getUserPrivilege(setRole);
		}else if(piFlag == 5){
			setRole = getRole(String.valueOf(user.getId()));
			setPrivilege = getUserPrivilege(setRole);
			return getUserSeqcode(setPrivilege);
		}else if(piFlag == 6){
			//获取用户的人员信息
			lstPerson = getUserPerson(user.getFullname());
			if(lstPerson != null && lstPerson.size()>0){
//				user.setPerson(lstPerson.get(0));
				//根据人员信息,获取用户的组织部门信息
				lstOrg = getUserOrg(lstPerson.get(0).getTblSuoshubumen());
				user.setOrgs(lstOrg);
			}
			
			//得到用户的角色信息
			setRole = getRole(String.valueOf(user.getId()));
			//得到用户的权限信息
			setPrivilege = getUserPrivilege(setRole);
			//得到用户的资源信息
			setSeqcode = getUserSeqcode(setPrivilege);
			
//			user.setRole(setRole);
//			user.setPrivileges(setPrivilege);
//			user.setSeqcode(setSeqcode);
			
			return user;
		}
		return new Object();
	}

	@Override
	public List<SysUser> getUsersByOrgId(String orgId) {
		String sql = "select * from sys_user where tbl_department = ? ";
		Object[] params = new Object[] { orgId };
		return jdbcTemplate.query(sql, params, new SysUserRowMapper());
	}

	@Override
	public Person selectPersonById(String personId) {
		String sql = "select * from sys_renyuan where id=?";
		Object[] params = new Object[] { personId };
		List list = jdbcTemplate.query(sql,params,new BeanPropertyRowMapper(Person.class));
		return (Person)list.get(0);
	}

	@Override
	public SysUser getUserById(String userId) {
		String sql = "select * from sys_user where id = ? ";
		Object[] params = new Object[] { userId };
		return (SysUser)jdbcTemplate.queryForObject(sql, params, new SysUserRowMapper());
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'selectPersonNameByIds:'+#ids")
	public List<Person> selectPersonNameByIds(String ids) {
		List<Person> list=new ArrayList<Person>();
		if(ids!=null&&!ids.equals("")){
			List params=new ArrayList();
			String[]personIds=ids.split(";");
			StringBuffer sql=new StringBuffer("select * from sys_renyuan where id in (");
			for(int i=0;i<personIds.length;i++){
				if(i==0){
					sql.append("?");
				}else{
					sql.append(",?");
				}
				params.add(personIds[i]);
			}
			sql.append(")");
			list=jdbcTemplate.query(sql.toString(),params.toArray(),new BeanPropertyRowMapper(Person.class));
		}
		return list;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'selectUserNameByIds:'+#ids")
	public List<SysUser> selectUserNameByIds(String ids) {
		List<SysUser> list=new ArrayList<SysUser>();
		if(ids!=null&&!ids.equals("")){
			List params=new ArrayList();
			String[] userIds=ids.split(";");
			StringBuffer sql=new StringBuffer("select * from sys_user where id in (");
			for(int i=0;i<userIds.length;i++){
				if(i==0){
					sql.append("?");
				}else{
					sql.append(",?");
				}
				params.add(userIds[i]);
			}
			sql.append(")");
			list=jdbcTemplate.query(sql.toString(),params.toArray(),new SysUserRowMapper());
		}
		return list;
	}

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
}
