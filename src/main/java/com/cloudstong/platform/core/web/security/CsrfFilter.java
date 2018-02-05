package com.cloudstong.platform.core.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CsrfFilter implements Filter {
	@Resource(name = "csrfUrl")
	RegMatchers matchers;

	public CsrfFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String referer = req.getHeader("Referer");
		String serverName = request.getServerName();
		if (referer != null && referer.indexOf(serverName) < 0) {
			boolean isIngoreUrl = this.matchers.isContainUrl(referer);
			if (isIngoreUrl) {
				chain.doFilter(request, response);
			} else {
				req.getRequestDispatcher("/commons/csrf.jsp").forward(req, response);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig config) throws ServletException {
	}

}
