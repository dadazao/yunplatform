/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.util.List;

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
import com.cloudstong.platform.system.model.AuthTemplate;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthProductService;
import com.cloudstong.platform.system.service.AuthTemplateService;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/authTemplate")
@Results(value = { @Result(name = "list", location = "/pages/system/authorization/authTemplateList.jsp"),
		@Result(name = "add", location = "/pages/system/authorization/authTemplateEdit.jsp"), 
		@Result(name = "edit", location = "/pages/system/authorization/authTemplateEdit.jsp"),
		@Result(name = "view", location = "/pages/system/authorization/authTemplateView.jsp") })
public class AuthTemplateAction extends BaseAction {
	
	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -939557928730636063L;
	
	@Resource
	private AuthTemplateService authTemplateService;
	@Resource
	private AuthProductService authProductService;
	
	private AuthTemplate authTemplate;
	
	private AuthProduct authProduct;
	
	private List<AuthProduct> authProducts;
	
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (authTemplate != null) {
			if (authTemplate.getName() != null && !"".equals(authTemplate.getName())) {
				queryCriteria.addQueryCondition("at.tbl_name", "%" + authTemplate.getName().trim() + "%");
			}
			if (authTemplate.getProductname() != null && !"".equals(authTemplate.getProductname())) {
				queryCriteria.addQueryCondition("at.tbl_productid", "%" + authTemplate.getProductname().trim() + "%");
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

		this.pageResult = authTemplateService.queryAuthTemplate(queryCriteria);
		
		authProducts = authProductService.queryAll();

		return "list";
	}
	
	@Action("add")
	public String add() {
		authTemplate = new AuthTemplate();
		authProducts = authProductService.queryAll();
		return "add";
	}

	@Action("edit")
	public String edit() {
		this.authTemplate = authTemplateService.findAuthTemplateById(authTemplate.getId());
		authProducts = authProductService.queryAll();
		return "edit";
	}
	
	@Action("save")
	public String save() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			authTemplateService.doSaveOrUpdate(authTemplate, user);
			printJSON("success", false, String.valueOf(authTemplate.getId()));
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
				authTemplateService.doDeleteAuthTemplates(selectedIDs);
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
		this.authTemplate = authTemplateService.findAuthTemplateById(authTemplate.getId());
		this.authProduct = authProductService.findAuthProductById(authTemplate.getProductid());
		return "view";
	}

	/**
	 * @return Returns the value of authTemplate field with the type AuthTemplate.
	 */
	public AuthTemplate getAuthTemplate() {
		return authTemplate;
	}

	/**
	 * @param authTemplate. Set the value of authTemplate field with the type AuthTemplate by the authTemplate parameter.
	 */
	public void setAuthTemplate(AuthTemplate authTemplate) {
		this.authTemplate = authTemplate;
	}

	/**
	 * @return Returns the value of authProducts field with the type List<AuthProduct>.
	 */
	public List<AuthProduct> getAuthProducts() {
		return authProducts;
	}

	/**
	 * @param authProducts. Set the value of authProducts field with the type List<AuthProduct> by the authProducts parameter.
	 */
	public void setAuthProducts(List<AuthProduct> authProducts) {
		this.authProducts = authProducts;
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
