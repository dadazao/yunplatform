package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;

@Repository
public class BpmNodeButtonDao extends BaseMyBatisDaoImpl<BpmNodeButton, Long> {
	public Class getEntityClass() {
		return BpmNodeButton.class;
	}

	public List<BpmNodeButton> getByDefId(Long defId) {
		List list = getBySqlKey("getByDefId", defId);
		return list;
	}

	public List<BpmNodeButton> getByDefNodeId(Long defId, String nodeId) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("nodeId", nodeId);
		List list = getBySqlKey("getByDefNodeId", params);
		return list;
	}

	public List<BpmNodeButton> getByStartForm(Long defId) {
		List list = getBySqlKey("getByStartForm", defId);
		return list;
	}

	public Integer isStartFormExist(Long defId, Integer operatortype) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("operatortype", operatortype);
		Integer rtn = (Integer) getOne("isStartFormExist", params);
		return rtn;
	}

	public Integer isExistByNodeId(Long defId, String nodeId, Integer operatortype) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("nodeId", nodeId);
		params.put("operatortype", operatortype);
		Integer rtn = (Integer) getOne("isExistByNodeId", params);
		return rtn;
	}

	public Integer isStartFormExistForUpd(Long defId, Integer operatortype, Long id) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("operatortype", operatortype);
		params.put("id", id);
		Integer rtn = (Integer) getOne("isStartFormExistForUpd", params);
		return rtn;
	}

	public Integer isExistByNodeIdForUpd(Long defId, String nodeId, Integer operatortype, Long id) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("nodeId", nodeId);
		params.put("operatortype", operatortype);
		params.put("id", id);
		Integer rtn = (Integer) getOne("isExistByNodeIdForUpd", params);
		return rtn;
	}

	public void updSn(Long id, Long sn) {
		Map params = new HashMap();
		params.put("id", id);
		params.put("sn", sn);
		update("updSn", params);
	}

	public void delByStartForm(Long defId) {
		delBySqlKey("delByStartForm", defId);
	}

	public void delByNodeId(Long defId, String nodeId) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByNodeId", params);
	}

	public void delByDefId(Long defId) {
		delBySqlKey("delByDefId", defId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}