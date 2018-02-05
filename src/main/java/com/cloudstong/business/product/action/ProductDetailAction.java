/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.action;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.business.product.model.ProductDetail;
import com.cloudstong.business.product.service.ProductDetailService;
import com.cloudstong.business.product.service.ProductService;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;

/**
 * @author Allan
 * 
 *         Created on 2016-4-21 20:36:10
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/business/product/productDetail")
@Results(value = { @Result(name = "edit", location = "/WEB-INF/view/business/product/productDetailEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/business/product/productDetailView.jsp") })
public class ProductDetailAction extends BaseAction {

	@Resource
	private ProductDetailService productDetailService;
	
	private ProductDetail productDetail;
	
	private Long[] productDetailIDs;
	
	@Resource
	private ProductService productService;
	
	private Long productId;
	@Action("save")
	public String save() throws Exception {
		try {
			productDetail.setProductId(productId);
			productDetailService.doSaveProductDetail(productDetail);
			printSuccess(productDetail);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	@Action("edit")
	public String edit() {
		productDetail = productDetailService.findProductDetailByProductId(productId);
		if (productDetail == null) {
			productDetail = new ProductDetail();
		}
		return "edit";
	}
	@Action("view")
	public String view() {
		productDetail = productDetailService.findProductDetailByProductId(productId);
		return "view";
	}
	
	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}
	
	public Long[] getProductDetailIDs() {
		return productDetailIDs;
	}

	public void setProductDetailIDs(Long[] productDetailIDs) {
		this.productDetailIDs = productDetailIDs;
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
