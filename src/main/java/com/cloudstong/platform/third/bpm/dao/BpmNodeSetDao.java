package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;

@Repository
public class BpmNodeSetDao extends BaseMyBatisDaoImpl<BpmNodeSet, Long> {
	public Class getEntityClass() {
		return BpmNodeSet.class;
	}

	public List<BpmNodeSet> getByDefId(Long defId) {
		return getBySqlKey("getByDefId", defId);
	}

	public List<BpmNodeSet> getAllByDefId(Long defId) {
		return getBySqlKey("getAllByDefId", defId);
	}

	public List<BpmNodeSet> getByActDef(String actDefId) {
		return getBySqlKey("getByActDef", actDefId);
	}

	public BpmNodeSet getByDefIdNodeId(Long defId, String nodeId) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("nodeId", nodeId);

		return (BpmNodeSet) getUnique("getByDefIdNodeId", params);
	}

	public BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		return (BpmNodeSet) getUnique("getByActDefIdNodeId", params);
	}

	public BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("joinTaskKey", joinTaskKey);
		return (BpmNodeSet) getUnique("getByActDefIdJoinTaskKey", params);
	}

	public void delByDefId(Long defId) {
		delBySqlKey("delByDefId", defId);
	}

	public BpmNodeSet getBySetType(Long defId, Short setType) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("setType", setType);
		return (BpmNodeSet) getUnique("getBySetType", params);
	}
	
	  public BpmNodeSet getByStartGlobal(Long defId)
	  {
	    List list = getBySqlKey("getByStartGlobal", defId);
	    if (list.size() == 0)
	      return null;
	    return (BpmNodeSet)list.get(0);
	  }

	public void delByStartGlobalDefId(Long defId) {
		delBySqlKey("delByStartGlobalDefId", defId);
	}

	public Map<String, BpmNodeSet> getMapByDefId(Long defId) {
		Map map = new HashMap();
		List<BpmNodeSet> list = getByDefId(defId);
		for (BpmNodeSet bpmNodeSet : list) {
			map.put(bpmNodeSet.getNodeId()+bpmNodeSet.getNodeName(), bpmNodeSet);
		}
		return map;
	}

	public List<BpmNodeSet> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	public List<BpmNodeSet> getOnlineFormByActDefId(String actDefId) {
		return getBySqlKey("getOnlineFormByActDefId", actDefId);
	}

	public void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef) {
		Map params = new HashMap();
		params.put("isJumpForDef", isJumpForDef);
		params.put("nodeId", nodeId);
		params.put("actDefId", actDefId);
		update("updateIsJumpForDef", params);
	}
}