/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.action;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.business.employee.model.Detail;
import com.cloudstong.business.employee.service.DetailService;
import com.cloudstong.business.employee.service.EmployeeService;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;

/**
 * @author Allan
 * 
 *         Created on 2014-10-10 11:35:19
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/business/employee/detail")
@Results(value = { @Result(name = "edit", location = "/WEB-INF/view/business/employee/detailEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/business/employee/detailView.jsp") })
public class DetailAction extends BaseAction {

	@Resource
	private DetailService detailService;
	
	private Detail detail;
	
	private Long[] detailIDs;
	
	@Resource
	private EmployeeService employeeService;
	
	private Long employeeId;
	@Action("save")
	public String save() throws Exception {
		try {
			detail.setEmployeeId(employeeId);
			detailService.doSaveDetail(detail);
			printSuccess(detail);
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
		detail = detailService.findDetailByEmployeeId(employeeId);
		if (detail == null) {
			detail = new Detail();
		}
		return "edit";
	}
	@Action("view")
	public String view() {
		detail = detailService.findDetailByEmployeeId(employeeId);
		return "view";
	}
	
	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}
	
	public Long[] getDetailIDs() {
		return detailIDs;
	}

	public void setDetailIDs(Long[] detailIDs) {
		this.detailIDs = detailIDs;
	}
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}
