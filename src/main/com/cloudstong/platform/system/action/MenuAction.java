package com.cloudstong.platform.system.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
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
	public String load() throws Exception{
		List<MenuItem> menuItems = menuService.getDefaultMenu();
		HttpServletRequest request = getRequest();
		SysUser user = (SysUser)request.getSession().getAttribute("user");
//		List<Seqcode> seqcodes = user.getSeqcode();
//		List<MenuItem> result = new ArrayList<MenuItem>();
//		for(int i=0;menuItems!=null && i< menuItems.size(); i++) {
//			MenuItem item = menuItems.get(i);
//			for(int j=0; seqcodes!=null && j<seqcodes.size(); j++) {
//				Seqcode code = seqcodes.get(j);
//				if(code.getTblValue().equals(item.getCatalogCode()) && !result.contains(item)) {
//					result.add(item);
//				}
//			}
//		}
//		ObjectMapper mapper = new ObjectMapper();
//		HttpServletResponse response = getResponse();
//		response.setCharacterEncoding("utf-8");
//		PrintWriter writer = response.getWriter();
//		mapper.writeValue(writer, result);
		return NONE;
	}
	
	/**
	 * Description:更换菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String change() throws Exception{
		try{
			menuService.doUpdateMenu(id);
			printJSON("success", false);
		}catch(Exception e) {
			printJSON("fail", false);
		}
		return NONE;
	}

}
