/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.BpmTypeDao;
import com.cloudstong.platform.third.bpm.model.BpmType;

/**
 * @author Allan
 * 
 *         Created on 2014-9-11
 * 
 *         Description:
 * 
 */
@Service
public class BpmTypeService {
	@Resource
	private BpmTypeDao dao;
	
	public List<BpmType> findAll(){
		return dao.getAll();
	}

	public BpmType getById(Long typeId) {
		return (BpmType)dao.getById(typeId);
	}
}
