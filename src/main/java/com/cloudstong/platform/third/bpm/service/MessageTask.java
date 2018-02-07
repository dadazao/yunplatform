package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.InnerMessage;
import com.cloudstong.platform.core.model.Mail;
import com.cloudstong.platform.core.model.SmsMobile;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.model.Message;
import com.cloudstong.platform.third.bpm.model.ProcessRun;

public class MessageTask implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		ExecutionEntity ent = (ExecutionEntity) execution;

		String nodeId = ent.getActivityId();
		String actDefId = ent.getProcessDefinitionId();

		MessageService messageService = (MessageService) AppUtil.getBean("messageService");
		Map dataMap = messageService.getMapByActDefIdNodeId(actDefId, nodeId);

		if (BeanUtils.isEmpty(dataMap))
			return;

		Map varMap = execution.getVariables();
		Long startUserId = Long.valueOf(Long.parseLong(execution.getVariable("startUser").toString()));

		ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean("processRunService");
		String instanceId = execution.getProcessInstanceId();
		ProcessRun processRun = processRunService.getByActInstanceId(instanceId);
		String subject = processRun.getSubject();

		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		params.put("startUserId", startUserId);
		params.put("subject", subject);

		List list = getSendList(dataMap, params, varMap);

//		MessageProducer messageProducer = (MessageProducer) AppUtil.getBean("messageProducer");
//		if (messageProducer != null)
//			for (int i = 0; i < list.size(); i++)
//				messageProducer.send(list.get(i));
	}

	private List getSendList(Map<Integer, Message> dataMap, Map<String, Object> param, Map<String, Object> varsMap) {
		List list = new ArrayList();

		SysUser currentUser = AppContext.getCurrentUser();
		if (currentUser == null) {
			currentUser = new SysUser();
			currentUser.setFullname("系统");
			currentUser.setId(Long.valueOf(0L));
		}

		Long startUserId = (Long) param.get("startUserId");
		String subject = param.get("subject").toString();

		Message mailMessage = (Message) dataMap.get(Message.MAIL_TYPE);
		if (mailMessage != null) {
			String receiver = mailMessage.getReceiver();
			String starter = "";
			if (mailMessage.getSendToStartUser().intValue() == 1) {
				SysUserDao sysUserDao = (SysUserDao) AppUtil.getBean("sysUserDao");
				SysUser startUser = sysUserDao.getById(startUserId);

				if ((startUser != null) && (startUser.getEmail() != null)) {
					starter = startUser.getFullname() + "(" + startUser.getEmail() + ")";
					if (StringUtil.isEmpty(receiver)) {
						receiver = starter;
					} else {
						receiver = receiver + "," + starter;
					}
				}
			}
			if (StringUtil.isNotEmpty(receiver)) {
				mailMessage.setReceiver(receiver);
				List mailModelList = getMailModel(mailMessage, subject, varsMap, currentUser);
				list.addAll(mailModelList);
			}
		}

		Message mobileMessage = (Message) dataMap.get(Message.MOBILE_TYPE);
		if (mobileMessage != null) {
			String receiver = mobileMessage.getReceiver();
			String starter = "";
			if (mobileMessage.getSendToStartUser().intValue() == 1) {
				SysUserDao sysUserDao = (SysUserDao) AppUtil.getBean("sysUserDao");
				SysUser startUser = sysUserDao.getById(startUserId);
				if ((startUser != null) && (startUser.getPhone() != null)) {
					starter = startUser.getFullname() + "(" + startUser.getPhone() + ")";
					if (StringUtil.isEmpty(receiver)) {
						receiver = starter;
					} else {
						receiver = receiver + "," + starter;
					}
				}
			}
			if (StringUtil.isNotEmpty(receiver)) {
				mobileMessage.setReceiver(receiver);
				List smsList = getMobileModel(mobileMessage, subject, varsMap, currentUser);
				list.addAll(smsList);
			}
		}

		Message innerMessage = (Message) dataMap.get(Message.INNER_TYPE);
		if (innerMessage != null) {
			String receiver = innerMessage.getReceiver();
			String starter = "";
			if (innerMessage.getSendToStartUser().intValue() == 1) {
				SysUserDao sysUserDao = (SysUserDao) AppUtil.getBean("sysUserDao");
				SysUser startUser = sysUserDao.getById(startUserId);
				starter = startUser.getFullname() + "(" + startUserId + ")";
				if (StringUtil.isEmpty(receiver)) {
					receiver = starter;
				} else {
					receiver = receiver + "," + starter;
				}
			}
			if (StringUtil.isNotEmpty(receiver)) {
				innerMessage.setReceiver(receiver);
				List innerMesageList = getInnerModel(innerMessage, subject, varsMap, currentUser);
				list.addAll(innerMesageList);
			}
		}

		return list;
	}

	public List<Mail> getMailModel(Message messageModel, String subject, Map<String, Object> varsMap, SysUser currentUser) {
		Date sendDate = new Date();
		List messageList = new ArrayList();

		String receiver = messageModel.getReceiver();
		Map mapReceiver = splitString(receiver);
		Set set = mapReceiver.entrySet();

		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry ent = (Map.Entry) it.next();
			String toEmail = (String) ent.getKey();
			String toName = (String) ent.getValue();

			String content = messageModel.getContent().replace("${收件人}", toName).replace("${发件人}", currentUser.getFullname())
					.replace("${事项名称}", subject);
			content = replaceVars(content, varsMap);

			Mail mailModel = new Mail();

			if (StringUtil.isNotEmpty(messageModel.getSubject())) {
				subject = messageModel.getSubject();
			}

			mailModel.setSubject(subject);
			mailModel.setContent(content);

			mailModel.setTo(new String[] { toEmail });

			mailModel.setSendDate(sendDate);

			messageList.add(mailModel);
		}
		return messageList;
	}

	public List<SmsMobile> getMobileModel(Message messageModel, String subject, Map<String, Object> varsMap, SysUser currentUser) {
		List messageList = new ArrayList();

		String receiver = messageModel.getReceiver();
		Map mapReceiver = splitString(receiver);
		Set set = mapReceiver.entrySet();

		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry ent = (Map.Entry) it.next();
			String mobileNo = (String) ent.getKey();
			String userName = (String) ent.getValue();

			String content = messageModel.getContent().replace("${收件人}", userName).replace("${发件人}", currentUser.getFullname())
					.replace("${事项名称}", subject);

			content = replaceVars(content, varsMap);

			Date sendDate = new Date();

			SmsMobile smsMobile = new SmsMobile();
			smsMobile.setPhoneNumber(mobileNo);
			smsMobile.setSmsContent(content);
			smsMobile.setUserName(messageModel.getFromUser());
			smsMobile.setSendTime(sendDate);

			messageList.add(smsMobile);
		}
		return messageList;
	}

	private List<InnerMessage> getInnerModel(Message messageModel, String subject, Map<String, Object> varsMap, SysUser currentUser) {
		List messageList = new ArrayList();
		String receiver = messageModel.getReceiver();
		Map mapReceiver = splitString(receiver);
		Set set = mapReceiver.entrySet();

		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry ent = (Map.Entry) it.next();
			String userId = (String) ent.getKey();
			String userName = (String) ent.getValue();
			String content = messageModel.getContent().replace("${收件人}", userName).replace("${发件人}", currentUser.getFullname())
					.replace("${事项名称}", subject);

			content = replaceVars(content, varsMap);

			Date sendDate = new Date();
			InnerMessage innerMessage = new InnerMessage();

			if (StringUtil.isNotEmpty(messageModel.getSubject())) {
				subject = messageModel.getSubject();
			}

			innerMessage.setSubject(subject);
			innerMessage.setContent(content);
			innerMessage.setTo(userId);
			innerMessage.setToName(userName);
			innerMessage.setFrom(currentUser.getId().toString());
			innerMessage.setFromName(currentUser.getFullname());
			innerMessage.setSendDate(sendDate);
			messageList.add(innerMessage);
		}
		return messageList;
	}

	private String replaceVars(String content, Map<String, Object> vars) {
		for (Map.Entry entry : vars.entrySet()) {
			String hold = "${" + (String) entry.getKey() + "}";
			content = content.replace(hold, entry.getValue() == null ? "" : entry.getValue().toString());
		}
		return content;
	}

	private Map<String, String> splitString(String message) {
		if (message == null)
			return null;
		String[] strs = message.split(",");
		Pattern pattern = Pattern.compile("(.*)\\((.*)\\)");
		Map map = new HashMap();
		for (String str : strs) {
			Matcher match = pattern.matcher(str);
			if (match.find()) {
				map.put(match.group(2), match.group(1));
			}
		}
		return map;
	}
}