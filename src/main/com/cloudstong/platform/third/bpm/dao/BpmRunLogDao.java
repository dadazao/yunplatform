package com.cloudstong.platform.third.bpm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;

@Repository
public class BpmRunLogDao extends BaseMyBatisDaoImpl<BpmRunLog, Long> {
	public Class getEntityClass() {
		return BpmRunLog.class;
	}

	public List<BpmRunLog> getByUserId(Long userId) {
		List list = getBySqlKey("getByUserId", userId);
		return list;
	}

	public List<BpmRunLog> getByRunId(Long runId) {
		List list = getBySqlKey("getByRunId", runId);
		return list;
	}
}