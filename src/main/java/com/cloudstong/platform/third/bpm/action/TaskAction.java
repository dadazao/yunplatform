package com.cloudstong.platform.third.bpm.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.ExceptionUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;
import com.cloudstong.platform.third.bpm.service.BpmFormRunService;
import com.cloudstong.platform.third.bpm.service.BpmNodeButtonService;
import com.cloudstong.platform.third.bpm.service.BpmRunLogService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;
import com.cloudstong.platform.third.bpm.thread.MessageUtil;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@ParentPackage("default")
@Namespace("/pages/third/bpm/task")
@Results(value = { @Result(name = "list", location = "/WEB-INF/view/third/bpm/taskList.jsp"),
		@Result(name = "startFlowForm", location = "/WEB-INF/view/third/bpm/taskStartFlowForm.jsp") })
@SuppressWarnings("serial")
public class TaskAction extends BaseAction {
	private Log logger = LogFactory.getLog(getClass());

	@Resource
	private BpmService bpmService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmRunLogService bpmRunLogService;

	@Resource
	private TaskService taskService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;
	
	@Resource
	private BpmNodeButtonService bpmNodeButtonService;
	
	@Resource
	private BpmFormRunService bpmFormRunService;
	
	@Resource
	private FormService formService;

	private List<TaskEntity> taskList;

	private String taskId;

	@Action("list")
	public String list() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		try {
			pageResult = bpmService.queryTasks(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "list";
	}

	@Action("assign")
	public String assign() throws Exception {
		HttpServletRequest request = getRequest();
		String userId = request.getParameter("userId");

		try {
			for (Long tId : selectedIDs) {
				bpmService.assignTask(tId.toString(), userId);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
		}
		return NONE;
	}

	@Action("startFlow")
	public String startFlow() throws Exception {
		try {
			ProcessCmd processCmd = BpmUtil.getProcessCmd(getRequest());
			SysUser user = (SysUser)(getSession().getAttribute("user"));
			String businessKey = RequestUtil.getString(getRequest(), "businessKey");
			processCmd.setBusinessKey(businessKey);
			
			processCmd.setCurrentUserId(user.getId().toString());
			processRunService.startProcess(processCmd);

			printJSON("200", "启动流程成功!", true);
		} catch (Exception ex) {
			logger.debug("startFlow:" + ex.getMessage());
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				printJSON("300", "启动流程失败：" + ex.getMessage());
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				printJSON("300", "启动流程失败：" + ex.getMessage());
			}
		}

		return NONE;
	}

	@Action("startFlowForm")
	public String startFlowForm() throws Exception {
		HttpServletRequest request = getRequest();
		Long defId = Long.valueOf(RequestUtil.getLong(request, "definitionId"));
		String businessKey = RequestUtil.getString(request, "businessKey", "");

		String copyKey = RequestUtil.getString(request, "copyKey", "");
		SysUser user = (SysUser)getSession().getAttribute("user");
		Long userId = AppContext.getCurrentUserId();
		String ctxPath = request.getContextPath();

		Long runId = Long.valueOf(RequestUtil.getLong(request, "runId", 0L));

		Map paraMap = RequestUtil.getParameterValueMap(request, false, false);
		paraMap.remove("businessKey");
		paraMap.remove("defId");

		ProcessRun processRun = null;
		BpmDefinition bpmDefinition = null;

		if (runId.longValue() != 0L) {
			processRun = (ProcessRun) this.processRunService.getById(runId);
			defId = processRun.getDefId();
		}

		if (defId.longValue() != 0L)
			bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(defId);
		if (BeanUtils.isEmpty(bpmDefinition))
			request.setAttribute("statusCode", "300");
			request.setAttribute("message", "该流程定义已经被删除!");
		if ((bpmDefinition.getStatus().shortValue() == BpmDefinition.STATUS_DISABLED.shortValue())
				|| (bpmDefinition.getStatus().shortValue() == BpmDefinition.STATUS_INST_DISABLED.shortValue())) {
			request.setAttribute("statusCode", "300");
			request.setAttribute("message", "该流程已经被禁用!");
		}
		boolean isProcessInstanceExisted = this.processRunService.isProcessInstanceExisted(businessKey);
		if (isProcessInstanceExisted) {
			request.setAttribute("statusCode", "300");
			request.setAttribute("message", "对不起，该流程实例已存在，不需要再次启动!");
		}
		Boolean isFormEmpty = Boolean.valueOf(false);
		Boolean isExtForm = Boolean.valueOf(false);
		String actDefId = bpmDefinition.getActDefId();


		Map mapButton = this.bpmNodeButtonService.getMapByStartForm(defId);


//		if (NodeCache.isMultipleFirstNode(actDefId)) {
//			mv.addObject("flowNodeList", NodeCache.getFirstNode(actDefId)).addObject("isMultipleFirstNode",
//					Boolean.valueOf(true));
//		}
		BpmNodeSet bpmNodeSet = bpmFormRunService.getStartBpmNodeSet(bpmDefinition.getDefId(), actDefId);
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		// 通过表单ID查找表单信息
		Form form = formService.getFormByIdAndDomainVO(bpmNodeSet.getFormKey(), domainVOs, user);
		
		if(form.getId() == null) {
			printJSON("300", "请设置表单！");
			
			return NONE;
		}
		
		String model = form.getTableName();
		String simpleModel = model;

		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("isExtForm", isExtForm);
		request.setAttribute("isFormEmpty", isFormEmpty);
		request.setAttribute("mapButton", mapButton);
		request.setAttribute("defId", defId);
		request.setAttribute("paraMap", paraMap);
		request.setAttribute("formId", form.getId().toString());
		request.setAttribute("model", model);
		request.setAttribute("simpleModel", simpleModel);
		request.setAttribute("runId", runId);
		request.setAttribute("businessKey", StringUtil.isEmpty(copyKey) ? businessKey : "");

		return "startFlowForm";
	}

	@Action("endProcess")
	public String endProcess() throws Exception {

		TaskEntity taskEnt = bpmService.getTask(taskId);

		String instanceId = taskEnt.getProcessInstanceId();
		try {
			ProcessRun processRun = bpmService.endProcessByInstanceId(new Long(instanceId));
			String memo = "结束了:" + processRun.getSubject();
			bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_ENDTASK, memo);
			printJSON("success", "结束流程实例成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			printJSON("fail", "结束流程实例失败!");
		}

		return NONE;
	}
	
	  @RequestMapping({"pendingMattersList"})
	  public String pendingMattersList() throws Exception {
		  
		  List list = this.bpmService.getMyTasks(queryCriteria);

		  return "taskList";
	  }

	public List<TaskEntity> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskEntity> taskList) {
		this.taskList = taskList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}