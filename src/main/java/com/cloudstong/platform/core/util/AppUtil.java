package com.cloudstong.platform.core.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloudstong.platform.core.model.OnlineUser;

public class AppUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;
	private static Map<Long, OnlineUser> onlineUsers = new LinkedHashMap();

	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		applicationContext = contex;
	}

	public static ApplicationContext getContext() {
		if(applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return applicationContext;
	}

	public static ServletContext getServletContext() throws Exception {
		return servletContext;
	}

	public static Object getBean(Class cls) {
		return getContext().getBean(cls);
	}

	public static Object getBean(String beanId) {
		return getContext().getBean(beanId);
	}

	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	public static Map<Long, OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}

	public static String getClasspath() {
		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String rootPath = "";

		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1);
			rootPath = rootPath.replace("/", "\\");
		}

		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(1);
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public static void main(String[] args) {
		System.out.println("path:" + getClasspath());
	}
}