package com.cloudstong.platform.third.bpm.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.TaskDao;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class EndEventListener extends BaseNodeEventListener {
	protected void execute(DelegateExecution execution, String actDefId, String nodeId) {
		ExecutionEntity ent = (ExecutionEntity) execution;
		if (!ent.isEnded())
			return;

		if (ent.getId().equals(ent.getProcessInstanceId())) {
			handEnd(ent);
		}

		if (ent.getActivity() != null) {
			String type = (String) ent.getActivity().getProperties().get("type");
			if ("subProcess".equals(type))
				TaskUserAssignService.clearFormUsers();
		}
	}

	private void handEnd(ExecutionEntity ent) {
		if (ent.getParentId() == null) {
			updProcessRunStatus(ent);

			delNotifyTask(ent);

			ExecutionDao executionDao = (ExecutionDao) AppUtil.getBean("executionDao");
			executionDao.delVariableByProcInstId(new Long(ent.getId()));
			executionDao.delSubExecutionByProcInstId(new Long(ent.getId()));
		}
	}

	private void delNotifyTask(ExecutionEntity ent) {
		Long instanceId = new Long(ent.getProcessInstanceId());
		TaskDao taskDao = (TaskDao) AppUtil.getBean("taskDao");

		taskDao.delCandidateByInstanceId(instanceId);

		taskDao.delByInstanceId(instanceId);
	}

	private void updProcessRunStatus(ExecutionEntity ent) {
		ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean("processRunService");
		ProcessRun processRun = processRunService.getByActInstanceId(ent.getProcessInstanceId());
		if (BeanUtils.isEmpty(processRun))
			return;

		processRun.setStatus(ProcessRun.STATUS_FINISH);
		processRunService.update(processRun);
	}

	protected Integer getScriptType() {
		return BpmConst.EndScript;
	}
}