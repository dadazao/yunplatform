package com.cloudstong.platform.third.bpm.dao;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.ProcessTaskHistory;

@Repository
public class TaskHistoryDao extends BaseMyBatisDaoImpl<ProcessTaskHistory, Long> {
	public Class getEntityClass() {
		return ProcessTaskHistory.class;
	}

	public String getIbatisMapperNamespace() {
		return "com.cloudstongplatform.third.bpm.model.ProcessTaskHistory";
	}
}