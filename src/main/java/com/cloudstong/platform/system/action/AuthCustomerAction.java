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
import com.cloudstong.platform.system.model.AuthCustomer;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthCustomerService;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/authCustomer")
@Results(value = { @Result(name = "list", location = "/WEB-INF/view/system/authorization/authCustomerList.jsp"),
		@Result(name = "add", location = "/WEB-INF/view/system/authorization/authCustomerEdit.jsp"), 
		@Result(name = "edit", location = "/WEB-INF/view/system/authorization/authCustomerEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/system/authorization/authCustomerView.jsp") })
public class AuthCustomerAction extends BaseAction {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -7179482490306928809L;
	
	@Resource
	private AuthCustomerService authCustomerService;
	
	private AuthCustomer authCustomer;

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (authCustomer != null) {
			if (authCustomer.getName() != null && !"".equals(authCustomer.getName())) {
				queryCriteria.addQueryCondition("tbl_name", "%" + authCustomer.getName().trim() + "%");
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

		this.pageResult = authCustomerService.queryAuthCustomer(queryCriteria);

		return "list";
	}
	
	@Action("add")
	public String add() {
		authCustomer = new AuthCustomer();
		return "add";
	}

	@Action("edit")
	public String edit() {
		this.authCustomer = authCustomerService.findAuthCustomerById(authCustomer.getId());
		return "edit";
	}
	
	@Action("save")
	public String save() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long cusId = authCustomerService.doSaveOrUpdate(authCustomer, user);
			this.authCustomer.setId(cusId);
			printJSON("success", false, String.valueOf(cusId));
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
				authCustomerService.doDeleteAuthCustomers(selectedIDs);
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
		this.authCustomer = authCustomerService.findAuthCustomerById(authCustomer.getId());
		return "view";
	}
	
	/**
	 * @return Returns the value of authCustomer field with the type AuthCustomer.
	 */
	public AuthCustomer getAuthCustomer() {
		return authCustomer;
	}

	/**
	 * @param authCustomer. Set the value of authCustomer field with the type AuthCustomer by the authCustomer parameter.
	 */
	public void setAuthCustomer(AuthCustomer authCustomer) {
		this.authCustomer = authCustomer;
	}
	
	
	
}
