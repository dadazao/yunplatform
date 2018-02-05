package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

@Repository
public class HistoryTaskInstanceDao extends BaseMyBatisDaoImpl<HistoricTaskInstanceEntity, Long> {
	public Class getEntityClass() {
		return HistoricTaskInstanceEntity.class;
	}

	public HistoricTaskInstanceEntity getByInstanceIdAndNodeId(String actInstanceId, String nodeId) {
		Map map = new HashMap();
		map.put("actInstanceId", actInstanceId);
		map.put("nodeId", nodeId);
		return (HistoricTaskInstanceEntity) getUnique("getByInstanceIdAndNodeId", map);
	}
}