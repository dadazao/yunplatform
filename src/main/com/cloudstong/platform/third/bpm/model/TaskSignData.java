package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskSignData extends EntityBase {
	public static Short AGREE = Short.valueOf((short) 1);

	public static Short REFUSE = Short.valueOf((short) 2);

	public static Short ABORT = Short.valueOf((short) 0);

	public static Short BACK = Short.valueOf((short) 3);

	public static Short COMPLETED = Short.valueOf((short) 1);

	public static Short NOT_COMPLETED = Short.valueOf((short) 0);
	protected Long dataId;
	protected String actInstId;
	protected String nodeName;
	protected String nodeId;
	protected String taskId;
	protected Long voteUserId;
	protected String voteUserName;
	protected Date voteTime;
	protected Short isAgree;
	protected String content;
	protected Integer signNums;
	protected Short isCompleted;
	protected String actDefId;

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setVoteUserId(Long voteUserId) {
		this.voteUserId = voteUserId;
	}

	public Long getVoteUserId() {
		return voteUserId;
	}

	public void setVoteUserName(String voteUserName) {
		this.voteUserName = voteUserName;
	}

	public String getVoteUserName() {
		return voteUserName;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setIsAgree(Short isAgree) {
		this.isAgree = isAgree;
	}

	public Short getIsAgree() {
		return isAgree;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setSignNums(Integer signNums) {
		this.signNums = signNums;
	}

	public Integer getSignNums() {
		return signNums;
	}

	public void setIsCompleted(Short isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Short getIsCompleted() {
		return isCompleted;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskSignData)) {
			return false;
		}
		TaskSignData rhs = (TaskSignData) object;
		return new EqualsBuilder().append(dataId, rhs.dataId).append(actInstId, rhs.actInstId).append(nodeName, rhs.nodeName)
				.append(nodeId, rhs.nodeId).append(taskId, rhs.taskId).append(voteUserId, rhs.voteUserId).append(voteUserName, rhs.voteUserName)
				.append(voteTime, rhs.voteTime).append(isAgree, rhs.isAgree).append(content, rhs.content).append(signNums, rhs.signNums)
				.append(isCompleted, rhs.isCompleted).append(actDefId, rhs.actDefId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(dataId).append(actInstId).append(nodeName).append(nodeId).append(taskId)
				.append(voteUserId).append(voteUserName).append(voteTime).append(isAgree).append(content).append(signNums).append(isCompleted)
				.append(actDefId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("dataId", dataId).append("actInstId", actInstId).append("nodeName", nodeName)
				.append("nodeId", nodeId).append("taskId", taskId).append("voteUserId", voteUserId).append("voteUserName", voteUserName)
				.append("voteTime", voteTime).append("isAgree", isAgree).append("content", content).append("signNums", signNums)
				.append("isCompleted", isCompleted).append("actDefId", actDefId).toString();
	}

	@Override
	public Long getId() {
		return dataId;
	}

	@Override
	public void setId(Long id) {
		dataId = id;
	}
	
}