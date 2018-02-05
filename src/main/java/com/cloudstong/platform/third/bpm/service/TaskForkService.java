package com.cloudstong.platform.third.bpm.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.dao.TaskForkDao;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.model.TaskFork;

@Service
public class TaskForkService {

	@Resource
	private TaskForkDao dao;

	@Resource
	private ProcessRunDao processRunDao;

	public Integer getMaxSn(String actInstId) {
		return dao.getMaxSn(actInstId);
	}

	public TaskFork newTaskFork(DelegateTask delegateTask, String joinTaskName, String joinTaskKey, Integer forkCount) {
		String token = (String) delegateTask.getVariableLocal(TaskFork.TAKEN_VAR_NAME);

		TaskFork taskFork = new TaskFork();
		taskFork.setTaskForkId(Long.valueOf(UniqueIdUtil.genId()));
		taskFork.setActInstId(delegateTask.getProcessInstanceId());
		taskFork.setForkTime(new Date());
		taskFork.setFininshCount(Integer.valueOf(0));
		taskFork.setForkCount(forkCount);
		taskFork.setForkTaskKey(delegateTask.getTaskDefinitionKey());
		taskFork.setForkTaskName(delegateTask.getName());
		taskFork.setJoinTaskKey(joinTaskKey);
		taskFork.setJoinTaskName(joinTaskName);

		Integer sn = Integer.valueOf(1);
		if (token == null) {
			taskFork.setForkTokenPre(TaskFork.TAKEN_PRE + "_");
		} else {
			String[] lines = token.split("[_]");
			if ((lines != null) && (lines.length > 1)) {
				sn = Integer.valueOf(lines.length - 1);
				String forkTokenPre = token.substring(0, token.lastIndexOf(lines[sn.intValue()]));
				taskFork.setForkTokenPre(forkTokenPre);
			}
		}

		String forkTokens = "";
		for (int i = 1; i <= forkCount.intValue(); i++) {
			forkTokens = forkTokens + taskFork.getForkTokenPre() + i + ",";
		}
		taskFork.setForkTokens(forkTokens);

		taskFork.setForkSn(sn);
		dao.save(taskFork);
		return taskFork;
	}

	public TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey) {
		return dao.getByInstIdJoinTaskKey(actInstId, joinTaskKey);
	}

	public TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId, String joinTaskKey, String forkToken) {
		return dao.getByInstIdJoinTaskKeyForkToken(actInstId, joinTaskKey, forkToken);
	}

	public void delByActDefId(String actDefId) {
		List<ProcessRun> prolist = processRunDao.getbyActDefId(actDefId);
		if (prolist.size() > 0)
			for (ProcessRun processRun : prolist)
				dao.delByActInstId(processRun.getActInstId());
	}
	
	public void update(TaskFork entity){
		dao.update(entity);
	}
}