package com.cloudstong.platform.third.bpm.dao;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

@Repository
public class ExecutionExtDao extends BaseMyBatisDaoImpl<ExecutionEntity, String> {
	public Class getEntityClass() {
		return ExecutionEntity.class;
	}
}