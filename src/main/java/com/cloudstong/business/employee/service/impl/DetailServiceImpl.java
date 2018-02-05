/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.employee.dao.DetailDao;
import com.cloudstong.business.employee.model.Detail;
import com.cloudstong.business.employee.service.DetailService;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2014-10-10 11:35:19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("detailService")
public class DetailServiceImpl implements DetailService {

	@Resource
	private DetailDao detailDao;

	@Override
	public List<Detail> getAllDetail() {
		return detailDao.getAll();
	}

	@Override
	public void doSaveDetail(Detail detail) {
		detailDao.saveOrUpdate(detail);
	}

	@Override
	public void doDeleteDetail(Long detailId) {
		detailDao.delById(detailId);
	}

	@Override
	public Detail findDetailById(Long detailId) {
		return (Detail)detailDao.getById(detailId);
	}

	@Override
	public void doUpdateDetail(Detail detail) {
		detailDao.update(detail);
	}

	@Override
	public PageResult queryDetail(QueryCriteria queryCriteria){
		return detailDao.query(queryCriteria);
	}

	@Override
	public void doDeleteDetails(Long[] detailIds) {
		for (Long id : detailIds) {
			doDeleteDetail(id);
		}
	}
	@Override
	public Detail findDetailByEmployeeId(Long employeeId) {
		List<Detail> detailList = detailDao.getBySqlKey("getByEmployeeId", employeeId);
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		return detailList.get(0);
	}
}
