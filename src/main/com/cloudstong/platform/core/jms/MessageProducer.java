package com.cloudstong.platform.core.jms;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

public class MessageProducer {
	private static final Log logger = LogFactory.getLog(MessageProducer.class);

	@Resource(name = "messageQueue")
	private Queue messageQueue;

	@Resource
	private JmsTemplate jmsTemplate;

	public void send(Object model) {
		logger.debug("procduce the message");

		jmsTemplate.convertAndSend(messageQueue, model);
	}
}