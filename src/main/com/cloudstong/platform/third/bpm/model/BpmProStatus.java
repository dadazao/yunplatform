package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmProStatus extends EntityBase {
	protected Long id = Long.valueOf(0L);

	protected Long actinstid = Long.valueOf(0L);

	protected String nodeid = "";

	protected String nodename = "";

	protected Short status = TaskOpinion.STATUS_INIT;
	protected Date lastupdatetime;
	protected String actdefid = "";

	protected Long defId = Long.valueOf(0L);

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setActinstid(Long actinstid) {
		this.actinstid = actinstid;
	}

	public Long getActinstid() {
		return actinstid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getNodename() {
		return nodename;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return status;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setActdefid(String actdefid) {
		this.actdefid = actdefid;
	}

	public String getActdefid() {
		return actdefid;
	}

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmProStatus)) {
			return false;
		}
		BpmProStatus rhs = (BpmProStatus) object;
		return new EqualsBuilder().append(id, rhs.id).append(actinstid, rhs.actinstid).append(nodeid, rhs.nodeid).append(nodename, rhs.nodename)
				.append(status, rhs.status).append(lastupdatetime, rhs.lastupdatetime).append(actdefid, rhs.actdefid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(actinstid).append(nodeid).append(nodename).append(status)
				.append(lastupdatetime).append(actdefid).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("actinstid", actinstid).append("nodeid", nodeid).append("nodename", nodename)
				.append("status", status).append("lastupdatetime", lastupdatetime).append("actdefid", actdefid).toString();
	}
}