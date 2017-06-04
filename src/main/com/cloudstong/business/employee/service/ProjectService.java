/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service;

import java.util.List;

import com.cloudstong.business.employee.model.Project;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2014-10-10 11:35:19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public interface ProjectService {
	
	public PageResult queryProject(QueryCriteria queryCriteria);

	public List<Project> getAllProject();

	public void doSaveProject(Project project);

	public void doUpdateProject(Project project);

	public Project findProjectById(Long projectId);

	public void doDeleteProject(Long projectId);

	public void doDeleteProjects(Long[] projectIds);
	public Project findProjectByEmployeeId(Long employeeId);
}
