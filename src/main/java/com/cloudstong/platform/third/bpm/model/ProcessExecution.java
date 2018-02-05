package com.cloudstong.platform.third.bpm.model;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class ProcessExecution {
	private String id;
	private Integer revision;
	private String processInstanceId;
	private String businessKey;
	private String processDefinitionId;
	private String activityId;
	private Short isActive;
	private Short isConcurrent;
	private Short isScope;
	private String parentId;
	private String superExecutionId;
	private Short isEventScope;
	private Integer suspensionState;
	private Integer cachedEntityState;

	public ProcessExecution() {
	}

	public ProcessExecution(ExecutionEntity executionEntity) {
		revision = Integer.valueOf(executionEntity.getRevision());
		processInstanceId = executionEntity.getProcessInstanceId();

		processDefinitionId = executionEntity.getProcessDefinitionId();
		activityId = executionEntity.getActivityId();
		isActive = Short.valueOf((short) (executionEntity.isActive() ? 1 : 0));
		isConcurrent = Short.valueOf((short) (executionEntity.isConcurrent() ? 1 : 0));
		isScope = Short.valueOf((short) (executionEntity.isScope() ? 1 : 0));
		parentId = executionEntity.getParentId();
		superExecutionId = executionEntity.getSuperExecutionId();
		isEventScope = Short.valueOf((short) (executionEntity.isEventScope() ? 1 : 0));
		suspensionState = Integer.valueOf(executionEntity.getSuspensionState());
		cachedEntityState = Integer.valueOf(executionEntity.getCachedEntityState());
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getSuperExecutionId() {
		return superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Short getIsActive() {
		return isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Short getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(Short isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public Short getIsScope() {
		return isScope;
	}

	public void setIsScope(Short isScope) {
		this.isScope = isScope;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getIsEventScope() {
		return isEventScope;
	}

	public void setIsEventScope(Short isEventScope) {
		this.isEventScope = isEventScope;
	}

	public Integer getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	public Integer getCachedEntityState() {
		return cachedEntityState;
	}

	public void setCachedEntityState(Integer cachedEntityState) {
		this.cachedEntityState = cachedEntityState;
	}
}