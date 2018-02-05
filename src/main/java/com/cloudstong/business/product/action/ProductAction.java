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

import com.cloudstong.business.product.model.Product;
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
@Namespace("/pages/business/product")
@Results(value = { 
	@Result(name = "list", location = "/WEB-INF/view/business/product/productList.jsp"),
	@Result(name = "add", location = "/WEB-INF/view/business/product/productEdit.jsp"),
	@Result(name = "edit", location = "/WEB-INF/view/business/product/productEdit.jsp"),
	@Result(name = "view", location = "/WEB-INF/view/business/product/productView.jsp")
})
public class ProductAction extends BaseAction {

	@Resource
	private ProductService productService;
	
	private Product product;
	
	private Long[] productIDs;
	
	@Action("save")
	public String save() throws Exception{
	    try {
			productService.doSaveProduct(product);
			printSuccess(product);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
	    return NONE;
	}
	@Action("edit")
	public String edit() {
	    this.product = productService.findProductById(product.getId());
		return "edit";
	}
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (productIDs != null) {
				productService.doDeleteProducts(productIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	@Action("view")
	public String view() {
		this.product = productService.findProductById(product.getId());
		return "view";
	}
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(product != null) {
			if(product.getName() != null && !"".equals(product.getName())) {
				queryCriteria.addQueryCondition("name", "%" + product.getName() + "%");
			}
			if(product.getPrice() != null && !"".equals(product.getPrice())) {
				queryCriteria.addQueryCondition("price", "%" + product.getPrice() + "%");
			}
			if(product.getDescription() != null && !"".equals(product.getDescription())) {
				queryCriteria.addQueryCondition("description", "%" + product.getDescription() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		
		//列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		this.pageResult = productService.queryProduct(queryCriteria);
		
		return "list";
	}
	@Action("add")
	public String add() {
		product = new Product();
		return "add";
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Long[] getProductIDs() {
		return productIDs;
	}

	public void setProductIDs(Long[] productIDs) {
		this.productIDs = productIDs;
	}

}
