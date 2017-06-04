package com.cloudstong.platform.third.bpm.service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.model.BpmProStatus;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;

@Service
public class BpmProStatusService {

	@Resource
	private BpmProStatusDao dao;

	public void addOrUpd(String actDefId, Long processInstanceId, String nodeId) {
		BpmProStatus bpmProStatus = dao.getByInstNodeId(processInstanceId, nodeId);
		if (bpmProStatus == null) {
			Map mapNode = NodeCache.getByActDefId(actDefId);
			BpmProStatus tmp = new BpmProStatus();
			tmp.setId(Long.valueOf(UniqueIdUtil.genId()));
			tmp.setActdefid(actDefId);
			tmp.setActinstid(processInstanceId);
			tmp.setLastupdatetime(new Date());
			tmp.setNodeid(nodeId);
			tmp.setStatus(TaskOpinion.STATUS_CHECKING);
			FlowNode flowNode = (FlowNode) mapNode.get(nodeId);
			tmp.setNodename(flowNode.getNodeName());
			dao.save(tmp);
		} else {
			dao.updStatus(processInstanceId, nodeId, TaskOpinion.STATUS_CHECKING);
		}
	}
}