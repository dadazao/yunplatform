package com.cloudstong.platform.third.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeScriptDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;

@Service
public class BpmNodeScriptService {

	@Resource
	private BpmNodeScriptDao dao;

	public List<BpmNodeScript> getByNodeScriptId(String nodeId, String actDefId) {
		return dao.getByBpmNodeScriptId(nodeId, actDefId);
	}

	public Map<String, BpmNodeScript> getMapByNodeScriptId(String nodeId, String actDefId) {
		List<BpmNodeScript> list = getByNodeScriptId(nodeId, actDefId);
		Map map = new HashMap();
		for (BpmNodeScript script : list) {
			map.put("type" + script.getScriptType(), script);
		}

		return map;
	}

	public BpmNodeScript getScriptByType(String nodeId, String actDefId, Integer scriptType) {
		return dao.getScriptByType(nodeId, actDefId, scriptType);
	}

	public void saveScriptDef(String defId, String nodeId, List<BpmNodeScript> list) throws Exception {
		dao.delByDefAndNodeId(defId, nodeId);

		for (BpmNodeScript script : list) {
			long id = UniqueIdUtil.genId();
			script.setId(Long.valueOf(id));
			script.setActDefId(defId);
			script.setNodeId(nodeId);
			dao.save(script);
		}
	}
}