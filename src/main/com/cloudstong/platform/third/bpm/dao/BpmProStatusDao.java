package com.cloudstong.platform.third.bpm.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmProStatus;

@Repository
public class BpmProStatusDao extends BaseMyBatisDaoImpl<BpmProStatus, Long> {
	public Class getEntityClass() {
		return BpmProStatus.class;
	}

	public List<BpmProStatus> getByActInstanceId(String instanceId) {
		List list = getBySqlKey("getByActInstanceId", instanceId);
		return list;
	}

	public boolean isExistByInstanceId(Long instanceId) {
		Integer rtn = (Integer) getOne("isExistByInstanceId", instanceId);
		return rtn.intValue() > 0;
	}

	public Integer getStatusByRunidNodeid(String runId, String nodeId) {
		Map map = new HashMap();
		map.put("runId", runId);
		map.put("nodeId", nodeId);
		Integer rtn = (Integer) getOne("getStatusByRunidNodeid", map);
		return rtn;
	}

	public void updStatus(Long actInstanceId, String nodeId, Short status) {
		BpmProStatus bpmProStatus = new BpmProStatus();
		bpmProStatus.setActinstid(actInstanceId);
		bpmProStatus.setNodeid(nodeId);
		bpmProStatus.setStatus(status);
		bpmProStatus.setLastupdatetime(new Date());
		update("updStatus", bpmProStatus);
	}

	public BpmProStatus getByInstNodeId(Long instanceId, String nodeId) {
		Map params = new HashMap();
		params.put("actinstid", instanceId);
		params.put("nodeid", nodeId);
		BpmProStatus rtn = (BpmProStatus) getOne("getByInstNodeId", params);
		return rtn;
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}