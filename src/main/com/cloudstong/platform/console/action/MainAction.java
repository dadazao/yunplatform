/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. ( 
 *
 ******************************************************************************/
package com.cloudstong.platform.console.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;
import com.cloudstong.platform.resource.tree.model.KMap;
import com.cloudstong.platform.system.model.Logo;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.Theme;
import com.cloudstong.platform.system.service.LogoService;
import com.cloudstong.platform.system.service.SysResourceService;
import com.cloudstong.platform.system.service.ThemeService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-16
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/")
@Results(value = { 
		@Result(name = "index", location = "/index.jsp")
})
@SuppressWarnings("serial")
public class MainAction extends BaseAction {
	@Resource
	private ThemeService themeService;
	@Resource
	private LogoService logoService;
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	@Resource
	private SysResourceService sysResourceService;
	
	@Action("main")
	public String main() {
		Theme theme = themeService.doGetDefaultTheme();
		Logo logo = logoService.findDefaultLogo();
		EnterpriseInfo enterpriseInfo = enterpriseInfoService.findDefaultInfo();
		
		HttpSession session = getSession();
		session.setAttribute("theme", theme);
		session.setAttribute("logo", logo);
		session.setAttribute("enterpriseInfo", enterpriseInfo);
		
		return "index";
	}
	
	@Action("getTreeData")
	public String getTreeData() throws Exception {
		List<KMap> resutList = new ArrayList<KMap>();
		
		SysUser user = (SysUser)getSession().getAttribute("user");		
		List<Catalog> resources = sysResourceService.queryResourceSysRole(Constants.DEFAULT_SYSTEM_ID,user);
		if(resources!=null && resources.size()>0) {
			for(Catalog resource:resources) {
				Long parentId = resource.getParentId();
				if(parentId == 1L){
					KMap kmap = new KMap();
					List<Catalog> _lstResult = dataFilter(resources, resource.getId(), false);
					List<Map> _lstZtree = new ArrayList<Map>();
					if (_lstResult != null && _lstResult.size() > 0) {
						for (Catalog sr:_lstResult) {
							Map ztree = new HashMap();
							ztree.put("id", sr.getId().toString());
							ztree.put("belong", sr.getId().toString());
							ztree.put("pId", sr.getParentId().toString());
							ztree.put("name", "<span style=''>"+sr.getName()+"</span>");
							ztree.put("path", sr.getPath());
							_lstZtree.add(ztree);
						}
					}
					kmap.setKey(resource);
					kmap.setValue(_lstZtree);
					resutList.add(kmap);
				}
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Writer writer = (Writer) response.getWriter();
		writer.write(objectMapper.writeValueAsString(resutList));
		
		return NONE;
	}
	
	private List<Catalog> dataFilter(List<Catalog> lstResult, Long parentid, boolean hasRoot){
		List<Catalog> lstJoinZtree = new ArrayList<Catalog>();
		for (int i = 0; i < lstResult.size(); ++i) {
			Catalog sr = (Catalog)lstResult.get(i);
			boolean flag = false; 
			if(parentid.equals(sr.getParentId())){
				flag = true; 
			}
			if(sr.getId().equals(parentid) && hasRoot){
				lstJoinZtree.add(sr);
			}
			if(flag){
				List<Catalog> lstTmp = dataFilter(lstResult, sr.getId(), true);
				lstJoinZtree.addAll(lstTmp);
			}
		}
		return lstJoinZtree;
	}

}
