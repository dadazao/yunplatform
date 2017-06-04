package com.cloudstong.platform.third.bpm.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.service.BpmNodeScriptService;

public abstract class BaseNodeEventListener implements ExecutionListener {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("enter the node event listener.." + execution.getId());

		ExecutionEntity ent = (ExecutionEntity) execution;

		String actDefId = ent.getProcessDefinitionId();
		String nodeId = ent.getActivityId();

		execute(execution, actDefId, nodeId);

		if (nodeId != null) {
			Integer scriptType = getScriptType();

			exeEventScript(execution, scriptType.intValue(), actDefId, nodeId);
		}
	}

	protected abstract void execute(DelegateExecution paramDelegateExecution, String paramString1, String paramString2);

	protected abstract Integer getScriptType();

	private void exeEventScript(DelegateExecution execution, int scriptType, String actDefId, String nodeId) {
		BpmNodeScriptService bpmNodeScriptService = (BpmNodeScriptService) AppUtil.getBean("bpmNodeScriptService");
		BpmNodeScript model = bpmNodeScriptService.getScriptByType(nodeId, actDefId, Integer.valueOf(scriptType));
		if (model == null)
			return;

		String script = model.getScript();
		if (StringUtil.isEmpty(script))
			return;

		GroovyScriptEngine scriptEngine = (GroovyScriptEngine) AppUtil.getBean("scriptEngine");
		Map vars = execution.getVariables();
		vars.put("execution", execution);

		scriptEngine.execute(script, vars);

		logger.debug("execution script :" + script);
	}
}