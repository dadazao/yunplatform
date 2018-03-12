package com.cloudstong.platform.third.bpm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.activity.ActivityRequiredException;
import javax.annotation.Resource;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.engine.IScript;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmDao;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionExtDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionStackDao;
import com.cloudstong.platform.third.bpm.dao.HistoryActivityDao;
import com.cloudstong.platform.third.bpm.dao.HistoryProcessInstanceDao;
import com.cloudstong.platform.third.bpm.dao.TaskDao;
import com.cloudstong.platform.third.bpm.dao.TaskForkDao;
import com.cloudstong.platform.third.bpm.dao.TaskHistoryDao;
import com.cloudstong.platform.third.bpm.dao.TaskOpinionDao;
import com.cloudstong.platform.third.bpm.dao.TaskUserDao;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.model.NodeTranUser;
import com.cloudstong.platform.third.bpm.model.NodeUserMap;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.ProcessExecution;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.model.ProcessTask;
import com.cloudstong.platform.third.bpm.model.ProcessTaskHistory;
import com.cloudstong.platform.third.bpm.model.TaskFork;
import com.cloudstong.platform.third.bpm.model.TaskNodeStatus;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.model.TaskUser;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@Service
public class BpmService implements IScript {
	private Logger logger = LoggerFactory.getLogger(BpmService.class);

	@Resource
	private BpmDao bpmDao;

	@Resource
	private TaskDao taskDao;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private ExecutionDao executionDao;

	@Resource
	private TaskUserDao taskUserDao;

	@Resource
	private TaskOpinionDao taskOpinionDao;
	
	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private TaskHistoryDao taskHistoryDao;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Autowired
	private RepositoryService repositoryService;

	@Resource
	ProcessEngineConfiguration processEngineConfiguration;

	@Resource
	BpmDefinitionDao bpmDefinitionDao;

	@Resource
	BpmNodeSetService bpmNodeSetService;
	
	@Resource
	TaskForkService taskForkService;

	@Resource
	TaskForkDao taskForkDao;

	@Resource
	BpmNodeUserService bpmNodeUserService;

	@Resource
	SysUserDao sysUserDao;

	@Resource
	BpmRunLogService bpmRunLogService;

	@Resource
	private ExecutionStackDao executionStackDao;

	@Resource
	IdGenerator idGenerator;

	@Resource
	HistoryActivityDao historyActivityDao;

	@Resource
	HistoryProcessInstanceDao historyProcessInstanceDao;

	@Resource
	private ExecutionExtDao executionExtDao;
	private static Lock lockTransto = new ReentrantLock();

	private static Lock lockComplete = new ReentrantLock();

	public ProcessInstance startFlowById(String proessDefId, Map<String, Object> variables) {
		ProcessInstance instance = runtimeService.startProcessInstanceById(proessDefId, variables);

		return instance;
	}

	public ProcessInstance startFlowById(String porcessDefId, String businessKey, Map<String, Object> variables) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(porcessDefId, businessKey, variables);

		return processInstance;
	}

	public ProcessInstance startFlowByKey(String processDefKey, String businessKey, Map<String, Object> variables) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefKey, businessKey, variables);

		return processInstance;
	}

	public Deployment deploy(String name, String xml) throws UnsupportedEncodingException {
		InputStream stream = new ByteArrayInputStream(xml.getBytes("utf-8"));
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.name(name);
		deploymentBuilder.addInputStream("bpmn20.xml", stream);
		Deployment deploy = deploymentBuilder.deploy();

		return deploy;
	}

	public ProcessDefinitionEntity getProcessDefinitionEntity(String processDefinitionId) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		return processDefinition;
	}

	public void transTo(String taskId, String toNode) throws ActivityRequiredException {
		TaskEntity task = getTask(taskId);

		ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(task.getProcessDefinitionId());

		ActivityImpl curActi = processDefinition.findActivity(task.getTaskDefinitionKey());

		BpmNodeSet bpmNodeSet = null;

		ActivityImpl destAct = null;

		boolean isNeedRemoveTran = false;

		if ("_RULE_INVALID".equals(toNode)) {
			isNeedRemoveTran = true;
		} else {
			if (StringUtils.isEmpty(toNode)) {
				for (PvmTransition tran : curActi.getOutgoingTransitions()) {
					String destActId = tran.getDestination().getId();
					bpmNodeSet = bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destActId);
					if (bpmNodeSet != null) {
						destAct = (ActivityImpl) tran.getDestination();
						break;
					}
				}
			} else
				destAct = processDefinition.findActivity(toNode);

			if (curActi == destAct) {
				throw new ActivitiException("不能跳转到本节点!");
			}
			if (destAct == null) {
				taskService.complete(task.getId());

				return;
			}

			if (bpmNodeSet == null) {
				bpmNodeSet = bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destAct.getId());
			}
			if (bpmNodeSet != null) {
				String token = (String) taskService.getVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME);
				if (token != null) {
					TaskFork taskFork = taskForkService.getByInstIdJoinTaskKeyForkToken(task.getProcessInstanceId(), destAct.getId(), token);
					if (taskFork != null) {
						if (taskFork.getFininshCount().intValue() < taskFork.getForkCount().intValue() - 1) {
							taskFork.setFininshCount(Integer.valueOf(taskFork.getFininshCount().intValue() + 1));
							taskForkService.update(taskFork);

							String[] tokenSplits = token.split("[_]");
							if (tokenSplits.length == 2) {
								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);
							}
							isNeedRemoveTran = true;
						} else {
							String executionId = task.getExecutionId();
							taskForkDao.delById(taskFork.getTaskForkId());

							String[] tokenSplits = token.split("[_]");
							if (tokenSplits.length == 2) {
								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);

								String instanceId = task.getProcessInstanceId();
								ExecutionEntity ent = (ExecutionEntity) executionExtDao.getById(executionId);
								ActivityImpl curAct = processDefinition.findActivity(ent.getActivityId());
								ExecutionEntity processInstance = (ExecutionEntity) executionExtDao.getById(instanceId);
								processInstance.setActivity(curAct);
								executionExtDao.update(processInstance);

								taskDao.updTaskExecution(taskId);

								executionDao.delTokenVarByTaskId(taskId, TaskFork.TAKEN_VAR_NAME);

								executionDao.delVarsByExecutionId(executionId);

								executionDao.delExecutionById(executionId);
							} else if (tokenSplits.length >= 3) {
								String newToken = token.substring(0, token.lastIndexOf("_" + tokenSplits[(tokenSplits.length - 1)]));
								taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, newToken);
							}
						}
					}
				}

			}

		}

		List backTransList = new ArrayList();
		backTransList.addAll(curActi.getOutgoingTransitions());
		try {
			lockTransto.lock();

			curActi.getOutgoingTransitions().clear();
			if (!isNeedRemoveTran) {
				TransitionImpl transitionImpl = curActi.createOutgoingTransition();
				transitionImpl.setDestination(destAct);
			}

			taskService.complete(task.getId());
		} finally {
			curActi.getOutgoingTransitions().clear();

			curActi.getOutgoingTransitions().addAll(backTransList);

			lockTransto.unlock();
		}
	}

	public void onlyCompleteTask(String taskId) {
		TaskEntity task = getTask(taskId);

		ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(task.getProcessDefinitionId());

		ActivityImpl curActi = processDefinition.findActivity(task.getTaskDefinitionKey());

		lockComplete.lock();

		List backTransList = new ArrayList();
		backTransList.addAll(curActi.getOutgoingTransitions());
		try {
			curActi.getOutgoingTransitions().clear();

			taskService.complete(task.getId());
		} finally {
			curActi.getOutgoingTransitions().addAll(backTransList);

			lockComplete.unlock();
		}
	}

	public ProcessDefinitionEntity getProcessDefinitionByDeployId(String deployId) {
		ProcessDefinition proDefinition = (ProcessDefinition) repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
		if (proDefinition == null)
			return null;
		return getProcessDefinitionByDefId(proDefinition.getId());
	}

	public ProcessDefinitionEntity getProcessDefinitionByDefId(String actDefId) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(actDefId);
		return ent;
	}

	public ProcessDefinitionEntity getProcessDefinitionByTaskId(String taskId) {
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		return getProcessDefinitionByDefId(taskEntity.getProcessDefinitionId());
	}

	public List<ProcessDefinition> getProcessDefinitionByKey(String key) {
		List list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();
		return list;
	}

	public ProcessDefinitionEntity getProcessDefinitionByProcessInanceId(String processInstanceId) {
		String processDefinitionId = null;
		ProcessInstance processInstance = (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		if (processInstance == null) {
			HistoricProcessInstance hisProInstance = (HistoricProcessInstance) historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			processDefinitionId = hisProInstance.getProcessDefinitionId();
		} else {
			processDefinitionId = processInstance.getProcessDefinitionId();
		}
		return getProcessDefinitionByDefId(processDefinitionId);
	}

	public List<String> getActiveTasks(String taskId) {
		List acts = new ArrayList();
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();

		List<ProcessTask> tasks = taskDao.getByInstanceId(taskEntity.getProcessInstanceId());

		for (ProcessTask task : tasks) {
			acts.add(task.getName());
		}

		return acts;
	}

	public Map<String, String> getOutNodesByTaskId(String taskId) {
		Map map = new HashMap();
		Task task = getTask(taskId);
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task
				.getProcessDefinitionId());

		ActivityImpl curActi = ent.findActivity(task.getTaskDefinitionKey());

		List<PvmTransition> outs = curActi.getOutgoingTransitions();
		for (PvmTransition tran : outs) {
			ActivityImpl destNode = (ActivityImpl) tran.getDestination();
			map.put(destNode.getId(), (String) destNode.getProperty("name"));
		}
		return map;
	}

	public List<String> getActiveActIdsByTaskId(String taskId) {
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		return getActiveActIdsByProcessInstanceId(taskEntity.getProcessInstanceId());
	}

	public List<String> getActiveActIdsByProcessInstanceId(String processInstanceId) {
		List acts = new ArrayList();
		List<ProcessTask> taskList = getTasks(processInstanceId);

		for (ProcessTask entity : taskList) {
			acts.add(entity.getTaskDefinitionKey());
		}

		return acts;
	}

	public String getDefXmlByDeployId(String deployId) {
		return bpmDao.getDefXmlByDeployId(deployId);
	}

	public String getDefXmlByProcessDefinitionId(String processDefinitionId) {
		ProcessDefinitionEntity entity = getProcessDefinitionByDefId(processDefinitionId);
		if (entity == null) {
			return null;
		}
		String defXml = getDefXmlByDeployId(entity.getDeploymentId());
		return defXml;
	}

	public String getDefXmlByProcessTaskId(String taskId) {
		ProcessDefinitionEntity entity = getProcessDefinitionByTaskId(taskId);
		if (entity == null) {
			return null;
		}
		String defXml = getDefXmlByDeployId(entity.getDeploymentId());
		return defXml;
	}

	public String getDefXmlByProcessProcessInanceId(String processInstanceId) {
		ProcessDefinitionEntity entity = getProcessDefinitionByProcessInanceId(processInstanceId);
		if (entity == null) {
			return null;
		}
		String defXml = getDefXmlByDeployId(entity.getDeploymentId());
		return defXml;
	}

	public void wirteDefXml(String deployId, String defXml) {
		bpmDao.wirteDefXml(deployId, defXml);
		ProcessDefinitionEntity ent = getProcessDefinitionByDeployId(deployId);

		((ProcessEngineConfigurationImpl) processEngineConfiguration).getProcessDefinitionCache().remove(ent.getId());
	}

	public List<ActivityImpl> getActivityNodes(String actDefId) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(actDefId);
		return ent.getActivities();
	}

	public boolean isSignTask(String actDefId, String nodeId) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(actDefId);
		List<ActivityImpl> list = ent.getActivities();
		for (ActivityImpl actImpl : list) {
			if (actImpl.getId().equals(nodeId)) {
				String multiInstance = (String) actImpl.getProperty("multiInstance");
				if (multiInstance != null)
					return true;
			}
		}
		return false;
	}

	public Map<String, Integer> getTaskType(String actDefId) {
		Map map = new HashMap();
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(actDefId);
		List<ActivityImpl> list = ent.getActivities();
		for (ActivityImpl actImpl : list) {
			String type = (String) actImpl.getProperty("type");
			if ("userTask".equals(type)) {
				String multiInstance = (String) actImpl.getProperty("multiInstance");
				if (multiInstance != null) {
					map.put(actImpl.getId(), Integer.valueOf(1));
				} else
					map.put(actImpl.getId(), Integer.valueOf(0));
			}
		}
		return map;
	}

	public TaskEntity getTask(String taskId) {
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		return taskEntity;
	}

	public Map<String, Map<String, String>> getJumpNodes(String taskId) {
		List actIds = getActiveActIdsByTaskId(taskId);
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		String defXml = getDefXmlByProcessDefinitionId(taskEntity.getProcessDefinitionId());
		Map map = BpmUtil.getTranstoActivitys(defXml, actIds, Boolean.valueOf(false));
		return map;
	}

	public Map<String, String> getTaskNodes(String actDefId, String nodeId) {
		Map nodeMaps = getExecuteNodesMap(actDefId, true);

		if (nodeMaps.containsKey(nodeId)) {
			nodeMaps.remove(nodeId);
		}
		return nodeMaps;
	}

	protected Map<String, String> getExecuteNodes(ActivityImpl actImpl) {
		Map nodeMap = new HashMap();
		List<ActivityImpl> acts = actImpl.getActivities();
		if (acts.size() == 0)
			return nodeMap;
		for (ActivityImpl act : acts) {
			String nodeType = (String) act.getProperties().get("type");

			if (nodeType.indexOf("Task") != -1) {
				String name = (String) act.getProperties().get("name");
				nodeMap.put(act.getId(), name);
			} else if ("subProcess".equals(nodeType)) {
				nodeMap.putAll(getExecuteNodes(act));
			}
		}
		return nodeMap;
	}

	public List<String> getExecuteNodes(String actDefId) {
		List values = new ArrayList();
		Map nodeMap = getExecuteNodesMap(actDefId, true);

		values.addAll(nodeMap.values());

		Iterator valuesIt = nodeMap.values().iterator();
		while (valuesIt.hasNext()) {
			String value = (String) valuesIt.next();
			if (StringUtils.isNotEmpty(value)) {
				values.add(value);
			}
		}

		return values;
	}

	public Map<String, String> getExecuteNodesMap(String actDefId, boolean includeSubProcess) {
		Map nodeMap = new HashMap();

		List<ActivityImpl> acts = getActivityNodes(actDefId);
		for (ActivityImpl actImpl : acts) {
			String nodeType = (String) actImpl.getProperties().get("type");

			if (nodeType.indexOf("Task") != -1) {
				String name = (String) actImpl.getProperties().get("name");
				nodeMap.put(actImpl.getId(), name);
			} else if ((includeSubProcess) && ("subProcess".equals(nodeType))) {
				nodeMap.putAll(getExecuteNodes(actImpl));
			}
		}
		return nodeMap;
	}

	public List<TaskEntity> getTasks(QueryCriteria queryCriteria) {
		return taskDao.getAll(queryCriteria);
	}
	
	public PageResult queryTasks(QueryCriteria queryCriteria) {
		return taskDao.query(queryCriteria);
	}

	public List<TaskEntity> getMyTasks(QueryCriteria queryCriteria) {
		return taskDao.getMyTasks(AppContext.getCurrentUserId(), queryCriteria);
	}

	public List<TaskEntity> getMyMobileTasks(QueryCriteria filter) {
		return taskDao.getMyMobileTasks(filter);
	}

	public boolean isEndProcess(String processInstanceId) {
		HistoricProcessInstance his = (HistoricProcessInstance) historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if ((his != null) && (his.getEndTime() != null)) {
			return true;
		}
		return false;
	}

	public boolean isSignTask(TaskEntity taskEntity) {
		RepositoryServiceImpl impl = (RepositoryServiceImpl) repositoryService;

		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) impl.getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());

		ActivityImpl taskAct = ent.findActivity(taskEntity.getTaskDefinitionKey());

		String multiInstance = (String) taskAct.getProperty("multiInstance");

		if (multiInstance != null)
			return true;

		return false;
	}

	public List<HistoricTaskInstance> getHistoryTasks(String processInstanceId) {
		List list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
		return list;
	}

	public HistoricTaskInstanceEntity getHistoricTaskInstanceEntity(String taskId) {
		return (HistoricTaskInstanceEntity) historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
	}

	public void assignTask(String taskId, String userId) {
		taskService.setOwner(taskId, userId);
		taskService.setAssignee(taskId, userId);
	}

	public void setDueDate(String taskId, Date dueDate) {
		taskDao.setDueDate(taskId, dueDate);
	}

	public ExecutionEntity getExecution(String executionId) {
		Execution execution = (Execution) runtimeService.createExecutionQuery().executionId(executionId).singleResult();
		return (ExecutionEntity) execution;
	}

	public ExecutionEntity getExecutionByTaskId(String taskId) {
		TaskEntity taskEntity = getTask(taskId);
		return getExecution(taskEntity.getExecutionId());
	}

	public Map<String, Object> getVarsByTaskId(String taskId) {
		return taskService.getVariables(taskId);
	}

	public void setExecutionVariable(String executionId, String variableName, Object varVal) {
		runtimeService.setVariable(executionId, variableName, varVal);
	}

	public void setTaskVariable(String taskId, String variableName, Object varVal) {
		taskService.setVariable(taskId, variableName, varVal);
	}

	public ProcessTask newTask(String orgTaskId, String assignee) {
		return newTask(orgTaskId, assignee, null, null);
	}

	public ProcessTask newTask(String orgTaskId, String assignee, String newNodeId, String newNodeName) {
		String newExecutionId = idGenerator.getNextId();
		String newTaskId = idGenerator.getNextId();

		TaskEntity taskEntity = getTask(orgTaskId);
		ExecutionEntity executionEntity = null;
		executionEntity = getExecution(taskEntity.getExecutionId());

		ProcessExecution newExecution = new ProcessExecution(executionEntity);
		newExecution.setId(newExecutionId);

		ProcessTask newTask = new ProcessTask();
		BeanUtils.copyProperties(newTask, taskEntity);
		newTask.setId(newTaskId);
		newTask.setExecutionId(newExecutionId);
		newTask.setCreateTime(new Date());

		newTask.setAssignee(assignee);
		newTask.setOwner(assignee);

		ProcessTaskHistory newTaskHistory = new ProcessTaskHistory(taskEntity);
		newTaskHistory.setAssignee(assignee);
		newTaskHistory.setStartTime(new Date());
		newTaskHistory.setId(newTaskId);
		newTaskHistory.setOwner(assignee);

		if (newNodeId != null) {
			newExecution.setActivityId(newNodeId);
			newTask.setTaskDefinitionKey(newNodeId);
			newTask.setName(newNodeName);
			newTaskHistory.setTaskDefinitionKey(newNodeId);
			newTaskHistory.setName(newNodeName);
		}

		executionDao.save(newExecution);
		taskDao.insertTask(newTask);
		taskHistoryDao.save(newTaskHistory);

		return newTask;
	}

	public void newForkTasks(TaskEntity taskEntity, Set<TaskExecutor> uIds) {
		String token = (String) taskEntity.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
		if (token == null)
			token = TaskFork.TAKEN_PRE;
		Iterator uIt = uIds.iterator();
		int i = 0;
		while (uIt.hasNext())
			if (i++ == 0) {
				assignTask(taskEntity, (TaskExecutor) uIt.next());

				taskEntity.setVariableLocal(TaskFork.TAKEN_VAR_NAME, token + "_" + i);

				changeTaskExecution(taskEntity);
			} else {
				ProcessTask processTask = newTask(taskEntity, (TaskExecutor) uIt.next());

				TaskEntity newTask = getTask(processTask.getId());

				TaskThreadService.addTask(newTask);

				taskService.setVariableLocal(processTask.getId(), TaskFork.TAKEN_VAR_NAME, token + "_" + i);

				TaskOpinion taskOpinion = new TaskOpinion(processTask);
				taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
				taskOpinion.setTaskToken(token);
				taskOpinionDao.save(taskOpinion);
			}
	}

	public void newNotifyTasks(TaskEntity taskEntity, List<String> uIds) {
		for (String userId : uIds) {
			String taskId = idGenerator.getNextId();

			TaskEntity task = (TaskEntity) taskService.newTask(taskId);
			task.setAssignee(userId);
			task.setOwner(userId);
			task.setName(taskEntity.getName());

			task.setCreateTime(new Date());
			task.setDescription("通知任务");
			task.setParentTaskId(taskEntity.getId());
			task.setTaskDefinitionKey(taskEntity.getTaskDefinitionKey());
			taskService.saveTask(task);
		}
	}

	public void newForkTasks(TaskEntity taskEntity, List<TaskExecutor> uIdList) {
		Set uIdSet = new HashSet();
		uIdSet.addAll(uIdList);
		newForkTasks(taskEntity, uIdSet);
	}

	public void assignTask(TaskEntity taskEntity, TaskExecutor taskExecutor) {
		if ("user".equals(taskExecutor.getType())) {
			taskEntity.setAssignee(taskExecutor.getExecuteId());
			taskEntity.setOwner(taskExecutor.getExecuteId());
		} else {
			taskEntity.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
		}
	}

	protected void changeTaskExecution(TaskEntity taskEntity) {
		String newExecutionId = idGenerator.getNextId();
		ProcessExecution newExecution = new ProcessExecution(taskEntity.getExecution());

		newExecution.setId(newExecutionId);

		executionDao.save(newExecution);

		taskEntity.setExecutionId(newExecutionId);
	}

	protected ProcessTask newTask(TaskEntity taskEntity, TaskExecutor taskExecutor) {
		String newExecutionId = idGenerator.getNextId();
		String newTaskId = idGenerator.getNextId();

		ProcessExecution newExecution = new ProcessExecution(taskEntity.getExecution());
		newExecution.setId(newExecutionId);

		ProcessTask newTask = new ProcessTask();
		BeanUtils.copyProperties(newTask, taskEntity);
		newTask.setId(newTaskId);
		newTask.setExecutionId(newExecutionId);
		newTask.setCreateTime(new Date());
		ProcessTaskHistory newTaskHistory = new ProcessTaskHistory(taskEntity);

		TaskUser taskUser = null;

		String executorId = taskExecutor.getExecuteId();

		if ("user".equals(taskExecutor.getType())) {
			newTask.setAssignee(executorId);
			newTask.setOwner(executorId);
			newTaskHistory.setAssignee(executorId);
			newTaskHistory.setOwner(executorId);
		} else {
			taskUser = new TaskUser();
			taskUser.setId(idGenerator.getNextId());
			taskUser.setGroupId(executorId);
			taskUser.setType(taskExecutor.getType());
			taskUser.setReversion(Integer.valueOf(1));
			taskUser.setTaskId(newTaskId);
		}

		newTaskHistory.setStartTime(new Date());
		newTaskHistory.setId(newTaskId);

		executionDao.save(newExecution);
		taskDao.insertTask(newTask);
		taskHistoryDao.save(newTaskHistory);
		if (taskUser != null) {
			taskUserDao.save(taskUser);
		}

		return newTask;
	}

	public List<ProcessTask> getTasks(String processInstanceId) {
		List taskList = taskDao.getByInstanceId(processInstanceId);
		return taskList;
	}

	public List<TaskUser> getCandidateUsers(String taskId) {
		return taskDao.getCandidateUsers(taskId);
	}

	public void saveCondition(long defId, String forkNode, Map<String, String> map, String canChoicePathNodeId) throws IOException {
		BpmDefinition bpmDefinition = (BpmDefinition) bpmDefinitionDao.getById(Long.valueOf(defId));
		if (StringUtil.isNotEmpty(canChoicePathNodeId)) {
			bpmDefinition.getCanChoicePathNodeMap().put(forkNode, canChoicePathNodeId);
			bpmDefinition.updateCanChoicePath();
		} else {
			Object o = bpmDefinition.getCanChoicePathNodeMap().get(forkNode);
			if (o != null)
				bpmDefinition.getCanChoicePathNodeMap().remove(forkNode);
			bpmDefinition.updateCanChoicePath();
		}
		String deployId = bpmDefinition.getActDeployId().toString();
		String actDefId = bpmDefinition.getActDefId();
		String defXml = bpmDao.getDefXmlByDeployId(deployId);
		String graphXml = bpmDefinition.getDefXml();
		defXml = BpmUtil.setCondition(forkNode, map, defXml);
		graphXml = BpmUtil.setGraphXml(forkNode, map, graphXml);
		bpmDefinition.setDefXml(graphXml);
		bpmDao.wirteDefXml(deployId, defXml);

		bpmDefinitionDao.update(bpmDefinition);

		((ProcessEngineConfigurationImpl) processEngineConfiguration).getProcessDefinitionCache().remove(actDefId);

		((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(actDefId);
	}

	public void delLoopAssigneeVars(String executionId) {
		executionDao.delLoopAssigneeVars(executionId);
	}

	public List<TaskEntity> getAgentTasks(Long userId, String actDefId, QueryCriteria queryFilter) {
		return taskDao.getAgentTasks(userId, actDefId, queryFilter);
	}

	public List<Long> getAgentIdByTaskId(String taskId, String userId) {
		return taskDao.getAgentIdByTaskId(taskId, userId);
	}

	public List<TaskEntity> getTaskByUserId(Long agentuserid, QueryCriteria filter) {
		return taskDao.getMyTasks(agentuserid, filter);
	}

	public List<TaskEntity> getAllAgentTask(Long userId, QueryCriteria filter) {
		return taskDao.getAllAgentTask(userId, filter);
	}

	public String getMyEvents(Map map) {
		List list = taskDao.getMyEvents(map);

		String mode = (String) map.get("mode");
		StringBuffer sb = new StringBuffer();
		sb.append("[");

		for (int idx = 0; idx < list.size(); idx++) {
			Object obj = list.get(idx);
			ProcessTask task = (ProcessTask) obj;

			sb.append("{\"id\":\"").append(task.getId()).append("\",");

			Date startTime = task.getCreateTime();
			if (startTime == null) {
				Calendar curCal = Calendar.getInstance();
				startTime = curCal.getTime();
			}

			Date endTime = task.getDueDate();
			if ((endTime == null) && ("month".equals(mode))) {
				endTime = startTime;
			}

			String sTime = DateUtil.formatEnDate(startTime);
			String eTime = endTime == null ? "" : DateUtil.formatEnDate(endTime);

			String eTime0 = "";
			if (("month".equals(mode)) && (sTime.substring(0, 10).equals(eTime.substring(0, 10)))) {
				String[] dateArr = sTime.substring(0, 10).split("/");
				eTime0 = DateUtil.addOneDay(new StringBuilder(String.valueOf(dateArr[2])).append("-").append(dateArr[0]).append("-")
						.append(dateArr[1]).toString())
						+ " 00:00:00 AM";
			}

			if (!"month".equals(mode)) {
				String[] dateArr = sTime.substring(0, 10).split("/");
				eTime0 = DateUtil.addOneHour(dateArr[2] + "-" + dateArr[0] + "-" + dateArr[1] + sTime.substring(10, sTime.length()));
			}

			sb.append("\"type\":\"").append("2").append("\",");
			sb.append("\"startTime\":\"");

			if ("month".equals(mode))
				sb.append(sTime.substring(0, 10) + " 00:00:00 AM").append("\",");
			else {
				sb.append(sTime).append("\",");
			}

			if (!eTime0.equals(""))
				sb.append("\"endTime\":\"").append(eTime0).append("\",");
			else {
				sb.append("\"endTime\":\"").append(eTime).append("\",");
			}

			sb.append("\"title\":\"").append(task.getSubject()).append("\",");
			sb.append("\"description\":\"").append(task.getProcessName()).append("\",");
			sb.append("\"status\":\"").append("0").append("\"");
			sb.append("},");
		}

		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");

		return sb.toString();
	}

	public boolean getCanChoicePath(String actDefId, String taskId) {
		if (StringUtil.isEmpty(taskId)) {
			return false;
		}
		TaskEntity taskEntity = getTask(taskId);
		String taskKey = taskEntity.getTaskDefinitionKey();
		if (StringUtil.isEmpty(actDefId))
			actDefId = taskEntity.getProcessDefinitionId();
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);
		String canChoicePath = bpmDefinition.getCanChoicePath();
		if (StringUtil.isNotEmpty(canChoicePath)) {
			return canChoicePath.contains(taskKey);
		}
		return false;
	}

	public List<NodeTranUser> getNodeTaskUserMap(String taskId, Long preUserId, boolean canChoicePath) {
		TaskEntity taskEntity = getTask(taskId);

		String instanceId = taskEntity.getProcessInstanceId();

		String actDefId = taskEntity.getProcessDefinitionId();

		FlowNode flowNode = (FlowNode) NodeCache.getByActDefId(actDefId).get(taskEntity.getTaskDefinitionKey());

		ProcessRun processRun = processRunService.getByActInstanceId(instanceId);

		Long curUserId = null;

		if (processRun != null) {
			curUserId = processRun.getCreatorId();
		}

		Map vars = new HashMap();
		vars.put("executionId", taskEntity.getExecutionId());
		return getNodeUserMap(actDefId, instanceId, flowNode.getNextFlowNodes(), curUserId, preUserId, canChoicePath, vars);
	}

	public List<NodeTranUser> getStartNodeUserMap(String actDefId, Long startUserId, Map<String, Object> vars) throws Exception {
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);

		boolean jumpOverFirstNode = bpmDefinition.getToFirstNode().shortValue() == 1;

		FlowNode startNode = NodeCache.getStartNode(actDefId);

		List nextNodes = startNode.getNextFlowNodes();

		if (nextNodes.size() != 1)
			throw new Exception("开始节点后没有连接的节点!");

		if (jumpOverFirstNode) {
			FlowNode flowNode = (FlowNode) nextNodes.get(0);
			if ((!"userTask".equals(flowNode.getNodeType())) && (nextNodes.size() != 1))
				throw new Exception("第一个节点必须为任务节点!");

			nextNodes = flowNode.getNextFlowNodes();
		}

		return getNodeUserMap(actDefId, null, nextNodes, startUserId, startUserId, false, vars);
	}

	public List<NodeTranUser> getNodeUserMap(String processDefinitionId, String instanceId, List<FlowNode> nextFlowNodes, Long curUserId,
			Long preUserId, boolean canChoicePath, Map<String, Object> vars) {
		List nodeList = new ArrayList();
		for (FlowNode flowNode : nextFlowNodes) {
			Set nodeUserMapSet = new LinkedHashSet();
			NodeTranUser nodeTranUser = new NodeTranUser(flowNode.getNodeId(), flowNode.getNodeName(), nodeUserMapSet);

			if (flowNode.getNodeType().equals("userTask")) {
				Set taskExecutors = getNodeHandlerUsers(processDefinitionId, instanceId, flowNode.getNodeId(), curUserId, preUserId, vars);
				nodeUserMapSet.add(new NodeUserMap(flowNode.getNodeId(), flowNode.getNodeName(), taskExecutors, flowNode.getIsMultiInstance()
						.booleanValue()));
			} else if (flowNode.getNodeType().indexOf("Gateway") != -1) {
				if ((canChoicePath) && ("inclusiveGateway".equals(flowNode.getNodeType()))) {
					Map nextPathMap = new HashMap();
					List<FlowNode> gatewayPathList = flowNode.getNextFlowNodes();
					for (FlowNode gatewayFlowNode : gatewayPathList) {
						nextPathMap.put(gatewayFlowNode.getNodeId(), gatewayFlowNode.getNodeName());

						String nodeType = gatewayFlowNode.getNodeType();
						if (nodeType.equals("callActivity")) {
							Map subGatewayChildNodes = gatewayFlowNode.getSubProcessNodes();
							String subFlowKey = gatewayFlowNode.getAttribute("subFlowKey");
							FlowNode startNode = NodeCache.getStartNode(subGatewayChildNodes);

							BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefKeyIsMain(subFlowKey);
							String subProcessDefinitionId = bpmDefinition.getActDefId();
							genUserMap(subProcessDefinitionId, instanceId, startNode.getNextFlowNodes(), nodeUserMapSet, curUserId, preUserId, vars);
						}
					}
					nodeTranUser.setNextPathMap(nextPathMap);
				}
				genUserMap(processDefinitionId, instanceId, flowNode.getNextFlowNodes(), nodeUserMapSet, curUserId, preUserId, vars);
			} else if ((flowNode.getNodeType().equals("subProcess")) && (flowNode.getSubFirstNode() != null)) {
				genUserMap(processDefinitionId, instanceId, flowNode.getSubFirstNode().getNextFlowNodes(), nodeUserMapSet, curUserId, preUserId, vars);
			} else if (flowNode.getNodeType().equals("callActivity")) {
				Map subChildNodes = flowNode.getSubProcessNodes();
				String subFlowKey = flowNode.getAttribute("subFlowKey");
				FlowNode startNode = NodeCache.getStartNode(subChildNodes);

				BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefKeyIsMain(subFlowKey);
				String subProcessDefinitionId = bpmDefinition.getActDefId();
				genUserMap(subProcessDefinitionId, instanceId, startNode.getNextFlowNodes(), nodeUserMapSet, curUserId, preUserId, vars);
			}
			nodeList.add(nodeTranUser);
		}
		return nodeList;
	}

	private void genUserMap(String processDefinitionId, String instanceId, List<FlowNode> nextFlowNodes, Set<NodeUserMap> nodeUserMapSet,
			Long curUserId, Long preUserId, Map<String, Object> vars) {
		for (FlowNode flowNode : nextFlowNodes)
			if (flowNode.getNodeType().indexOf("Gateway") != -1) {
				genUserMap(processDefinitionId, instanceId, flowNode.getNextFlowNodes(), nodeUserMapSet, curUserId, preUserId, vars);
			} else if ("userTask".equals(flowNode.getNodeType())) {
				Set taskExecutors = getNodeHandlerUsers(processDefinitionId, instanceId, flowNode.getNodeId(), curUserId, preUserId, vars);
				NodeUserMap nodeUserMap = new NodeUserMap(flowNode.getNodeId(), flowNode.getNodeName(), taskExecutors, flowNode.getIsMultiInstance()
						.booleanValue());
				nodeUserMapSet.add(nodeUserMap);
			}
	}

	public Set<TaskExecutor> getNodeHandlerUsers(String actInstanceId, String nodeId, Map<String, Object> vars) {
		Set uSet = new HashSet();
		List<TaskExecutor> taskExecutorList = (List) bpmNodeUserService.getExeUserIdsByInstance(actInstanceId, nodeId, "", vars).get("PARTICIPATION");
		if (BeanUtils.isEmpty(taskExecutorList)) {
			return uSet;
		}
		for (TaskExecutor taskExecutor : taskExecutorList) {
			uSet.add(taskExecutor);
		}
		return uSet;
	}

	public Set<TaskExecutor> getNodeHandlerUsers(String actDefId, String instanceId, String nodeId, Long startUserId, Long preUserId,
			Map<String, Object> vars) {
		Set uSet = new HashSet();
		List<TaskExecutor> taskExecutorList = (List) bpmNodeUserService.getExeUserIds(actDefId, instanceId, nodeId, startUserId.toString(),
				preUserId.toString(), vars).get("PARTICIPATION");
		if (taskExecutorList == null) {
			return uSet;
		}
		for (TaskExecutor taskExecutor : taskExecutorList) {
			uSet.add(taskExecutor);
		}
		return uSet;
	}

	public void deleteTask(String taskId) {
		TaskEntity taskEntity = getTask(taskId);
		taskService.deleteTask(taskId);
		executionDao.delById(taskEntity.getExecutionId());
	}

	public void deleteTasks(String[] taskIds) {
		for (String taskId : taskIds)
			deleteTask(taskId);
	}

	public void updateTaskAssignee(String taskId, String userId) {
		taskDao.updateTaskAssignee(taskId, userId);
	}

	public void updateTaskAssigneeNull(String taskId) {
		taskDao.updateTaskAssigneeNull(taskId);
	}

	public void updateTaskOwner(String taskId, String userId) {
		taskDao.updateTaskOwner(taskId, userId);
	}

	public ProcessInstance getProcessInstance(String actInstId) {
		ProcessInstance processInstance = (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(actInstId).singleResult();
		return processInstance;
	}

	public List<TaskNodeStatus> getNodeCheckStatusInfo(String actInstId) {
		List taskNodeStatusList = new ArrayList();
		ProcessRun processRun = processRunService.getByActInstanceId(actInstId);
		String actDefId = processRun.getActDefId();
		Map nodeMaps = getExecuteNodesMap(actDefId, true);
		Set taskNodeSet = nodeMaps.keySet();
		Iterator taskNodeIt = taskNodeSet.iterator();

		while (taskNodeIt.hasNext()) {
			String nodeId = (String) taskNodeIt.next();
			List taskOpinions = taskOpinionService.getByActInstIdTaskKey(actInstId, nodeId);

			TaskNodeStatus taskNodeStatus = new TaskNodeStatus();
			taskNodeStatus.setActInstId(actInstId);
			taskNodeStatus.setTaskKey(nodeId);
			if ((taskOpinions != null) && (taskOpinions.size() > 0)) {
				TaskOpinion taskOpinion = (TaskOpinion) taskOpinions.get(0);
				if (taskOpinion.getCheckStatus() == null)
					taskNodeStatus.setLastCheckStatus(TaskOpinion.STATUS_INIT);
				else {
					taskNodeStatus.setLastCheckStatus(taskOpinion.getCheckStatus());
				}
			}
			taskNodeStatus.setTaskOpinionList(taskOpinions);
			taskNodeStatusList.add(taskNodeStatus);
		}

		return taskNodeStatusList;
	}

	public void genNotifyTask(DelegateTask ent, List<TaskExecutor> taskExecutors) {
		if (BeanUtils.isEmpty(taskExecutors))
			return;
		for (TaskExecutor taskExecutor : taskExecutors) {
			String taskId = idGenerator.getNextId();
			TaskEntity task = (TaskEntity) taskService.newTask(taskId);
			if ("user".equals(taskExecutor.getType())) {
				task.setAssignee(taskExecutor.getExecuteId());
				task.setOwner(taskExecutor.getExecuteId());
			}
			task.setCreateTime(new Date());
			task.setName(ent.getName());
			task.setTaskDefinitionKey(ent.getTaskDefinitionKey());
			task.setProcessInstanceId(ent.getProcessInstanceId());
			task.setDescription("通知任务");
			task.setProcessDefinitionId(ent.getProcessDefinitionId());
			taskService.saveTask(task);

			if (!"user".equals(taskExecutor.getType())) {
				TaskUser taskUser = new TaskUser();
				taskUser.setId(idGenerator.getNextId());
				taskUser.setGroupId(taskExecutor.getExecuteId());
				taskUser.setType(taskExecutor.getType());
				taskUser.setReversion(Integer.valueOf(1));
				taskUser.setTaskId(taskId);
				taskUserDao.save(taskUser);
			}
		}
	}

	public ProcessRun endProcessByInstanceId(Long instanceId) {
		ProcessRun processRun = processRunService.getByActInstanceId(instanceId.toString());
		processRun.setStatus(ProcessRun.STATUS_MANUAL_FINISH);

		processRunService.update(processRun);

		taskDao.delCandidateByInstanceId(instanceId);

		taskDao.delByInstanceId(instanceId);

		List activitiList = ((HistoricActivityInstanceQuery) historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(instanceId.toString()).orderByHistoricActivityInstanceStartTime().asc()).list();

		String lastActivitiId = ((HistoricActivityInstance) activitiList.get(activitiList.size() - 1)).getActivityId();

		updHistoryActInst(activitiList);

		HistoricProcessInstanceEntity processInstance = (HistoricProcessInstanceEntity) historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(instanceId.toString()).singleResult();
		processInstance.setEndTime(new Date());
		long duration = System.currentTimeMillis() - processInstance.getStartTime().getTime();
		processInstance.setDurationInMillis(Long.valueOf(duration));
		processInstance.setEndActivityId(lastActivitiId);
		historyProcessInstanceDao.update(processInstance);

		//executionDao.delVariableByProcInstId(instanceId);

		//executionDao.delExecutionByProcInstId(instanceId);

		return processRun;
	}

	private void updHistoryActInst(List<HistoricActivityInstance> list) {
		for (HistoricActivityInstance instance : list) {
			HistoricActivityInstanceEntity ent = (HistoricActivityInstanceEntity) instance;
			ent.setEndTime(new Date());
			long duration = System.currentTimeMillis() - ent.getStartTime().getTime();
			ent.setDurationInMillis(Long.valueOf(duration));
			historyActivityDao.update(ent);
		}
	}

	public void genNotifyTaskByUsers(DelegateTask task, String userIds) {
		if (StringUtil.isEmpty(userIds))
			return;
		String[] aryUserId = userIds.split(",");
		List userList = new ArrayList();
		for (String userId : aryUserId) {
			TaskExecutor executor = TaskExecutor.getTaskUser(userId, "");
			userList.add(executor);
		}

		genNotifyTask(task, userList);
	}

	public List<Map> getHasCandidateExecutor(String taskIds) {
		return taskDao.getHasCandidateExecutor(taskIds);
	}

	public void setInnerVariable(DelegateTask task, String varName, Object obj) {
		Map map = (Map) task.getVariable("innerPassVars");
		map.put(varName, obj);
		task.setVariable("innerPassVars", map);
	}

	public Object getOutVariable(DelegateTask task, String varName) {
		Map vars = (Map) task.getVariable("outPassVars");
		return vars.get(varName);
	}

	public Object getOutVariable(DelegateExecution excution, String varName) {
		Map vars = (Map) excution.getVariable("outPassVars");
		return vars.get(varName);
	}

	public void setObject(Object obj) {
		TaskThreadService.setObject(obj);
	}

	public Object getObject() {
		return TaskThreadService.getObject();
	}

	public ProcessCmd getProcessCmd() {
		return TaskThreadService.getProcessCmd();
	}

	public void setFormUsers(String users) {
		if (StringUtil.isEmpty(users))
			return;
		String[] aryUsers = users.split(",");
		List list = new ArrayList();
		for (String user : aryUsers) {
			SysUser sysUser = sysUserDao.getById(new Long(user));
			TaskExecutor excutor = TaskExecutor.getTaskUser(user, sysUser.getFullname());
			list.add(excutor);
		}
		TaskUserAssignService.setFormUsers(list);
	}
}