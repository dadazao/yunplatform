/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.product.dao.PingjiaDao;
import com.cloudstong.business.product.model.Pingjia;
import com.cloudstong.business.product.service.PingjiaService;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2016-4-21 20:36:10
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("pingjiaService")
public class PingjiaServiceImpl implements PingjiaService {

	@Resource
	private PingjiaDao pingjiaDao;

	@Override
	public List<Pingjia> getAllPingjia() {
		return pingjiaDao.getAll();
	}

	@Override
	public void doSavePingjia(Pingjia pingjia) {
		pingjiaDao.saveOrUpdate(pingjia);
	}

	@Override
	public void doDeletePingjia(Long pingjiaId) {
		pingjiaDao.delById(pingjiaId);
	}

	@Override
	public Pingjia findPingjiaById(Long pingjiaId) {
		return (Pingjia)pingjiaDao.getById(pingjiaId);
	}

	@Override
	public void doUpdatePingjia(Pingjia pingjia) {
		pingjiaDao.update(pingjia);
	}

	@Override
	public PageResult queryPingjia(QueryCriteria queryCriteria){
		return pingjiaDao.query(queryCriteria);
	}

	@Override
	public void doDeletePingjias(Long[] pingjiaIds) {
		for (Long id : pingjiaIds) {
			doDeletePingjia(id);
		}
	}
	@Override
	public Pingjia findPingjiaByProductId(Long productId) {
		List<Pingjia> pingjiaList = pingjiaDao.getBySqlKey("getByProductId", productId);
		if (pingjiaList == null || pingjiaList.isEmpty()) {
			return null;
		}
		return pingjiaList.get(0);
	}
}
