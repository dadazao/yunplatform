package com.cloudstong.platform.system.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.Logo;
import com.cloudstong.platform.system.service.LogoService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:Logo Action(new)
 */
@ParentPackage("default")
@Namespace("/pages/system/logo")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/system/logo/compexLogoList.jsp"),
		@Result(name="add", location = "/WEB-INF/view/system/logo/logoEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/system/logo/logoView.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/system/logo/logoEdit.jsp")
})
public class CompexLogoAction extends CompexDomainAction {

	private static final long serialVersionUID = -6321328142259780078L;

	@Resource
	private LogoService logoService;
	
	/* 
	 * 显示Logo列表
	 */
	@Action("list")
	public String list() {
		return super.list();
	}

	@Action("showLogo")
	public String showLogo() throws IOException {
		Logo logo = logoService.findDefaultLogo();
		getRequest().getSession().setAttribute("defaultLogo", logo);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(logo.getLogoPath());
		return NONE;
	}
	
	@Action("view")
	public String view() {
		try {
			Long id = Long.valueOf(getRequest().getParameter("id"));
			Logo logo = logoService.findLogoById(id);
			getRequest().setAttribute("logoPath", logo.getLogoPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view";
	}
	
	@Action("del")
	public String delete() throws IOException {
		logoService.doDelete(selectedIDs);
		printJSON("success");
		return NONE;
	}
	
	@Action("edit")
	public String edit() {
		return "edit";
	}
	
	@Action("add")
	public String add() {
		return super.add();
	}
	
	@Action("save")
	public String save() throws Exception {
		return super.save();
	}
}
