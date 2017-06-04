package com.cloudstong.platform.core.jms;

import java.util.HashMap;
import java.util.Map;

import com.cloudstong.platform.core.jms.service.IJmsHandler;
import com.cloudstong.platform.core.util.BeanUtils;

public class MessageConsumer {
	private Map<String, IJmsHandler> handlers = new HashMap();

	public void setHandlers(Map<String, IJmsHandler> handlers) {
		this.handlers = handlers;
	}

	public void sendMessage(Object model) throws Exception {
		if ((BeanUtils.isNotEmpty(handlers)) && (BeanUtils.isNotEmpty(model)))
			((IJmsHandler) handlers.get(model.getClass().getName())).handMessage(model);
		else
			throw new Exception("Object:[" + model + "] is not  entity Object ");
	}
}