package com.cloudstong.platform.third.bpm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class ProcessRun extends EntityBase implements Cloneable {
	public static final Short STATUS_SUSPEND = Short.valueOf((short) 0);

	public static final Short STATUS_RUNNING = Short.valueOf((short) 1);

	public static final Short STATUS_FINISH = Short.valueOf((short) 2);

	public static final Short STATUS_MANUAL_FINISH = Short.valueOf((short) 3);
	
	public static final Short STATUS_FORM = 4;

	public static final Short RECOVER_NO = Short.valueOf((short) 0);

	public static final Short RECOVER = Short.valueOf((short) 1);
	protected Long runId;
	protected Long defId;
	protected String subject;
	protected Long creatorId;
	protected String creator;
	protected Date createtime;
	protected String busDescp;
	protected Short status;
	protected String actInstId;
	protected String actDefId;
	protected String businessKey;
	protected String businessUrl;
	protected Date endTime;
	protected Long duration;
	protected String processName;
	protected String pkName = "";

	protected String tableName = "";

	protected Long parentId = Long.valueOf(0L);

	protected String startOrgName = "";

	protected Long startOrgId = Long.valueOf(0L);

	protected Short recover = RECOVER_NO;

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return defId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setBusDescp(String busDescp) {
		this.busDescp = busDescp;
	}

	public String getBusDescp() {
		return busDescp;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return status;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBusinessUrl() {
		return businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ProcessRun)) {
			return false;
		}
		ProcessRun rhs = (ProcessRun) object;
		return new EqualsBuilder().append(runId, rhs.runId).append(defId, rhs.defId).append(subject, rhs.subject).append(creatorId, rhs.creatorId)
				.append(creator, rhs.creator).append(createtime, rhs.createtime).append(busDescp, rhs.busDescp).append(status, rhs.status)
				.append(actInstId, rhs.actInstId).append(actDefId, rhs.actDefId).append(businessKey, rhs.businessKey)
				.append(businessUrl, rhs.businessUrl).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(runId).append(defId).append(subject).append(creatorId).append(creator)
				.append(createtime).append(busDescp).append(status).append(actInstId).append(actDefId).append(businessKey).append(businessUrl)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("runId", runId).append("defId", defId).append("subject", subject).append("creatorId", creatorId)
				.append("creator", creator).append("createtime", createtime).append("busDescp", busDescp).append("status", status)
				.append("actInstId", actInstId).append("actDefId", actDefId).append("businessKey", businessKey).append("businessUrl", businessUrl)
				.toString();
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getStartOrgName() {
		return startOrgName;
	}

	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}

	public Long getStartOrgId() {
		return startOrgId;
	}

	public void setStartOrgId(Long startOrgId) {
		this.startOrgId = startOrgId;
	}

	public Short getRecover() {
		return recover;
	}

	public void setRecover(Short recover) {
		this.recover = recover;
	}

	public Object clone() {
		ProcessRun obj = null;
		try {
			obj = (ProcessRun) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return runId;
	}

	@Override
	public void setId(Long id) {
		runId = id;
	}
	
}