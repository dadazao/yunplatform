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

import com.cloudstong.business.product.dao.ProductDao;
import com.cloudstong.business.product.model.Product;
import com.cloudstong.business.product.service.ProductService;
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
@Repository("productService")
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductDao productDao;

	@Override
	public List<Product> getAllProduct() {
		return productDao.getAll();
	}

	@Override
	public void doSaveProduct(Product product) {
		productDao.saveOrUpdate(product);
	}

	@Override
	public void doDeleteProduct(Long productId) {
		productDao.delById(productId);
	}

	@Override
	public Product findProductById(Long productId) {
		return (Product)productDao.getById(productId);
	}

	@Override
	public void doUpdateProduct(Product product) {
		productDao.update(product);
	}

	@Override
	public PageResult queryProduct(QueryCriteria queryCriteria){
		return productDao.query(queryCriteria);
	}

	@Override
	public void doDeleteProducts(Long[] productIds) {
		for (Long id : productIds) {
			doDeleteProduct(id);
		}
	}
}
