/**
 * 
 */
package com.cloudstong.platform.system.action;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysOrgService;
import com.cloudstong.platform.system.service.SysPositionService;
import com.cloudstong.platform.system.service.SysRoleService;
import com.cloudstong.platform.system.service.SysUserService;

/**
 * @author liuqi
 * Created on 2014-8-23
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/system/user")
@Results(value = { 
		@Result(name = "dialogUserList", location = "/WEB-INF/view/system/user/dialogUserList.jsp"),
		@Result(name = "pUserList", location = "/WEB-INF/view/system/position/addUserList.jsp"),
		@Result(name = "userList", location = "/WEB-INF/view/system/user/userList.jsp"),
		@Result(name = "selectedOrgList", location = "/WEB-INF/view/system/user/selectedOrgList.jsp"),
		@Result(name = "selectedPositionList", location = "/WEB-INF/view/system/user/selectedPositionList.jsp"),
		@Result(name = "selectedRoleList", location = "/WEB-INF/view/system/user/selectedRoleList.jsp"),
		@Result(name = "add", location = "/WEB-INF/view/system/user/userEdit.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/system/user/userView.jsp"),
		@Result(name = "edit", location = "/WEB-INF/view/system/user/userEdit.jsp"),
		@Result(name = "resetPassword", location = "/WEB-INF/view/system/user/resetPassword.jsp"),
		@Result(name = "resetStatus", location = "/WEB-INF/view/system/user/resetStatus.jsp"),
		@Result(name = "dialog", location = "/WEB-INF/view/system/user/sysUserDialog.jsp")
})
public class SysUserAction extends BaseAction {
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysOrgService orgService;
	
	@Resource
	private SysPositionService positionService;
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private Properties configproperties;
	
	/**
	 * 单个上传文件上传的文件
	 */
	private File[] upload;
	/**
	 * 单个上传文件上传的文件类型
	 */
	private String[] uploadContentType;
	/**
	 * 单个上传文件上传的文件名称
	 */
	private String[] uploadFileName;
	
	private SysUser sysUser;
	
	private Long userId;
	
	private Long userOrgId;
	
	private Long userPositionId;
	
	private Long userRoleId;
	
	private String relationId;
	
	private String queryType="all";
	
	private String oldPassword;
	
	private String newPassword;
	
	private String reNewPassword;
	
	@Action("dialog")
	public String dialog() {
		return "dialog";
	}

	@Action("useradd")
	public String add() {
		sysUser = new SysUser();
		return "add";
	}
	
	@Action("userview")
	public String view() {
		this.sysUser = sysUserService.findUserById(userId);
		return "view";
	}
	
	@Action("userinitResetPassword")
	public String initResetPassword() {
		this.sysUser = sysUserService.findUserById(userId);
		return "resetPassword";
	}
	
	@Action("userresetPassword")
	public String resetPassword() throws IOException {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			sysUserService.doResetPassword(sysUser,user);
			printJSON("success", true);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}
	
	@Action("userinitResetStatus")
	public String initResetStatus() {
		this.sysUser = sysUserService.findUserById(userId);
		return "resetStatus";
	}
	
	@Action("change")
	public String change() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			if (!user.getPassword().equals(EncryptUtil.encryptSha256(oldPassword))) {
				printJSON("300", "原密码错误", false);
			} else if (!newPassword.equals(reNewPassword)) {
				printJSON("300", "两次密码不一致", false);
			} else {
				sysUserService.doChangePassword(user.getId(), EncryptUtil.encryptSha256(newPassword));
				user.setPassword(newPassword);
				printJSON("200", "修改成功", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	@Action("userresetStatus")
	public String resetStatus() throws IOException {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			sysUserService.doResetStatus(sysUser,user);
			printJSON("success", true);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}
	
	@Action("useredit")
	public String edit() {
		this.sysUser = sysUserService.findUserById(userId);
		return "edit";
	}
	
	@SuppressWarnings("deprecation")
	@Action("usersave")
	public String save() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			if(upload!=null){
				String uploadDir = configproperties.getProperty("uploadDir");
				String _fileName = System.currentTimeMillis() + uploadFileName[0].substring(uploadFileName[0].lastIndexOf("."));
				File target = new File(getRequest().getRealPath(uploadDir), _fileName);
				FileUtils.copyFile(upload[0], target);
				sysUser.setUserPic(uploadDir + "/" + _fileName);
			}
			Long id = sysUserService.doSaveUser(sysUser,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("userdelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				sysUserService.doDeleteUsers(selectedIDs);
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
	
	@Action("userpUserList")
	public String pUserList(){
		queryCriteria = new QueryCriteria();
		if(sysUser != null) {
			if(sysUser.getFullname()!=null && !sysUser.getFullname().equals("")){
				queryCriteria.addQueryCondition("tbl_yonghuxingming", "%"+sysUser.getFullname()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysUserService.queryUser(queryType,relationId,queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "pUserList";
	}
	
	@Action("dialogUserList")
	public String dialogUserList(){
		queryCriteria = new QueryCriteria();
		if(sysUser != null) {
			if(sysUser.getFullname()!=null && !sysUser.getFullname().equals("")){
				queryCriteria.addQueryCondition("u.tbl_yonghuxingming", "%"+sysUser.getFullname()+"%");
			}
			queryCriteria.addQueryCondition("u.id", "!=1");
		}else{
			queryCriteria.addQueryCondition("id", "!=1");
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysUserService.queryUser(queryType,relationId,queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "dialogUserList";
	}
	
	@Action("userlist")
	public String list(){
		queryCriteria = new QueryCriteria();
		if(sysUser != null) {
			if(sysUser.getFullname()!=null && !sysUser.getFullname().equals("")){
				queryCriteria.addQueryCondition("tbl_yonghuxingming", "%"+sysUser.getFullname().trim()+"%");
			}
		}
		//排除"系统"用户
		queryCriteria.addQueryCondition("id", "!=1");
		
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = sysUserService.queryUser(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "userList";
	}
	
	@Action("userselectedOrgList")
	public String selectedOrgList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(userId!=null && !userId.equals(0l)){
				queryCriteria.addQueryCondition("uo.tbl_userid", userId);
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
		return "selectedOrgList";
	}
	
	@Action("useraddUserOrg")
	public String addUserOrg(){
		String orgIds = getRequest().getParameter("orgIds");
		sysUserService.doAddUserOrg(userId,orgIds);
		return NONE;
	}
	
	@Action("userdeleteUserOrg")
	public String deleteUserOrg(){
		sysUserService.doDeleteUserOrg(userOrgId);
		return NONE;
	}
	
	@Action("usersetMainOrg")
	public String setMainOrg(){
		orgService.doSetupMainOrg(userOrgId);
		return NONE;
	}
	
	@Action("usersetPrincipal")
	public String setPrincipal(){
		String _status = getRequest().getParameter("status");
		if(_status.equals("true")){
			orgService.doSetupPrincipal(userOrgId);
		}else{
			orgService.doCancelPrincipal(userOrgId);
		}
		return NONE;
	}
	
	@Action("userselectedPositionList")
	public String selectedPositionList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(userId!=null && !userId.equals(0l)){
				queryCriteria.addQueryCondition("up.tbl_userid", userId);
				this.pageResult = positionService.queryUserPosition(queryCriteria);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "selectedPositionList";
	}
	
	@Action("useraddUserPosition")
	public String addUserPosition(){
		String positionIds = getRequest().getParameter("positionIds");
		sysUserService.doAddUserPosition(userId,positionIds);
		return NONE;
	}
	
	@Action("userdeleteUserPosition")
	public String deleteUserPosition(){
		sysUserService.doDeleteUserPosition(userPositionId);
		return NONE;
	}
	
	@Action("usersetMainPosition")
	public String setMainPosition(){
		positionService.doShezhiZhugang(userPositionId);
		return NONE;
	}
	
	@Action("userselectedRoleList")
	public String selectedRoleList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(userId!=null && !userId.equals(0l)){
				queryCriteria.addQueryCondition("up.tbl_userid", userId);
				this.pageResult = sysRoleService.queryUserRole(queryCriteria);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "selectedRoleList";
	}
	
	@Action("useraddUserRole")
	public String addUserRole(){
		String roleIds = getRequest().getParameter("roleIds");
		sysUserService.doAddUserRole(userId,roleIds);
		return NONE;
	}
	
	@Action("userdeleteUserRole")
	public String deleteUserRole(){
		sysUserService.doDeleteUserRole(userRoleId);
		return NONE;
	}
	
	/**
	 * @return the sysUser
	 */
	public SysUser getSysUser() {
		return sysUser;
	}

	/**
	 * @param sysUser the sysUser to set
	 */
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	/**
	 * @return Returns the value of userId field with the type Long.
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId. Set the value of userId field with the type Long by the userId parameter.
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the value of userOrgId field with the type Long.
	 */
	public Long getUserOrgId() {
		return userOrgId;
	}

	/**
	 * @param userOrgId. Set the value of userOrgId field with the type Long by the userOrgId parameter.
	 */
	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	/**
	 * @return Returns the value of userPositionId field with the type Long.
	 */
	public Long getUserPositionId() {
		return userPositionId;
	}

	/**
	 * @param userPositionId. Set the value of userPositionId field with the type Long by the userPositionId parameter.
	 */
	public void setUserPositionId(Long userPositionId) {
		this.userPositionId = userPositionId;
	}

	/**
	 * @return Returns the value of userRoleId field with the type Long.
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId. Set the value of userRoleId field with the type Long by the userRoleId parameter.
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * @return the relationId
	 */
	public String getRelationId() {
		return relationId;
	}

	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return Returns the value of upload field with the type File[].
	 */
	public File[] getUpload() {
		return upload;
	}

	/**
	 * @param upload. Set the value of upload field with the type File[] by the upload parameter.
	 */
	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	/**
	 * @return Returns the value of uploadContentType field with the type String[].
	 */
	public String[] getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * @param uploadContentType. Set the value of uploadContentType field with the type String[] by the uploadContentType parameter.
	 */
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * @return Returns the value of uploadFileName field with the type String[].
	 */
	public String[] getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName. Set the value of uploadFileName field with the type String[] by the uploadFileName parameter.
	 */
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
	
}
