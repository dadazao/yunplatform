package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeMessageDao;
import com.cloudstong.platform.third.bpm.dao.MessageDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeMessage;
import com.cloudstong.platform.third.bpm.model.Message;

@Service
public class BpmNodeMessageService {

	@Resource
	private BpmNodeMessageDao dao;

	@Resource
	private MessageDao messageDao;

	public List<BpmNodeMessage> getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	public List<BpmNodeMessage> getListByActDefIdNodeId(String actDefId, String nodeId) {
		return dao.getMessageByActDefIdNodeId(actDefId, nodeId);
	}

	public void saveAndEdit(String actDefId, String nodeId, List<Message> messages) throws Exception {
		messageDao.delByActdefidAndNodeid(actDefId, nodeId);
		dao.delByActDefIdAndNodeId(actDefId, nodeId);
		BpmNodeMessage bpmMessage = new BpmNodeMessage();
		bpmMessage.setActDefId(actDefId);
		bpmMessage.setNodeId(nodeId);
		for (Message message : messages) {
			bpmMessage.setId(Long.valueOf(UniqueIdUtil.genId()));
			bpmMessage.setMessageId(Long.valueOf(UniqueIdUtil.genId()));
			message.setMessageId(bpmMessage.getMessageId());
			dao.save(bpmMessage);
			messageDao.save(message);
		}
	}
}