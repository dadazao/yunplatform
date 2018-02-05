package com.cloudstong.platform.third.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.dao.BpmFormRunDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmFormRun;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;

@Service
public class BpmFormRunService {

	@Resource
	private BpmFormRunDao dao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	public void addFormRun(String actDefId, Long runId, String actInstanceId) {
		List<BpmNodeSet> list = bpmNodeSetDao.getOnlineFormByActDefId(actDefId);
		for (BpmNodeSet bpmNodeSet : list) {
			BpmFormRun bpmFormRun = getByBpmNodeSet(runId, actInstanceId, bpmNodeSet);
			dao.save(bpmFormRun);
		}
	}

	private BpmNodeSet getDefalutStartForm(List<BpmNodeSet> list, Short setType) {
		BpmNodeSet bpmNodeSet = null;
		for (BpmNodeSet node : list) {
			if (node.getSetType().equals(setType)) {
				bpmNodeSet = node;
				break;
			}
		}
		return bpmNodeSet;
	}

	private BpmNodeSet getStartForm(List<BpmNodeSet> list) {
		BpmNodeSet bpmNodeSet = getDefalutStartForm(list, BpmNodeSet.SetType_StartForm);
		return bpmNodeSet;
	}

	private BpmNodeSet getGlobalForm(List<BpmNodeSet> list) {
		BpmNodeSet bpmNodeSet = getDefalutStartForm(list, BpmNodeSet.SetType_GloabalForm);
		return bpmNodeSet;
	}

	public Map<String, BpmNodeSet> getTaskForm(List<BpmNodeSet> list) {
		Map map = new HashMap();
		for (BpmNodeSet node : list) {
			if (node.getSetType().equals(BpmNodeSet.SetType_TaskNode)) {
				map.put(node.getNodeId(), node);
			}
		}
		return map;
	}

	private BpmFormRun getByBpmNodeSet(Long runId, String actInstanceId, BpmNodeSet bpmNodeSet) {
		BpmFormRun bpmFormRun = new BpmFormRun();
		bpmFormRun.setId(Long.valueOf(UniqueIdUtil.genId()));
		bpmFormRun.setRunId(runId);
		bpmFormRun.setActInstanceId(actInstanceId);
		bpmFormRun.setActDefId(bpmNodeSet.getActDefId());
		bpmFormRun.setActNodeId(bpmNodeSet.getNodeId());
		bpmFormRun.setFormdefId(bpmNodeSet.getFormDefId());
		bpmFormRun.setFormdefKey(bpmNodeSet.getFormKey());
		bpmFormRun.setFormType(bpmNodeSet.getFormType());
		bpmFormRun.setFormUrl(bpmNodeSet.getFormUrl());
		bpmFormRun.setSetType(bpmNodeSet.getSetType());
		return bpmFormRun;
	}

	public BpmNodeSet getStartBpmNodeSet(String actDefId, Short toFirstNode) {
		String firstTaskName = processRunService.getFirstNodetByDefId(actDefId);
		List list = bpmNodeSetDao.getByActDefId(actDefId);
		BpmNodeSet bpmNodeSetStart = getStartForm(list);
		BpmNodeSet bpmNodeSetGlobal = getGlobalForm(list);
		Map taskMap = getTaskForm(list);
		BpmNodeSet firstBpmNodeSet = (BpmNodeSet) taskMap.get(firstTaskName);
		if (bpmNodeSetStart == null) {
			if (toFirstNode.shortValue() == 1) {
				if ((firstBpmNodeSet != null) && (firstBpmNodeSet.getFormType() != null) && (firstBpmNodeSet.getFormType().shortValue() != -1)) {
					return firstBpmNodeSet;
				}

				if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null) && (bpmNodeSetGlobal.getFormType().shortValue() != -1)) {
					return bpmNodeSetGlobal;
				}

			} else if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null) && (bpmNodeSetGlobal.getFormType().shortValue() != -1)) {
				return bpmNodeSetGlobal;
			}
		} else {
			return bpmNodeSetStart;
		}
		return null;
	}

	public boolean getCanDirectStart(Long defId) {
		 BpmDefinition bpmDefinition = (BpmDefinition)this.bpmDefinitionDao.getById(defId);

		    Integer directStart = bpmDefinition.getDirectstart();
		    if (directStart == null) {
		      return true;
		    }
		    return directStart.intValue() == 1;
	}

	private boolean hasForm(BpmNodeSet nodeSet) {
		if ((nodeSet == null) || (nodeSet.getFormType().shortValue() == -1))
			return false;
		return true;
	}

	public BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId) {
		BpmFormRun bpmFormRun = dao.getByInstanceAndNode(actInstanceId, actNodeId);
		if ((bpmFormRun != null) && (bpmFormRun.getFormType() != null) && (bpmFormRun.getFormType().shortValue() != -1)) {
			return bpmFormRun;
		}

		bpmFormRun = dao.getGlobalForm(actInstanceId);
		if ((bpmFormRun != null) && (bpmFormRun.getFormType() != null) && (bpmFormRun.getFormType().shortValue() != -1)) {
			return bpmFormRun;
		}
		return null;
	}

	public BpmFormRun getByInstanceAndNodeId(String actInstanceId, String actNodeId) {
		BpmFormRun bpmFormRun = dao.getByInstanceAndNode(actInstanceId, actNodeId);
		return bpmFormRun;
	}

	public List<BpmFormRun> getByInstanceId(String actInstanceId) {
		return dao.getByInstanceId(actInstanceId);
	}

	public BpmNodeSet getStartBpmNodeSet(Long defId, String actDefId) throws Exception {
		FlowNode flowNode = NodeCache.getFirstNodeId(actDefId);
	    String nodeId = "";
	    if (flowNode != null) {
	      nodeId = flowNode.getNodeId();
	    }

	    BpmNodeSet firstNodeSet = this.bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);
	    if ((firstNodeSet != null) && (-1 != firstNodeSet.getFormKey())) {
	      return firstNodeSet;
	    }

	    BpmNodeSet globalNodeSet = this.bpmNodeSetDao.getByStartGlobal(defId);
	    return globalNodeSet;

	}
}