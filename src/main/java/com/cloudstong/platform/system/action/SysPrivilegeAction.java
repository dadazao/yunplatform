/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.service.FormButtonService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.service.TabulationButtonService;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysPrivilegeService;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/system/privilege")
@Results(value = { 
		@Result(name = "privilegeList", location = "/WEB-INF/view/system/privilege/privilegeList.jsp"),
		@Result(name = "add", location = "/WEB-INF/view/system/privilege/privilegeEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/system/privilege/privilegeView.jsp"),
		@Result(name = "edit", location = "/WEB-INF/view/system/privilege/privilegeEdit.jsp"),
		@Result(name = "addResource", location = "/WEB-INF/view/system/privilege/resourceEdit.jsp"),
		@Result(name = "editResource", location = "/WEB-INF/view/system/privilege/resourceEdit.jsp"),
		@Result(name = "resourceList", location = "/WEB-INF/view/system/privilege/resourceList.jsp"),
		@Result(name = "catalog", location = "/WEB-INF/view/system/privilege/catalogTree.jsp"),
		@Result(name = "index", location = "/WEB-INF/view/system/privilege/privilegeIndex.jsp"),
		@Result(name = "readme", location = "/WEB-INF/view/system/privilege/readme.jsp")
})
public class SysPrivilegeAction extends BaseAction {
	@Resource
	private SysPrivilegeService sysPrivilegeService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private CatalogService catalogService;
	@Resource
	private TabulationService tabulationService;
	@Resource
	private TabulationButtonService tabulationButtonService;
	@Resource
	private FormButtonService formButtonService;
	
	private SysPrivilege sysPrivilege;
	
	private SysResource sysResource;
	
	private Long module;
	
	private Long privilegeId;
	
	private Long resourceId;
	
	private String status;
	
	private List<Dictionary> resourceTypes;
	
	@Action("index")
	public String index() {
		return "index";
	}
	
	@Action("catalog")
	public String catalog() {
		return "catalog";
	}
	
	@Action("readme")
	public String readme() {
		return "readme";
	}

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	@Action("privilegeshowCatalogTree")
	public String showCatalogTree() {
		try {
			List<Map> _lstZtree = new ArrayList<Map>();
			List<Catalog> _lstCatalog = catalogService.getSysCatalogTree();
			for (Catalog catalog: _lstCatalog) {
				Map ztree = new HashMap();
				ztree.put("id", catalog.getId());
				ztree.put("pId", catalog.getParentId());
				ztree.put("name", catalog.getName());
				if(catalog.getId().equals(1L)){
					ztree.put("open", true);
				}else{
					ztree.put("open", false);
				}
				if(!catalog.getId().equals(1L)){
					_lstZtree.add(ztree);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstZtree));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	@Action("privilegeauthList")
	public String authList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(module!=null && !module.equals(0l)){
				queryCriteria.addQueryCondition("tbl_module", module);
				this.pageResult = sysPrivilegeService.queryPrivilege(queryCriteria);
				
				//查询配置出来的列表按钮,列表操作按钮和表单按钮
				Catalog _catalog = catalogService.findCatalogById(module);
				Tabulation _tabulation = tabulationService.findTabulationById(_catalog.getTabulationId());
				List<TabulationButton> _tbuttonList = tabulationService.findTabulationButton(_catalog.getTabulationId());
				List<TabulationOpt> _optbuttonList = tabulationService.findTabulationOpt(_catalog.getTabulationId());
				List<FormButton> _fbuttonList = formButtonService.findFormButton(_tabulation.getFormId());
				getRequest().setAttribute("tbuttonList", _tbuttonList);
				getRequest().setAttribute("fbuttonList", _fbuttonList);
				getRequest().setAttribute("optbuttonList", _optbuttonList);
				//查询所有该模块下的权限，主要用于配置模块部分
				List<SysPrivilege> _allModuleAuths = sysPrivilegeService.findAllPrivilegeByModule(module);
				getRequest().setAttribute("allModuleAuths", _allModuleAuths);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "privilegeList";
	}
	
	@Action("privilegeadd")
	public String add(){
		sysPrivilege = new SysPrivilege();
		sysPrivilege.setModule(module);
		return "add";
	}
	
	@Action("privilegeview")
	public String view(){
		this.sysPrivilege = sysPrivilegeService.findPrivilegeById(privilegeId);
		return "view";
	}
	
	@Action("privilegeedit")
	public String edit() {
		this.sysPrivilege = sysPrivilegeService.findPrivilegeById(privilegeId);
		return "edit";
	}
	
	@Action("privilegesave")
	public String save() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = sysPrivilegeService.doSavePrivilege(sysPrivilege,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("privilegedelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				sysPrivilegeService.doDeletePrivileges(selectedIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("privilegeenable")
	public String enable(){
		if(selectedIDs != null) {
			sysPrivilegeService.doEnablePrivileges(selectedIDs);
		}
		return NONE;
	}
	
	@Action("privilegedisable")
	public String disable(){
		if(selectedIDs != null) {
			sysPrivilegeService.doDisablePrivileges(selectedIDs);
		}
		return NONE;
	}
	
	//以下是资源相关方法
	@Action("privilegeaddResource")
	public String addResource(){
		sysResource = new SysResource();
		sysResource.setModule(module);
		resourceTypes = dictionaryService.queryByParent(10000000360000L);
		return "addResource";
	}
	
	@Action("privilegeresourceList")
	public String resourceList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(module!=null && !module.equals(0l)){
				queryCriteria.addQueryCondition("tbl_module", module);
				this.pageResult = sysPrivilegeService.queryPrivilegeResource(privilegeId,queryCriteria);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "resourceList";
	}
	
	@Action("privilegeeditResource")
	public String editResource(){
		this.sysResource = sysPrivilegeService.findResourceById(resourceId);
		resourceTypes = dictionaryService.queryByParent(10000000360000L);
		return "editResource";
	}
	
	@Action("privilegesaveResource")
	public String saveResource() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = sysPrivilegeService.doSaveResource(sysResource,privilegeId,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("privilegedeleteResource")
	public String deleteResource() throws IOException{
		try {
			if(selectedSubIDs != null) {
				sysPrivilegeService.doDeleteResources(selectedSubIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("privilegeenableResource")
	public String enableResource(){
		if(selectedSubIDs != null) {
			sysPrivilegeService.doEnableResources(selectedSubIDs);
		}
		return NONE;
	}
	
	@Action("privilegedisableResource")
	public String disableResource(){
		if(selectedSubIDs != null) {
			sysPrivilegeService.doDisableResources(selectedSubIDs);
		}
		return NONE;
	}
	
	@Action("privilegeaddModuleAuth")
	public String addModuleAuth(){
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		Catalog _catalog = catalogService.findCatalogById(module);
		sysPrivilegeService.doAddModuleAuth(status,_catalog,user);
		return NONE;
	}
	
	@Action("privilegeaddTabulationButtonAuth")
	public String addTabulationButtonAuth(){
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String tId = getRequest().getParameter("tbuttonId");
		Catalog _catalog = catalogService.findCatalogById(module);
		TabulationButton _tabulationButton = tabulationButtonService.findTabulationButtonById(Long.valueOf(tId));
		sysPrivilegeService.doAddTabulationButtonAuth(status,_catalog,_tabulationButton,user);
		return NONE;
	}
	
	@Action("privilegeaddOptButtonAuth")
	public String addOptButtonAuth(){
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String optbuttonId = getRequest().getParameter("optbuttonId");
		Catalog _catalog = catalogService.findCatalogById(module);
		TabulationOpt _tabulationOpt = tabulationButtonService.findTabulationOptById(Long.valueOf(optbuttonId));
		sysPrivilegeService.doAddTabulationOptAuth(status,_catalog,_tabulationOpt,user);
		return NONE;
	}
	
	@Action("privilegeaddFormButtonAuth")
	public String addFormButtonAuth(){
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String fbuttonId = getRequest().getParameter("fbuttonId");
		Catalog _catalog = catalogService.findCatalogById(module);
		FormButton _formButton = formButtonService.findFormButtonById(Long.valueOf(fbuttonId));
		sysPrivilegeService.doAddFormButtonAuth(status,_catalog,_formButton,user);
		return NONE;
	}
	
	/**
	 * @return Returns the value of sysPrivilege field with the type SysPrivilege.
	 */
	public SysPrivilege getSysPrivilege() {
		return sysPrivilege;
	}

	/**
	 * @param sysPrivilege. Set the value of sysPrivilege field with the type SysPrivilege by the sysPrivilege parameter.
	 */
	public void setSysPrivilege(SysPrivilege sysPrivilege) {
		this.sysPrivilege = sysPrivilege;
	}

	/**
	 * @return Returns the value of sysResource field with the type SysResource.
	 */
	public SysResource getSysResource() {
		return sysResource;
	}

	/**
	 * @param sysResource. Set the value of sysResource field with the type SysResource by the sysResource parameter.
	 */
	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	/**
	 * @return Returns the value of module field with the type Long.
	 */
	public Long getModule() {
		return module;
	}

	/**
	 * @param module. Set the value of module field with the type Long by the module parameter.
	 */
	public void setModule(Long module) {
		this.module = module;
	}

	/**
	 * @return Returns the value of privilegeId field with the type Long.
	 */
	public Long getPrivilegeId() {
		return privilegeId;
	}

	/**
	 * @param privilegeId. Set the value of privilegeId field with the type Long by the privilegeId parameter.
	 */
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	/**
	 * @return Returns the value of resourceId field with the type Long.
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId. Set the value of resourceId field with the type Long by the resourceId parameter.
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return Returns the value of status field with the type String.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status. Set the value of status field with the type String by the status parameter.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the value of resourceTypes field with the type List<Dictionary>.
	 */
	public List<Dictionary> getResourceTypes() {
		return resourceTypes;
	}

	/**
	 * @param resourceTypes. Set the value of resourceTypes field with the type List<Dictionary> by the resourceTypes parameter.
	 */
	public void setResourceTypes(List<Dictionary> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}
	
}
