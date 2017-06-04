package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.ProcessExecution;

@Repository
public class ExecutionDao extends BaseMyBatisDaoImpl<ProcessExecution, String> {
	public Class getEntityClass() {
		return ProcessExecution.class;
	}

	public String getIbatisMapperNamespace() {
		return "com.cloudstongplatform.third.bpm.model.ProcessExecution";
	}

	public void updateMainThread(String executionId, String nodeId) {
		Map params = new HashMap();
		params.put("nodeId", nodeId);
		params.put("executionId", executionId);
		update("updateMainThread", params);
	}

	public void delNotMainThread(String procInstId) {
		Map params = new HashMap();
		params.put("procInstId", procInstId);
		delBySqlKey("delNotMainThread", params);
	}

	public void updateTaskToMainThreadId(String executionId, String taskId) {
		Map params = new HashMap();
		params.put("executionId", executionId);
		params.put("taskId", taskId);
		update("updateTaskToMainThreadId", params);
	}

	public void delLoopAssigneeVars(String executionId) {
		delBySqlKey("delAssigneeByExecutionId", executionId);
		delBySqlKey("delLoopCounterByExecutionId", executionId);
	}

	public void delExecutionById(String executionId) {
		delBySqlKey("delById", executionId);
	}

	public void delTokenVarByTaskId(String taskId, String token) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("name", token);
		delBySqlKey("delTokenVarByTaskId", params);
	}

	public void delVariableByProcInstId(Long procInstId) {
		delBySqlKey("delVariableByProcInstId", procInstId);
	}

	public void delExecutionByProcInstId(Long procInstId) {
		delBySqlKey("delExecutionByProcInstId", procInstId);
	}

	public void delSubExecutionByProcInstId(Long procInstId) {
		delBySqlKey("delSubExecutionByProcInstId", procInstId);
	}

	public void updateRevByInstanceId(String actInstId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		update("updateRevByInstanceId", params);
	}

	public void delVarsByExecutionId(String executionId) {
		delBySqlKey("delVarsByExecutionId", executionId);
	}
}