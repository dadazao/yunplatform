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

import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysPositionService;

/**
 * @author liuqi
 * Created on 2014-8-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/system/position")
@Results(value = { 
		@Result(name = "positionList", location = "/pages/system/position/positionList.jsp"),
		@Result(name = "add", location = "/pages/system/position/form/positionEdit.jsp"),
		@Result(name = "view", location = "/pages/system/position/form/positionView.jsp"),
		@Result(name = "edit", location = "/pages/system/position/form/positionEdit.jsp"),
		@Result(name = "userList", location = "/pages/system/position/userList.jsp"),
		@Result(name = "setUserList", location = "/pages/system/position/setuserList.jsp")
})
public class SysPositionAction extends BaseAction {
	
	@Resource
	private SysPositionService positionService;
	
	private SysPosition position;
	
	private Long parentId;
	
	private Long positionId;
	
	private String userIds;
	
	private Long userPositionId;
	
	@Action("positionadd")
	public String add() {
		position = new SysPosition();
		position.setParentId(parentId);
		return "add";
	}
	
	@Action("positionview")
	public String view() {
		this.position = positionService.findPositionById(positionId);
		return "view";
	}
	
	@Action("positionedit")
	public String edit() {
		this.position = positionService.findPositionById(positionId);
		return "edit";
	}
	
	@Action("positionsave")
	public String save() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = positionService.doSavePosition(position,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}
	
	@Action("positionisCanDelete")
	public String isCanDelete() throws Exception {
		String canDelete = "true";
		List _list = positionService.findSubPositions(selectedIDs);
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
	
	@Action("positiondelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				positionService.doDeletePositions(selectedIDs);
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
	
	@Action("positionlist")
	public String list(){
		queryCriteria = new QueryCriteria();
		if(position != null) {
			if(position.getParentId()!=null && !position.getParentId().equals(0L) && !position.getParentId().equals(1L)){
				queryCriteria.addQueryCondition("tbl_parentid", position.getParentId());
			}
			if(position.getPositionName()!=null && !position.getPositionName().equals("")){
				queryCriteria.addQueryCondition("tbl_positionname", "%"+position.getPositionName().trim()+"%");
			}
			if(position.getPositionNo()!=null && !position.getPositionNo().equals("")){
				queryCriteria.addQueryCondition("tbl_positionno", "%"+position.getPositionNo().trim()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			this.pageResult = positionService.queryPosition(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "positionList";
	}
	
	@Action("positionshowTree")
	public String showTree(){
		try {
			List<Map> _lstZtree = new ArrayList<Map>();
			queryCriteria.setPageSize(-1);
			List<SysPosition> nodes = positionService.selectAllPostion();
			for (SysPosition position : nodes) {
				Map ztree = new HashMap();
				ztree.put("id", position.getId());
				ztree.put("pId", position.getParentId());
				ztree.put("name", position.getPositionName());
				ztree.put("icon", "/images/icon/folderClose.gif");
				ztree.put("iconOpen", "/images/icon/folderOpen.gif");
				ztree.put("iconClose", "/images/icon/folderClose.gif");
				if (position.getId().equals(1l)) {
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
	
	@Action("positionuserList")
	public String userList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(positionId!=null && !positionId.equals(0l)){
				queryCriteria.addQueryCondition("up.tbl_positionid", positionId);
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
		String forward = getRequest().getParameter("forward");
		if(forward!=null && !forward.equals("")){
			return forward;
		}
		return "userList";
	}
	
	@Action("positionaddUser")
	public String addUser(){
		positionService.doAddUser(userIds,positionId);
		return NONE;
	}
	
	@Action("positiondeleteUser")
	public String deleteUser(){
		if (selectedSubIDs != null) {
			positionService.doDeleteUser(selectedSubIDs);
		}
		return NONE;
	}
	
	@Action("positionshezhiZhugang")
	public String shezhiZhugang(){
		positionService.doShezhiZhugang(userPositionId);
		return NONE;
	}
	
	/**
	 * @return the position
	 */
	public SysPosition getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(SysPosition position) {
		this.position = position;
	}

	
	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public SysPositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(SysPositionService positionService) {
		this.positionService = positionService;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getUserPositionId() {
		return userPositionId;
	}

	public void setUserPositionId(Long userPositionId) {
		this.userPositionId = userPositionId;
	}

}
