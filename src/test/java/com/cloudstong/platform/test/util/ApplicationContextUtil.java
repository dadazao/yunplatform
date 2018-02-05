/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.util;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Allan
 * 
 *         Created on 2013-8-30
 * 
 *         Description:
 * 
 */
public class ApplicationContextUtil {
	private static ApplicationContext applicationContext = null;
	
	private static ApplicationContext getInstance() {
		if(applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
		return applicationContext;
	}
	
	public static Object getBean(Class cls) {
		return getInstance().getBean(cls);
	}

	public static Object getBean(String beanId) {
		return getInstance().getBean(beanId);
	}
	
	public static void main(String[] args) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)getBean("jdbcTemplate");
		List  list = jdbcTemplate.queryForList("select * from sys_log");
		System.out.println(list.size());
	}

}
