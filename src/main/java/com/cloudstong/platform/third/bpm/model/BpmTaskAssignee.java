package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmTaskAssignee extends EntityBase {
	protected Long id;
	protected Long userId;
	protected String userName;
	protected Long assigneeId;
	protected String taskId;
	protected String taskName;
	protected String subject;
	protected Short taskStatus;
	protected String assigneeName;
	protected Date assigneeTime;
	protected Long runId;
	protected String memo;
	public static final Short TASK_NO_EXC = Short.valueOf((short) 0);
	public static final Short TASK_EXC = Short.valueOf((short) 1);

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setTaskStatus(Short taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Short getTaskStatus() {
		return taskStatus;
	}

	public Date getAssigneeTime() {
		return assigneeTime;
	}

	public void setAssigneeTime(Date assigneeTime) {
		this.assigneeTime = assigneeTime;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmTaskAssignee)) {
			return false;
		}
		BpmTaskAssignee rhs = (BpmTaskAssignee) object;
		return new EqualsBuilder().append(id, rhs.id).append(userId, rhs.userId).append(assigneeId, rhs.assigneeId).append(taskId, rhs.taskId)
				.append(subject, rhs.subject).append(taskStatus, rhs.taskStatus).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(userId).append(assigneeId).append(taskId).append(subject)
				.append(taskStatus).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("userId", userId).append("assigneeId", assigneeId).append("taskId", taskId)
				.append("subject", subject).append("taskStatus", taskStatus).toString();
	}
}