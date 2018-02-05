package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeRuleDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;

@Service
public class BpmNodeRuleService {

	@Resource
	private BpmNodeRuleDao dao;

	public List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId) {
		return dao.getByDefIdNodeId(actDefId, nodeId);
	}

	public void reSort(String ruleIds) {
		if (StringUtil.isEmpty(ruleIds))
			return;
		String[] aryRuleIds = ruleIds.split(",");
		for (int i = 0; i < aryRuleIds.length; i++) {
			Long ruleId = Long.valueOf(Long.parseLong(aryRuleIds[i]));
			dao.reSort(ruleId, Long.valueOf(i));
		}
	}

	public List<BpmNodeRule> getByActDefId(String actDefId) {
		return dao.getByDefIdNodeId(actDefId, null);
	}
}