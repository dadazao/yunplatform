package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmAgent;

@Repository
public class BpmAgentDao extends BaseMyBatisDaoImpl<BpmAgent, Long> {
	public Class getEntityClass() {
		return BpmAgent.class;
	}

	public List<BpmAgent> getByAgentId(Long agentid) {
		Map params = new HashMap();
		params.put("agentid", agentid);
		List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getAll", params);
		return list;
	}

	public void delByAgentId(Long agentid) {
		Map params = new HashMap();
		params.put("agentid", agentid);
		getBySqlKey("delByAgentId", params);
	}

	public List<BpmAgent> getByDefKey(String defKey) {
		List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getByDefKey", defKey);
		return list;
	}

	public List<String> getNotInByAgentId(Long agentid) {
		List list = getBySqlKey("getNotInByAgentId", agentid);
		return list;
	}

	public List<BpmAgent> getByActDefId(String actDefId) {
		List list = getBySqlKey("getByActDefId", actDefId);
		return list;
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}
}