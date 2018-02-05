/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cloudstong.platform.system.service.SysResourceService;

/**
 * @author Allan
 * 
 *         Created on 2013-11-6
 * 
 *         Description:
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public class SysResourceServiceTestCase {
	
	@Resource
	private SysResourceService sysResourceService;
	
	@Test
	public void testQueryResourceSysRole(){
		
	}

}
