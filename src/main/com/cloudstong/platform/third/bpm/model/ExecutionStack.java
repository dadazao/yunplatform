package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExecutionStack extends EntityBase {
	public static final Short MULTI_TASK = Short.valueOf((short) 1);

	public static final Short COMMON_TASK = Short.valueOf((short) 0);
	protected Long stackId;
	protected String nodeId;
	protected String nodeName;
	protected Date startTime;
	protected Date endTime;
	protected String assignees;
	protected Short isMultiTask = COMMON_TASK;
	protected Long parentId;
	protected String actInstId;
	protected String taskIds;
	protected String nodePath;
	protected Integer depth;
	protected String actDefId;
	protected String taskToken;

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	public Long getStackId() {
		return stackId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setAssignees(String assignees) {
		this.assignees = assignees;
	}

	public String getAssignees() {
		return assignees;
	}

	public void setIsMultiTask(Short isMultiTask) {
		this.isMultiTask = isMultiTask;
	}

	public Short getIsMultiTask() {
		return isMultiTask;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public String getTaskIds() {
		return taskIds;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getDepth() {
		return depth;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ExecutionStack)) {
			return false;
		}
		ExecutionStack rhs = (ExecutionStack) object;
		return new EqualsBuilder().append(stackId, rhs.stackId).append(nodeId, rhs.nodeId).append(nodeName, rhs.nodeName)
				.append(startTime, rhs.startTime).append(endTime, rhs.endTime).append(assignees, rhs.assignees).append(isMultiTask, rhs.isMultiTask)
				.append(parentId, rhs.parentId).append(actInstId, rhs.actInstId).append(taskIds, rhs.taskIds).append(nodePath, rhs.nodePath)
				.append(depth, rhs.depth).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(stackId).append(nodeId).append(nodeName).append(startTime).append(endTime)
				.append(assignees).append(isMultiTask).append(parentId).append(actInstId).append(taskIds).append(nodePath).append(depth).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("stackId", stackId).append("nodeId", nodeId).append("nodeName", nodeName)
				.append("startTime", startTime).append("endTime", endTime).append("assignees", assignees).append("isMultiTask", isMultiTask)
				.append("parentId", parentId).append("actInstId", actInstId).append("taskIds", taskIds).append("nodePath", nodePath)
				.append("depth", depth).toString();
	}

	public String getTaskToken() {
		return taskToken;
	}

	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}

	@Override
	public Long getId() {
		return stackId;
	}

	@Override
	public void setId(Long id) {
		stackId = id;
	}
	
}