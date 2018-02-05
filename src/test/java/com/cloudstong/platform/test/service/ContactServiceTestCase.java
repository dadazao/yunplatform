/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cloudstong.platform.email.model.Contact;
import com.cloudstong.platform.email.service.ContactService;

/**
 * @author Jason
 * 
 *         Created on 2013-9-17
 * 
 *         Description:
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext-cache.xml", "classpath:applicationContext-dao.xml",
		"classpath:applicationContext-resources.xml", "classpath:applicationContext-service.xml" })
public class ContactServiceTestCase {

	@Resource
	private ContactService contactService;

	@Test
	public void testInsert() {
		for (int i = 0; i < 10; i++) {
			Contact c = new Contact();
			c.setEmail(i + "@163.com");
			c.setLastUsedTime(new Date());
			c.setUserId(2052751705L);
			contactService.doSaveContact(c);
		}
	}

}
