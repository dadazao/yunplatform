package com.cloudstong.platform.core.web.security;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractDirectUrlResolver implements DirectUrlResolver {
	protected String pattern;
	protected String directUrl;

	public abstract boolean support(HttpServletRequest paramHttpServletRequest);

	public String directUrl() {
		return directUrl;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
}