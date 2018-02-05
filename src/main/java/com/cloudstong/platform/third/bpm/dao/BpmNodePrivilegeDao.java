package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodePrivilege;

@Repository
public class BpmNodePrivilegeDao extends BaseMyBatisDaoImpl<BpmNodePrivilege, Long> {
	public Class<?> getEntityClass() {
		return BpmNodePrivilege.class;
	}

	public List<BpmNodePrivilege> getPrivilegesByDefIdAndNodeId(String actDefId, String nodeId) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		List list = getList("getPrivilegesByDefIdAndNodeId", map);
		return list;
	}

	public void delByDefIdAndNodeId(String actDefId, String nodeId) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		delBySqlKey("delByDefIdAndNodeId", map);
	}

	public List<BpmNodePrivilege> getPrivilegesByDefIdAndNodeIdAndMode(String actDefId, String nodeId, Long privilegeMode) {
		Map map = new HashMap();
		map.put("actDefId", actDefId);
		map.put("nodeId", nodeId);
		map.put("privilegeMode", privilegeMode);
		List list = getList("getPrivilegesByDefIdAndNodeIdAndMode", map);
		return list;
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}