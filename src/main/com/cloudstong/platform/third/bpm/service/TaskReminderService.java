package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.TaskReminderDao;
import com.cloudstong.platform.third.bpm.model.TaskReminder;

@Service
public class TaskReminderService {

	@Resource
	private TaskReminderDao dao;

	public List<TaskReminder> getByActDefAndNodeId(String actDefId, String nodeId) {
		return dao.getByActDefAndNodeId(actDefId, nodeId);
	}

	public List<TaskReminder> getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	public boolean isExistByActDefAndNodeId(String actDefId, String nodeId) {
		Integer rtn = dao.isExistByActDefAndNodeId(actDefId, nodeId);
		return rtn.intValue() > 0;
	}

	public void add(TaskReminder taskReminder) {
		dao.save(taskReminder);
	}

	public void update(TaskReminder taskReminder) {
		dao.update(taskReminder);
	}
}