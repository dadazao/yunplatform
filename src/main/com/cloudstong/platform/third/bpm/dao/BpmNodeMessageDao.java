package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeMessage;

@Repository
public class BpmNodeMessageDao extends BaseMyBatisDaoImpl<BpmNodeMessage, Long> {
	public Class getEntityClass() {
		return BpmNodeMessage.class;
	}

	public List<BpmNodeMessage> getMessageByActDefIdNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		return getBySqlKey("getMessageByActDefIdNodeId", params);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}

	public void delByActDefIdAndNodeId(String actDefId, String nodeId) {
		Map param = new HashMap();
		param.put("actDefId", actDefId);
		param.put("nodeId", nodeId);
		delBySqlKey("delByActDefIdAndNodeId", param);
	}

	public List<BpmNodeMessage> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}
}