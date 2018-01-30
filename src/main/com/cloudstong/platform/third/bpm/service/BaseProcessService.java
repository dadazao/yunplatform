package com.cloudstong.platform.third.bpm.service;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.ServiceImpl;
import org.springframework.beans.factory.InitializingBean;

public class BaseProcessService extends ServiceImpl implements InitializingBean {

	@Resource
	ProcessEngine processEngine;

	public void afterPropertiesSet() throws Exception {
		ProcessEngineImpl engine = (ProcessEngineImpl) processEngine;
		setCommandExecutor(engine.getProcessEngineConfiguration().getCommandExecutor());
	}
}