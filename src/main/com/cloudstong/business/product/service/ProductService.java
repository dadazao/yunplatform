/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.service;

import java.util.List;

import com.cloudstong.business.product.model.Product;
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
public interface ProductService {
	
	public PageResult queryProduct(QueryCriteria queryCriteria);

	public List<Product> getAllProduct();

	public void doSaveProduct(Product product);

	public void doUpdateProduct(Product product);

	public Product findProductById(Long productId);

	public void doDeleteProduct(Long productId);

	public void doDeleteProducts(Long[] productIds);
}
