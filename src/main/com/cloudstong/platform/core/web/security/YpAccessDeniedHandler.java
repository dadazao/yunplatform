package com.cloudstong.platform.core.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


public class YpAccessDeniedHandler implements AccessDeniedHandler {
	private String accessDeniedUrl;

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
		request.setAttribute("ex", ex);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		request.getRequestDispatcher(accessDeniedUrl).forward(request, response);
	}

}