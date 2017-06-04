package com.cloudstong.platform.third.bpm.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.task.TaskExecutor;

public class NodeUserMap {
	private String nodeId;
	private String nodeName;
	private Long setId;
	private List<BpmNodeUser> nodeUserList;
	private Set<TaskExecutor> taskExecutors = new HashSet();
	private boolean isMultipleInstance;
	private List<BpmUserCondition> bpmUserConditionList;
	public static short CHOOICETYPE_NO = 0;
	public static short CHOOICETYPE_RADIO = 1;
	public static short CHOOICETYPE_CHECK = 2;

	private short chooiceType = 0;

	public short getChooiceType() {
		return chooiceType;
	}

	public void setChooiceType(short chooiceType) {
		this.chooiceType = chooiceType;
	}

	public NodeUserMap() {
	}

	public NodeUserMap(Long setId, String nodeId, String nodeName, List<BpmNodeUser> nodeUserList) {
		this.setId = setId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeUserList = nodeUserList;
	}

	public NodeUserMap(String nodeId, String nodeName, Set<TaskExecutor> taskExecutors) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.taskExecutors = taskExecutors;
	}

	public NodeUserMap(String nodeId, String nodeName, Set<TaskExecutor> taskExecutors, boolean isMultipleInstance) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.taskExecutors = taskExecutors;
		this.isMultipleInstance = isMultipleInstance;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<BpmNodeUser> getNodeUserList() {
		return nodeUserList;
	}

	public void setNodeUserList(List<BpmNodeUser> nodeUserList) {
		this.nodeUserList = nodeUserList;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Set<TaskExecutor> getTaskExecutors() {
		return taskExecutors;
	}

	public void setTaskExecutors(Set<TaskExecutor> users) {
		taskExecutors = users;
	}

	public boolean isMultipleInstance() {
		return isMultipleInstance;
	}

	public void setMultipleInstance(boolean isMultipleInstance) {
		this.isMultipleInstance = isMultipleInstance;
	}

	public List<BpmUserCondition> getBpmUserConditionList() {
		return bpmUserConditionList;
	}

	public void setBpmUserConditionList(List<BpmUserCondition> bpmUserConditionList) {
		this.bpmUserConditionList = bpmUserConditionList;
	}
}