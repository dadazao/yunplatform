package com.cloudstong.platform.third.bpm.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang.StringUtils;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionStackDao;
import com.cloudstong.platform.third.bpm.dao.TaskDao;
import com.cloudstong.platform.third.bpm.model.ExecutionStack;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.TaskFork;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.util.BpmConst;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

public class TaskCompleteListener extends BaseTaskListener {
	TaskOpinionService taskOpinionService = (TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);
	BpmProStatusDao bpmProStatusDao = (BpmProStatusDao) AppUtil.getBean(BpmProStatusDao.class);
	ExecutionStackDao executionStackDao = (ExecutionStackDao) AppUtil.getBean(ExecutionStackDao.class);
	TaskDao taskDao = (TaskDao) AppUtil.getBean(TaskDao.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		String taskAssignee = delegateTask.getAssignee();

		setPreUser(taskAssignee);

		String token = (String) delegateTask.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
		if (token != null) {
			TaskThreadService.setToken(token);
		}

		ProcessCmd processCmd = TaskThreadService.getProcessCmd();
		if ((processCmd != null) && ((processCmd.isBack().intValue() > 0) || (StringUtils.isNotEmpty(processCmd.getDestTask())))) {
			taskDao.updateNewTaskDefKeyByInstIdNodeId(delegateTask.getTaskDefinitionKey() + "_1", delegateTask.getTaskDefinitionKey(),
					delegateTask.getProcessInstanceId());
		}

		updateExecutionStack(delegateTask.getProcessInstanceId(), delegateTask.getTaskDefinitionKey(), token);

		updTaskOpinion(delegateTask);

		updNodeStatus(nodeId, delegateTask);
	}

	private void updateExecutionStack(String instanceId, String nodeId, String token) {
		ExecutionStack executionStack = executionStackDao.getLastestStack(instanceId, nodeId, token);
		if (executionStack != null) {
			SysUser curUser = AppContext.getCurrentUser();

			executionStack.setAssignees(curUser.getId().toString());

			executionStack.setEndTime(new Date());
			executionStackDao.update(executionStack);
		}
	}

	private void updNodeStatus(String nodeId, DelegateTask delegateTask) {
		boolean isMuliti = BpmUtil.isMultiTask(delegateTask);

		if (!isMuliti) {
			String actInstanceId = delegateTask.getProcessInstanceId();

			Short approvalStatus = (Short) delegateTask.getVariable("approvalStatus_" + delegateTask.getTaskDefinitionKey());
			if (approvalStatus == null) {
				approvalStatus = (Short) TaskThreadService.getVariables().get("approvalStatus_" + delegateTask.getTaskDefinitionKey());
			}
			bpmProStatusDao.updStatus(new Long(actInstanceId), nodeId, approvalStatus);
		}
	}

	private void updTaskOpinion(DelegateTask delegateTask) {
		TaskOpinion taskOpinion = taskOpinionService.getByTaskId(new Long(delegateTask.getId()));

		if (taskOpinion == null)
			return;

		String nodeId = delegateTask.getTaskDefinitionKey();

		Short approvalStatus = (Short) delegateTask.getVariable("approvalStatus_" + nodeId);
		String approvalContent = (String) delegateTask.getVariable("approvalContent_" + nodeId);

		SysUser sysUser = AppContext.getCurrentUser();
		Long userId = 1L;
		String userName = "系统";
		if (sysUser != null) {
			userId = sysUser.getId();
			userName = sysUser.getFullname();
		}

		taskOpinion.setExeUserId(userId);
		taskOpinion.setExeFullname(userName);
		if (approvalStatus == null)
			taskOpinion.setCheckStatus(TaskOpinion.STATUS_AGREE);
		else {
			taskOpinion.setCheckStatus(approvalStatus);
		}
		taskOpinion.setOpinion(approvalContent);
		taskOpinion.setEndTime(new Date());
		taskOpinion.setDurTime(Long.valueOf(taskOpinion.getEndTime().getTime() - taskOpinion.getStartTime().getTime()));

		taskOpinionService.update(taskOpinion);
	}

	private void setPreUser(String assignee) {
		TaskThreadService.cleanTaskUser();
		if (StringUtil.isNotEmpty(assignee))
			TaskThreadService.setPreTaskUser(assignee);
	}

	protected int getScriptType() {
		return BpmConst.EndScript.intValue();
	}
}