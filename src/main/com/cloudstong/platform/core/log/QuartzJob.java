/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.log;

import javax.annotation.Resource;

import com.cloudstong.platform.resource.metadata.service.TableService;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
public class QuartzJob {
	
	@Resource
	private TableService tableService;
	
	public void createTable(){
		tableService.doAutoCreateLogTable();
	}
	
}
