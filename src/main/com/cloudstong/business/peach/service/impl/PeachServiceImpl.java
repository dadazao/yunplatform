/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.peach.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.peach.dao.PeachDao;
import com.cloudstong.business.peach.model.Peach;
import com.cloudstong.business.peach.service.PeachService;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2015-6-11 12:28:44
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("peachService")
public class PeachServiceImpl implements PeachService {

	@Resource
	private PeachDao peachDao;

	@Override
	public List<Peach> getAllPeach() {
		return peachDao.getAll();
	}

	@Override
	public void doSavePeach(Peach peach) {
		peachDao.saveOrUpdate(peach);
	}

	@Override
	public void doDeletePeach(Long peachId) {
		peachDao.delById(peachId);
	}

	@Override
	public Peach findPeachById(Long peachId) {
		return (Peach)peachDao.getById(peachId);
	}

	@Override
	public void doUpdatePeach(Peach peach) {
		peachDao.update(peach);
	}

	@Override
	public PageResult queryPeach(QueryCriteria queryCriteria){
		return peachDao.query(queryCriteria);
	}

	@Override
	public void doDeletePeachs(Long[] peachIds) {
		for (Long id : peachIds) {
			doDeletePeach(id);
		}
	}
}
