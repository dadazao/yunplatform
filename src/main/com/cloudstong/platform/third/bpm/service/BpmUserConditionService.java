package com.cloudstong.platform.third.bpm.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.third.bpm.dao.BpmUserConditionDao;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;

@Service
public class BpmUserConditionService {

	@Resource
	private BpmUserConditionDao dao;

	public List<BpmUserCondition> getBySetId(Long setId) {
		return dao.getBySetId(setId);
	}

	public List<BpmUserCondition> getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	public BpmUserCondition getById(Long conditionId) {
		return (BpmUserCondition)dao.getById(conditionId);
	}

	public void add(BpmUserCondition bpmUserCondition) {
		dao.save(bpmUserCondition);
	}

	public void update(BpmUserCondition bpmUserCondition) {
		dao.update(bpmUserCondition);
	}

	public void delByIds(Long[] ids) {
		if (BeanUtils.isEmpty(ids)) return;
	    for (Long id : ids){
	      dao.delById(id);
	    }
	}

	public List getAll() {
		return dao.getAll();
	}

}