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
import com.cloudstong.platform.system.model.AuthCustomerHadware;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthCustomerHadwareService;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/authCustomerHadware")
@Results(value = { @Result(name = "list", location = "/pages/system/authorization/authCustomerHadwareList.jsp"),
		@Result(name = "edit", location = "/pages/system/authorization/authCustomerHadwareEdit.jsp"),
		@Result(name = "tab", location = "/pages/system/authorization/authCustomerHadwareTab.jsp")})
public class AuthCustomerHadwareAction extends BaseAction {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = 2043060671381297666L;
	
	@Resource
	private AuthCustomerHadwareService authCustomerHadwareService;
	
	private AuthCustomerHadware authCustomerHadware;
	
	private Long authCustomerId;
	
	private Long authCustomerHadwareId;

	
	@SuppressWarnings("unchecked")
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (authCustomerHadware != null) {
			
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		if(authCustomerId != null){
			queryCriteria.getQueryCondition().put("tbl_customerid", authCustomerId);
			
			this.pageResult = authCustomerHadwareService.queryAuthCustomerHadware(queryCriteria);
		}

		return "list";
	}
	
	@Action("edit")
	public String edit() {
		if(authCustomerHadwareId != null){
			this.authCustomerHadware = authCustomerHadwareService.findAuthCustomerHadwareById(authCustomerHadwareId);
		}
		return "edit";
	}
	
	@Action("save")
	public String save() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			authCustomerHadware.setCustomerid(authCustomerId);
			authCustomerHadwareService.doSaveOrUpdate(authCustomerHadware, user);
			printJSON("success", false, String.valueOf(authCustomerHadware.getId()));
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
			if (selectedSubIDs != null) {
				authCustomerHadwareService.doDeleteAuthCustomerHadwares(selectedSubIDs);
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

	@Action("tab")
	public String tab() {
		return "tab";
	}
	
	/**
	 * @return Returns the value of authCustomerHadware field with the type AuthCustomerHadware.
	 */
	public AuthCustomerHadware getAuthCustomerHadware() {
		return authCustomerHadware;
	}

	/**
	 * @param authCustomerHadware. Set the value of authCustomerHadware field with the type AuthCustomerHadware by the authCustomerHadware parameter.
	 */
	public void setAuthCustomerHadware(AuthCustomerHadware authCustomerHadware) {
		this.authCustomerHadware = authCustomerHadware;
	}

	/**
	 * @return Returns the value of authCustomerId field with the type Long.
	 */
	public Long getAuthCustomerId() {
		return authCustomerId;
	}

	/**
	 * @param authCustomerId. Set the value of authCustomerId field with the type Long by the authCustomerId parameter.
	 */
	public void setAuthCustomerId(Long authCustomerId) {
		this.authCustomerId = authCustomerId;
	}

	/**
	 * @return Returns the value of authCustomerHadwareId field with the type Long.
	 */
	public Long getAuthCustomerHadwareId() {
		return authCustomerHadwareId;
	}

	/**
	 * @param authCustomerHadwareId. Set the value of authCustomerHadwareId field with the type Long by the authCustomerHadwareId parameter.
	 */
	public void setAuthCustomerHadwareId(Long authCustomerHadwareId) {
		this.authCustomerHadwareId = authCustomerHadwareId;
	}
	
	
	

}
