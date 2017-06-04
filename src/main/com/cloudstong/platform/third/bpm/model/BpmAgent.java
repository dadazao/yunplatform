package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmAgent extends EntityBase implements Cloneable {
	protected Long id;
	protected Long agentid;
	protected String defKey;
	protected Long agentuserid;
	protected Long touserid;
	protected String actdefid;
	protected String subject;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAgentid(Long agentid) {
		this.agentid = agentid;
	}

	public Long getAgentid() {
		return agentid;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	public String getDefKey() {
		return defKey;
	}

	public void setAgentuserid(Long agentuserid) {
		this.agentuserid = agentuserid;
	}

	public Long getAgentuserid() {
		return agentuserid;
	}

	public void setTouserid(Long touserid) {
		this.touserid = touserid;
	}

	public Long getTouserid() {
		return touserid;
	}

	public void setActdefid(String actdefid) {
		this.actdefid = actdefid;
	}

	public String getActdefid() {
		return actdefid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmAgent)) {
			return false;
		}
		BpmAgent rhs = (BpmAgent) object;
		return new EqualsBuilder().append(id, rhs.id).append(agentid, rhs.agentid).append(defKey, rhs.defKey).append(agentuserid, rhs.agentuserid)
				.append(touserid, rhs.touserid).append(actdefid, rhs.actdefid).append(subject, rhs.subject).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(agentid).append(defKey).append(agentuserid).append(touserid)
				.append(actdefid).append(subject).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("agentid", agentid).append("defid", defKey).append("agentuserid", agentuserid)
				.append("touserid", touserid).append("actdefid", actdefid).append("subject", subject).toString();
	}

	public Object clone() {
		BpmAgent obj = null;
		try {
			obj = (BpmAgent) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}