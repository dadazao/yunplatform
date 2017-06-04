package com.cloudstong.platform.third.bpm.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;
import com.cloudstong.platform.third.bpm.service.BpmNodeSetService;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.BpmTaskAssigneeService;
import com.cloudstong.platform.third.bpm.service.ExecutionStackService;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;
import com.cloudstong.platform.third.bpm.service.TaskUserService;

@ParentPackage("default")
@Namespace("/pages/third/bpm/processRun")
@Results(value = { 
		@Result(name = "list", location = "/pages/third/bpm/processRunList.jsp"),
		@Result(name = "view", location = "/pages/third/bpm/processRunView.jsp"),
		@Result(name = "history", location = "/pages/third/bpm/processRunHistory.jsp")
})
@SuppressWarnings("serial")
public class ProcessRunAction extends BaseAction {
	
	private ProcessRun processRun;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private ExecutionStackService executionStackService;

	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private TaskService taskService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private HistoryService historyService;

	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;

	@Resource
	private TaskUserService taskUserService;

	@Resource
	private SysUserService sysUserService;

	@Action("list")
	public String list() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		pageResult = processRunService.getAll(queryCriteria);
		
		return "list";
	}

	@Action("history")
	public String history() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		pageResult = processRunService.queryHistory(queryCriteria);
		
		return "history";
	}

	@Action("del")
	public String del() throws Exception {
		try {
			processRunService.delByIds(selectedIDs);
			printJSON("success", "删除流程实例成功");
		} catch (Exception e) {
			printJSON("fail", "删除流程实例失败");
		}
		
		return NONE;
	}

	@Action("view")
	public String view() throws Exception {
		HttpServletRequest request = getRequest();
		long runId = RequestUtil.getLong(request, "runId", 0L);
		String taskId = RequestUtil.getString(request, "taskId");

		if (runId != 0L) {
			processRun = (ProcessRun) processRunService.getById(Long.valueOf(runId));
		} else {
			TaskEntity task = bpmService.getTask(taskId);
			processRun = processRunService.getByActInstanceId(task.getProcessInstanceId());
		}

		List hisTasks = bpmService.getHistoryTasks(processRun.getActInstId());
		request.setAttribute("hisTasks", hisTasks);
		
		return "view";
	}

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}
	
}