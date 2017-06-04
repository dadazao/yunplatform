package com.cloudstong.platform.core.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class MultipleAuthenticationLoginEntry implements AuthenticationEntryPoint {
	private String defaultLoginUrl = "/login.html";
	private List<DirectUrlResolver> directUrlResolvers = new ArrayList();

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
			ServletException {
		String ctxPath = request.getContextPath();
		for (DirectUrlResolver directUrlResolver : directUrlResolvers) {
			if (directUrlResolver.support(request)) {
				String loginUrl = directUrlResolver.directUrl();
				response.sendRedirect(ctxPath + loginUrl);
				return;
			}
		}
		response.sendRedirect(ctxPath + defaultLoginUrl);
	}

	public void setDefaultLoginUrl(String defaultLoginUrl) {
		this.defaultLoginUrl = defaultLoginUrl;
	}

	public void setDirectUrlResolvers(List<DirectUrlResolver> directUrlResolvers) {
		this.directUrlResolvers = directUrlResolvers;
	}
}