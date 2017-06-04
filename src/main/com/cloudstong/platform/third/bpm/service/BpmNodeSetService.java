package com.cloudstong.platform.third.bpm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormRightsDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;

@Service
public class BpmNodeSetService {

	@Resource
	private BpmNodeSetDao dao;

	@Resource
	private BpmFormRightsDao bpmFormRightsDao;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	public void save(Long defId, List<BpmNodeSet> nodeList) throws Exception {
		//dao.delByStartGlobalDefId(defId);
		for (BpmNodeSet node : nodeList)
			if (node.getSetId() == null) {
				node.setSetId(Long.valueOf(UniqueIdUtil.genId()));
				dao.save(node);
			} else {
				dao.update(node);

//				if (node.getFormType().shortValue() == 0) {
//					if ((node.getOldFormKey().longValue() != 0L) && (!node.getFormKey().equals(node.getOldFormKey()))) {
//						bpmFormRightsDao.delByFlowFormNodeId(node.getActDefId(), node.getNodeId());
//					}
//				}
			}
	}

	public List<BpmNodeSet> getByDefId(Long defId) {
		return dao.getByDefId(defId);
	}

	public List<BpmNodeSet> getAllByDefId(Long defId) {
		return dao.getAllByDefId(defId);
	}

	public BpmNodeSet getByDefIdNodeId(Long defId, String nodeId) {
		return dao.getByDefIdNodeId(defId, nodeId);
	}

	public Map<String, BpmNodeSet> getMapByDefId(Long defId) {
		return dao.getMapByDefId(defId);
	}

	public BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId) {
		return dao.getByActDefIdNodeId(actDefId, nodeId);
	}

	public BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey) {
		return dao.getByActDefIdJoinTaskKey(actDefId, joinTaskKey);
	}

	public BpmNodeSet getBySetType(Long defId, Short setType) {
		return dao.getBySetType(defId, setType);
	}

	public List getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	public void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef) {
		dao.updateIsJumpForDef(nodeId, actDefId, isJumpForDef);
	}

	public BpmNodeSet getById(Long pk) {
		return (BpmNodeSet)dao.getById(pk);
	}

	public void delByIds(Long[] lAryId) {
		for(Long id:lAryId){
			dao.delById(id);
		}
		
	}

	public BpmNodeSet getBpmNodeSetByActDefIdNodeId(String actDefId, String nodeId) {
		return this.dao.getByActDefIdNodeId(actDefId, nodeId);
	}

	public void update(BpmNodeSet bpmNodeSet) {
		dao.update(bpmNodeSet);
	}
}