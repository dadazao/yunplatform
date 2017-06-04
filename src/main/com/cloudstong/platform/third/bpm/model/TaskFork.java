package com.cloudstong.platform.third.bpm.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class TaskFork extends EntityBase {
	public static String TAKEN_PRE = "T";

	public static String TAKEN_VAR_NAME = "_token_";
	protected Long taskForkId;
	protected String actInstId;
	protected String forkTaskName;
	protected String forkTaskKey;
	protected Integer forkSn;
	protected Integer forkCount;
	protected Integer fininshCount;
	protected Date forkTime;
	protected String joinTaskKey;
	protected String joinTaskName;
	protected String forkTokens;
	protected String forkTokenPre;

	public void setTaskForkId(Long taskForkId) {
		this.taskForkId = taskForkId;
	}

	public Long getTaskForkId() {
		return taskForkId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setForkTaskName(String forkTaskName) {
		this.forkTaskName = forkTaskName;
	}

	public String getForkTaskName() {
		return forkTaskName;
	}

	public void setForkTaskKey(String forkTaskKey) {
		this.forkTaskKey = forkTaskKey;
	}

	public String getForkTaskKey() {
		return forkTaskKey;
	}

	public void setForkSn(Integer forkSn) {
		this.forkSn = forkSn;
	}

	public Integer getForkSn() {
		return forkSn;
	}

	public void setForkCount(Integer forkCount) {
		this.forkCount = forkCount;
	}

	public Integer getForkCount() {
		return forkCount;
	}

	public void setFininshCount(Integer fininshCount) {
		this.fininshCount = fininshCount;
	}

	public Integer getFininshCount() {
		return fininshCount;
	}

	public void setForkTime(Date forkTime) {
		this.forkTime = forkTime;
	}

	public Date getForkTime() {
		return forkTime;
	}

	public void setJoinTaskKey(String joinTaskKey) {
		this.joinTaskKey = joinTaskKey;
	}

	public String getJoinTaskKey() {
		return joinTaskKey;
	}

	public void setJoinTaskName(String joinTaskName) {
		this.joinTaskName = joinTaskName;
	}

	public String getJoinTaskName() {
		return joinTaskName;
	}

	public String getForkTokens() {
		return forkTokens;
	}

	public void setForkTokens(String forkTokens) {
		this.forkTokens = forkTokens;
	}

	public String getForkTokenPre() {
		return forkTokenPre;
	}

	public void setForkTokenPre(String forkTokenPre) {
		this.forkTokenPre = forkTokenPre;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskFork)) {
			return false;
		}
		TaskFork rhs = (TaskFork) object;
		return new EqualsBuilder().append(taskForkId, rhs.taskForkId).append(actInstId, rhs.actInstId).append(forkTaskName, rhs.forkTaskName)
				.append(forkTaskKey, rhs.forkTaskKey).append(forkSn, rhs.forkSn).append(forkCount, rhs.forkCount)
				.append(fininshCount, rhs.fininshCount).append(forkTime, rhs.forkTime).append(joinTaskKey, rhs.joinTaskKey)
				.append(joinTaskName, rhs.joinTaskName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(taskForkId).append(actInstId).append(forkTaskName).append(forkTaskKey)
				.append(forkSn).append(forkCount).append(fininshCount).append(forkTime).append(joinTaskKey).append(joinTaskName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("taskForkId", taskForkId).append("actInstId", actInstId).append("forkTaskName", forkTaskName)
				.append("forkTaskKey", forkTaskKey).append("forkSn", forkSn).append("forkCount", forkCount).append("fininshCount", fininshCount)
				.append("forkTime", forkTime).append("joinTaskKey", joinTaskKey).append("joinTaskName", joinTaskName).toString();
	}

	@Override
	public Long getId() {
		return taskForkId;
	}

	@Override
	public void setId(Long id) {
		taskForkId = id;
	}

}