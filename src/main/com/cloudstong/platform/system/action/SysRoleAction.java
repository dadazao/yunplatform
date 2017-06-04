/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysPrivilegeService;
import com.cloudstong.platform.system.service.SysRoleService;

/**
 * @author liuqi
 * 
 *         Created on 2014-8-28
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/role")
@Results(value = { 
		@Result(name = "dialogRoleList", location = "/pages/system/role/dialogRoleList.jsp"),
		@Result(name = "roleList", location = "/pages/system/role/roleList.jsp"),
		@Result(name = "add", location = "/pages/system/role/roleEdit.jsp"),
		@Result(name = "view", location = "/pages/system/role/roleView.jsp"),
		@Result(name = "copy", location = "/pages/system/role/roleCopy.jsp"),
		@Result(name = "edit", location = "/pages/system/role/roleEdit.jsp"),
		@Result(name = "userList", location = "/pages/system/role/userList.jsp"),
		@Result(name = "catalogAuthList", location = "/pages/system/role/catalogAuthList.jsp"),
		@Result(name = "dialog", location = "/pages/system/role/sysRoleDialog.jsp")
})
public class SysRoleAction extends BaseAction {

	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SysPrivilegeService sysPrivilegeService;
	
	private SysRole sysRole;
	
	private Long roleId;
	
	private String userIds;
	
	private String authIds;
	
	private String module;
	
	private String modules;
	
	/**
	 * @return Returns the value of sysRole field with the type SysRole.
	 */
	public SysRole getSysRole() {
		return sysRole;
	}

	/**
	 * @param sysRole. Set the value of sysRole field with the type SysRole by the sysRole parameter.
	 */
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	
	@Action("dialog")
	public String dialog() {
		return "dialog";
	}

	@Action("roleadd")
	public String add() {
		sysRole = new SysRole();
		return "add";
	}
	
	@Action("roleview")
	public String view() {
		this.sysRole = sysRoleService.findRoleById(roleId);
		return "view";
	}
	
	@Action("roleinitCopy")
	public String initCopy() {
		this.sysRole = sysRoleService.findRoleById(roleId);
		return "copy";
	}
	
	@Action("rolecopy")
	public String copy() throws IOException {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			sysRoleService.doCopyRole(sysRole,user);
			printJSON("success", true);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}
	
	@Action("roleedit")
	public String edit() {
		this.sysRole = sysRoleService.findRoleById(roleId);
		return "edit";
	}
	
	@Action("rolesave")
	public String save() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = sysRoleService.doSaveRole(sysRole,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("roleisCanDelete")
	public String isCanDelete() throws Exception {
		String canDelete = "true";
		List _list = sysRoleService.findNotCanDeleteRole(selectedIDs);
		if(_list.size()>0){
			canDelete = "false";
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(canDelete);
		return NONE;
	}
	
	@Action("roledelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				sysRoleService.doDeleteRoles(selectedIDs);
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
	
	@Action("rolelist")
	public String list(){
		queryCriteria = new QueryCriteria();
		if(sysRole != null) {
			if(sysRole.getRoleName()!=null && !sysRole.getRoleName().equals("")){
				queryCriteria.addQueryCondition("tbl_rolename", "%"+sysRole.getRoleName().trim()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysRoleService.queryRole(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "roleList";
	}
	
	@Action("roleuserList")
	public String userList(){
		queryCriteria = new QueryCriteria();
		if(roleId==null){
			roleId = -1L;
		}
		queryCriteria.addQueryCondition("up.tbl_roleid", roleId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysRoleService.queryUserRole(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "userList";
	}
	
	@Action("roleaddUser")
	public String addUser(){
		sysRoleService.doAddUser(userIds,roleId);
		return NONE;
	}
	
	@Action("roledeleteUser")
	public String deleteUser(){
		if (selectedSubIDs != null) {
			sysRoleService.doDeleteUser(selectedSubIDs);
		}
		return NONE;
	}
	
	@Action("dialogRoleList")
	public String dialogUserList(){
		queryCriteria = new QueryCriteria();
		if(sysRole != null) {
			if(sysRole.getRoleName()!=null && !sysRole.getRoleName().equals("")){
				queryCriteria.addQueryCondition("tbl_rolename", "%"+sysRole.getRoleName()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysRoleService.queryRole(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "dialogRoleList";
	}
	
	@Action("roleshowTree")
	public String showTree(){
		try {
			List<Map> _lstZtree = new ArrayList<Map>();
			Map _topztree = new HashMap();
			_topztree.put("id", "1");
			_topztree.put("name", "全部角色");
			_topztree.put("icon", "/images/icon/folderClose.gif");
			_topztree.put("iconOpen", "/images/icon/folderOpen.gif");
			_topztree.put("iconClose", "/images/icon/folderClose.gif");
			_topztree.put("open", true);
			_lstZtree.add(_topztree);
			List<SysRole> nodes = sysRoleService.selectAllRole();
			for (SysRole role : nodes) {
				Map ztree = new HashMap();
				ztree.put("id", role.getId());
				ztree.put("pId", "1");
				ztree.put("name", role.getRoleName());
				ztree.put("icon", "/images/icon/folderClose.gif");
				ztree.put("iconOpen", "/images/icon/folderOpen.gif");
				ztree.put("iconClose", "/images/icon/folderClose.gif");
				_lstZtree.add(ztree);
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
	
	@Action("rolecatalogAuthList")
	public String catalogAuthList(){
		List<SysPrivilege> _sysPrivilege = null;
		if(modules==null||modules.equals("")){
			_sysPrivilege = new ArrayList<SysPrivilege>();
		}else{
			List<SysPrivilege> selectedSysPrivileges = sysPrivilegeService.selectHasAuthList(roleId);
			_sysPrivilege = sysPrivilegeService.selectCatalogAuthListByModules(modules);
			if(_sysPrivilege!=null && _sysPrivilege.size()>0 && selectedSysPrivileges != null && selectedSysPrivileges.size()>0) {
				for(SysPrivilege sysPrivilege:_sysPrivilege){
					if(selectedSysPrivileges.contains(sysPrivilege)) {
						sysPrivilege.setSelected(true);
					}
				}
			}
		}
		getRequest().setAttribute("catalogAuthList", _sysPrivilege);
		return "catalogAuthList";
	}

	@Action("roleaddAuth")
	public String addAuth(){
		sysRoleService.doAddAuth(authIds,roleId);
		return NONE;
	}
	
	@Action("roleloadHasAuth")
	public String loadHasAuth(){
		try {
			List<SysPrivilege> _sysPrivilege = sysPrivilegeService.selectHasAuthList(roleId);
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, _sysPrivilege);
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return NONE;
	}
	
	/**
	 * @return Returns the value of roleId field with the type Long.
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId. Set the value of roleId field with the type Long by the roleId parameter.
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return Returns the value of userIds field with the type String.
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds. Set the value of userIds field with the type String by the userIds parameter.
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return Returns the value of module field with the type String.
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module. Set the value of module field with the type String by the module parameter.
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return Returns the value of modules field with the type String.
	 */
	public String getModules() {
		return modules;
	}

	/**
	 * @param modules. Set the value of modules field with the type String by the modules parameter.
	 */
	public void setModules(String modules) {
		this.modules = modules;
	}

	/**
	 * @return Returns the value of authIds field with the type String.
	 */
	public String getAuthIds() {
		return authIds;
	}

	/**
	 * @param authIds. Set the value of authIds field with the type String by the authIds parameter.
	 */
	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}
	
}
