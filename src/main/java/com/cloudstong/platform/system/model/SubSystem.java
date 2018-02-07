package com.cloudstong.platform.system.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@SuppressWarnings("serial")
public class SubSystem extends EntityBase {
	
	public static String CURRENT_SYSTEM = "YUNPLATFORM";

	public static String DEFAULT_LOG = "images/logo/logo_big.png";

	public static short isLocal_Y = 1;
	public static short isLocal_N = 0;

	private long systemId;
	private String sysName;
	private String alias;
	private String logo;
	private String defaultUrl;
	private String memo;
	private Date createtime;
	private String creator;
	private Short allowDel = Short.valueOf((short) 0);
	private Short needOrg;
	private Short isActive = Short.valueOf((short) 1);

	private Long parentId = Long.valueOf(0L);

	private Short isLocal = Short.valueOf((short) 1);
	private String homePage;
	private String remark;
	List<SysRole> roleList;

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public Short getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Short isLocal) {
		this.isLocal = isLocal;
	}

	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}

	public long getSystemId() {
		return systemId;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		if (logo != null) {
			return logo;
		}
		return DEFAULT_LOG;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setAllowDel(Short allowDel) {
		this.allowDel = allowDel;
	}

	public Short getAllowDel() {
		return allowDel;
	}

	public void setNeedOrg(Short needOrg) {
		this.needOrg = needOrg;
	}

	public Short getNeedOrg() {
		return needOrg;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Short getIsActive() {
		return isActive;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int hashCode() {
		HashCodeBuilder has = new HashCodeBuilder();
		has.append(systemId);
		return has.toHashCode();
	}

	public boolean equals(Object object) {
		if (!(object instanceof SubSystem)) {
			return false;
		}
		SubSystem rhs = (SubSystem) object;
		return new EqualsBuilder().append(systemId, rhs.systemId).isEquals();
	}

	public Long getId() {
		return systemId;
	}

	public void setId(Long id) {
		systemId = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}