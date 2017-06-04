package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.model.BpmProStatus;
import com.cloudstong.platform.third.bpm.service.IFlowStatus;

public class FlowStatus implements IFlowStatus {
	private BpmProStatusDao bpmProStatusDao;
	private Map<Short, String> statusColorMap = new HashMap();

	public void setBpmProStatus(BpmProStatusDao bpmProStatusDao) {
		this.bpmProStatusDao = bpmProStatusDao;
	}

	public void setStatusColor(Map<Short, String> tmp) {
		statusColorMap = tmp;
	}

	public Map<String, String> getStatusByInstanceId(Long instanceId) {
		Map map = new HashMap();
		List<BpmProStatus> list = bpmProStatusDao.getByActInstanceId(instanceId.toString());
		for (BpmProStatus obj : list) {
			String color = (String) statusColorMap.get(obj.getStatus());
			map.put(obj.getNodeid(), color);
		}

		return map;
	}
}