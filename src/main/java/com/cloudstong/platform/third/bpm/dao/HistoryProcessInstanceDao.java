package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

@Repository
public class HistoryProcessInstanceDao extends BaseMyBatisDaoImpl<HistoricProcessInstanceEntity, Long> {
	public Class getEntityClass() {
		return HistoricProcessInstanceEntity.class;
	}

	public HistoricProcessInstanceEntity getByInstanceIdAndNodeId(String actInstanceId, String nodeId) {
		Map map = new HashMap();
		map.put("actInstanceId", actInstanceId);
		map.put("nodeId", nodeId);
		return (HistoricProcessInstanceEntity) getUnique("getByInstanceIdAndNodeId", map);
	}
}