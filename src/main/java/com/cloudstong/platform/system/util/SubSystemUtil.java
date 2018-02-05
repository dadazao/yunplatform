package com.cloudstong.platform.system.util;

import javax.servlet.http.HttpServletRequest;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.CookieUitl;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SubSystemService;

public class SubSystemUtil {
	public static SubSystem getCurrentSystem(HttpServletRequest request) {
		SysUser curUser = AppContext.getCurrentUser();

		if (curUser == null)
			return null;

		SubSystem subSystem = (SubSystem) request.getSession().getAttribute(SubSystem.CURRENT_SYSTEM);

		if (subSystem != null)
			return subSystem;

		boolean isCookieExists = CookieUitl.isExistByName(SubSystem.CURRENT_SYSTEM, request);

		if (!isCookieExists) {
			return null;
		}

		String systemId = CookieUitl.getValueByName(SubSystem.CURRENT_SYSTEM, request);
		SubSystemService subSystemService = (SubSystemService) AppUtil.getBean(SubSystemService.class);
		subSystem = (SubSystem) subSystemService.getById(Long.valueOf(systemId));

		if(subSystem == null){
			subSystem = new SubSystem();
		}
		
		request.getSession().setAttribute(SubSystem.CURRENT_SYSTEM, subSystem);
		
		

		return subSystem;
	}

	public static Long getCurrentSystemId(HttpServletRequest request) {
		SubSystem subSystem = getCurrentSystem(request);
		if (subSystem != null)
			return Long.valueOf(subSystem.getSystemId());
		return null;
	}

	public static void removeSystem() {
		RequestUtil.getHttpServletRequest().getSession().removeAttribute(SubSystem.CURRENT_SYSTEM);
	}
}