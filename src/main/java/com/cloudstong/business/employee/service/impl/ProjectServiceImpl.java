/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.employee.dao.ProjectDao;
import com.cloudstong.business.employee.model.Project;
import com.cloudstong.business.employee.service.ProjectService;
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
@Repository("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectDao projectDao;

	@Override
	public List<Project> getAllProject() {
		return projectDao.getAll();
	}

	@Override
	public void doSaveProject(Project project) {
		projectDao.saveOrUpdate(project);
	}

	@Override
	public void doDeleteProject(Long projectId) {
		projectDao.delById(projectId);
	}

	@Override
	public Project findProjectById(Long projectId) {
		return (Project)projectDao.getById(projectId);
	}

	@Override
	public void doUpdateProject(Project project) {
		projectDao.update(project);
	}

	@Override
	public PageResult queryProject(QueryCriteria queryCriteria){
		return projectDao.query(queryCriteria);
	}

	@Override
	public void doDeleteProjects(Long[] projectIds) {
		for (Long id : projectIds) {
			doDeleteProject(id);
		}
	}
	@Override
	public Project findProjectByEmployeeId(Long employeeId) {
		List<Project> projectList = projectDao.getBySqlKey("getByEmployeeId", employeeId);
		if (projectList == null || projectList.isEmpty()) {
			return null;
		}
		return projectList.get(0);
	}
}
