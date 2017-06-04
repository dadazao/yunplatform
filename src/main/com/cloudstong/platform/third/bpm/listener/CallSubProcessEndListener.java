package com.cloudstong.platform.third.bpm.listener;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmConst;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

public class CallSubProcessEndListener extends BaseNodeEventListener {
	private BpmProStatusDao bpmProStatusDao = (BpmProStatusDao) AppUtil.getBean(BpmProStatusDao.class);

	private RuntimeService runtimeService = (RuntimeService) AppUtil.getBean(RuntimeService.class);

	protected void execute(DelegateExecution execution, String actDefId, String nodeId) {
		Long processInstanceId = new Long(execution.getProcessInstanceId());
		ExecutionEntity ent = (ExecutionEntity) execution;
		String varName = ent.getActivityId() + "_" + "subExtAssignIds";

		boolean rtn = BpmUtil.isMuiltiExcetion(ent);
		if (rtn) {
			int completeInstance = ((Integer) execution.getVariable("nrOfCompletedInstances")).intValue();
			int nrOfInstances = ((Integer) execution.getVariable("nrOfInstances")).intValue();
			if (completeInstance == nrOfInstances) {
				bpmProStatusDao.updStatus(processInstanceId, nodeId, TaskOpinion.STATUS_AGREE);
				execution.removeVariable(varName);

				deleteInstanceBySupperInstanceId(processInstanceId);
			}
		} else {
			bpmProStatusDao.updStatus(processInstanceId, nodeId, TaskOpinion.STATUS_AGREE);
			execution.removeVariable(varName);
			deleteInstanceBySupperInstanceId(processInstanceId);
		}

		if (ent.getActivity() != null) {
			String type = (String) ent.getActivity().getProperties().get("type");
			if ("subProcess".equals(type))
				TaskUserAssignService.clearFormUsers();
		}
	}

	private void deleteInstanceBySupperInstanceId(Long processInstanceId) {
		List<ProcessInstance> subInstances = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstanceId.toString()).list();
		for (ProcessInstance instance : subInstances)
			runtimeService.deleteProcessInstance(instance.getProcessInstanceId(), "结束子流程");
	}

	protected Integer getScriptType() {
		return BpmConst.EndScript;
	}
}