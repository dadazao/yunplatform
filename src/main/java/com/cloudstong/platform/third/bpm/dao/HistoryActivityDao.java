package com.cloudstong.platform.third.bpm.dao;

import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

@Repository
public class HistoryActivityDao extends BaseMyBatisDaoImpl<HistoricActivityInstanceEntity, Long> {
	public Class getEntityClass() {
		return HistoricActivityInstanceEntity.class;
	}
}