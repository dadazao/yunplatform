package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.BpmDefVarDao;
import com.cloudstong.platform.third.bpm.model.BpmDefVar;

@Service
public class BpmDefVarService {

	@Resource
	private BpmDefVarDao dao;

	public boolean isVarNameExist(String varName, String varKey, Long defId) {
		return dao.isVarNameExist(varName, varKey, defId);
	}

	public List<BpmDefVar> getByDeployAndNode(String deployId, String nodeId) {
		return dao.getByDeployAndNode(deployId, nodeId);
	}

	public List<BpmDefVar> getVarsByFlowDefId(long defId) {
		return dao.getVarsByFlowDefId(Long.valueOf(defId));
	}
}