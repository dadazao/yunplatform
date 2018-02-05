package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.TaskFork;

@Repository
public class TaskForkDao extends BaseMyBatisDaoImpl<TaskFork,Long> {
	public Class getEntityClass() {
		return TaskFork.class;
	}

	public Integer getMaxSn(String actInstId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		Integer sn = (Integer) getOne("getMaxSn", params);
		return sn;
	}

	public TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("joinTaskKey", joinTaskKey);
		TaskFork taskFork = (TaskFork) getUnique("getByInstIdJoinTaskKey", params);
		return taskFork;
	}

	public TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId, String joinTaskKey, String forkToken) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("joinTaskKey", joinTaskKey);
		params.put("forkToken", StringUtil.isNotEmpty(forkToken) ? "%" + forkToken + ",%" : forkToken);
		return (TaskFork) getUnique("getByInstIdJoinTaskKeyForkToken", params);
	}

	public void delByActInstId(String actInstId) {
		delBySqlKey("delByActInstId", actInstId);
	}
}