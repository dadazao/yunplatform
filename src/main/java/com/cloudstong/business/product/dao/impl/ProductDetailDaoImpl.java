/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.product.dao.ProductDetailDao;
import com.cloudstong.business.product.model.ProductDetail;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

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
@Repository("productDetailDao")
public class ProductDetailDaoImpl extends BaseMyBatisDaoImpl<ProductDetail, Long> implements ProductDetailDao  {

}
