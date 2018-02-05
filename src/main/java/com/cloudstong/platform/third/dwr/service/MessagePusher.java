/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.third.dwr.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;

import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-25
 * 
 *         Description:
 * 
 */
public class MessagePusher {

	public void onPageLoad() {
		try {
			Container container = ServerContextFactory.get().getContainer();
			ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);

			ScriptSessionListener listener = new ScriptSessionListener() {
				public void sessionCreated(ScriptSessionEvent ev) {
					HttpSession session = WebContextFactory.get().getSession();
					SysUser user = ((SysUser) session.getAttribute("user"));
					if(user != null) {
						ev.getSession().setAttribute("userId", user.getId());
					}
				}

				public void sessionDestroyed(ScriptSessionEvent ev) {
					ev.getSession().invalidate();
				}
			};
			manager.addScriptSessionListener(listener);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}

	/**
	 * Description:更新多个人的客户端消息数量
	 * 
	 * @param useridList
	 */
	public static void sendMessageAuto(List<Long> useridList) {
		for (Long userid : useridList) {
			final String userId = String.valueOf(userid);
			Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
				public boolean match(ScriptSession session) {
					if (session.getAttribute("userId") == null)
						return false;
					else
						return (session.getAttribute("userId")).equals(userId);
				}
			}, new Runnable() {
				private ScriptBuffer script = new ScriptBuffer();

				public void run() {
					script.appendCall("updateMessage");// , autoMessage);
					Collection<ScriptSession> sessions = Browser.getTargetSessions();
					for (ScriptSession scriptSession : sessions) {
						scriptSession.addScript(script);
					}
				}
			});
		}
	}

	/**
	 * Description: 更新一个人
	 * 
	 * @param userid
	 */
	public static void sendMessageAuto(Long userid) {
		final String userId = String.valueOf(userid);
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("userId") == null)
					return false;
				else
					return (session.getAttribute("userId")).equals(userId);
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall("updateMessage");
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
