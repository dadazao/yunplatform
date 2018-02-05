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

import com.cloudstong.business.product.model.Pingjia;
import com.cloudstong.business.product.service.PingjiaService;
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
@Namespace("/pages/business/product/pingjia")
@Results(value = {
		@Result(name = "edit", location = "/WEB-INF/view/business/product/pingjiaEdit.jsp"),
		@Result(name = "sublist", location = "/WEB-INF/view/business/product/pingjiaList.jsp"), })
public class PingjiaAction extends BaseAction {

	@Resource
	private PingjiaService pingjiaService;
	
	private Pingjia pingjia;
	
	private Long[] pingjiaIDs;
	
	@Resource
	private ProductService productService;
	
	private Long productId;
	private Long pingjiaId;
	@Action("save")
	public String save() throws Exception {
		try {
			pingjia.setProductId(productId);
			pingjiaService.doSavePingjia(pingjia);
			printJSON("success", false, String.valueOf(pingjia.getId()));
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
		pingjia = pingjiaService.findPingjiaById(pingjiaId);
		return "edit";
	}
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (pingjiaIDs != null) {
				pingjiaService.doDeletePingjias(pingjiaIDs);
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
	@SuppressWarnings("unchecked")
	@Action("sublist")
	public String sublist() {

		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		this.numPerPage = 10;
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		// 将主记录id作为查询条件
		queryCriteria.getQueryCondition().put("productId", productId);

		pageResult = pingjiaService.queryPingjia(queryCriteria);

		return "sublist";
	}
	
	public Pingjia getPingjia() {
		return pingjia;
	}

	public void setPingjia(Pingjia pingjia) {
		this.pingjia = pingjia;
	}
	
	public Long[] getPingjiaIDs() {
		return pingjiaIDs;
	}

	public void setPingjiaIDs(Long[] pingjiaIDs) {
		this.pingjiaIDs = pingjiaIDs;
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getPingjiaId() {
		return pingjiaId;
	}

	public void setPingjiaId(Long pingjiaId) {
		this.pingjiaId = pingjiaId;
	}

}
