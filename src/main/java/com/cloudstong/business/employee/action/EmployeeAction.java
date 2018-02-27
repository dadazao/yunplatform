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

import com.cloudstong.business.employee.model.Employee;
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
@Namespace("/pages/business/employee")
@Results(value = { 
	@Result(name = "list", location = "/WEB-INF/view/business/employee/employeeList.jsp"),
	@Result(name = "add", location = "/WEB-INF/view/business/employee/employeeEdit.jsp"),
	@Result(name = "edit", location = "/WEB-INF/view/business/employee/employeeEdit.jsp"),
	@Result(name = "view", location = "/WEB-INF/view/business/employee/employeeView.jsp")	
})
public class EmployeeAction extends BaseAction {

	@Resource
	private EmployeeService employeeService;
	
	private Employee employee;
	
	private Long[] employeeIDs;
	
	@Action("save")
	public String save() throws Exception{
	    try {
			employeeService.doSaveEmployee(employee);
			printSuccess(employee);
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
	    this.employee = employeeService.findEmployeeById(employee.getId());
		return "edit";
	}
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (employeeIDs != null) {
				employeeService.doDeleteEmployees(employeeIDs);
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
		this.employee = employeeService.findEmployeeById(employee.getId());
		return "view";
	}
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(employee != null) {
			if(employee.getName() != null && !"".equals(employee.getName())) {
				queryCriteria.addQueryCondition("name", "%" + employee.getName() + "%");
			}
			if(employee.getCode() != null && !"".equals(employee.getCode())) {
				queryCriteria.addQueryCondition("code", "%" + employee.getCode() + "%");
			}
			if(employee.getBirthday() != null && !"".equals(employee.getBirthday())) {
				queryCriteria.addQueryCondition("birthday", employee.getBirthday());
			}
			if(employee.getPhone() != null && !"".equals(employee.getPhone())) {
				queryCriteria.addQueryCondition("phone", "%" + employee.getPhone() + "%");
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
		
		this.pageResult = employeeService.queryEmployee(queryCriteria);
		
		return "list";
	}
	@Action("add")
	public String add() {
		employee = new Employee();
		return "add";
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Long[] getEmployeeIDs() {
		return employeeIDs;
	}

	public void setEmployeeIDs(Long[] employeeIDs) {
		this.employeeIDs = employeeIDs;
	}

}
