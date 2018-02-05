package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmFormRun;

@Repository
public class BpmFormRunDao extends BaseMyBatisDaoImpl<BpmFormRun, Long> {
	public Class getEntityClass() {
		return BpmFormRun.class;
	}

	public BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId) {
		Map params = new HashMap();
		params.put("actInstanceId", actInstanceId);
		params.put("actNodeId", actNodeId);
		return (BpmFormRun) getUnique("getByInstanceAndNode", params);
	}

	public List<BpmFormRun> getByInstanceId(String actInstanceId) {
		return getBySqlKey("getByInstanceId", actInstanceId);
	}

	public BpmFormRun getGlobalForm(String actInstanceId) {
		return (BpmFormRun) getUnique("getGlobalForm", actInstanceId);
	}

	public void delByInstanceId(String actInstanceId) {
		delBySqlKey("delByInstanceId", actInstanceId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}