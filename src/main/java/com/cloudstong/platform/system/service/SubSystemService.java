/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.system.model.SubSystem;

/**
 * @author Allan
 * 
 *         Created on 2014-10-15
 * 
 *         Description:
 * 
 */
public interface SubSystemService {

	List<SubSystem> getLocalSystem();

	SubSystem getById(Long id);

}
