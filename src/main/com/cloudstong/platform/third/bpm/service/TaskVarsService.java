package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.third.bpm.dao.TaskVarsDao;
import com.cloudstong.platform.third.bpm.model.TaskVars;

@Service
public class TaskVarsService {

	@Resource
	private TaskVarsDao dao;

	public List<TaskVars> getVars(QueryCriteria queryCriteria) {
		return dao.getTaskVars(queryCriteria);
	}
}