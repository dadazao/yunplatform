package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;

@Repository
public class BpmNodeRuleDao extends BaseMyBatisDaoImpl<BpmNodeRule, Long> {
	public Class getEntityClass() {
		return BpmNodeRule.class;
	}

	public List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		return getBySqlKey("getByDefIdNodeId", params);
	}

	public void reSort(Long ruleId, Long priority) {
		Map map = new HashMap();
		map.put("ruleId", ruleId);
		map.put("priority", priority);
		update("reSort", map);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}