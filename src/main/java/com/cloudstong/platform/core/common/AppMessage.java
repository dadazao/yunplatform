package com.cloudstong.platform.core.common;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:全局消息
 */
public class AppMessage {

	/**
	 * <code>messageKey</code> 消息键.
	 */
	private String messageKey = null;

	/**
	 * <code>messageArgs</code> 消息参数.
	 */
	private Object[] messageArgs = null;

	public Object[] getMessageArgs() {
		return messageArgs;
	}

	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}

	public String getMessageKey() {

		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

}
