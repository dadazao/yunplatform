package com.cloudstong.platform.system.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
public class CompexLogoAction extends CompexDomainAction {

	private static final long serialVersionUID = -6321328142259780078L;

	@Resource
	private LogoService logoService;
	
	/* 
	 * 显示Logo列表
	 */
	public String list() {
		super.list();
		return "list";
	}

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
	
	public String delete() throws IOException {
		logoService.doDelete(selectedIDs);
		printJSON("success");
		return NONE;
	}
}
