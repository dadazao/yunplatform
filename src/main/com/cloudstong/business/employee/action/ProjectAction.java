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

import com.cloudstong.business.employee.model.Project;
import com.cloudstong.business.employee.service.EmployeeService;
import com.cloudstong.business.employee.service.ProjectService;
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
@Namespace("/pages/business/employee/project")
@Results(value = {
		@Result(name = "edit", location = "/pages/business/employee/projectEdit.jsp"),
		@Result(name = "sublist", location = "/pages/business/employee/projectList.jsp"), })
public class ProjectAction extends BaseAction {

	@Resource
	private ProjectService projectService;
	
	private Project project;
	
	private Long[] projectIDs;
	
	@Resource
	private EmployeeService employeeService;
	
	private Long employeeId;
	private Long projectId;
	@Action("save")
	public String save() throws Exception {
		try {
			project.setEmployeeId(employeeId);
			projectService.doSaveProject(project);
			printJSON("success", false, String.valueOf(project.getId()));
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
		project = projectService.findProjectById(projectId);
		return "edit";
	}
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (projectIDs != null) {
				projectService.doDeleteProjects(projectIDs);
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
		queryCriteria.getQueryCondition().put("employeeId", employeeId);

		pageResult = projectService.queryProject(queryCriteria);

		return "sublist";
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Long[] getProjectIDs() {
		return projectIDs;
	}

	public void setProjectIDs(Long[] projectIDs) {
		this.projectIDs = projectIDs;
	}
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
