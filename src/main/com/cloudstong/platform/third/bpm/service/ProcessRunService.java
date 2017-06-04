package com.cloudstong.platform.third.bpm.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.jms.MessageProducer;
import com.cloudstong.platform.core.model.InnerMessage;
import com.cloudstong.platform.core.model.Mail;
import com.cloudstong.platform.core.model.SmsMobile;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.TimeUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.dao.BpmTaskAssigneeDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.dao.TaskDao;
import com.cloudstong.platform.third.bpm.dao.TaskOpinionDao;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormHandlerDao;
import com.cloudstong.platform.third.bpm.form.model.BpmFormData;
import com.cloudstong.platform.third.bpm.form.model.PkValue;
import com.cloudstong.platform.third.bpm.form.service.FormDataUtil;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.model.BpmTaskAssignee;
import com.cloudstong.platform.third.bpm.model.ExecutionStack;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.model.ProcessTask;
import com.cloudstong.platform.third.bpm.model.TaskFork;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.impl.BpmActService;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmConst;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@Service
public class ProcessRunService {
	protected Logger log = LoggerFactory.getLogger(ProcessRunService.class);

	@Resource
	private ProcessRunDao dao;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	@Resource
	private BpmService bpmService;

	@Resource
	private TaskSignDataService taskSignDataService;

	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskUserAssignService taskUserAssignService;

	@Resource
	private TaskUserService taskUserService;

	@Resource
	private TaskOpinionDao taskOpinionDao;

	@Resource
	private SysUserDao sysUserDao;
	private MessageProducer messageProducer;

	@Resource
	private BpmFormRunService bpmFormRunService;

	@Resource
	private TaskDao taskDao;

	@Resource
	private BpmRunLogService bpmRunLogService;

	// @Resource
	// private SysTemplateService sysTemplateService;

	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;

//	@Resource
//	private CalendarAssignService calendarAssignService;

	@Resource
	private BpmTaskAssigneeDao bpmTaskAssigneeDao;

	@Resource
	private ExecutionDao executionDao;

	@Resource
	private JumpRule jumpRule;

	@Resource
	private BpmActService bpmActService;

	@Resource
	private ExecutionStackService executionStackService;

	@Resource
	private Properties configproperties;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private SysUserService sysUserService;

	public void setMessageProducer(MessageProducer producer) {
		messageProducer = producer;
	}

	private void setNextTaskUser(ProcessCmd processCmd) {
		if (processCmd.isBack().intValue() == 0) {
			String[] nodeIds = processCmd.getLastDestTaskIds();
			String[] nodeUserIds = processCmd.getLastDestTaskUids();

			if ((nodeIds != null) && (nodeUserIds != null)) {
				taskUserAssignService.addNodeUser(nodeIds, nodeUserIds);
			}
		}

		if (processCmd.getTaskExecutors().size() > 0) {
			taskUserAssignService.setExecutorsFromList(processCmd.getTaskExecutors());
		}
		TaskThreadService.setProcessCmd(processCmd);
	}

	public void delegate(String taskId, String userId, String description) {
		bpmService.updateTaskAssignee(taskId, userId);

		TaskEntity taskEntity = bpmService.getTask(taskId);
		ProcessRun processRun = processRunService.getByActInstanceId(taskEntity.getProcessInstanceId());
		Long runId = processRun.getRunId();

		SysUser user = sysUserDao.getById(Long.valueOf(Long.parseLong(userId)));
		SysUser curUser = AppContext.getCurrentUser();
		Long curUserId = 1L;
		String curUserName = "系统";
		if (curUser != null) {
			curUserId = curUser.getId();
			curUserName = curUser.getFullname();
		}

		BpmTaskAssignee bpmTaskAssignee = new BpmTaskAssignee();
		bpmTaskAssignee.setId(Long.valueOf(UniqueIdUtil.genId()));
		bpmTaskAssignee.setTaskId(taskId);
		bpmTaskAssignee.setTaskName(taskEntity.getName());
		bpmTaskAssignee.setUserId(curUserId);
		bpmTaskAssignee.setUserName(curUserName);
		bpmTaskAssignee.setSubject(processRun.getSubject());
		bpmTaskAssignee.setTaskStatus(BpmTaskAssignee.TASK_NO_EXC);
		bpmTaskAssignee.setAssigneeId(Long.valueOf(Long.parseLong(userId)));
		bpmTaskAssignee.setAssigneeTime(new Date());
		bpmTaskAssignee.setAssigneeName(user.getFullname());
		bpmTaskAssignee.setRunId(runId);
		bpmTaskAssignee.setMemo(description);
		bpmTaskAssigneeDao.save(bpmTaskAssignee);
		String memo = "流程:" + processRun.getSubject() + ", 【" + curUserName + "】将任务【" + taskEntity.getName() + "】交给【" + user.getFullname() + "】执行。";
		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_DELEGATE, memo);

		memo = "流程:" + processRun.getSubject() + ", 【" + curUserName + "】将任务【" + taskEntity.getName() + "】交给【" + user.getFullname() + "】执行。";
		bpmRunLogService.addRunLog(user, runId, BpmRunLog.OPERATOR_TYPE_DELEGATE, memo);
	}

	private void setAgentUser(TaskEntity taskEntity, ProcessCmd processCmd, Long runId) {
		SysUser curUser = AppContext.getCurrentUser();
		SysUser sysUser = (SysUser) sysUserDao.getById(new Long(taskEntity.getAssignee()));
		String memo = "代理执行了【" + sysUser.getUsername() + "】在任务节点【" + taskEntity.getName() + "】的任务";

		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_AGENT, memo);
		memo = "在节点【" + taskEntity.getName() + "】的任务，被【" + curUser.getUsername() + "】代理执行了";
		bpmRunLogService.addRunLog(sysUser, runId, BpmRunLog.OPERATOR_TYPE_AGENT, memo);
	}

	private void setVariables(String taskId, ProcessCmd processCmd) {
		Map vars = processCmd.getVariables();
		if (BeanUtils.isNotEmpty(vars)) {
			for (Iterator it = vars.entrySet().iterator(); it.hasNext();) {
				Map.Entry obj = (Map.Entry) it.next();
				taskService.setVariable(taskId, (String) obj.getKey(), obj.getValue());
			}
		}
		Map formVars = taskService.getVariables(taskId);
		formVars.put("isExtCall", Boolean.valueOf(processCmd.isInvokeExternal()));
		formVars.put("subject_", processCmd.getSubject());

		TaskThreadService.setVariables(formVars);
		TaskThreadService.getVariables().putAll(processCmd.getVariables());
	}

	public ProcessRun nextProcess(ProcessCmd processCmd) throws Exception {
		ProcessRun processRun = null;
		String taskId = processCmd.getTaskId();
		TaskEntity taskEntity = bpmService.getTask(taskId);
		if ((taskEntity.getExecutionId() == null) && ("通知任务".equals(taskEntity.getDescription()))) {
			return null;
		}

		Object nextPathObj = processCmd.getFormDataMap().get("nextPathId");
		if (nextPathObj != null) {
			bpmService.setTaskVariable(taskId, "NextPathId", nextPathObj.toString());
		}
		String parentNodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		String instanceId = taskEntity.getProcessInstanceId();
		String executionId = taskEntity.getExecutionId();

		processRun = dao.getByActInstanceId(instanceId);
		processCmd.setProcessRun(processRun);

		if ((processCmd.isAgentTask()) && (StringUtils.isNotEmpty(taskEntity.getAssignee()))) {
			setAgentUser(taskEntity, processCmd, processRun.getRunId());
		}

		boolean isAssigneeTask = isAssigneeTask(taskId);
		try {
			String taskToken = (String) taskService.getVariableLocal(taskId, TaskFork.TAKEN_VAR_NAME);
			BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId, parentNodeId);

			setNextTaskUser(processCmd);

			Map optionsMap = null;
			if (!processCmd.isInvokeExternal()) {
				BpmFormData bpmFormData = handlerFormData(processCmd, processRun, parentNodeId);
				if (bpmFormData != null) {
					optionsMap = bpmFormData.getOptions();
				}
			}

			if (!processCmd.isSkipPreHandler()) {
				invokeHandler(processCmd, bpmNodeSet, true);
			}

			ExecutionStack parentStack = backPrepare(processCmd, taskEntity, taskToken);

			if (parentStack != null) {
				parentNodeId = parentStack.getNodeId();
			}

			signUsersOrSignVoted(processCmd, taskEntity);

			processCmd.setSubject(processRun.getSubject());
			setVariables(taskId, processCmd);

			if (processCmd.isOnlyCompleteTask()) {
				bpmService.onlyCompleteTask(taskId);
			} else if (StringUtils.isNotEmpty(processCmd.getDestTask())) {
				bpmService.transTo(taskId, processCmd.getDestTask());
			} else {
				ExecutionEntity execution = bpmActService.getExecution(taskEntity.getExecutionId());

				TaskThreadService.setObject(processCmd.getVoteAgree());
				String jumpTo = jumpRule.evaluate(execution, bpmNodeSet.getIsJumpForDef());
				TaskThreadService.removeObject();
				bpmService.transTo(taskId, jumpTo);
			}

			if (!processCmd.isSkipAfterHandler()) {
				invokeHandler(processCmd, bpmNodeSet, false);
			}

			if ((StringUtil.isEmpty(processRun.getBusinessKey())) && (StringUtil.isNotEmpty(processCmd.getBusinessKey()))) {
				processRun.setBusinessKey(processCmd.getBusinessKey());

				runtimeService.setVariable(executionId, "businessKey", processCmd.getBusinessKey());
			}

			if ((processCmd.isBack().intValue() > 0) && (parentStack != null)) {
				executionStackService.pop(parentStack, processCmd.isRecover());
			} else {
				List map = TaskThreadService.getExtSubProcess();
				if (BeanUtils.isEmpty(map)) {
					executionStackService.addStack(instanceId, parentNodeId, taskToken);
				} else {
					initExtSubProcessStack();
				}

			}

			if ((processCmd.isBack().intValue() > 0) || (StringUtils.isNotEmpty(processCmd.getDestTask()))) {
				taskDao.updateOldTaskDefKeyByInstIdNodeId(taskEntity.getTaskDefinitionKey() + "_1", taskEntity.getTaskDefinitionKey(),
						taskEntity.getProcessInstanceId());
			}

			if (isAssigneeTask) {
				List<BpmTaskAssignee> list = bpmTaskAssigneeService.getByTaskId(taskId);
				for (BpmTaskAssignee bta : list) {
					bta.setTaskStatus(BpmTaskAssignee.TASK_EXC);
					bpmTaskAssigneeDao.update(bta);
				}
			}

			genNotifyTasks();

			updOption(optionsMap, taskId);

			notify(TaskThreadService.getNewTasks(), processCmd.getInformType(), processRun.getSubject(), null);

			recordLog(processCmd, taskEntity.getName(), processRun.getRunId());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			clearThreadLocal();
		}
		return processRun;
	}

	public boolean isAssigneeTask(String taskId) {
		int count = bpmTaskAssigneeService.getCountByTaskId(taskId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	private void initExtSubProcessStack() {
		List<String> list = TaskThreadService.getExtSubProcess();
		if (BeanUtils.isEmpty(list))
			return;
		List taskList = TaskThreadService.getNewTasks();
		Map map = getMapByTaskList(taskList);
		for (String instanceId : list) {
			List tmpList = (List) map.get(instanceId);
			executionStackService.initStack(instanceId, tmpList);
		}
	}

	private Map<String, List<Task>> getMapByTaskList(List<Task> taskList) {
		Map map = new HashMap();
		for (Task task : taskList) {
			String instanceId = task.getProcessInstanceId();
			if (map.containsKey(instanceId)) {
				((List) map.get(instanceId)).add(task);
			} else {
				List list = new ArrayList();
				list.add(task);
				map.put(instanceId, list);
			}
		}
		return map;
	}

	private void clearThreadLocal() {
		TaskThreadService.clearAll();
		TaskUserAssignService.clearAll();
	}

	private void genNotifyTasks() {
		List<Task> taskList = TaskThreadService.getNewTasks();
		if (BeanUtils.isEmpty(taskList)) {
			return;
		}
		String preTaskUser = TaskThreadService.getPreTaskUser();
		for (Task task : taskList) {
			String actDefId = task.getProcessDefinitionId();
			String nodeId = task.getTaskDefinitionKey();
			String instanceId = task.getProcessInstanceId();
			String startUserId = (String) runtimeService.getVariable(instanceId, "startUser");
			List userList = bpmNodeUserService.getUsers("NOTIFY", actDefId, instanceId, nodeId, startUserId, preTaskUser);
			bpmService.genNotifyTask((DelegateTask) task, userList);
		}
	}

	public void saveOpinion(String taskId, TaskOpinion taskOpinion) {
		taskOpinionDao.save(taskOpinion);
		taskService.deleteTask(taskId);
	}

	private void recordLog(ProcessCmd processCmd, String taskName, Long runId) throws Exception {
		String memo = "";
		Integer type = Integer.valueOf(-1);

		if (processCmd.isRecover()) {
			type = BpmRunLog.OPERATOR_TYPE_RETRIEVE;
			memo = "用户在任务节点[" + taskName + "]执行了追回操作。";
		} else if (BpmConst.TASK_BACK.equals(processCmd.isBack())) {
			type = BpmRunLog.OPERATOR_TYPE_REJECT;
			memo = "用户在任务节点[" + taskName + "]执行了驳回操作。";
		} else if (BpmConst.TASK_BACK_TOSTART.equals(processCmd.isBack())) {
			type = BpmRunLog.OPERATOR_TYPE_REJECT2SPONSOR;
			memo = "用户在任务节点[" + taskName + "]执行了驳回到发起人操作。";
		} else {
			if (TaskOpinion.STATUS_AGREE.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_AGREE;
				memo = "用户在任务节点[" + taskName + "]执行了同意操作。";
			} else if (TaskOpinion.STATUS_REFUSE.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_OBJECTION;
				memo = "用户在任务节点[" + taskName + "]执行了反对操作。";
			} else if (TaskOpinion.STATUS_ABANDON.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_ABSTENTION;
				memo = "用户在任务节点[" + taskName + "]执行了弃权操作。";
			}

			if (TaskOpinion.STATUS_CHANGEPATH.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_CHANGEPATH;
				memo = "用户在任务节点[" + taskName + "]执行了更改执行路径操作。";
			}
		}
		if (type.intValue() == -1) {
			return;
		}
		bpmRunLogService.addRunLog(runId, type, memo);
	}

	private ExecutionStack backPrepare(ProcessCmd processCmd, TaskEntity taskEntity, String taskToken) {
		if (processCmd.isBack().intValue() == 0)
			return null;
		String aceDefId = taskEntity.getProcessDefinitionId();
		String instanceId = taskEntity.getProcessInstanceId();
		String backToNodeId = bpmDefinitionDao.getByActDefId(aceDefId).getStartFirstNode();
		ExecutionStack parentStack = null;

		if (processCmd.isBack().equals(BpmConst.TASK_BACK)) {
			parentStack = executionStackService.backPrepared(processCmd, taskEntity, taskToken);
		} else if (processCmd.isBack() == BpmConst.TASK_BACK_TOSTART) {
			if (StringUtil.isEmpty(backToNodeId)) {
				backToNodeId = getFirstNodetByDefId(aceDefId);
			}
			if (StringUtil.isNotEmpty(backToNodeId)) {
				parentStack = executionStackService.getLastestStack(instanceId, backToNodeId, null);
				if (parentStack != null) {
					processCmd.setDestTask(parentStack.getNodeId());
					taskUserAssignService.addNodeUser(parentStack.getNodeId(), parentStack.getAssignees());
				}
			}
		}
		return parentStack;
	}

	private void pushUser(Map<SysUser, List<Task>> users, SysUser user, Task task) {
		if (users.containsKey(user)) {
			((List) users.get(user)).add(task);
		} else {
			List list = new ArrayList();
			list.add(task);
			users.put(user, list);
		}
	}

	private Map<String, String> getDefaultTemp() throws Exception {
		// SysTemplate innerTemp = sysTemplateService.getDefaultByType(SysTemplate.INNER_TEMP_TYPE);
		// if (innerTemp == null) {
		// throw new Exception("模板中未找到内部消息的默认模板或系统模板");
		// }
		// SysTemplate mailTemp = sysTemplateService.getDefaultByType(SysTemplate.MAIL_TEMP_TYPE);
		// if (mailTemp == null) {
		// throw new Exception("模板中未找到邮件的默认模板或系统模板");
		// }
		// SysTemplate shortTemp = sysTemplateService.getDefaultByType(SysTemplate.SHORT_TEMP_TYPE);
		// if (shortTemp == null) {
		// throw new Exception("模板中未找到手机短信的默认模板或系统模板");
		// }
		 Map map = new HashMap();
		// map.put("inner", innerTemp.getContent());
		// map.put("mail", mailTemp.getContent());
		// map.put("shortmsg", shortTemp.getContent());

		return map;
	}

	public void notify(List<Task> taskList, String informTypes, String title, Map<String, String> msgTempMap) throws Exception {
		if (messageProducer == null) {
			log.debug("notify messageProducer is null ");
			return;
		}

		if (msgTempMap == null)
			msgTempMap = getDefaultTemp();
		if (taskList == null)
			return;
		Map users = new HashMap();

		for (Task task : taskList) {
			if (StringUtil.isNotEmpty(task.getAssignee())) {
				SysUser user = (SysUser) sysUserDao.getById(Long.valueOf(Long.parseLong(task.getAssignee())));
				pushUser(users, user, task);
			} else {
				Set<SysUser> cUIds = taskUserService.getCandidateUsers(task.getId());
				for (SysUser user : cUIds) {
					pushUser(users, user, task);
				}
			}
		}

		for (Iterator iterator = users.keySet().iterator(); iterator.hasNext();) {
			SysUser user = (SysUser) iterator.next();
			List tasks = (List) users.get(user);
			Iterator taskIterator = tasks.iterator();
			for (; taskIterator.hasNext();) {
				Task task = (Task) taskIterator.next();

				sendInnerMessage(user, title, task.getId(), (String) msgTempMap.get("inner"));

				if (!StringUtil.isEmpty(informTypes)) {
					if (informTypes.contains("1")) {
						sendShortMessage(user, title, (String) msgTempMap.get("mail"));
					}

					if (informTypes.contains("2"))
						sendMail(user, title, task.getId(), (String) msgTempMap.get("shortmsg"));
				}
			}
		}
	}

	private void sendInnerMessage(SysUser receiverUser, String subject, String taskId, String tempStr) throws Exception {
		String url = configproperties.get("serverUrl") + "/platform/bpm/task/toStart.ht?taskId=" + taskId;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname()).replace("${发件人}", "系统消息").replace("${跳转地址}", url).replace("${事项名称}", subject);

		InnerMessage innerMessage = new InnerMessage();
		innerMessage.setSubject(subject);
		innerMessage.setFrom("0");
		innerMessage.setFromName("系统消息");
		innerMessage.setCanReply(new Short("0").shortValue());
		innerMessage.setContent(tempStr);
		innerMessage.setTo(receiverUser.getId().toString());
		innerMessage.setToName(receiverUser.getFullname());
		innerMessage.setSendDate(new Date());

		messageProducer.send(innerMessage);
	}

	private void sendShortMessage(SysUser receiverUser, String subject, String tempStr) throws Exception {
		String phone = receiverUser.getMobile();
		if (phone == null)
			return;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname()).replace("${发件人}", "系统消息").replace("${跳转地址}", "【跳转地址】")
				.replace("${事项名称}", subject);

		SmsMobile smsMobile = new SmsMobile();
		smsMobile.setPhoneNumber(receiverUser.getMobile());
		smsMobile.setSmsContent(tempStr);

		messageProducer.send(smsMobile);
	}

	private void sendMail(SysUser receiverUser, String subject, String taskId, String tempStr) throws Exception {
		if (receiverUser.getEmail().isEmpty())
			return;
		String url = configproperties.get("serverUrl") + "/platform/bpm/task/toStart.ht?taskId=" + taskId;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname()).replace("${发件人}", "系统消息").replace("${跳转地址}", url).replace("${事项名称}", subject);

		Mail mailModel = new Mail();
		mailModel.setSubject(subject);
		String[] sendTos = { receiverUser.getEmail() };
		mailModel.setTo(sendTos);
		mailModel.setContent(tempStr);
		mailModel.setSendDate(new Date());

		messageProducer.send(mailModel);
	}

	private void updOption(Map<String, String> optionsMap, String taskId) {
		if (BeanUtils.isEmpty(optionsMap))
			return;

		Long lTaskId = Long.valueOf(Long.parseLong(taskId));
		TaskOpinion taskOpinion = taskOpinionDao.getByTaskId(lTaskId);

		if (taskOpinion == null)
			return;

		Set set = optionsMap.keySet();
		String key = (String) set.iterator().next();
		String value = (String) optionsMap.get(key);

		taskOpinion.setFieldName(key);
		taskOpinion.setOpinion(value);

		taskOpinionDao.update(taskOpinion);
	}

	private void signUsersOrSignVoted(ProcessCmd processCmd, TaskEntity taskEntity) {
		String nodeId = taskEntity.getTaskDefinitionKey();
		String taskId = taskEntity.getId();

		boolean isSignTask = bpmService.isSignTask(taskEntity);

		if (isSignTask) {
			Map executorMap = processCmd.getTaskExecutor();
			if ((executorMap != null) && (executorMap.containsKey(nodeId))) {
				List executorList = (List) executorMap.get(nodeId);
				taskUserAssignService.setExecutorsFromList(executorList);
			}
		}

		if (processCmd.getVoteAgree() != null) {
			if (isSignTask) {
				taskSignDataService.signVoteTask(taskId, processCmd.getVoteContent(), processCmd.getVoteAgree());
			}
			processCmd.getVariables().put("approvalStatus_" + nodeId, processCmd.getVoteAgree());
			processCmd.getVariables().put("approvalContent_" + nodeId, processCmd.getVoteContent());
		}
	}

	private BpmDefinition getBpmDefinitionProcessCmd(ProcessCmd processCmd) {
		BpmDefinition bpmDefinition = null;
		if (processCmd.getActDefId() != null)
			bpmDefinition = bpmDefinitionDao.getByActDefId(processCmd.getActDefId());
		else {
			bpmDefinition = bpmDefinitionDao.getByActDefKeyIsMain(processCmd.getFlowKey());
		}
		return bpmDefinition;
	}

	private ProcessInstance startWorkFlow(ProcessCmd processCmd) {
		String businessKey = processCmd.getBusinessKey();
		String userId = AppContext.getCurrentUserId().toString();
		ProcessInstance processInstance = null;
		processCmd.getVariables().put("businessKey", businessKey);

		Authentication.setAuthenticatedUserId(userId);
		if (processCmd.getActDefId() != null)
			processInstance = bpmService.startFlowById(processCmd.getActDefId(), businessKey, processCmd.getVariables());
		else {
			processInstance = bpmService.startFlowByKey(processCmd.getFlowKey(), businessKey, processCmd.getVariables());
		}
		Authentication.setAuthenticatedUserId(null);
		return processInstance;
	}

	public String getFirstNodetByDefId(String actDefId) {
		String bpmnXml = bpmService.getDefXmlByProcessDefinitionId(actDefId);

		String firstTaskNode = BpmUtil.getFirstTaskNode(bpmnXml);
		return firstTaskNode;
	}

	private void invokeHandler(ProcessCmd processCmd, BpmNodeSet bpmNodeSet, boolean isBefore) throws Exception {
		if (bpmNodeSet == null)
			return;
		String handler = "";
		if (isBefore)
			handler = bpmNodeSet.getBeforeHandler();
		else {
			handler = bpmNodeSet.getAfterHandler();
		}
		if (StringUtil.isEmpty(handler)) {
			return;
		}

		String[] aryHandler = handler.split("[.]");
		if (aryHandler != null) {
			String beanId = aryHandler[0];
			String method = aryHandler[1];

			Object serviceBean = AppUtil.getBean(beanId);
			if (serviceBean != null) {
				Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[] { ProcessCmd.class });
				invokeMethod.invoke(serviceBean, new Object[] { processCmd });
			}
		}
	}

	public BpmNodeSet getStartBpmNodeSet(Long defId, String actDefId, String nodeId, Short toFirstNode) {
		BpmNodeSet bpmNodeSetStart = bpmNodeSetDao.getBySetType(defId, BpmNodeSet.SetType_StartForm);
		BpmNodeSet bpmNodeSetGlobal = bpmNodeSetDao.getBySetType(defId, BpmNodeSet.SetType_GloabalForm);
		if (bpmNodeSetStart == null) {
			if (toFirstNode.shortValue() == 1) {
				BpmNodeSet firstBpmNodeSet = bpmNodeSetDao.getByDefIdNodeId(defId, nodeId);

				if (firstBpmNodeSet.getFormType() != null) {
					return firstBpmNodeSet;
				}

				if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null)) {
					return bpmNodeSetGlobal;
				}

			} else if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null)) {
				return bpmNodeSetGlobal;
			}
		} else {
			return bpmNodeSetStart;
		}
		return null;
	}

	public ProcessRun startProcess(ProcessCmd processCmd) throws Exception {
		BpmDefinition bpmDefinition = getBpmDefinitionProcessCmd(processCmd);

		if (bpmDefinition == null)
			throw new Exception("没有该流程的定义或未发布！");
		if (bpmDefinition.getDisableStatus() == BpmDefinition.DISABLEStATUS_DA) {
			throw new Exception("该流程已经被禁用！");
		}
		Long defId = bpmDefinition.getDefId();

		Short toFirstNode = bpmDefinition.getToFirstNode();
		String actDefId = bpmDefinition.getActDefId();

		String nodeId = getFirstNodetByDefId(actDefId);

		BpmNodeSet bpmNodeSet = getStartBpmNodeSet(defId, actDefId, nodeId, toFirstNode);

		SysUser sysUser = AppContext.getCurrentUser();
		ProcessRun processRun = null;
		try {
			if (toFirstNode.shortValue() == 1) {
				List excutorList = new ArrayList();
				excutorList.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
				taskUserAssignService.addNodeUser(nodeId, excutorList);
			}

			setNextTaskUser(processCmd);

			processRun = initProcessRun(bpmDefinition);

			processCmd.getVariables().put("flowRunId", processRun.getRunId());

			String businessKey = "";

			if (!processCmd.isInvokeExternal()) {
				BpmFormData bpmFormData = handlerFormData(processCmd, processRun, "");
				if (bpmFormData != null) {
					businessKey = processCmd.getBusinessKey();
				}

			}

			if (!processCmd.isSkipPreHandler()) {
				invokeHandler(processCmd, bpmNodeSet, true);
			}

			if (StringUtil.isEmpty(businessKey)) {
				businessKey = processCmd.getBusinessKey();
			}

			String subject = getSubject(bpmDefinition, processCmd);
			
			SysOrg sysOrg = AppContext.getCurrentOrg();

		      if (sysOrg != null) {
		        processCmd.addVariable("startOrgId", sysOrg.getId());
		      }

		      processCmd.addVariable("flowRunId", processRun.getRunId());

		      processCmd.addVariable("subject_", subject);


			ProcessInstance processInstance = startWorkFlow(processCmd);

			String processInstanceId = processInstance.getProcessInstanceId();
			processRun.setBusinessKey(businessKey);

			processRun.setActInstId(processInstanceId);
			processRun.setSubject(subject);
			if (sysOrg != null) {
				processRun.setStartOrgId(sysOrg.getId());
				processRun.setStartOrgName(sysOrg.getOrgName());
			}

			add(processRun);
			List taskList = TaskThreadService.getNewTasks();

			executionStackService.initStack(processInstanceId, taskList);

			processCmd.setProcessRun(processRun);

			if (!processCmd.isSkipAfterHandler()) {
				invokeHandler(processCmd, bpmNodeSet, false);
			}

			if (toFirstNode.shortValue() == 1) {
				handJumpOverFirstNode(processInstanceId, processCmd);
			}

			if (!processCmd.isInvokeExternal()) {
				bpmFormRunService.addFormRun(actDefId, processRun.getRunId(), processInstanceId);
			}

			genNotifyTasks(processInstanceId, AppContext.getCurrentUserId().toString());

			notify(TaskThreadService.getNewTasks(), processCmd.getInformType(), subject, null);

			String memo = "启动流程:" + subject;
			bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_START, memo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			clearThreadLocal();
		}

		return processRun;
	}

	private void genNotifyTasks(String instanceId, String startUserId) {
		List<Task> taskList = TaskThreadService.getNewTasks();
		if (taskList == null)
			return;
		for (Task task : taskList) {
			String actDefId = task.getProcessDefinitionId();
			String nodeId = task.getTaskDefinitionKey();
			List userList = bpmNodeUserService.getUsers("NOTIFY", actDefId, instanceId, nodeId, startUserId, startUserId);
			bpmService.genNotifyTask((DelegateTask) task, userList);
		}
	}

	private void handJumpOverFirstNode(String processInstanceId, ProcessCmd processCmd) throws Exception {
		TaskThreadService.clearNewTasks();
		List taskList = bpmService.getTasks(processInstanceId);
		ProcessTask taskEntity = (ProcessTask) taskList.get(0);
		String taskId = taskEntity.getId();
		String parentNodeId = taskEntity.getTaskDefinitionKey();

		processCmd.getVariables().put("approvalStatus_" + parentNodeId, TaskOpinion.STATUS_AGREE);
		processCmd.getVariables().put("approvalContent_" + parentNodeId, "填写表单");
		setVariables(taskId, processCmd);

		bpmService.transTo(taskId, "");
		executionStackService.addStack(taskEntity.getProcessInstanceId(), parentNodeId, "");
	}

	private BpmFormData handlerFormData(ProcessCmd processCmd, ProcessRun processRun, String nodeId) throws Exception {
//		String json = processCmd.getFormData();
//
//		if (StringUtils.isEmpty(json)) {
//			return null;
//		}

		BpmFormData bpmFormData = new BpmFormData();
		String businessKey = "";

		boolean isStartFlow = false;
		if (StringUtil.isEmpty(nodeId)) {
			businessKey = processCmd.getBusinessKey();
			isStartFlow = true;
		} else {
			businessKey = processRun.getBusinessKey();
		}

		if (StringUtil.isNotEmpty(businessKey)) {
			PkValue pkValue = new PkValue("ID", businessKey);
			pkValue.setIsAdd(false);
//			bpmFormData = FormDataUtil.parseJson(json, pkValue);
//			Map map = bpmFormHandlerDao.getByKey(bpmFormData.getTableName(), businessKey);
//			if (BeanUtils.isEmpty(map.get("flowRunId_"))) {
//				setContextMainFields(bpmFormData, processRun);
//			}
		} else {
			bpmFormData.setPkValue(new PkValue("ID",UniqueIdUtil.genId()));

//			setContextMainFields(bpmFormData, processRun);
		}

//		processCmd.putVariables(bpmFormData.getVariables());
//
//		PkValue pkValue = bpmFormData.getPkValue();
//		businessKey = pkValue.getValue().toString();

		processRun.setBusinessKey(businessKey);
		processCmd.setBusinessKey(businessKey);

		if (isStartFlow) {
			//bpmFormHandlerDao.handFormData(bpmFormData, "", "");
		} else {
			//bpmFormHandlerDao.handFormData(bpmFormData, processRun.getActDefId(), nodeId);
			update(processRun);
		}
		return bpmFormData;
	}

	private void setContextMainFields(BpmFormData bpmFormData, ProcessRun processRun) {
		String userId = AppContext.getCurrentUserId().toString();

		bpmFormData.addMainFields("curentUserId_", userId);

		SysOrg currentOrg = new SysOrg();// AppContext.getCurrentOrg();
		if (currentOrg != null) {
			bpmFormData.addMainFields("curentOrgId_", currentOrg.getId());
		}

		bpmFormData.addMainFields("flowRunId_", processRun.getRunId());

		bpmFormData.addMainFields("defId_", processRun.getDefId());
	}

	public String saveData(String json, String userId, String taskId, Long defId, String bizKey) throws Exception {
		if (StringUtil.isEmpty(json)) {
			return "-1";
		}
		String businessKey = "";
		if (StringUtil.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = dao.getByActInstanceId(taskEntity.getProcessInstanceId());
			PkValue pkValue = new PkValue("ID", processRun.getBusinessKey());
			BpmFormData bpmFormData = FormDataUtil.parseJson(json, pkValue);
			bpmFormHandlerDao.handFormData(bpmFormData, "", "");
			businessKey = processRun.getBusinessKey();
		} else {
			BpmFormData bpmFormData = null;
			if (StringUtil.isNotEmpty(bizKey)) {
				PkValue pkValue = new PkValue("ID", bizKey);
				bpmFormData = FormDataUtil.parseJson(json, pkValue);
			} else {
				bpmFormData = FormDataUtil.parseJson(json);
			}

			bpmFormData.addMainFields("curentUserId_", userId);

			bpmFormData.addMainFields("defId_", defId);

			bpmFormHandlerDao.handFormData(bpmFormData, "", "");
			PkValue pkValue = bpmFormData.getPkValue();
			businessKey = pkValue.getValue().toString();
		}
		return businessKey;
	}

	private String getSubject(BpmDefinition bpmDefinition, ProcessCmd processCmd) {
		if (StringUtils.isNotEmpty(processCmd.getSubject())) {
			return processCmd.getSubject();
		}

		String rule = bpmDefinition.getTaskNameRule();
		Map map = new HashMap();
		map.put("title", bpmDefinition.getSubject());
		SysUser user = AppContext.getCurrentUser();
		map.put("startUser", user.getFullname());
		map.put("startDate", TimeUtil.getCurrentDate());
		map.put("startTime", TimeUtil.getCurrentTime());
		map.put("businessKey", processCmd.getBusinessKey());
		map.putAll(processCmd.getVariables());
		rule = BpmUtil.getTitleByRule(rule, map);
		return rule;
	}

	private ProcessRun initProcessRun(BpmDefinition bpmDefinition) {
		ProcessRun processRun = new ProcessRun();
		SysUser curUser = AppContext.getCurrentUser();
		processRun.setCreator(curUser.getFullname());
		processRun.setCreatorId(curUser.getId());

		processRun.setActDefId(bpmDefinition.getActDefId());
		processRun.setDefId(bpmDefinition.getDefId());
		processRun.setProcessName(bpmDefinition.getSubject());

		processRun.setCreatetime(new Date());
		processRun.setStatus(ProcessRun.STATUS_RUNNING);
		try {
			processRun.setRunId(Long.valueOf(UniqueIdUtil.genId()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return processRun;
	}

	public ProcessRun getByActInstanceId(String processInstanceId) {
		return dao.getByActInstanceId(processInstanceId);
	}

	public List<ProcessRun> getAllHistory(QueryCriteria queryFilter) {
		return dao.getAllHistory(queryFilter);
	}
	
	public PageResult queryHistory(QueryCriteria queryFilter) {
		return dao.query("getAllFinish",queryFilter);
	}

	public List<ProcessRun> getMyAttend(QueryCriteria filter) {
		return dao.getMyAttend(filter);
	}

	public void delByIds(Long[] ids) {
		if ((ids == null) || (ids.length == 0))
			return;
		for (Long uId : ids) {
			ProcessRun processRun = (ProcessRun) dao.getById(uId);
			Short procStatus = processRun.getStatus();

			if (ProcessRun.STATUS_FINISH != procStatus) {
				deleteProcessInstance(processRun);
			} else {
				String memo = "用户删除了流程实例[" + processRun.getProcessName() + "]。";
				bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_DELETEINSTANCE, memo);
				delById(uId);
			}
		}
	}

	public List<ProcessRun> getMyProcessRun(Long creatorId, String subject, Short status, QueryCriteria pb) {
		return dao.getMyProcessRun(creatorId, subject, status, pb);
	}

	public void add(ProcessRun entity) {
		dao.save(entity);
		ProcessRun history = (ProcessRun) entity.clone();
		dao.addHistory(history);
	}

	public void update(ProcessRun entity) {
		ProcessRun history = (ProcessRun) entity.clone();
		if ((ProcessRun.STATUS_MANUAL_FINISH == entity.getStatus()) || (ProcessRun.STATUS_FINISH == entity.getStatus())) {
			Date endDate = new Date();
			Date startDate = history.getCreatetime();
			long userId = history.getCreatorId().longValue();
			long duration = 180L;//calendarAssignService.getTaskMillsTime(startDate, endDate, userId);
			history.setEndTime(endDate);
			history.setDuration(Long.valueOf(duration));
			dao.updateHistory(history);
			dao.delById(entity.getRunId());
		} else {
			dao.updateHistory(history);
			dao.update(entity);
		}
	}

	public void delById(Long id) {
		dao.delById(id);
		dao.delByIdHistory(id);
	}

	public List<ProcessRun> getByActDefId(String actDefId, QueryCriteria pb) {
		return dao.getByActDefId(actDefId, pb);
	}

	private void deleteProcessRunCasade(ProcessRun processRun) {
		List<ProcessInstance> childrenProcessInstance = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processRun.getActInstId())
				.list();
		for (ProcessInstance instance : childrenProcessInstance) {
			ProcessRun pr = getByActInstanceId(instance.getProcessInstanceId());
			if (pr != null) {
				deleteProcessRunCasade(pr);
			}
		}
		long procInstId = Long.parseLong(processRun.getActInstId());
		Short procStatus = processRun.getStatus();
		if (ProcessRun.STATUS_FINISH != procStatus) {
			executionDao.delVariableByProcInstId(Long.valueOf(procInstId));
			taskDao.delCandidateByInstanceId(Long.valueOf(procInstId));
			taskDao.delByInstanceId(Long.valueOf(procInstId));
			executionDao.delExecutionByProcInstId(Long.valueOf(procInstId));
		}
		String memo = "用户删除了流程实例[" + processRun.getProcessName() + "]。";
		bpmRunLogService.addRunLog(processRun, BpmRunLog.OPERATOR_TYPE_DELETEINSTANCE, memo);
		delById(processRun.getRunId());
	}

	private ProcessRun getRootProcessRun(ProcessRun processRun) {
		ProcessInstance parentProcessInstance = (ProcessInstance) runtimeService.createProcessInstanceQuery()
				.subProcessInstanceId(processRun.getActInstId()).singleResult();
		if (parentProcessInstance != null) {
			ProcessRun parentProcessRun = getByActInstanceId(parentProcessInstance.getProcessInstanceId());

			return getRootProcessRun(parentProcessRun);
		}
		return processRun;
	}

	private void deleteProcessInstance(ProcessRun processRun) {
		ProcessRun rootProcessRun = getRootProcessRun(processRun);
		deleteProcessRunCasade(rootProcessRun);
	}

	public void delByActDefId(String actDefId) {
		List<ProcessRun> list = dao.getbyActDefId(actDefId);
		for (ProcessRun processRun : list) {
			bpmTaskAssigneeDao.delByRunId(processRun.getRunId());
			deleteProcessInstance(processRun);
		}
		dao.delHistroryByActDefId(actDefId);
	}

	public ProcessRun getById(Long id) {
		return dao.getById(id);
	}

	public PageResult getAll(QueryCriteria queryCriteria) {
		return dao.query(queryCriteria);
	}

	public boolean isProcessInstanceExisted(String businessKey) {
		 if (StringUtil.isEmpty(businessKey)) {
	      return false;
	    }
	    return this.dao.isProcessInstanceExisted(businessKey);
	}
}