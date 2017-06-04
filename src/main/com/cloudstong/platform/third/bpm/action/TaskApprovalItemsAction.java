package com.cloudstong.platform.third.bpm.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudstong.platform.core.model.ResultMessage;
import com.cloudstong.platform.core.util.ExceptionUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.TaskApprovalItems;
import com.cloudstong.platform.third.bpm.service.BpmNodeSetService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.TaskApprovalItemsService;
import com.cloudstong.platform.third.bpm.thread.MessageUtil;

@ParentPackage("default")
@Namespace("/pages/third/bpm/taskApprovalItems")
@Results(value = { 
		@Result(name = "edit", location = "/pages/third/bpm/taskApprovalItemsEdit.jsp") 
})
public class TaskApprovalItemsAction extends BaseAction {

	@Resource
	private TaskApprovalItemsService taskApprovalItemsService;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Action("get")
	public String get() throws Exception {
		HttpServletRequest request = getRequest();
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");

		TaskApprovalItems nodeExpItems = this.taskApprovalItemsService.getTaskApproval(actDefId, nodeId,
				TaskApprovalItems.notGlobal.shortValue());
		String nodeExp = nodeExpItems == null ? "" : nodeExpItems.getExpItems();
		return "get";
	}

	@Action( "edit" )
	public String edit() throws Exception {
		HttpServletRequest request = getRequest();
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");

		Long setId = null;

		Map nodeMap = this.bpmService.getExecuteNodesMap(actDefId, true);
		if (StringUtil.isNotEmpty(nodeId)) {
			BpmNodeSet bns = this.bpmNodeSetService.getByDefIdNodeId(defId, nodeId);
			setId = bns.getSetId();
		}

		Short isGlobal = -1;

		TaskApprovalItems defExpItems = this.taskApprovalItemsService.getFlowApproval(actDefId,
				TaskApprovalItems.global.shortValue());
		String defExp = defExpItems == null ? "" : defExpItems.getExpItems();
		isGlobal = Short.valueOf(defExpItems == null ? 0 : defExpItems.getIsGlobal().shortValue());

		TaskApprovalItems nodeExpItems = this.taskApprovalItemsService.getTaskApproval(actDefId, nodeId,
				TaskApprovalItems.notGlobal.shortValue());
		String nodeExp = nodeExpItems == null ? "" : nodeExpItems.getExpItems();
		if (isGlobal.intValue() == 1) {
			isGlobal = Short.valueOf(nodeExpItems == null ? 1 : nodeExpItems.getIsGlobal().shortValue());
		}

		request.setAttribute("nodeMap", nodeMap);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("actDefId", actDefId);
		request.setAttribute("defExp", defExp);
		request.setAttribute("nodeExp", nodeExp);
		request.setAttribute("setId", setId);
		request.setAttribute("isGlobal", isGlobal.toString());
		
		return "edit";
	}

	@Action( "save" )
	public void save() throws Exception {
		HttpServletRequest request=getRequest();
		String isGlobal = RequestUtil.getString(request, "isGlobal");
		String approvalItem = RequestUtil.getString(request, "approvalItem");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Long setId = Long.valueOf(RequestUtil.getLong(request, "setId"));

		if (isGlobal.equals("1"))
			this.taskApprovalItemsService.delFlowApproval(actDefId, TaskApprovalItems.global.shortValue());
		else {
			this.taskApprovalItemsService.delTaskApproval(actDefId, nodeId, TaskApprovalItems.notGlobal.shortValue());
		}
		try {
			this.taskApprovalItemsService.addTaskApproval(approvalItem, isGlobal, actDefId, setId, nodeId);
			printJSON("success","保存常用语成功");
		} catch (Exception e) {
			printJSON("fail","保存常用语失败");
		}
	}
}