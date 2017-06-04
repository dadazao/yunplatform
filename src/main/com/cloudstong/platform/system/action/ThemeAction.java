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
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.Logo;
import com.cloudstong.platform.system.model.Theme;
import com.cloudstong.platform.system.service.LogoService;
import com.cloudstong.platform.system.service.ThemeService;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主题管理Action
 */
@ParentPackage("default")
@Namespace("/theme")
@Results(value = { 
		@Result(name = "list", location = "/pages/system/theme/compexThemeList.jsp")
})
public class ThemeAction extends CompexDomainAction {

	@Resource
	private ThemeService themeService;
	
	@Resource
	private LogoService logoService;
	
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	
	public ThemeService getThemeService() {
		return themeService;
	}

	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}
	
	@Action("list")
	public String list(){
		return super.list();
	}

	/**
	 * Description:更换主题
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action("change")
	public String change() throws IOException {
		if (selectedIDs != null) {
			themeService.doUpdateTheme(selectedIDs[0]);
		}
		printJSON("success", false);
		return NONE;
	}

	/**
	 * Description:加载主题
	 */
	@Action("load")
	public String load() throws IOException {
		try {
			Theme theme = themeService.doGetDefaultTheme();
			
			Logo logo = logoService.findDefaultLogo();
			EnterpriseInfo enterpriseInfo = enterpriseInfoService.findDefaultInfo();
			theme.setDefaultLogo(logo);
			theme.setEnterpriseInfo(enterpriseInfo);
			
			ObjectMapper mapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			mapper.writeValue(writer, theme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/** 
	 * 删除主题
	 */
	@Action("delete")
	public String delete() throws IOException {
		Theme theme = themeService.doGetDefaultTheme();
		boolean flag = false;
		for(Long id:selectedIDs) {
			if(id.equals(theme.getId())) {
				flag = true;
			}
		}
		if(flag) {
			printJSON("200","默认主题不能删除！",false);
		}else{
			themeService.doDelete(selectedIDs);
			printJSON("success");
		}
		return NONE;
	}
}
