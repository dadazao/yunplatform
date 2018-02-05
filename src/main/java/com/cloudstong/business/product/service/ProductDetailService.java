/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.service;

import java.util.List;

import com.cloudstong.business.product.model.ProductDetail;
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
public interface ProductDetailService {
	
	public PageResult queryProductDetail(QueryCriteria queryCriteria);

	public List<ProductDetail> getAllProductDetail();

	public void doSaveProductDetail(ProductDetail productDetail);

	public void doUpdateProductDetail(ProductDetail productDetail);

	public ProductDetail findProductDetailById(Long productDetailId);

	public void doDeleteProductDetail(Long productDetailId);

	public void doDeleteProductDetails(Long[] productDetailIds);
	public ProductDetail findProductDetailByProductId(Long productId);
}
