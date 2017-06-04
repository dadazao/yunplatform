package com.cloudstong.platform.system.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.resource.metadata.model.Seqcode;

/**
 * @author sam
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户表的映射对象
 */

public class User extends EntityBase implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户的id
	 */
	private Long id;

	/**
	 * 用户的登陆用户名
	 */
	private String tblUsername;

	/**
	 * 用户的登陆密码
	 */
	private String tblPassword;

	/**
	 * 用户姓名
	 */
	private String tblYonghuxingming;
	
	private String fullname;
	
	/**
	 * 用户所属部门
	 */
	private String tblDepartment;

	/**
	 * 用户所属的人员信息
	 */
	private Person person;

	/**
	 * 用户所属的组织部门信息
	 */
	private List<Org> orgs;
	
	/**
	 * 用户所属的角色信息
	 */
	private Set<Role> role;
	
	/**
	 * 用户所属的权限组信息
	 */
	private Set<Role> roles;
	
	/**
	 * 用户所属的权限信息
	 */
	private Set<Privilege> privileges;
	
	/**
	 * 用户所属的编码信息
	 */
	private List<Seqcode> seqcode;
	
	/**
	 * 当前用户所属的编码信息,存储为map 方便遍历
	 */
	private Map<String, Seqcode> curUserSeqcode;
	
	/**
	 * 当前用户所有的资源信息，存储为map 方便遍历
	 */
	private Map<String, SysResource> curUserResource;
	
	/**
	 * 用户是否为超级用户
	 */
	private boolean isSuper;
	
	/**
	 * 登录用户的IP add by lq at 2012-12-10
	 */
	private String ip;
	
	/**
	 * RTX的session key
	 */
	private String sessionKey;
	
	/**
	 * 动态口令
	 */
	private String dynamicPassword;
	
	/**
	 * 人员ID
	 */
	private String tblPerson;
	
	/**
	 * 邮箱
	 */
	private String tblEmail;
	
	public String getTblEmail() {
		return tblEmail;
	}

	public void setTblEmail(String tblEmail) {
		this.tblEmail = tblEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblUsername() {
		return tblUsername;
	}

	public void setTblUsername(String tblUsername) {
		this.tblUsername = tblUsername;
	}

	public String getTblPassword() {
		return tblPassword;
	}

	public void setTblPassword(String tblPassword) {
		this.tblPassword = tblPassword;
	}

	public String getTblYonghuxingming() {
		return tblYonghuxingming;
	}

	public void setTblYonghuxingming(String tblYonghuxingming) {
		this.tblYonghuxingming = tblYonghuxingming;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Org> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}
	
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public List<Seqcode> getSeqcode() {
		return seqcode;
	}

	public void setSeqcode(List<Seqcode> seqcode) {
		this.seqcode = seqcode;
	}

	public boolean isSuper() {
		return isSuper;
	}

	public void setSuper(boolean isSuper) {
		this.isSuper = isSuper;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getTblDepartment() {
		return tblDepartment;
	}

	public void setTblDepartment(String tblDepartment) {
		this.tblDepartment = tblDepartment;
	}

	public String getTblPerson() {
		return tblPerson;
	}

	public void setTblPerson(String tblPerson) {
		this.tblPerson = tblPerson;
	}

	public String getDynamicPassword() {
		return dynamicPassword;
	}

	public void setDynamicPassword(String dynamicPassword) {
		this.dynamicPassword = dynamicPassword;
	}

	public Map<String, Seqcode> getCurUserSeqcode() {
		return curUserSeqcode;
	}

	public void setCurUserSeqcode(Map<String, Seqcode> curUserSeqcode) {
		this.curUserSeqcode = curUserSeqcode;
	}

	public Map<String, SysResource> getCurUserResource() {
		return curUserResource;
	}

	public void setCurUserResource(Map<String, SysResource> curUserResource) {
		this.curUserResource = curUserResource;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
