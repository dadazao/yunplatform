package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeWebService;

@Repository
public class BpmNodeWebServiceDao extends BaseMyBatisDaoImpl<BpmNodeWebService, Long> {
	public Class<?> getEntityClass() {
		return BpmNodeWebService.class;
	}

	public BpmNodeWebService getByNodeIdActDefId(String nodeId, String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		return (BpmNodeWebService) getUnique("getBpmNodeWebService", params);
	}

	public void deleteByNodeIdActDefId(String nodeId, String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		update("deleteByNodeIdActDefId", params);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}