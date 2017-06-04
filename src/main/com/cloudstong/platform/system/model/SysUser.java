/**
 * 
 */
package com.cloudstong.platform.system.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.system.service.SysRoleService;

/**
 * @author liuqi
 * Created on 2014-8-23
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户表的映射对象
 */
@SuppressWarnings("serial")
public class SysUser extends EntityBase implements UserDetails{
	
	public static final String SEARCH_BY_ROL = "1";
	public static final String SEARCH_BY_ORG = "2";
	public static final String SEARCH_BY_POS = "3";
	public static final String SEARCH_BY_ONL = "4";

	public static final Integer UN_LOCKED = 0;
	public static final Integer LOCKED = 1;

	public static final Integer UN_EXPIRED = 0;
	public static final Integer EXPIRED = 1;

	public static final Integer STATUS_OK = 1;
	public static final Integer STATUS_NO = 0;
	public static final Integer STATUS_Del = -1;

	public static final Integer FROMTYPE_DB = 0;
	public static final Integer FROMTYPE_AD = 1;
	public static final Integer FROMTYPE_AD_SET = 2;
	
	/**
	 * 用户的id
	 */
	private Long id;

	/**
	 * 用户的登陆用户名
	 */
	private String username;

	/**
	 * 用户的登陆密码
	 */
	private String password;

	/**
	 * 用户照片
	 */
	private String userPic;
	
	/**
	 * 用户姓名
	 */
	private String fullname;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 性别
	 */
	private String sex = "0";
	
	/**
	 * 锁定
	 */
	private Integer lock;
	
	/**
	 * 过期
	 */
	private Integer overdue;
	
	/**
	 * 激活
	 */
	private Integer active;
	
	/**
	 * 是否超级用户
	 */
	private boolean isSuper;
	
	/**
	 * 登录用户IP
	 */
	private String ip;
	
	/**
	 * 来源类型
	 */
	private Integer fromType = FROMTYPE_DB;
	
	public SysUser() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return Returns the value of userPic field with the type String.
	 */
	public String getUserPic() {
		return userPic;
	}

	/**
	 * @param userPic. Set the value of userPic field with the type String by the userPic parameter.
	 */
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return Returns the value of lock field with the type Integer.
	 */
	public Integer getLock() {
		return lock;
	}

	/**
	 * @param lock. Set the value of lock field with the type Integer by the lock parameter.
	 */
	public void setLock(Integer lock) {
		this.lock = lock;
	}

	/**
	 * @return Returns the value of overdue field with the type Integer.
	 */
	public Integer getOverdue() {
		return overdue;
	}

	/**
	 * @param overdue. Set the value of overdue field with the type Integer by the overdue parameter.
	 */
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	/**
	 * @return Returns the value of active field with the type Integer.
	 */
	public Integer getActive() {
		return active;
	}

	/**
	 * @param active. Set the value of active field with the type Integer by the active parameter.
	 */
	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
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

	public List<Org> getOrgs() {
		return new ArrayList<Org>();
	}

	public Set<Role> getRoles() {
		return new HashSet<Role>();
	}

	public String getDepartment() {
		return null;
	}

	public void setOrgs(List<Org> _lstResultJ) {
		
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List rtnList = new ArrayList();
		SysRoleService sysRoleService = (SysRoleService) AppUtil.getBean(SysRoleService.class);
		List<String> roleCols = sysRoleService.getRolesByUserId(id);
		if (BeanUtils.isNotEmpty(roleCols)) {
			for (String role : roleCols) {
				if(role != null) {
					rtnList.add(new GrantedAuthorityImpl(role));
				}
			}
		}

		if ("admin".equals(username)) {
			rtnList.add(Constants.ROLE_GRANT_SUPER);
			this.isSuper = true;
		}
		return rtnList;
	}

	@Override
	public boolean isAccountNonExpired() {
		if(overdue == 0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		if(lock == 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if(overdue == 0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		if(active == 0) {
			return true;
		}else{
			return false;
		}
	}

}
