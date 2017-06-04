package com.cloudstong.platform.console.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.CookieUitl;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.web.action.BaseAction;

@ParentPackage("default")
@Namespace("/")
@SuppressWarnings("serial")
public class LoginHandlerAction extends BaseAction{

	private String defaultLogin = "/login.jsp";
	
	@Action("loginRedirect")
	public String login() throws Exception {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		
		getSession().removeAttribute("user");
		
		String loginAction = CookieUitl.getValueByName("loginAction", request);
		String redirectUrl = "";
		Map actionPageMap = (Map) AppUtil.getBean("actionPageMap");
		if ((StringUtil.isNotEmpty(loginAction)) && (actionPageMap.containsKey(loginAction))) {
			redirectUrl = (String) actionPageMap.get(loginAction);
			
			response.sendRedirect(request.getContextPath() + redirectUrl);
			return NONE;
		}

		response.sendRedirect(request.getContextPath() + defaultLogin);

		return NONE;
	}
}
