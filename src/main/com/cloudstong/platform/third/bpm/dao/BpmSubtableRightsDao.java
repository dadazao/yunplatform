package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmSubtableRights;

@Repository
public class BpmSubtableRightsDao extends BaseMyBatisDaoImpl<BpmSubtableRights, Long> {
	public Class<?> getEntityClass() {
		return BpmSubtableRights.class;
	}

	public BpmSubtableRights getByDefIdAndNodeId(String actDefId, String nodeId, Long tableId) {
		Map map = new HashMap();
		map.put("actdefid", actDefId);
		map.put("nodeid", nodeId);
		map.put("tableid", tableId);
		BpmSubtableRights model = (BpmSubtableRights) getUnique("getByDefIdAndNodeId", map);
		return model;
	}
}