package com.cloudstong.platform.third.bpm.service;

import java.util.Map;

import net.sf.json.JSONArray;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.cloudstong.platform.core.soap.SoapUtil;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.third.bpm.model.BpmNodeWebService;

public class WebServiceTask implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		ExecutionEntity ent = (ExecutionEntity) execution;
		String nodeId = ent.getActivityId();
		String actDefId = ent.getProcessDefinitionId();

		Map map = execution.getVariables();
		BpmNodeWebServiceService bpmNodeWebServiceService = (BpmNodeWebServiceService) AppUtil.getBean("bpmNodeWebServiceService");

		BpmNodeWebService bpmNodeWebService = bpmNodeWebServiceService.getByNodeIdActDefId(nodeId, actDefId);

		if (BeanUtils.isEmpty(bpmNodeWebService))
			return;
		String document = bpmNodeWebService.getDocument();

		JSONArray jArray = JSONArray.fromObject(document);
		if (jArray.size() == 0)
			return;

		SoapUtil.invoke(map, jArray);
		execution.setVariables(map);
	}
}