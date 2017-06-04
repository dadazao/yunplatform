package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.Message;

@Repository
public class MessageDao extends BaseMyBatisDaoImpl<Message, Long> {
	public Class getEntityClass() {
		return Message.class;
	}

	public List<Message> getListByActDefIdNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		return getBySqlKey("getListByActDefIdNodeId", params);
	}

	public void delByActdefidAndNodeid(String actdefId, String nodeId) {
		Map params = new HashMap();
		params.put("actdefId", actdefId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByMessageId", params);
	}

	public List<Message> getByActDefId(String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		return getBySqlKey("getByActDefId", params);
	}
}