package com.cloudstong.platform.third.bpm.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.util.ContextUtil;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.ProcessTask;
import com.cloudstong.platform.third.bpm.model.TaskUser;

@Repository
public class TaskDao extends BaseMyBatisDaoImpl<TaskEntity, Long> {
	public Class<TaskEntity> getEntityClass() {
		return TaskEntity.class;
	}

	public String getIbatisMapperNamespace() {
		return "com.cloudstong.platform.third.bpm.model.ProcessTask";
	}

	public void updTaskExecution(String taskId) {
		update("updTaskExecution", taskId);
	}

	public List<TaskEntity> getMyTasks(Long userId, QueryCriteria queryCriteria) {
		String statmentName = "getAllMyTask";
		queryCriteria.addQueryCondition("userId", userId);
		return getBySqlKey(statmentName, queryCriteria);
	}

	public List<TaskEntity> getMyMobileTasks(QueryCriteria queryCriteria) {
		String statmentName = "getMyMobileTask";
		return getBySqlKey(statmentName, queryCriteria);
	}

	public List<ProcessTask> getTasks(Long userId, String taskName, String subject, String processName, String orderField, String orderSeq,
			QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("name", taskName);
		params.put("subject", subject);
		params.put("processName", processName);
		params.put("orderField", orderField);
		params.put("orderSeq", orderSeq);
		List list = getBySqlKey("getAllMyTask", params, queryCriteria);
		return list;
	}

	public List<TaskEntity> getMyEvents(Map param) {
		Log logger = LogFactory.getLog(getClass());
		Map map = param;
		String mode = (String) map.get("mode");
		String sDate = (String) map.get("startDate");
		String eDate = (String) map.get("endDate");

		Date startDate = null;
		Date endDate = null;

		if ("month".equals(mode)) {
			try {
				Date reqDate = DateUtils.parseDate(sDate, new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();
				cal.setTime(reqDate);
				startDate = DateUtil.setStartDay(cal).getTime();
				reqDate = DateUtils.parseDate(eDate, new String[] { "MM/dd/yyyy" });
				cal.setTime(reqDate);
				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("day".equals(mode)) {
			try {
				Date reqDay = DateUtils.parseDate(sDate, new String[] { "MM/dd/yyyy" });

				Calendar cal = Calendar.getInstance();
				cal.setTime(reqDay);

				startDate = DateUtil.setStartDay(cal).getTime();

				cal.add(2, 1);
				cal.add(5, -1);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("week".equals(mode)) {
			try {
				Date reqStartWeek = DateUtils.parseDate(sDate, new String[] { "MM/dd/yyyy" });
				Date reqEndWeek = DateUtils.parseDate(eDate, new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();

				cal.setTime(reqStartWeek);

				startDate = DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else if ("workweek".equals(mode)) {
			try {
				Date reqStartWeek = DateUtils.parseDate(sDate, new String[] { "MM/dd/yyyy" });
				Date reqEndWeek = DateUtils.parseDate(eDate, new String[] { "MM/dd/yyyy" });
				Calendar cal = Calendar.getInstance();

				cal.setTime(reqStartWeek);

				startDate = DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate = DateUtil.setEndDay(cal).getTime();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}

		Map params = new HashMap();
		params.put("userId", AppContext.getCurrentUserId());
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return getBySqlKey("getAllMyEvent", params);
	}

	public List<TaskEntity> getAgentTasks(Long userId, String actDefId, QueryCriteria queryCriteria) {
		String statmentName = "getByAgent";
		queryCriteria.addQueryCondition("userId", userId);
		queryCriteria.addQueryCondition("actDefId", actDefId);
		return getBySqlKey(statmentName, queryCriteria);
	}

	public List<Long> getAgentIdByTaskId(String taskId, String userId) {
		Map map = new HashMap();
		map.put("taskId", taskId);
		map.put("userId", userId);
		List list = getBySqlKey("getAgentIdByTaskId", map);
		return list;
	}

	public int setDueDate(String taskId, Date dueDate) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("dueDate", dueDate);
		return update("setDueDate", params);
	}

	public void insertTask(ProcessTask task) {
		String statement = getIbatisMapperNamespace() + ".add";
		getSqlSessionTemplate().insert(statement, task);
	}

	public List<TaskUser> getCandidateUsers(String taskId) {
		List list = getBySqlKey("getCandidateUsers", taskId);
		return list;
	}

	public List<TaskEntity> getAllAgentTask(Long userId, QueryCriteria queryCriteria) {
		String statmentName = "getAllAgent";
		queryCriteria.addQueryCondition("userId", userId);
		return getBySqlKey(statmentName, queryCriteria);
	}

	public List<ProcessTask> getReminderTask() {
		Date curDate = new Date(System.currentTimeMillis());
		List list = getSqlSessionTemplate().selectList("getReminderTask", curDate);
		return list;
	}

	public List<ProcessTask> getTasksByRunId(Long runId) {
		List list = getBySqlKey("getTasksByRunId", runId);
		return list;
	}

	public void updateTaskAssignee(String taskId, String userId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskAssignee", params);
	}

	public void updateTaskAssigneeNull(String taskId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		update("updateTaskAssigneeNull", params);
	}

	public void updateTaskOwner(String taskId, String userId) {
		Map params = new HashMap();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskOwner", params);
	}

	public List<ProcessTask> getByTaskNameOrTaskIds(String userId, String taskName, String taskIds, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("taskName", taskName);
		params.put("taskIds", taskIds);
		List list = getBySqlKey("getByTaskNameOrTaskIds", params, queryCriteria);
		return list;
	}

	public ProcessTask getByTaskId(String taskId) {
		return (ProcessTask) getOne("getByTaskId", taskId);
	}

	public List<ProcessTask> getByInstanceId(String instanceId) {
		List list = getBySqlKey("getByInstanceId", instanceId);
		return list;
	}

	public void delByInstanceId(Long instanceId) {
		update("delByInstanceId", instanceId);
	}

	public void delCandidateByInstanceId(Long instanceId) {
		update("delCandidateByInstanceId", instanceId);
	}

	public void updateNewTaskDefKeyByInstIdNodeId(String newTaskDefKey, String oldTaskDefKey, String actInstId) {
		Map params = new HashMap();
		params.put("newTaskDefKey", newTaskDefKey);
		params.put("oldTaskDefKey", oldTaskDefKey);
		params.put("actInstId", actInstId);
		update("updateNewTaskDefKeyByInstIdNodeId", params);
	}

	public void updateOldTaskDefKeyByInstIdNodeId(String newTaskDefKey, String oldTaskDefKey, String actInstId) {
		Map params = new HashMap();
		params.put("newTaskDefKey", StringUtil.isNotEmpty(newTaskDefKey) ? newTaskDefKey + "%" : newTaskDefKey);
		params.put("oldTaskDefKey", oldTaskDefKey);
		params.put("actInstId", actInstId);
		update("updateOldTaskDefKeyByInstIdNodeId", params);
	}

	public List<Map> getHasCandidateExecutor(String taskIds) {
		Map params = new HashMap();
		params.put("taskIds", taskIds);
		String statement = getIbatisMapperNamespace() + ".getHasCandidateExecutor";
		List list = getSqlSessionTemplate().selectList(statement, params);
		return list;
	}

	public void delByActDefId(String actDefId) {
		update("delByActDefId", actDefId);
	}

	public void delCandidateByActDefId(String actDefId) {
		update("delCandidateByActDefId", actDefId);
	}
}