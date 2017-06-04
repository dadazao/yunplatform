package com.cloudstong.platform.third.bpm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.TaskVars;

@Repository
public class TaskVarsDao extends BaseMyBatisDaoImpl<TaskVars, Long> {
	public Class<TaskVars> getEntityClass() {
		return TaskVars.class;
	}

	public List<TaskVars> getTaskVars(QueryCriteria queryCriteria) {
		return getBySqlKey("getTaskVars", queryCriteria);
	}
}