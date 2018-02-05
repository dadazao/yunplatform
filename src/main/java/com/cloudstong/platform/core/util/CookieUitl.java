package com.cloudstong.platform.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

public class CookieUitl {
	public static void addCookie(String name, String value, int maxAge, boolean httpOnly, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest req = (HttpServletRequest) context.getRequest();
		addCookie(name, value, maxAge, httpOnly, "", "", req, response);
	}

	public static void addCookie(String name, String value, boolean httpOnly, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest req = (HttpServletRequest) context.getRequest();
		addCookie(name, value, -1, httpOnly, "", "", req, response);
	}

	public static void addCookie(String name, String value, boolean httpOnly, HttpServletRequest req,
			HttpServletResponse response) {
		addCookie(name, value, -1, httpOnly, "", req.getContextPath(), req, response);
	}

	public static void addCookie(String name, String value, int maxAge, boolean httpOnly, String domain, String path,
			HttpServletRequest req, HttpServletResponse response) {
		if (response != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append('=');
			sb.append(value.trim() + "; ");
			if (maxAge != -1) {
				sb.append("max-age=");
				sb.append(maxAge + "; ");
			}

			if (StringUtil.isNotEmpty(domain)) {
				sb.append("domain=");
				sb.append(domain + "; ");
			}

			if (StringUtil.isNotEmpty(path)) {
				sb.append("path=");
				sb.append(path + ";");
			}

			if (httpOnly) {
				sb.append("HttpOnly");
			}

			response.addHeader("Set-Cookie", sb.toString());
		}
	}

	public static void delCookie(String name, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		delCookie(name, request, response);
	}

	 public static void delCookie(String name, HttpServletRequest request, HttpServletResponse response) {
	        addCookie(name, "", 0, true, "", request.getContextPath(), request, response);
	    }

	public static String getValueByName(String name, PageContext context) {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		String str = getValueByName(name, request);
		return str;
	}

	public static String getValueByName(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie sCookie = null;
		String svalue = null;
		String sname = null;

		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			sCookie = cookies[i];
			sname = sCookie.getName();
			if (sname.equals(name)) {
				svalue = sCookie.getValue();
				break;
			}
		}
		return svalue;
	}

	public static boolean isExistByName(String name, PageContext context) {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		return isExistByName(name, request);
	}

	public static boolean isExistByName(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie sCookie = null;

		String sname = null;
		boolean isExist = false;
		if (cookies == null)
			return false;
		for (int i = 0; i < cookies.length; i++) {
			sCookie = cookies[i];
			sname = sCookie.getName();
			if (sname.equals(name)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}
}