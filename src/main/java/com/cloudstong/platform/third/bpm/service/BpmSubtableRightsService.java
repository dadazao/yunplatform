package com.cloudstong.platform.third.bpm.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.BpmSubtableRightsDao;
import com.cloudstong.platform.third.bpm.model.BpmSubtableRights;

@Service
public class BpmSubtableRightsService {

	@Resource
	private BpmSubtableRightsDao dao;

	public BpmSubtableRights getByDefIdAndNodeId(String actDefId, String nodeId, Long tableId) {
		return dao.getByDefIdAndNodeId(actDefId, nodeId, tableId);
	}
}