/**
 * 
 */
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysOrgService;

/**
 * @author liuqi
 * Created on 2014-8-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@ParentPackage("default")
@Namespace("/pages/system/org")
@Results(value = { 
		@Result(name = "orgList", location = "/WEB-INF/view/system/org/orgList.jsp"),
		@Result(name = "add", location = "/WEB-INF/view/system/org/orgEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/system/org/orgView.jsp"),
		@Result(name = "edit", location = "/WEB-INF/view/system/org/orgEdit.jsp"),
		@Result(name = "userList", location = "/WEB-INF/view/system/org/userList.jsp"),
		@Result(name = "setUserList", location = "/WEB-INF/view/system/org/setuserList.jsp"),
		@Result(name = "index", location = "/WEB-INF/view/system/org/orgIndex.jsp"),
		@Result(name = "orgUserTree", location = "/WEB-INF/view/system/org/orgUserTree.jsp"),
		@Result(name = "selfTree", location = "/WEB-INF/view/system/org/orgSelfTree.jsp"),
		@Result(name = "addUser", location = "/WEB-INF/view/system/org/addUser.jsp")
})
public class SysOrgAction extends BaseAction {

	@Resource
	private SysOrgService orgService;
	
	private SysOrg sysOrg;
	
	private Long parentId;
	
	private Long orgId;
	
	private String userIds;
	
	private Long userOrgId;
	
	@Action("index")
	public String index() {
		return "index";
	}
	
	@Action("selfTree")
	public String selfTree() {
		return "selfTree";
	}
	
	@Action("orgUserTree")
	public String orgUserTree() {
		return "orgUserTree";
	}
	
	@Action("addUser")
	public String addUser() {
		return "addUser";
	}
	
	@Action("orgadd")
	public String add() {
		sysOrg = new SysOrg();
		sysOrg.setParentId(parentId);
		return "add";
	}
	
	@Action("orgview")
	public String view() {
		this.sysOrg = orgService.findOrgById(orgId);
		return "view";
	}
	
	@Action("orgedit")
	public String edit() {
		this.sysOrg = orgService.findOrgById(orgId);
		return "edit";
	}
	
	@Action("orgsave")
	public String save() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = orgService.doSaveOrg(sysOrg,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("orgisCanDelete")
	public String isCanDelete() throws Exception {
		String canDelete = "true";
		List _list = orgService.findSubOrgs(selectedIDs);
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
	
	@Action("orgdelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				orgService.doDeleteOrgs(selectedIDs);
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
	
	@Action("orglist")
	public String list(){
		queryCriteria = new QueryCriteria();
		if(sysOrg != null) {
			if(sysOrg.getParentId()!=null && !sysOrg.getParentId().equals(0l) && !sysOrg.getParentId().equals(1l)){
				queryCriteria.addQueryCondition("o1.tbl_parentId", sysOrg.getParentId());
			}
			if(sysOrg.getOrgName()!=null && !sysOrg.getOrgName().equals("")){
				queryCriteria.addQueryCondition("o1.tbl_name", "%"+sysOrg.getOrgName().trim()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		queryCriteria.setOrderField("id");
		queryCriteria.setOrderDirection(queryCriteria.SORT_DIRECTION_ASC);
		try {
			this.pageResult = orgService.queryOrg(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "orgList";
	}
	
	@Action("orgshowTree")
	public String showTree(){
		try {
			List<Map> _lstZtree = new ArrayList<Map>();
			queryCriteria.setPageSize(-1);
			List<SysOrg> nodes = orgService.selectAllOrg();
			for (SysOrg org : nodes) {
				Map ztree = new HashMap();
				ztree.put("id", org.getId());
				ztree.put("pId", org.getParentId());
				ztree.put("name", org.getOrgName());
				ztree.put("icon", "images/icon/folderClose.gif");
				ztree.put("iconOpen", "images/icon/folderOpen.gif");
				ztree.put("iconClose", "images/icon/folderClose.gif");
				if (org.getId().equals(1l)) {
					ztree.put("open", true);
				}
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
	
	@Action("orguserList")
	public String userList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(orgId!=null && !orgId.equals(0l)){
				queryCriteria.addQueryCondition("uo.tbl_orgid", orgId);
				this.pageResult = orgService.queryUserOrg(queryCriteria);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		String forward = getRequest().getParameter("forward");
		if(forward!=null && !forward.equals("")){
			return forward;
		}
		return "userList";
	}
	
	@Action("orgaddUser")
	public String orgaddUser(){
		orgService.doAddUser(userIds,orgId);
		return NONE;
	}
	
	@Action("orgdeleteUser")
	public String deleteUser(){
		if (selectedSubIDs != null) {
			orgService.doDeleteUser(selectedSubIDs);
		}
		return NONE;
	}
	
	@Action("orgsetupMainOrg")
	public String setupMainOrg(){
		orgService.doSetupMainOrg(userOrgId);
		return NONE;
	}
	
	@Action("orgsetupPrincipal")
	public String setupPrincipal(){
		orgService.doSetupPrincipal(userOrgId);
		return NONE;
	}
	
	/**
	 * @return the sysOrg
	 */
	public SysOrg getSysOrg() {
		return sysOrg;
	}

	/**
	 * @param sysOrg the sysOrg to set
	 */
	public void setSysOrg(SysOrg sysOrg) {
		this.sysOrg = sysOrg;
	}
	
	/**
	 * @return Returns the value of parentId field with the type String.
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId. Set the value of parentId field with the type String by the parentId parameter.
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the value of orgId field with the type String.
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId. Set the value of orgId field with the type String by the orgId parameter.
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	 * @return Returns the value of userOrgId field with the type String.
	 */
	public Long getUserOrgId() {
		return userOrgId;
	}

	/**
	 * @param userOrgId. Set the value of userOrgId field with the type String by the userOrgId parameter.
	 */
	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}
}
