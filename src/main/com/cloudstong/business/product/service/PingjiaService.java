/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.service;

import java.util.List;

import com.cloudstong.business.product.model.Pingjia;
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
public interface PingjiaService {
	
	public PageResult queryPingjia(QueryCriteria queryCriteria);

	public List<Pingjia> getAllPingjia();

	public void doSavePingjia(Pingjia pingjia);

	public void doUpdatePingjia(Pingjia pingjia);

	public Pingjia findPingjiaById(Long pingjiaId);

	public void doDeletePingjia(Long pingjiaId);

	public void doDeletePingjias(Long[] pingjiaIds);
	public Pingjia findPingjiaByProductId(Long productId);
}
