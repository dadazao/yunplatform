package com.cloudstong.platform.third.bpm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.TaskFork;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserService;
import com.cloudstong.platform.third.bpm.service.BpmProStatusService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.TaskForkService;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;
import com.cloudstong.platform.third.bpm.thread.MessageUtil;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmConst;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

public class TaskCreateListener extends BaseTaskListener {
	private TaskOpinionService taskOpinionService = (TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);

	private TaskUserAssignService taskUserAssignService = (TaskUserAssignService) AppUtil.getBean(TaskUserAssignService.class);

	private TaskForkService taskForkService = (TaskForkService) AppUtil.getBean(TaskForkService.class);

	private BpmNodeSetDao bpmNodeSetDao = (BpmNodeSetDao) AppUtil.getBean(BpmNodeSetDao.class);

	private BpmService bpmService = (BpmService) AppUtil.getBean(BpmService.class);

	private BpmProStatusService bpmProStatusService = (BpmProStatusService) AppUtil.getBean(BpmProStatusService.class);

	private SysUserService sysUserService = (SysUserService) AppUtil.getBean(SysUserService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		String token = TaskThreadService.getToken();
		if (token != null) {
			delegateTask.setVariableLocal(TaskFork.TAKEN_VAR_NAME, token);
		}

		TaskThreadService.addTask((TaskEntity) delegateTask);

		addOpinion(token, delegateTask);

		Long actInstanceId = new Long(delegateTask.getProcessInstanceId());

		bpmProStatusService.addOrUpd(actDefId, actInstanceId, nodeId);

		Map nodeUserMap = taskUserAssignService.getNodeUserMap();

		boolean isHandForkTask = handlerForkTask(actDefId, nodeId, nodeUserMap, delegateTask);
		if (isHandForkTask)
			return;

		boolean isSubProcess = handSubProcessUser(delegateTask);
		if (isSubProcess)
			return;

		boolean isHandExtUser = handExtSubProcessUser(delegateTask);
		if (isHandExtUser)
			return;

		if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
			List executorIds = (List) nodeUserMap.get(nodeId);
			assignUser(delegateTask, executorIds);
			return;
		}

		List executorUsers = taskUserAssignService.getExecutors();

		if (BeanUtils.isNotEmpty(executorUsers)) {
			assignUser(delegateTask, executorUsers);
			return;
		}

		List formUsers = taskUserAssignService.getFormUsers();

		if (BeanUtils.isNotEmpty(formUsers)) {
			assignUser(delegateTask, formUsers);
			return;
		}

		handAssignUserFromDb(actDefId, nodeId, delegateTask);
	}

	private boolean handSubProcessUser(DelegateTask delegateTask) {
		FlowNode flowNode = (FlowNode) NodeCache.getByActDefId(delegateTask.getProcessDefinitionId()).get(delegateTask.getTaskDefinitionKey());
		boolean isMultipleNode = flowNode.getIsMultiInstance().booleanValue();
		if (!isMultipleNode)
			return false;

		TaskExecutor taskExecutor = (TaskExecutor) delegateTask.getVariable("assignee");
		if (taskExecutor != null) {
			assignUser(delegateTask, taskExecutor);

			int completeInstance = ((Integer) delegateTask.getVariable("nrOfCompletedInstances")).intValue();
			int nrOfInstances = ((Integer) delegateTask.getVariable("nrOfInstances")).intValue();

			if (completeInstance == nrOfInstances) {
				delegateTask.removeVariable("subAssignIds");
			}
		}
		return true;
	}

	private boolean handExtSubProcessUser(DelegateTask delegateTask) {
		ExecutionEntity executionEnt = (ExecutionEntity) delegateTask.getExecution();

		if (executionEnt.getSuperExecution() == null)
			return false;
		if (!BpmUtil.isMuiltiExcetion(executionEnt.getSuperExecution()))
			return false;
		String actDefId = executionEnt.getSuperExecution().getProcessDefinitionId();
		Map mapParent = NodeCache.getByActDefId(actDefId);

		String parentNodeId = executionEnt.getSuperExecution().getActivityId();
		String curentNodeId = executionEnt.getActivityId();

		FlowNode parentFlowNode = (FlowNode) mapParent.get(parentNodeId);
		Map subNodeMap = parentFlowNode.getSubProcessNodes();
		FlowNode startNode = NodeCache.getStartNode(subNodeMap);

		if (startNode.getNextFlowNodes().size() == 1) {
			FlowNode nextNode = (FlowNode) startNode.getNextFlowNodes().get(0);
			if (nextNode.getNodeId().equals(curentNodeId)) {
				TaskExecutor taskExecutor = (TaskExecutor) executionEnt.getSuperExecution().getVariable("assignee");
				if (taskExecutor != null) {
					assignUser(delegateTask, taskExecutor);
				}
				return true;
			}
			return false;
		}
		logger.debug("多实例外部调用子流程起始节点后只能跟一个任务节点");
		return false;
	}

	private void addOpinion(String token, DelegateTask delegateTask) {
		TaskOpinion taskOpinion = new TaskOpinion(delegateTask);
		taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
		taskOpinion.setTaskToken(token);
		taskOpinionService.save(taskOpinion);
		logger.debug(taskOpinion.toString());
	}

	private void handAssignUserFromDb(String actDefId, String nodeId, DelegateTask delegateTask) {
		BpmNodeUserService userService = (BpmNodeUserService) AppUtil.getBean(BpmNodeUserService.class);

		ProcessInstance processInstance = bpmService.getProcessInstance(delegateTask.getProcessInstanceId());
		List users = null;
		Map vars = new HashMap();
		vars.put("executionId", delegateTask.getExecutionId());
		if (processInstance != null) {
			String preTaskUser = TaskThreadService.getPreTaskUser();
			if (StringUtils.isEmpty(preTaskUser)) {
				preTaskUser = (String) delegateTask.getVariable("startUser");
			}
			users = (List) userService.getExeUserIdsByInstance(delegateTask.getProcessInstanceId(), nodeId, preTaskUser, vars).get("PARTICIPATION");
		} else {
			String startUserId = (String) delegateTask.getVariable("startUser");
			users = (List) userService.getExeUserIds(actDefId, null, nodeId, startUserId, startUserId, vars).get("PARTICIPATION");
		}
		if (users != null)
			assignUser(delegateTask, users);
	}

	private boolean handlerForkTask(String actDefId, String nodeId, Map<String, List<TaskExecutor>> nodeUserMap, DelegateTask delegateTask) {
		ProcessCmd processCmd = TaskThreadService.getProcessCmd();
		if ((processCmd != null) && (BpmConst.TASK_BACK.equals(processCmd.isBack())))
			return false;
		BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);

		if ((bpmNodeSet != null) && (BpmNodeSet.NODE_TYPE_FORK.equals(bpmNodeSet.getNodeType()))) {
			List taskExecutors = taskUserAssignService.getExecutors();

			if (BeanUtils.isEmpty(taskExecutors)) {
				if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
					taskExecutors = (List) nodeUserMap.get(nodeId);
				} else {
					BpmNodeUserService userService = (BpmNodeUserService) AppUtil.getBean(BpmNodeUserService.class);
					ProcessInstance processInstance = bpmService.getProcessInstance(delegateTask.getProcessInstanceId());
					if (processInstance != null) {
						Map vars = new HashMap();
						vars.put("executionId", delegateTask.getExecutionId());
						String preTaskUser = TaskThreadService.getPreTaskUser();
						if (StringUtils.isEmpty(preTaskUser)) {
							preTaskUser = (String) delegateTask.getVariable("startUser");
						}
						taskExecutors = (List) userService.getExeUserIdsByInstance(delegateTask.getProcessInstanceId(), nodeId, preTaskUser, vars)
								.get("PARTICIPATION");
					}
				}
			}
			if (BeanUtils.isNotEmpty(taskExecutors)) {
				bpmService.newForkTasks((TaskEntity) delegateTask, taskExecutors);
				taskForkService.newTaskFork(delegateTask, bpmNodeSet.getJoinTaskName(), bpmNodeSet.getJoinTaskKey(),
						Integer.valueOf(taskExecutors.size()));
			} else {
				MessageUtil.addMsg("请设置分发人员");
			}

			return true;
		}
		return false;
	}

	private void assignUser(DelegateTask delegateTask, TaskExecutor taskExecutor) {
		if ("user".equals(taskExecutor.getType())) {
			delegateTask.setAssignee(taskExecutor.getExecuteId());
		} else
			delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
	}

	private void assignUser(DelegateTask delegateTask, List<TaskExecutor> executors) {
		if (BeanUtils.isEmpty(executors))
			return;
		TaskOpinion taskOpinion;
		if (executors.size() == 1) {
			TaskExecutor taskExecutor = (TaskExecutor) executors.get(0);

			if ("user".equals(taskExecutor.getType())) {
				delegateTask.setOwner(taskExecutor.getExecuteId());
				delegateTask.setAssignee(taskExecutor.getExecuteId());

				taskOpinion = taskOpinionService.getByTaskId(new Long(delegateTask.getId()));
				if (taskOpinion != null) {
					SysUser sysUser = sysUserService.getById(new Long(delegateTask.getAssignee()));
					Long userId = 1L;
					String userName = "系统";
					if (sysUser != null) {
						userId = sysUser.getId();
						userName = sysUser.getFullname();
					}
					taskOpinion.setExeUserId(userId);
					taskOpinion.setExeFullname(userName);
					taskOpinionService.update(taskOpinion);
				}
			} else {
				delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
			}
		} else {
			for (TaskExecutor taskExecutor : executors)
				if ("user".equals(taskExecutor.getType())) {
					delegateTask.addCandidateUser(taskExecutor.getExecuteId());
				} else
					delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
		}
	}

	protected int getScriptType() {
		return BpmConst.StartScript.intValue();
	}
}