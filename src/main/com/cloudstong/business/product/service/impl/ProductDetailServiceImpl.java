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

import com.cloudstong.business.product.dao.ProductDetailDao;
import com.cloudstong.business.product.model.ProductDetail;
import com.cloudstong.business.product.service.ProductDetailService;
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
@Repository("productDetailService")
public class ProductDetailServiceImpl implements ProductDetailService {

	@Resource
	private ProductDetailDao productDetailDao;

	@Override
	public List<ProductDetail> getAllProductDetail() {
		return productDetailDao.getAll();
	}

	@Override
	public void doSaveProductDetail(ProductDetail productDetail) {
		productDetailDao.saveOrUpdate(productDetail);
	}

	@Override
	public void doDeleteProductDetail(Long productDetailId) {
		productDetailDao.delById(productDetailId);
	}

	@Override
	public ProductDetail findProductDetailById(Long productDetailId) {
		return (ProductDetail)productDetailDao.getById(productDetailId);
	}

	@Override
	public void doUpdateProductDetail(ProductDetail productDetail) {
		productDetailDao.update(productDetail);
	}

	@Override
	public PageResult queryProductDetail(QueryCriteria queryCriteria){
		return productDetailDao.query(queryCriteria);
	}

	@Override
	public void doDeleteProductDetails(Long[] productDetailIds) {
		for (Long id : productDetailIds) {
			doDeleteProductDetail(id);
		}
	}
	@Override
	public ProductDetail findProductDetailByProductId(Long productId) {
		List<ProductDetail> productDetailList = productDetailDao.getBySqlKey("getByProductId", productId);
		if (productDetailList == null || productDetailList.isEmpty()) {
			return null;
		}
		return productDetailList.get(0);
	}
}
