package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.TaskUser;

@Repository
public class TaskUserDao extends BaseMyBatisDaoImpl<TaskUser, Long> {
	public Class getEntityClass() {
		return TaskUser.class;
	}

	public List<TaskUser> getByTaskId(String taskId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		return getBySqlKey("getByTaskId", params);
	}
}