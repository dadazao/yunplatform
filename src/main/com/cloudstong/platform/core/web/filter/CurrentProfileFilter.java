package com.cloudstong.platform.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.cloudstong.platform.core.common.AppContext;

public class CurrentProfileFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest reqeust, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		Long userId = AppContext.getCurrentUserId();
		if (userId == null) {
			filterChain.doFilter(reqeust, response);
		} else {
			AppContext.getCurrentOrg();

			filterChain.doFilter(reqeust, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}