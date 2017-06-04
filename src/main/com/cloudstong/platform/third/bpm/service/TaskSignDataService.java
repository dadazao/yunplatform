package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.TaskSignDataDao;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.model.ProcessTask;
import com.cloudstong.platform.third.bpm.model.TaskSignData;

@Service
public class TaskSignDataService {

	private final Log log = LogFactory.getLog(getClass());

	@Resource
	private TaskSignDataDao dao;

	@Resource
	private BpmService bpmService;

	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmRunLogService bpmRunLogService;

	public List<TaskSignData> getByNodeAndInstanceId(String actInstId, String nodeId, Integer signNums) {
		return dao.getByActInstIdNodeIdSignNums(actInstId, nodeId, signNums);
	}

	public List<TaskSignData> getByNodeAndInstanceId(String actInstId, String nodeId) {
		return dao.getByNodeAndInstanceId(actInstId, nodeId);
	}

	public Integer getMaxSignNums(String actInstId, String nodeId, Short isCompleted) {
		return dao.getMaxSignNums(actInstId, nodeId, isCompleted);
	}

	public void signVoteTask(String taskId, String content, Short isAgree) {
		SysUser sysUser = AppContext.getCurrentUser();
		TaskSignData taskSignData = dao.getByTaskId(taskId);
		if (taskSignData != null) {
			taskSignData.setIsAgree(new Short(isAgree.shortValue()));
			taskSignData.setContent(content);
			taskSignData.setVoteTime(new Date());
			taskSignData.setVoteUserId(sysUser.getId());
			taskSignData.setVoteUserName(sysUser.getFullname());
			dao.update(taskSignData);
		}
	}

	public Integer getTotalVoteCount(String actInstId, String nodeId) {
		return dao.getTotalVoteCount(actInstId, nodeId);
	}

	public Integer getAgreeVoteCount(String actInstId, String nodeId) {
		return dao.getAgreeVoteCount(actInstId, nodeId);
	}

	public Integer getRefuseVoteCount(String actInstId, String nodeId) {
		return dao.getRefuseVoteCount(actInstId, nodeId);
	}

	public Integer getAbortVoteCount(String actInstId, String nodeId) {
		return dao.getAbortVoteCount(actInstId, nodeId);
	}

	public void batchUpdateCompleted(String actInstId, String nodeId) {
		dao.batchUpdateCompleted(actInstId, nodeId);
	}

	public void addSign(String userIds, String taskId) {
		if (StringUtil.isEmpty(userIds))
			return;

		TaskEntity taskEntity = bpmService.getTask(taskId);
		ExecutionEntity executionEntity = bpmService.getExecution(taskEntity.getExecutionId());

		String processInstanceId = executionEntity.getProcessInstanceId();

		ProcessDefinitionEntity proDefEntity = bpmService.getProcessDefinitionByProcessInanceId(processInstanceId);

		ActivityImpl actImpl = proDefEntity.findActivity(executionEntity.getActivityId());

		String multiInstance = (String) actImpl.getProperty("multiInstance");

		if (multiInstance == null)
			return;

		Integer maxSignNums = dao.getMaxSignNums(processInstanceId, executionEntity.getActivityId(), TaskSignData.NOT_COMPLETED);
		log.debug("multiInstance:" + multiInstance);

		Integer signNums = Integer.valueOf(maxSignNums.intValue() == 0 ? 1 : maxSignNums.intValue());

		List uidlist = new ArrayList();

		List<TaskSignData> existTaskSignDatas = dao.getByNodeAndInstanceId(processInstanceId, taskEntity.getTaskDefinitionKey());
		for (TaskSignData taskSignData : existTaskSignDatas) {
			uidlist.add(taskSignData.getVoteUserId());
		}

		String[] uIds = userIds.split("[,]");

		List addUsers = getCanAddUsers(uidlist, uIds);

		int userAmount = addUsers.size();

		Integer nrOfInstances = (Integer) runtimeService.getVariable(executionEntity.getId(), "nrOfInstances");
		if (nrOfInstances != null) {
			runtimeService.setVariable(executionEntity.getId(), "nrOfInstances", Integer.valueOf(nrOfInstances.intValue() + userAmount));
		}

		if ("sequential".equals(multiInstance)) {
			for (int i = 0; i < userAmount; i++) {
				Long userId = (Long) addUsers.get(i);
				int sn = signNums.intValue() + 1;
				addSignData("", executionEntity.getActivityId(), taskEntity.getName(), processInstanceId, Integer.valueOf(sn), userId);
			}
		} else {
			Integer loopCounter = (Integer) runtimeService.getVariable(executionEntity.getId(), "loopCounter");
			for (int i = 0; i < userAmount; i++) {
				Long userId = (Long) addUsers.get(i);
				ProcessTask newProcessTask = bpmService.newTask(taskId, uIds[i]);
				String executionId = newProcessTask.getExecutionId();

				runtimeService.setVariable(executionId, "loopCounter", Integer.valueOf(loopCounter.intValue() + i + 1));
				runtimeService.setVariable(executionId, "assignee", userId.toString());

				int sn = signNums.intValue() + 1;
				addSignData(newProcessTask.getId(), executionEntity.getActivityId(), taskEntity.getName(), processInstanceId, Integer.valueOf(sn),
						userId);
			}
		}

		ProcessRun processRun = processRunService.getByActInstanceId(processInstanceId);
		String memo = "用户在任务[" + taskEntity.getName() + "]执行了补签操作。";
		bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_SIGN, memo);
	}

	private void addSignData(String taskId, String nodeId, String nodeName, String instanceId, Integer signNums, Long userId) {
		Long dataId = null;
		try {
			dataId = Long.valueOf(UniqueIdUtil.genId());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		TaskSignData newSignData = new TaskSignData();
		newSignData.setTaskId(taskId);

		newSignData.setDataId(dataId);
		newSignData.setNodeId(nodeId);
		newSignData.setNodeName(nodeName);
		newSignData.setActInstId(instanceId);
		newSignData.setSignNums(signNums);
		newSignData.setIsCompleted(TaskSignData.NOT_COMPLETED);
		newSignData.setIsAgree(null);
		newSignData.setContent(null);
		newSignData.setVoteTime(null);
		newSignData.setVoteUserId(userId);

		SysUser sysUser = sysUserDao.getById(userId);
		newSignData.setVoteUserName(sysUser.getFullname());

		dao.save(newSignData);
	}

	private List<Long> getCanAddUsers(List<Long> curUserList, String[] addUsers) {
		List users = new ArrayList();
		for (String userId : addUsers) {
			Long lUserId = new Long(userId);
			if (!curUserList.contains(lUserId)) {
				users.add(lUserId);
			}
		}
		return users;
	}

	public TaskSignData getUserTaskSign(String actInstId, String nodeId, Long executorId) {
		return dao.getUserTaskSign(actInstId, nodeId, executorId);
	}

	public void save(TaskSignData signData) {
		dao.save(signData);
	}

	public void update(TaskSignData taskSignData) {
		dao.update(taskSignData);
	}
}