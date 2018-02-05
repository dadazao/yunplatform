package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;

@Repository
public class BpmNodeScriptDao extends BaseMyBatisDaoImpl<BpmNodeScript, Long> {
	public Class getEntityClass() {
		return BpmNodeScript.class;
	}

	public List<BpmNodeScript> getByBpmNodeScriptId(String nodeId, String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		return getBySqlKey("getBpmNodeScript", params);
	}

	public List<BpmNodeScript> getByActDefId(String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);

		return getBySqlKey("getBpmNodeScript", params);
	}

	public void delByDefAndNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		update("delByDefAndNodeId", params);
	}

	public BpmNodeScript getScriptByType(String nodeId, String actDefId, Integer scriptType) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		params.put("scriptType", scriptType);
		return (BpmNodeScript) getUnique("getScriptByType", params);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}