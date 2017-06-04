package com.cloudstong.platform.third.bpm.model;

public class TaskUser {
	protected String id;
	protected Integer reversion;
	protected String groupId;
	protected String type;
	protected String userId;
	protected String taskId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getReversion() {
		return reversion;
	}

	public void setReversion(Integer reversion) {
		this.reversion = reversion;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}