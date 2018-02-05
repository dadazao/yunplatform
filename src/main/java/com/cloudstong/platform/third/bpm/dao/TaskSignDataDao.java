package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.TaskSignData;

@Repository
public class TaskSignDataDao extends BaseMyBatisDaoImpl<TaskSignData, Long> {
	public Class getEntityClass() {
		return TaskSignData.class;
	}

	public List<TaskSignData> getByActInstIdNodeIdSignNums(String actInstId, String nodeId, Integer signNums) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		params.put("signNums", signNums);

		return getBySqlKey("getByActInstIdNodeIdSignNums", params);
	}

	public Integer getMaxSignNums(String actInstId, String nodeId, Short isCompleted) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		params.put("isCompleted", isCompleted);

		Integer maxNums = (Integer) getOne("getMaxSignNums", params);

		return Integer.valueOf(maxNums == null ? 0 : maxNums.intValue());
	}

	public TaskSignData getByTaskId(String taskId) {
		return (TaskSignData) getUnique("getByTaskId", taskId);
	}

	public Integer getTotalVoteCount(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		Integer count = (Integer) getOne("getTotalVoteCount", params);
		return count;
	}

	public Integer getAgreeVoteCount(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		Integer count = (Integer) getOne("getAgreeVoteCount", params);
		return count;
	}

	public Integer getRefuseVoteCount(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		Integer count = (Integer) getOne("getRefuseVoteCount", params);
		return count;
	}

	public Integer getAbortVoteCount(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		Integer count = (Integer) getOne("getAbortVoteCount", params);
		return count;
	}

	public TaskSignData getUserTaskSign(String actInstId, String nodeId, Long voteUserId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		params.put("voteUserId", voteUserId);

		return (TaskSignData) getUnique("getUserTaskSign", params);
	}

	public void batchUpdateCompleted(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);

		update("updateCompleted", params);
	}

	public void delByIdActDefId(String actDefId) {
		delBySqlKey("delByIdActDefId", actDefId);
	}

	public List<TaskSignData> getByNodeAndInstanceId(String instanceId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", instanceId);
		params.put("nodeId", nodeId);

		return getBySqlKey("getByNodeAndInstanceId", params);
	}
}