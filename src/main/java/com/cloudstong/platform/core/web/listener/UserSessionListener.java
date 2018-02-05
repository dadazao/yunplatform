package com.cloudstong.platform.core.web.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.OnlineUser;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.system.model.SysUser;

public class UserSessionListener implements HttpSessionAttributeListener {
	public void attributeAdded(HttpSessionBindingEvent event) {
		if ("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())) {
			SysUser user = AppContext.getCurrentUser();
			if (user == null) {
				return;
			}
			Long userId = user.getId();
			if (!AppUtil.getOnlineUsers().containsKey(userId)) {
				OnlineUser onlineUser = new OnlineUser();

				onlineUser.setUserId(user.getId());
				onlineUser.setUsername(user.getUsername());
				// ISysOrg org = ContextUtil.getCurrentOrg();
				// if (org != null) {
				// onlineUser.setOrgId(org.getOrgId());
				// onlineUser.setOrgName(org.getOrgName());
				// }

				AppUtil.getOnlineUsers().put(user.getId(), onlineUser);
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		if ("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())) {
			SysUser user = AppContext.getCurrentUser();

			if (user != null)
				AppUtil.getOnlineUsers().remove(user.getId());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println(event.getName());
	}
}