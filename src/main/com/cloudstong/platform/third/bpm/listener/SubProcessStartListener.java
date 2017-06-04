package com.cloudstong.platform.third.bpm.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.service.BpmNodeScriptService;
import com.cloudstong.platform.third.bpm.util.BpmConst;

public class SubProcessStartListener implements ExecutionListener {
	public void notify(DelegateExecution execution) throws Exception {
		Integer loopCounter = (Integer) execution.getVariable("loopCounter");

		if ((loopCounter == null) || (loopCounter.intValue() == 0)) {
			String actDefId = execution.getProcessDefinitionId();
			String nodeId = execution.getCurrentActivityId();
			exeEventScript(execution, BpmConst.StartScript.intValue(), actDefId, nodeId);
		}
	}

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
	}
}