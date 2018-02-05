package com.cloudstong.platform.third.bpm.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.model.TaskSignData;
import com.cloudstong.platform.third.bpm.service.BpmProStatusService;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;
import com.cloudstong.platform.third.bpm.service.TaskSignDataService;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class TaskSignCreateListener extends BaseTaskListener {
	private Logger logger = LoggerFactory.getLogger(TaskSignCreateListener.class);

	private TaskSignDataService taskSignDataService = (TaskSignDataService) AppUtil.getBean(TaskSignDataService.class);
	private TaskOpinionService taskOpinionService = (TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);
	private TaskUserAssignService taskUserAssignService = (TaskUserAssignService) AppUtil.getBean(TaskUserAssignService.class);
	private BpmProStatusService bpmProStatusService = (BpmProStatusService) AppUtil.getBean(BpmProStatusService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		TaskOpinion taskOpinion = new TaskOpinion(delegateTask);
		taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
		taskOpinionService.save(taskOpinion);

		TaskExecutor taskExecutor = (TaskExecutor) delegateTask.getVariable("assignee");

		assignTaskExecutor(delegateTask, taskExecutor);

		TaskThreadService.addTask((TaskEntity) delegateTask);

		String processInstanceId = delegateTask.getProcessInstanceId();

		logger.debug("enter the signuser listener notify method, taskId:" + delegateTask.getId() + " assignee:" + delegateTask.getAssignee());

		Integer instanceOfNumbers = (Integer) delegateTask.getVariable("nrOfInstances");
		Integer loopCounter = (Integer) delegateTask.getVariable("loopCounter");

		if (loopCounter == null)
			loopCounter = Integer.valueOf(0);

		logger.debug("instance of numbers:" + instanceOfNumbers + " loopCounters:" + loopCounter);

		if (loopCounter.intValue() == 0) {
			addSignData(delegateTask, nodeId, processInstanceId, instanceOfNumbers);
		}

		bpmProStatusService.addOrUpd(actDefId, new Long(processInstanceId), nodeId);

		updTaskSignData(processInstanceId, nodeId, taskExecutor, delegateTask.getId());
	}

	private void updTaskSignData(String processInstanceId, String nodeId, TaskExecutor taskExecutor, String taskId) {
		String executorId = taskExecutor.getExecuteId();
		TaskSignData taskSignData = taskSignDataService.getUserTaskSign(processInstanceId, nodeId, new Long(executorId));
		taskSignData.setTaskId(taskId);
		taskSignDataService.update(taskSignData);
	}

	private void addSignData(DelegateTask delegateTask, String nodeId, String processInstanceId, Integer instanceOfNumbers) {
		List signUserList = taskUserAssignService.getExecutors();

		for (int i = 0; i < instanceOfNumbers.intValue(); i++) {
			int sn = i + 1;

			TaskSignData signData = new TaskSignData();
			signData.setActInstId(processInstanceId);

			signData.setNodeName(delegateTask.getName());
			signData.setNodeId(nodeId);
			signData.setSignNums(Integer.valueOf(sn));
			signData.setIsCompleted(TaskSignData.NOT_COMPLETED);

			TaskExecutor signUser = (TaskExecutor) signUserList.get(i);
			if (signUser != null) {
				signData.setVoteUserId(new Long(signUser.getExecuteId()));
				signData.setVoteUserName(signUser.getExecutor());
			}
			signData.setDataId(Long.valueOf(UniqueIdUtil.genId()));

			taskSignDataService.save(signData);
		}
	}

	private void assignTaskExecutor(DelegateTask delegateTask, TaskExecutor taskExecutor) {
		if ("user".equals(taskExecutor.getType())) {
			delegateTask.setAssignee(taskExecutor.getExecuteId());
		} else
			delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
	}

	protected int getScriptType() {
		return BpmConst.StartScript.intValue();
	}
}