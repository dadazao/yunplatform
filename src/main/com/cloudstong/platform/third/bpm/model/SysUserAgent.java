package com.cloudstong.platform.third.bpm.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class SysUserAgent extends EntityBase {
	public static short IS_ALL_FLAG = 1;

	public static short IS_NOT_ALL_FLAG = 0;
	protected Long agentid;
	protected Long agentuserid;
	protected Long touserid;
	protected String tofullname;
	protected Date starttime;
	protected Date endtime;
	protected Short isall;
	protected Short isvalid;

	public void setAgentid(Long agentid) {
		this.agentid = agentid;
	}

	public Long getAgentid() {
		return agentid;
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

	public void setTofullname(String tofullname) {
		this.tofullname = tofullname;
	}

	public String getTofullname() {
		return tofullname;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setIsall(Short isall) {
		this.isall = isall;
	}

	public Short getIsall() {
		return isall;
	}

	public void setIsvalid(Short isvalid) {
		this.isvalid = isvalid;
	}

	public Short getIsvalid() {
		return isvalid;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysUserAgent)) {
			return false;
		}
		SysUserAgent rhs = (SysUserAgent) object;
		return new EqualsBuilder().append(agentid, rhs.agentid).append(agentuserid, rhs.agentuserid).append(touserid, rhs.touserid)
				.append(tofullname, rhs.tofullname).append(starttime, rhs.starttime).append(endtime, rhs.endtime).append(isall, rhs.isall)
				.append(isvalid, rhs.isvalid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(agentid).append(agentuserid).append(touserid).append(tofullname).append(starttime)
				.append(endtime).append(isall).append(isvalid).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("agentid", agentid).append("agentuserid", agentuserid).append("touserid", touserid)
				.append("tofullname", tofullname).append("starttime", starttime).append("endtime", endtime).append("isall", isall)
				.append("isvalid", isvalid).toString();
	}

	@Override
	public Long getId() {
		return agentid;
	}

	@Override
	public void setId(Long id) {
		agentid = id;
	}

}