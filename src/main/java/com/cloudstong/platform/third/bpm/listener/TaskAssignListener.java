package com.cloudstong.platform.third.bpm.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class TaskAssignListener extends BaseTaskListener {
	private TaskOpinionService taskOpinionService = (TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		String userId = delegateTask.getAssignee();
		logger.debug("任务ID:" + delegateTask.getId());
		TaskOpinion taskOpinion = taskOpinionService.getByTaskId(new Long(delegateTask.getId()));
		if (taskOpinion != null) {
			logger.debug("update taskopinion exe userId" + userId);

			taskOpinion.setExeUserId(Long.valueOf(Long.parseLong(userId)));
			taskOpinionService.update(taskOpinion);
		}

		delegateTask.setOwner(userId);
	}

	protected int getScriptType() {
		return BpmConst.AssignScript.intValue();
	}
}