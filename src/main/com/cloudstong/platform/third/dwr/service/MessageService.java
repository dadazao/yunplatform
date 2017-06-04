/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.third.dwr.service;

import java.util.Collection;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * @author michael Created on 2012-11-28 Revision History: Date Reviser
 *         Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description: 消息推送
 * 
 */
public class MessageService {
	@SuppressWarnings("deprecation")
	public void sendMsg() {
		try {
			WebContext wctx = WebContextFactory.get();
			// 当前页面
			String currentPage = wctx.getCurrentPage();
			// 得到登录此页面的scriptSession的集合
			Collection<ScriptSession> sessions = wctx.getScriptSessionsByPage(currentPage);
			System.out.println("Reverse Ajax......");
			for (ScriptSession session : sessions) {
				ScriptBuffer script = new ScriptBuffer();
				script.appendScript("updateMessage(").appendScript(");");
				session.addScript(script);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
