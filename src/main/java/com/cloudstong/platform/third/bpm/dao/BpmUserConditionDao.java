package com.cloudstong.platform.third.bpm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;

@Repository
public class BpmUserConditionDao extends BaseMyBatisDaoImpl<BpmUserCondition, Long> {
	public Class<?> getEntityClass() {
		return BpmUserCondition.class;
	}

	public List<BpmUserCondition> getBySetId(Long setId) {
		return getBySqlKey("getBySetId", setId);
	}

	public List<BpmUserCondition> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}