package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmDefVar;

@Repository
public class BpmDefVarDao extends BaseMyBatisDaoImpl<BpmDefVar, Long> {
	public Class getEntityClass() {
		return BpmDefVar.class;
	}

	public boolean isVarNameExist(String varName, String varKey, Long defId) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("varName", varName);
		params.put("varKey", varKey);
		Integer obj = (Integer) getOne("isVarNameExist", params);
		return obj.intValue() > 0;
	}

	public List<BpmDefVar> getByDeployAndNode(String deployId, String nodeId) {
		Map map = new HashMap();
		map.put("actDeployId", deployId);
		map.put("nodeId", nodeId);
		return getBySqlKey("getByDeployAndNode", map);
	}

	public List<BpmDefVar> getVarsByFlowDefId(Long defId) {
		return getBySqlKey("getVars", defId);
	}

	public void delByDefId(Long defId) {
		delBySqlKey("delByDefId", defId);
	}

	public List<BpmDefVar> getByDefId(Long defId) {
		return getBySqlKey("getByDefId", defId);
	}
}