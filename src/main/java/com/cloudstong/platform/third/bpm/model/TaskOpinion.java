package com.cloudstong.platform.third.bpm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.TimeUtil;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TaskOpinion extends EntityBase {
	public static final Short STATUS_INIT = Short.valueOf((short) -2);

	public static final Short STATUS_CHECKING = Short.valueOf((short) -1);

	public static final Short STATUS_ABANDON = Short.valueOf((short) 0);

	public static final Short STATUS_AGREE = Short.valueOf((short) 1);

	public static final Short STATUS_REFUSE = Short.valueOf((short) 2);

	public static final Short STATUS_REJECT = Short.valueOf((short) 3);

	public static final Short STATUS_RECOVER = Short.valueOf((short) 4);

	public static final Short STATUS_PASSED = Short.valueOf((short) 5);

	public static final Short STATUS_NOT_PASSED = Short.valueOf((short) 6);

	public static final Short STATUS_NOTIFY = Short.valueOf((short) 7);

	public static final Short STATUS_CHANGEPATH = Short.valueOf((short) 8);
	protected Long opinionId;
	protected String actInstId;
	protected String taskName;
	protected String taskKey;
	protected String taskToken;
	protected Long taskId;
	protected Date startTime;
	protected Date endTime;
	protected Long durTime;
	protected Long exeUserId;
	protected String exeFullname;
	protected String opinion;
	protected Short checkStatus = STATUS_CHECKING;

	protected Long formDefId = Long.valueOf(0L);
	protected String fieldName;
	protected String actDefId;
	protected String superExecution;

	public TaskOpinion() {
	}

	public TaskOpinion(ProcessTask task) {
		actDefId = task.getProcessDefinitionId();
		actInstId = task.getProcessInstanceId();
		taskId = new Long(task.getId());
		taskName = task.getName();
		taskKey = task.getTaskDefinitionKey();
		startTime = new Date();
	}

	public TaskOpinion(DelegateTask task) {
		actDefId = task.getProcessDefinitionId();
		actInstId = task.getProcessInstanceId();
		taskId = new Long(task.getId());
		taskKey = task.getTaskDefinitionKey();
		taskName = task.getName();
		startTime = new Date();
		ExecutionEntity superExecution = ((ExecutionEntity) task.getExecution()).getProcessInstance().getSuperExecution();
		if (superExecution != null)
			this.superExecution = superExecution.getProcessInstanceId();
	}

	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}

	public Long getOpinionId() {
		return opinionId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStartTimeStr() {
		if (startTime == null)
			return "";
		return TimeUtil.getDateTimeString(startTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getEndTimeStr() {
		if (endTime == null)
			return "";
		return TimeUtil.getDateTimeString(endTime);
	}

	public void setDurTime(Long durTime) {
		this.durTime = durTime;
	}

	public String getDurTimeStr() {
		if (durTime == null)
			return "";
		return TimeUtil.getTime(durTime);
	}

	public Long getDurTime() {
		return durTime;
	}

	public void setExeUserId(Long exeUserId) {
		this.exeUserId = exeUserId;
	}

	public Long getExeUserId() {
		return exeUserId;
	}

	public void setExeFullname(String exeFullname) {
		this.exeFullname = exeFullname;
	}

	public String getExeFullname() {
		if (StringUtil.isEmpty(exeFullname))
			return "";
		return exeFullname;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getStatus() {
		String status = "";
		switch (checkStatus.shortValue()) {
		case -2:
			status = "未开始";
			break;
		case -1:
			status = "正在审批";
			break;
		case 0:
			status = "弃权";
			break;
		case 1:
			status = "同意";
			break;
		case 2:
			status = "反对";
			break;
		case 3:
			status = "驳回";
			break;
		case 4:
			status = "追回";
		}

		return status;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public Long getFormDefId() {
		return formDefId;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskToken() {
		return taskToken;
	}

	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}

	public String getSuperExecution() {
		return superExecution;
	}

	public void setSuperExecution(String superExecution) {
		this.superExecution = superExecution;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskOpinion)) {
			return false;
		}
		TaskOpinion rhs = (TaskOpinion) object;
		return new EqualsBuilder().append(opinionId, rhs.opinionId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(opinionId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("opinionId", opinionId).append("actInstId", actInstId).append("taskName", taskName)
				.append("taskId", taskId).append("startTime", startTime).append("endTime", endTime).append("durTime", durTime)
				.append("exeUserId", exeUserId).append("exeFullname", exeFullname).append("opinion", opinion).append("checkStatus", checkStatus)
				.append("actDefId", actDefId).append("superExecution", superExecution).toString();
	}

	@Override
	public Long getId() {
		return opinionId;
	}

	@Override
	public void setId(Long id) {
		opinionId = id;
	}
	
}