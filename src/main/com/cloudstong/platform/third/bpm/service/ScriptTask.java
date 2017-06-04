package com.cloudstong.platform.third.bpm.service;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.util.BpmConst;

@Service
public class ScriptTask implements JavaDelegate {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);

	public void execute(DelegateExecution execution) throws Exception {
		ExecutionEntity ent = (ExecutionEntity) execution;
		String nodeId = ent.getActivityId();
		String actDefId = ent.getProcessDefinitionId();

		BpmNodeScriptService bpmNodeScriptService = (BpmNodeScriptService) AppUtil.getBean("bpmNodeScriptService");

		BpmNodeScript model = bpmNodeScriptService.getScriptByType(nodeId, actDefId, BpmConst.ScriptNodeScript);
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