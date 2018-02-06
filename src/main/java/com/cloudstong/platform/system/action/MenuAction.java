package com.cloudstong.platform.system.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.MenuItem;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.MenuService;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单管理Action
 */
@ParentPackage("default")
@Namespace("/pages/system/menu")
@Results(value = { 
		@Result(name="menuhelp", location = "/WEB-INF/view/core/help/menuhelp.jsp")
})
public class MenuAction extends BaseAction{
	
	private Long id;
	
	/**
	 * <code>menuService</code> 菜单服务.
	 */
	@Resource
	private MenuService menuService;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * Description:加载菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action("load")
	public String load() throws Exception{
		List<MenuItem> menuItems = menuService.getDefaultMenu();
		HttpServletRequest request = getRequest();
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		return NONE;
	}
	
	/**
	 * Description:更换菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action("change")
	public String change() throws Exception{
		try{
			menuService.doUpdateMenu(id);
			printJSON("success", false);
		}catch(Exception e) {
			printJSON("fail", false);
		}
		return NONE;
	}
	
	public String help() {
		return "menuhelp";
	}

}
