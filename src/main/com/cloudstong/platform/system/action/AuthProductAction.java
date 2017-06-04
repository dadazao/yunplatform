/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.AuthProduct;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthProductService;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/authProduct")
@Results(value = { @Result(name = "list", location = "/pages/system/authorization/authProductList.jsp"),
		@Result(name = "add", location = "/pages/system/authorization/authProductEdit.jsp"), 
		@Result(name = "edit", location = "/pages/system/authorization/authProductEdit.jsp"),
		@Result(name = "view", location = "/pages/system/authorization/authProductView.jsp") })
public class AuthProductAction extends BaseAction {
	
	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -7567255986014503422L;

	@Resource
	private AuthProductService authProductService;
	
	private AuthProduct authProduct;
	
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (authProduct != null) {
			if (authProduct.getName() != null && !"".equals(authProduct.getName())) {
				queryCriteria.addQueryCondition("tbl_name", "%" + authProduct.getName().trim() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		this.pageResult = authProductService.queryAuthProduct(queryCriteria);

		return "list";
	}
	
	@Action("add")
	public String add() {
		authProduct = new AuthProduct();
		return "add";
	}

	@Action("edit")
	public String edit() {
		this.authProduct = authProductService.findAuthProductById(authProduct.getId());
		return "edit";
	}
	
	@Action("save")
	public String save() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			authProductService.doSaveOrUpdate(authProduct, user);
			printJSON("success", false, String.valueOf(authProduct.getId()));
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	
	@Action("delete")
	public String delete() throws Exception {
		try {
			if (selectedIDs != null) {
				authProductService.doDeleteAuthProducts(selectedIDs);
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
		this.authProduct = authProductService.findAuthProductById(authProduct.getId());
		return "view";
	}

	/**
	 * @return Returns the value of authProduct field with the type AuthProduct.
	 */
	public AuthProduct getAuthProduct() {
		return authProduct;
	}

	/**
	 * @param authProduct. Set the value of authProduct field with the type AuthProduct by the authProduct parameter.
	 */
	public void setAuthProduct(AuthProduct authProduct) {
		this.authProduct = authProduct;
	}
	
	
	

}
