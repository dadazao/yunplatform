package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.cloudstong.platform.third.bpm.model.BpmDefVar;

public abstract interface IBpmActService {
	public abstract List<BpmDefVar> getVarsByFlowDefId(Long paramLong);

	public abstract ExecutionEntity getExecution(String paramString);

	public abstract void endProcessByTaskId(String paramString);
}