/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SysLog;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * 
 *         Created on 2014-10-14
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/")
@Results(value = { 
		@Result(name = "logout", location = "/login.jsp")
})
@SuppressWarnings("serial")
public class LogoutAction extends BaseAction{
	
	@Action("logout")
	public String logout() throws IOException {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		
		request.getSession().setAttribute("user", null);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		
		return "logout";
	}

}
