package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;

@Repository
public class TaskOpinionDao extends BaseMyBatisDaoImpl<TaskOpinion, Long> {
	public Class getEntityClass() {
		return TaskOpinion.class;
	}

	public TaskOpinion getByTaskId(Long taskId) {
		return (TaskOpinion) getUnique("getByTaskId", taskId);
	}

	public List<TaskOpinion> getByActInstId(String[] actInstIds) {
		Map params = new HashMap();
		params.put("actInstIds", actInstIds);
		return getBySqlKey("getByActInstId", params);
	}

	public void delByTaskId(Long taskId) {
		delBySqlKey("delByTaskId", taskId);
	}

	public List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("taskKey", taskKey);
		return getBySqlKey("getByActInstIdTaskKey", params);
	}

	public List<TaskOpinion> getByActInstIdExeUserId(String actInstId, Long exeUserId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("exeUserId", exeUserId);
		return getBySqlKey("getByActInstIdExeUserId", params);
	}

	public List<TaskOpinion> getFormOptionsByInstance(String instanceId) {
		return getBySqlKey("getFormOptionsByInstance", instanceId);
	}

	public List getByFinishTask(Long userId, String subject, String taskName, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("userId", userId);
		if (StringUtil.isNotEmpty(subject))
			params.put("subject", "%" + subject + "%");
		if (StringUtil.isNotEmpty(taskName))
			params.put("taskName", "%" + taskName + "%");
		return getBySqlKey("getByFinishTask", params, queryCriteria);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}