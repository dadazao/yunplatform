package com.cloudstong.platform.third.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.third.bpm.dao.MessageDao;
import com.cloudstong.platform.third.bpm.model.Message;

@Service
public class MessageService {

	@Resource
	private MessageDao dao;

	public List<Message> getListByActDefIdNodeId(String actDefId, String nodeId) {
		return dao.getListByActDefIdNodeId(actDefId, nodeId);
	}

	public Map<Integer, Message> getMapByActDefIdNodeId(String actDefId, String nodeId) {
		List<Message> instList = getListByActDefIdNodeId(actDefId, nodeId);
		Map dataMap = new HashMap();
		if (BeanUtils.isEmpty(instList))
			return dataMap;
		for (Message mesModel : instList) {
			Integer messageType = mesModel.getMessageType();
			dataMap.put(messageType, mesModel);
		}
		return dataMap;
	}
}