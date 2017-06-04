package com.cloudstong.platform.third.bpm.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.third.bpm.service.BpmProStatusService;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class CallSubProcessStartListener extends BaseNodeEventListener {
	protected void execute(DelegateExecution execution, String actDefId, String nodeId) {
		Long processInstanceId = new Long(execution.getProcessInstanceId());
		Map flowVars = TaskThreadService.getVariables();
		if (execution.getVariable("outPassVars") == null) {
			execution.setVariable("outPassVars", flowVars);
			TaskThreadService.clearVariables();
		}
		Integer completeInstance = (Integer) execution.getVariable("nrOfCompletedInstances");

		if (completeInstance == null) {
			BpmProStatusService bpmProStatusService = (BpmProStatusService) AppUtil.getBean(BpmProStatusService.class);
			TaskThreadService.clearNewTasks();
			TaskThreadService.cleanExtSubProcess();
			bpmProStatusService.addOrUpd(actDefId, processInstanceId, nodeId);
		}
	}

	protected Integer getScriptType() {
		return BpmConst.StartScript;
	}
}