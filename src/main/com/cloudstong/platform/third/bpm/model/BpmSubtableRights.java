package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmSubtableRights extends EntityBase {
	protected Long id;
	protected String actdefid;
	protected String nodeid;
	protected Long tableid;
	protected Long permissiontype;
	protected String permissionseting;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public void setTableid(Long tableid) {
		this.tableid = tableid;
	}

	public Long getTableid() {
		return tableid;
	}

	public void setPermissiontype(Long permissiontype) {
		this.permissiontype = permissiontype;
	}

	public Long getPermissiontype() {
		return permissiontype;
	}

	public void setPermissionseting(String permissionseting) {
		this.permissionseting = permissionseting;
	}

	public String getPermissionseting() {
		return permissionseting;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmSubtableRights)) {
			return false;
		}
		BpmSubtableRights rhs = (BpmSubtableRights) object;
		return new EqualsBuilder().append(id, rhs.id).append(actdefid, rhs.actdefid).append(nodeid, rhs.nodeid).append(tableid, rhs.tableid)
				.append(permissiontype, rhs.permissiontype).append(permissionseting, rhs.permissionseting).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(actdefid).append(nodeid).append(tableid).append(permissiontype)
				.append(permissionseting).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("actdefid", actdefid).append("nodeid", nodeid).append("tableid", tableid)
				.append("permissiontype", permissiontype).append("permissionseting", permissionseting).toString();
	}
}