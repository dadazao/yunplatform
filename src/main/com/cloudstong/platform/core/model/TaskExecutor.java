package com.cloudstong.platform.core.model;

import java.io.Serializable;

public class TaskExecutor implements Serializable {
	public static final String USER_TYPE_USER = "user";
	public static final String USER_TYPE_ORG = "org";
	public static final String USER_TYPE_ROLE = "role";
	public static final String USER_TYPE_POS = "pos";
	private String type = "user";

	private String executeId = "";

	private String executor = "";

	public TaskExecutor() {
	}

	public TaskExecutor(String type, String executeId, String name) {
		this.type = type;
		this.executeId = executeId;
		executor = name;
	}

	public static TaskExecutor getTaskUser(String executeId, String name) {
		return new TaskExecutor("user", executeId, name);
	}

	public static TaskExecutor getTaskOrg(String executeId, String name) {
		return new TaskExecutor("org", executeId, name);
	}

	public static TaskExecutor getTaskRole(String executeId, String name) {
		return new TaskExecutor("role", executeId, name);
	}

	public static TaskExecutor getTaskPos(String executeId, String name) {
		return new TaskExecutor("pos", executeId, name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof TaskExecutor)) {
			return false;
		}
		TaskExecutor tmp = (TaskExecutor) obj;
		if ((type.equals(tmp.getType())) && (executeId.equals(tmp.getExecuteId()))) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		String tmp = type + executeId;
		return tmp.hashCode();
	}
}