package com.cloudstong.platform.third.bpm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.cmd.EndProcessCmd;
import com.cloudstong.platform.third.bpm.cmd.GetExecutionCmd;
import com.cloudstong.platform.third.bpm.dao.BpmDefVarDao;
import com.cloudstong.platform.third.bpm.model.BpmDefVar;
import com.cloudstong.platform.third.bpm.service.BaseProcessService;
import com.cloudstong.platform.third.bpm.service.IBpmActService;

@Service
public class BpmActService extends BaseProcessService implements IBpmActService {

	@Resource
	private BpmDefVarDao dao;

	public List<BpmDefVar> getVarsByFlowDefId(Long defId) {
		return dao.getVarsByFlowDefId(defId);
	}

	public ExecutionEntity getExecution(String executionId) {
		return (ExecutionEntity) commandExecutor.execute(new GetExecutionCmd(executionId));
	}

	public void endProcessByTaskId(String taskId) {
		EndProcessCmd cmd = new EndProcessCmd(taskId);
		commandExecutor.execute(cmd);
	}
}