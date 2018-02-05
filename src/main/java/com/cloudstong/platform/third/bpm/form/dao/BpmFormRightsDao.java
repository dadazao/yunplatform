package com.cloudstong.platform.third.bpm.form.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormRights;

@Repository
public class BpmFormRightsDao extends BaseMyBatisDaoImpl<BpmFormRights, Long> {
	public void delByFormDefId(Long formDefId) {
		delBySqlKey("delByFormDefId", formDefId);
	}

	public List<BpmFormRights> getByFormDefId(Long formDefId) {
		return getBySqlKey("getByFormDefId", formDefId);
	}

	public List<BpmFormRights> getByFlowFormNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		return getBySqlKey("getByFlowFormNodeId", params);
	}

	public void delByFlowFormNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByFlowFormNodeId", params);
	}

	public void deleteByTableId(Long tableId) {
		String statment = "deleteByTableId_" + getDbType();
		delBySqlKey(statment, tableId);
	}
}