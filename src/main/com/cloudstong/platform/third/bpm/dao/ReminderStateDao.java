package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.ReminderState;

@Repository
public class ReminderStateDao extends BaseMyBatisDaoImpl<ReminderState, Long> {
	public Class getEntityClass() {
		return ReminderState.class;
	}

	public Integer getAmountByUserTaskId(long taskId, long userId, int remindType) {
		Map params = new HashMap();
		params.put("taskId", Long.valueOf(taskId));
		params.put("userId", Long.valueOf(userId));
		params.put("remindType", Integer.valueOf(remindType));

		Integer rtn = (Integer) getOne("getAmountByUserTaskId", params);
		return rtn;
	}

	public Integer getAmountByTaskId(long taskId, int remindType) {
		Map params = new HashMap();
		params.put("taskId", Long.valueOf(taskId));
		params.put("remindType", Integer.valueOf(remindType));
		Integer rtn = (Integer) getOne("getAmountByTaskId", params);
		return rtn;
	}

	public void delExpiredTaskReminderState() {
		delBySqlKey("delExpiredTaskReminderState", null);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}