package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.util.ContextUtil;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionStackDao;
import com.cloudstong.platform.third.bpm.model.ExecutionStack;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.ProcessTask;
import com.cloudstong.platform.third.bpm.model.TaskFork;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;

@Service
public class ExecutionStackService {
	
	protected final Log log = LogFactory.getLog(getClass());

	@Resource
	private ExecutionStackDao dao;

	@Resource
	private ExecutionDao executionDao;

	@Resource
	private TaskService taskService;

	@Resource
	private BpmService bpmService;

	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private TaskUserAssignService taskUserAssignService;

	@Resource
	private BpmProStatusDao bpmProStatusDao;

	public void initStack(String actInstId, List<Task> taskList) {
		if (BeanUtils.isEmpty(taskList))
			return;
		Map nodeIdStackMap = new HashMap();
		for (Task task : taskList) {
			String nodeId = task.getTaskDefinitionKey();
			if (!nodeIdStackMap.containsKey(nodeId)) {
				ExecutionStack stack = new ExecutionStack();
				stack.setActInstId(actInstId);
				stack.setActDefId(task.getProcessDefinitionId());
				stack.setAssignees(task.getAssignee());
				stack.setDepth(Integer.valueOf(1));
				stack.setParentId(Long.valueOf(0L));
				stack.setStartTime(new Date());
				stack.setNodeId(nodeId);
				stack.setNodeName(task.getName());
				stack.setTaskIds(task.getId());
				Long stackId = Long.valueOf(UniqueIdUtil.genId());
				stack.setStackId(stackId);
				stack.setNodePath("0." + stackId + ".");
				nodeIdStackMap.put(nodeId, stack);
			} else {
				ExecutionStack stack = (ExecutionStack) nodeIdStackMap.get(nodeId);
				stack.setIsMultiTask(ExecutionStack.MULTI_TASK);
				stack.setAssignees(stack.getAssignees() + "," + task.getAssignee());
				stack.setTaskIds(stack.getTaskIds() + "," + task.getId());
			}
		}

		Iterator stackIt = nodeIdStackMap.values().iterator();
		while (stackIt.hasNext()) {
			ExecutionStack exeStack = (ExecutionStack) stackIt.next();
			dao.save(exeStack);
		}
	}

	public ExecutionStack backPrepared(ProcessCmd processCmd, TaskEntity taskEntity, String taskToken) {
		ExecutionStack parentStack = null;

		String instanceId = taskEntity.getProcessInstanceId();
		String nodeId = taskEntity.getTaskDefinitionKey();

		if (processCmd.getStackId() != null) {
			parentStack = (ExecutionStack) dao.getById(processCmd.getStackId());
		} else if (StringUtils.isEmpty(processCmd.getDestTask())) {
			ExecutionStack executionStack = getLastestStack(instanceId, nodeId, taskToken);
			if ((executionStack != null) && (executionStack.getParentId() != null) && (executionStack.getParentId().longValue() != 0L)) {
				parentStack = (ExecutionStack) dao.getById(executionStack.getParentId());
			}

		}

		if (parentStack != null) {
			processCmd.setDestTask(parentStack.getNodeId());

			String assignee = parentStack.getAssignees();
			String[] aryAssignee = assignee.split(",");
			List list = new ArrayList();
			for (String userId : aryAssignee) {
				list.add(TaskExecutor.getTaskUser(userId, ""));
			}
			taskUserAssignService.addNodeUser(parentStack.getNodeId(), list);
		}

		return parentStack;
	}

	public void pop(ExecutionStack parentStack, boolean isRecover) {
		String instanceId = parentStack.getActInstId();

		List subChilds = dao.getByParentId(parentStack.getStackId());

		SysUser curUser = AppContext.getCurrentUser();

		if (BeanUtils.isEmpty(subChilds))
			return;

		ExecutionStack executionStack = (ExecutionStack) subChilds.get(0);

		String prevNodeId = executionStack.getNodeId();
		TaskOpinion taskOpinion = taskOpinionService.getLatestTaskOpinion(instanceId, prevNodeId);
		Short status = TaskOpinion.STATUS_REJECT;
		if (taskOpinion != null) {
			taskOpinion.setExeUserId(curUser.getId());
			taskOpinion.setExeFullname(curUser.getFullname());
			taskOpinion.setEndTime(new Date());
			taskOpinion.setDurTime(Long.valueOf(taskOpinion.getEndTime().getTime() - taskOpinion.getStartTime().getTime()));

			if (isRecover) {
				status = TaskOpinion.STATUS_RECOVER;
			}
			taskOpinion.setCheckStatus(status);
			taskOpinionService.update(taskOpinion);
		}

		bpmProStatusDao.updStatus(new Long(instanceId), prevNodeId, status);

		dao.delById(executionStack.getStackId());
	}

	private void genSiblingTask(ExecutionStack parentStack, ProcessTask copyTaskEntity) {
		List<ExecutionStack> parentSiblings = dao.getByActInstIdDepExStackId(parentStack.getActInstId(), parentStack.getDepth(),
				parentStack.getStackId());

		if ((parentSiblings == null) || (parentSiblings.size() == 0)) {
			executionDao.updateMainThread(copyTaskEntity.getProcessInstanceId(), copyTaskEntity.getTaskDefinitionKey());
			executionDao.updateTaskToMainThreadId(copyTaskEntity.getProcessInstanceId(), copyTaskEntity.getId());

			executionDao.delNotMainThread(copyTaskEntity.getProcessInstanceId());
			return;
		}

		for (ExecutionStack stack : parentSiblings) {
			List<ExecutionStack> childStackList = dao.getByLikeNodePath(stack.getNodePath());
			boolean isChildTaskExist = false;
			String[] taskIds;
			for (ExecutionStack childStack : childStackList)
				if (childStack.getTaskIds() != null) {
					taskIds = childStack.getTaskIds().split("[,]");
					for (String taskId : taskIds) {
						TaskEntity task = bpmService.getTask(taskId);
						if (task != null) {
							isChildTaskExist = true;
							break;
						}
					}
					if (isChildTaskExist)
						break;
				}
			if (!isChildTaskExist)
				if (StringUtils.isNotEmpty(stack.getAssignees())) {
					String[] assignes = stack.getAssignees().split("[,]");
					for (int i = 0; i < assignes.length; i++) {
						String assign = assignes[i];
						ProcessTask newTask = bpmService.newTask(copyTaskEntity.getId(), assign, stack.getNodeId(), stack.getNodeName());
						bpmProStatusDao.updStatus(new Long(newTask.getProcessInstanceId()), newTask.getTaskDefinitionKey(),
								TaskOpinion.STATUS_CHECKING);
					}
				} else {
					ProcessTask newTask = bpmService.newTask(copyTaskEntity.getId(), stack.getAssignees(), stack.getNodeId(), stack.getNodeName());
					bpmProStatusDao.updStatus(new Long(newTask.getProcessInstanceId()), newTask.getTaskDefinitionKey(), TaskOpinion.STATUS_CHECKING);
				}
		}
	}

	public void pushNewTasks(String actInstId, String destNodeId, List<Task> newTasks, String oldTaskToken) throws Exception {
		if (newTasks.size() == 0)
			return;
		Long curUserId = AppContext.getCurrentUserId();
		if (curUserId == null)
			curUserId = 1L;

		ExecutionStack curExeNode = dao.getLastestStack(actInstId, destNodeId, oldTaskToken);

		ProcessDefinitionEntity processDef = null;

		Map nodeIdStackMap = new HashMap();
		int i = 0;

		boolean isIssued = false;
		Set nodeSet = new HashSet(newTasks);
		if (nodeSet.size() < newTasks.size()) {
			isIssued = true;
		}

		for (Task task : newTasks) {
			i++;
			TaskEntity taskEntity = (TaskEntity) task;
			try {
				taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
			} catch (Exception ex) {
				log.warn("ex:" + ex.getMessage());
			}
			if (taskEntity != null) {
				String nodeId = taskEntity.getTaskDefinitionKey();

				if (processDef == null) {
					processDef = bpmService.getProcessDefinitionEntity(taskEntity.getProcessDefinitionId());
				}

				ActivityImpl taskAct = processDef.findActivity(nodeId);

				if (taskAct != null) {
					String multiInstance = (String) taskAct.getProperty("multiInstance");
					ExecutionStack stack = (ExecutionStack) nodeIdStackMap.get(nodeId);

					if ((StringUtils.isNotEmpty(multiInstance)) && (stack != null)) {
						stack.setIsMultiTask(ExecutionStack.MULTI_TASK);
						stack.setAssignees(stack.getAssignees() + "," + task.getAssignee());
						stack.setTaskIds(stack.getTaskIds() + "," + task.getId());
						dao.update(stack);
					} else {
						Long stackId = Long.valueOf(UniqueIdUtil.genId());
						stack = new ExecutionStack();
						stack.setActInstId(taskEntity.getProcessInstanceId());
						stack.setAssignees(taskEntity.getAssignee());
						stack.setActDefId(taskEntity.getProcessDefinitionId());
						if (curExeNode == null) {
							stack.setDepth(Integer.valueOf(1));
							stack.setParentId(Long.valueOf(0L));
							stack.setNodePath("0." + stackId + ".");
						} else {
							stack.setDepth(Integer.valueOf(curExeNode.getDepth() == null ? 1 : curExeNode.getDepth().intValue() + 1));
							stack.setParentId(curExeNode.getStackId());
							stack.setNodePath(curExeNode.getNodePath() + stackId + ".");
						}

						stack.setStartTime(new Date());
						stack.setNodeId(nodeId);
						stack.setNodeName(taskEntity.getName());
						stack.setTaskIds(taskEntity.getId());
						stack.setStackId(stackId);

						String taskToken = (String) taskService.getVariableLocal(taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
						if (taskToken != null) {
							stack.setTaskToken(taskToken);
						} else if ((stack != null) && (isIssued)) {
							String token = "T_" + i;
							taskService.setVariableLocal(taskEntity.getId(), TaskFork.TAKEN_VAR_NAME, token);
							stack.setTaskToken(token);
						}
						dao.save(stack);
						nodeIdStackMap.put(nodeId, stack);
					}
				}
			}
		}
	}

	public void addStack(String actInstId, String destNodeId, String oldTaskToken) throws Exception {
		List taskList = TaskThreadService.getNewTasks();
		if (taskList != null)
			pushNewTasks(actInstId, destNodeId, taskList, oldTaskToken);
	}

	public List<ExecutionStack> getByParentId(Long parentId) {
		return dao.getByParentId(parentId);
	}

	public List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId) {
		return dao.getByParentIdAndEndTimeNotNull(parentId);
	}

	public List<ExecutionStack> getByActInstIdNodeId(String actInstId, String nodeId) {
		return dao.getByActInstIdNodeId(actInstId, nodeId);
	}

	public ExecutionStack getLastestStack(String actInstId, String nodeId) {
		return dao.getLastestStack(actInstId, nodeId);
	}

	public ExecutionStack getLastestStack(String actInstId, String nodeId, String taskToken) {
		return dao.getLastestStack(actInstId, nodeId, taskToken);
	}

	public Integer delSubChilds(Long stackId, String nodePath) {
		return dao.delSubChilds(stackId, nodePath);
	}
}