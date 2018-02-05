/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.service.SubSystemService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-30
 * 
 *         Description:
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/system/subsystem")
public class SubSystemAction extends BaseAction {
	private Long subSystemId;
	
	@Resource
	private SubSystemService subSystemService;
	
	@Action("visit")
	public String visit() throws Exception {
		SubSystem subSystem = subSystemService.getById(subSystemId);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		objectMapper.writeValue(out, subSystem);
		return NONE;
	}

	public Long getSubSystemId() {
		return subSystemId;
	}

	public void setSubSystemId(Long subSystemId) {
		this.subSystemId = subSystemId;
	}
	
	

}
