/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.employee.dao.ProjectDao;
import com.cloudstong.business.employee.model.Project;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

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
@Repository("projectDao")
public class ProjectDaoImpl extends BaseMyBatisDaoImpl<Project, Long> implements ProjectDao  {

}
