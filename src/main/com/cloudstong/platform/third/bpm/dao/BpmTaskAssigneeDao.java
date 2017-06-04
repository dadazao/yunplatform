package com.cloudstong.platform.third.bpm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmTaskAssignee;

@Repository
public class BpmTaskAssigneeDao extends BaseMyBatisDaoImpl<BpmTaskAssignee, Long> {
	public Class getEntityClass() {
		return BpmTaskAssignee.class;
	}

	public List<BpmTaskAssignee> getAllMyTask(QueryCriteria queryCriteria) {
		return getBySqlKey("getAllMyTask", queryCriteria);
	}

	public int getCountByTaskId(String taskId) {
		int count = ((Integer) getOne("getCountByTaskId", taskId)).intValue();
		return count;
	}

	public List<BpmTaskAssignee> getByTaskId(String taskId) {
		return getBySqlKey("getByTaskId", taskId);
	}

	public void delByTaskId(String taskId) {
		delBySqlKey("delByTaskId", taskId);
	}

	public void delByRunId(Long runId) {
		delBySqlKey("delByRunId", runId);
	}
}