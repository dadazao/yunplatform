package com.cloudstong.platform.third.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;

@Service
public class JumpRule {
	public static final String RULE_INVALID = "_RULE_INVALID";
	private Logger logger = LoggerFactory.getLogger(JumpRule.class);

	@Resource
	private BpmNodeRuleService bpmNodeRuleService;

	@Resource
	GroovyScriptEngine scriptEngine;

	@Resource
	RuntimeService runtimeService;

	public String evaluate(ExecutionEntity execution, Short isJumpForDef) {
		logger.debug("enter the rule decision");

		String activityId = execution.getActivityId();
		String actDefId = execution.getProcessDefinitionId();

		List<BpmNodeRule> ruleList = bpmNodeRuleService.getByDefIdNodeId(actDefId, activityId);

		Map vars = new HashMap();
		vars.putAll(execution.getVariables());

		if (BeanUtils.isEmpty(ruleList)) {
			return "";
		}

		for (BpmNodeRule nodeRule : ruleList) {
			try {
				Boolean rtn = Boolean.valueOf(scriptEngine.executeBoolean(nodeRule.getConditionCode(), vars));

				if (rtn != null) {
					if (rtn.booleanValue()) {
						logger.debug("the last rule decision is " + nodeRule.getTargetNode());
						return nodeRule.getTargetNode();
					}
				} else {
					logger.debug("条件表达式返回为空，请使用return 语句返回!");
				}

			} catch (Exception ex) {
				logger.debug("error message: " + ex.getMessage());
			}
		}
		if ((ruleList.size() > 0) && (BpmNodeSet.RULE_INVALID_NO_NORMAL.equals(isJumpForDef)))
			return "_RULE_INVALID";
		return "";
	}
}