package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.TaskOpinionDao;
import com.cloudstong.platform.third.bpm.model.BpmFinishTask;
import com.cloudstong.platform.third.bpm.model.ProcessExecution;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;

@Service
public class TaskOpinionService {

	@Resource
	private TaskOpinionDao dao;

	@Resource
	private ExecutionDao executionDao;

	public TaskOpinion getByTaskId(Long taskId) {
		return dao.getByTaskId(taskId);
	}

	public List<TaskOpinion> getByActInstId(String actInstId) {
		List actInstIds = new ArrayList();
		putRelateActInstIdIntoList(actInstIds, actInstId);
		return dao.getByActInstId((String[]) actInstIds.toArray(new String[actInstIds.size()]));
	}

	private void putRelateActInstIdIntoList(List<String> actInstIds, String actInstId) {
		actInstIds.add(actInstId);
		ProcessExecution execution = (ProcessExecution) executionDao.getById(actInstId);
		if ((execution != null) && (!StringUtil.isEmpty(execution.getSuperExecutionId()))) {
			ProcessExecution superExecution = (ProcessExecution) executionDao.getById(execution.getSuperExecutionId());
			if (superExecution != null)
				putRelateActInstIdIntoList(actInstIds, superExecution.getProcessInstanceId());
		}
	}

	public List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey) {
		return dao.getByActInstIdTaskKey(actInstId, taskKey);
	}

	public TaskOpinion getLatestTaskOpinion(String actInstId, String taskKey) {
		List list = getByActInstIdTaskKey(actInstId, taskKey);
		if ((list != null) && (list.size() > 0)) {
			return (TaskOpinion) list.get(0);
		}
		return null;
	}

	public TaskOpinion getLatestUserOpinion(String actInstId, Long exeUserId) {
		List taskOpinions = dao.getByActInstIdExeUserId(actInstId, exeUserId);
		if (taskOpinions.size() == 0)
			return null;
		return (TaskOpinion) taskOpinions.get(0);
	}

	public void delByTaskId(Long taskId) {
		dao.delByTaskId(taskId);
	}

	public List<BpmFinishTask> getByFinishTask(Long userId, String subject, String taskName, QueryCriteria pb) {
		return dao.getByFinishTask(userId, subject, taskName, pb);
	}

	public void update(TaskOpinion taskOpinion) {
		dao.update(taskOpinion);
	}

	public void save(TaskOpinion taskOpinion) {
		dao.save(taskOpinion);
	}
}