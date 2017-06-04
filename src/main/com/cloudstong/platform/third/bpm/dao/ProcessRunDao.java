package com.cloudstong.platform.third.bpm.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.third.bpm.model.ProcessRun;

@Repository
public class ProcessRunDao extends BaseMyBatisDaoImpl<ProcessRun, Long> {
	public Class getEntityClass() {
		return ProcessRun.class;
	}

	public ProcessRun getById(Long primaryKey) {
		ProcessRun processRun = (ProcessRun) super.getById(primaryKey);
		if (processRun == null) {
			return getByIdHistory(primaryKey);
		}
		return processRun;
	}

	public ProcessRun getByActInstanceId(String processInstanceId) {
		ProcessRun processRun = (ProcessRun) getUnique("getByActInstanceId", processInstanceId);
		if (processRun == null) {
			return getByActInstanceIdHistory(processInstanceId);
		}
		return processRun;
	}

	public List<ProcessRun> getAllHistory(QueryCriteria queryCriteria) {
		return getBySqlKey("getAllFinish", queryCriteria);
	}

	public List<ProcessRun> getMyAttend(QueryCriteria queryCriteria) {
		return getBySqlKey("getMyAttend", queryCriteria);
	}

	public List<ProcessRun> getMyProcessRun(Long creatorId, String subject, Short status, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("creatorId", creatorId);
		params.put("subject", subject);
		params.put("status", status);
		return getBySqlKey("getMyProcessRun", params, queryCriteria);
	}

	public List<ProcessRun> getMyAttend(Long assignee, Short status, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("assignee", assignee);
		params.put("status", status);
		return getBySqlKey("getMyAttend", params, queryCriteria);
	}

	public List<ProcessRun> myStart(Long creatorId, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("creatorId", creatorId);
		return getBySqlKey("getAll", params, queryCriteria);
	}

	public List<ProcessRun> getByActDefId(String actDefId, QueryCriteria queryCriteria) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		return getBySqlKey("getAll", params, queryCriteria);
	}

	public void addHistory(ProcessRun entity) {
		String addStatement = getIbatisMapperNamespace() + ".addHistory";
		if ((entity instanceof EntityBase)) {
			EntityBase baseModel = entity;
			baseModel.setCreateDate(new Date());
			baseModel.setUpdateDate(new Date());

			Long curUserId = AppContext.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setCreateBy(curUserId);
				baseModel.setUpdateBy(curUserId);
			}
		}
		getSqlSessionTemplate().insert(addStatement, entity);
	}

	public int updateHistory(ProcessRun entity) {
		String updStatement = getIbatisMapperNamespace() + ".updateHistory";

		if ((entity instanceof EntityBase)) {
			EntityBase baseModel = entity;
			baseModel.setUpdateDate(new Date());

			Long curUserId = AppContext.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setUpdateBy(curUserId);
			}
		}

		int affectCount = getSqlSessionTemplate().update(updStatement, entity);
		return affectCount;
	}

	public int delByIdHistory(Long id) {
		String delStatement = getIbatisMapperNamespace() + ".delByIdHistory";
		int affectCount = getSqlSessionTemplate().delete(delStatement, id);
		return affectCount;
	}

	public ProcessRun getByIdHistory(Long primaryKey) {
		String getStatement = getIbatisMapperNamespace() + ".getByIdHistory";
		return (ProcessRun) getSqlSessionTemplate().selectOne(getStatement, primaryKey);
	}

	public ProcessRun getByActInstanceIdHistory(String actInstId) {
		return (ProcessRun) getUnique("getByActInstanceIdHistory", actInstId);
	}

	public void save(ProcessRun entity) {
		super.save(entity);
	}

	public List<ProcessRun> getbyActDefId(String actDefId) {
		return getBySqlKey("getbyActDefId", actDefId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}

	public void delHistroryByActDefId(String actDefId) {
		delBySqlKey("delHistroryByActDefId", actDefId);
	}

	public boolean isProcessInstanceExisted(String businessKey) {
		Integer rtn = (Integer) getOne("getByBusinessKeyWithActInstId", businessKey);
		return rtn.intValue() > 0;
	}
}