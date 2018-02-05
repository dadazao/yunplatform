package com.cloudstong.platform.third.bpm.listener;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.BpmFormRunService;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class StartEventListener extends BaseNodeEventListener {
	private static final Log logger = LogFactory.getLog(StartEventListener.class);

	protected void execute(DelegateExecution execution, String actDefId, String nodeId) {
		logger.debug("nodeId" + nodeId);
		handExtSubProcess(execution);
	}

	private void handExtSubProcess(DelegateExecution execution) {
		ExecutionEntity ent = (ExecutionEntity) execution;

		if (execution.getVariable("innerPassVars") == null)
			return;

		BpmFormRunService bpmFormRunService = (BpmFormRunService) AppUtil.getBean(BpmFormRunService.class);

		Map variables = (Map) ent.getVariable("innerPassVars");

		Boolean isExtCall = (Boolean) variables.get("isExtCall");
		String businessKey = (String) variables.get("businessKey");

		String instanceId = ent.getProcessInstanceId();

		TaskThreadService.addExtSubProcess(instanceId);

		String actDefId = ent.getProcessDefinitionId();

		long runId = initProcessRun(actDefId, instanceId, variables).longValue();

		execution.setVariable("flowRunId", Long.valueOf(runId));
		execution.setVariable("businessKey", businessKey);
		String subject = (String) variables.get("subject_");
		execution.setVariable("subject_", subject);

		if ((isExtCall != null) && (!isExtCall.booleanValue()))
			bpmFormRunService.addFormRun(actDefId, Long.valueOf(runId), instanceId);
	}

	private Long initProcessRun(String actDefId, String instanceId, Map<String, Object> variables) {
		String businessKey = (String) variables.get("businessKey");
		Long parentRunId = (Long) variables.get("flowRunId");

		BpmDefinitionDao bpmDefinitionDao = (BpmDefinitionDao) AppUtil.getBean(BpmDefinitionDao.class);
		ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean(ProcessRunService.class);
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);

		ProcessRun processRun = new ProcessRun();
		SysUser curUser = AppContext.getCurrentUser();
		processRun.setCreator(curUser.getFullname());
		processRun.setCreatorId(curUser.getId());

		processRun.setActDefId(bpmDefinition.getActDefId());
		processRun.setDefId(bpmDefinition.getDefId());
		processRun.setProcessName(bpmDefinition.getSubject());

		processRun.setActInstId(instanceId);

		String subject = (String) variables.get("subject_");
		processRun.setSubject(subject);
		processRun.setBusinessKey(businessKey);

		processRun.setCreatetime(new Date());
		processRun.setStatus(ProcessRun.STATUS_RUNNING);
		processRun.setRunId(Long.valueOf(UniqueIdUtil.genId()));
		processRun.setParentId(parentRunId);

		processRunService.add(processRun);

		return processRun.getRunId();
	}

	protected Integer getScriptType() {
		return BpmConst.StartScript;
	}
}