package com.cloudstong.platform.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

public class CookieUitl {
	public static void addCookie(String name, String value, int maxAge, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest req = (HttpServletRequest) context.getRequest();
		addCookie(name, value, maxAge, req, response);
	}

	public static void addCookie(String name, String value, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest req = (HttpServletRequest) context.getRequest();
		addCookie(name, value, -1, req, response);
	}

	public static void addCookie(String name, String value, HttpServletRequest req, HttpServletResponse response) {
		addCookie(name, value, -1, req, response);
	}

	public static void addCookie(String name, String value, int maxAge, HttpServletRequest req, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(req.getContextPath());
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void delCookie(String name, PageContext context) {
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		delCookie(name, request, response);
	}

	public static void delCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		addCookie(name, "", 0, request, response);
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