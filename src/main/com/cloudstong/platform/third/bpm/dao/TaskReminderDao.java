package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.TaskReminder;

@Repository
public class TaskReminderDao extends BaseMyBatisDaoImpl<TaskReminder, Long> {
	public Class getEntityClass() {
		return TaskReminder.class;
	}

	public List<TaskReminder> getByActDefAndNodeId(String actDefId, String nodeId) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		List objs = getBySqlKey("getByActDefAndNodeId", map);
		return objs;
	}

	public Integer isExistByActDefAndNodeId(String actDefId, String nodeId) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		Integer obj = (Integer) getOne("isExistByActDefAndNodeId", map);
		return obj;
	}

	public List<TaskReminder> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}