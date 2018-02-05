package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmNodePrivilege extends EntityBase {
	protected Long privilegeid;
	protected String actdefid;
	protected String nodeid;
	protected Long privilegemode;
	protected Long usertype;
	protected String cmpnames;
	protected String cmpids;

	public void setPrivilegeid(Long privilegeid) {
		this.privilegeid = privilegeid;
	}

	public Long getPrivilegeid() {
		return privilegeid;
	}

	public void setActdefid(String actdefid) {
		this.actdefid = actdefid;
	}

	public String getActdefid() {
		return actdefid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setPrivilegemode(Long privilegemode) {
		this.privilegemode = privilegemode;
	}

	public Long getPrivilegemode() {
		return privilegemode;
	}

	public void setUsertype(Long usertype) {
		this.usertype = usertype;
	}

	public Long getUsertype() {
		return usertype;
	}

	public void setCmpnames(String cmpnames) {
		this.cmpnames = cmpnames;
	}

	public String getCmpnames() {
		return cmpnames;
	}

	public void setCmpids(String cmpids) {
		this.cmpids = cmpids;
	}

	public String getCmpids() {
		return cmpids;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodePrivilege)) {
			return false;
		}
		BpmNodePrivilege rhs = (BpmNodePrivilege) object;
		return new EqualsBuilder().append(privilegeid, rhs.privilegeid).append(actdefid, rhs.actdefid).append(nodeid, rhs.nodeid)
				.append(privilegemode, rhs.privilegemode).append(usertype, rhs.usertype).append(cmpnames, rhs.cmpnames).append(cmpids, rhs.cmpids)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(privilegeid).append(actdefid).append(nodeid).append(privilegemode).append(usertype)
				.append(cmpnames).append(cmpids).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("privilegeid", privilegeid).append("actdefid", actdefid).append("nodeid", nodeid)
				.append("privilegemode", privilegemode).append("usertype", usertype).append("cmpnames", cmpnames).append("cmpids", cmpids).toString();
	}

	@Override
	public Long getId() {
		return privilegeid;
	}

	@Override
	public void setId(Long id) {
		privilegeid = id;
	}

}