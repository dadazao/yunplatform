package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmTaskAssigneeDao;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.model.BpmTaskAssignee;
import com.cloudstong.platform.third.bpm.model.ProcessRun;

@Service
public class BpmTaskAssigneeService {

	@Resource
	private BpmTaskAssigneeDao dao;

	@Resource
	private BpmService bpmService;

	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private ProcessRunDao processRunDao;

	@Resource
	private BpmRunLogService bpmRunLogService;

	public List<BpmTaskAssignee> getAllMyTask(QueryCriteria queryFilter) {
		return dao.getAllMyTask(queryFilter);
	}

	public int getCountByTaskId(String taskId) {
		return dao.getCountByTaskId(taskId);
	}

	public List<BpmTaskAssignee> getByTaskId(String taskId) {
		return dao.getByTaskId(taskId);
	}

	public void delByTaskId(String taskId) {
		dao.delByTaskId(taskId);
	}

	public void back(Long id, SysUser sysUser) {
		BpmTaskAssignee taskAssignee = (BpmTaskAssignee) dao.getById(id);
		String taskId = taskAssignee.getTaskId();
		Long runId = taskAssignee.getRunId();
		bpmService.updateTaskAssignee(taskId, sysUser.getId().toString());

		dao.delById(id);

		ProcessRun processRun = (ProcessRun) processRunDao.getById(runId);
		String userName = sysUser.getFullname();
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String memo = "流程:" + processRun.getSubject() + ", 【" + userName + "】将任务【" + taskEntity.getName() + "】 收回";
		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_BACK, memo);
	}
}