package com.cloudstong.platform.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.RequestUtil;

public class AopFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			RequestUtil.setHttpServletRequest((HttpServletRequest) request);
			RequestUtil.setHttpServletResponse((HttpServletResponse) response);

			chain.doFilter(request, response);
		} finally {
			AppContext.clearAll();
		}
	}

	public void destroy() {
	}
}