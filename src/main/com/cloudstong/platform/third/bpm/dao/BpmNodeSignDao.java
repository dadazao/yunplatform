package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeSign;

@Repository
public class BpmNodeSignDao extends BaseMyBatisDaoImpl<BpmNodeSign, Long> {
	public Class getEntityClass() {
		return BpmNodeSign.class;
	}

	public BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		BpmNodeSign model = (BpmNodeSign) getUnique("getByDefIdAndNodeId", map);
		return model;
	}

	public List<BpmNodeSign> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	public void delActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}