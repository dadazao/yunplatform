package com.cloudstong.platform.third.bpm.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeWebServiceDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeWebService;

@Service
public class BpmNodeWebServiceService {

	@Resource
	private BpmNodeWebServiceDao dao;

	public BpmNodeWebService getByNodeIdActDefId(String nodeId, String actDefId) {
		return dao.getByNodeIdActDefId(nodeId, actDefId);
	}

	public void save(String nodeId, String actDefId, String json) throws Exception {
		BpmNodeWebService bpmNodeWebService = new BpmNodeWebService();
		bpmNodeWebService.setId(Long.valueOf(UniqueIdUtil.genId()));
		bpmNodeWebService.setNodeId(nodeId);
		bpmNodeWebService.setActDefId(actDefId);
		bpmNodeWebService.setDocument(json);
		dao.save(bpmNodeWebService);
	}

	protected BpmNodeWebService getFormObject(String nodeId, String actDefId, JSONObject jsonObject) throws Exception {
		Long id = (Long) jsonObject.get("wsid");
		BpmNodeWebService bpmNodeWebService = new BpmNodeWebService();
		bpmNodeWebService.setId(id);
		bpmNodeWebService.setNodeId(nodeId);
		bpmNodeWebService.setActDefId(actDefId);
		return bpmNodeWebService;
	}
}