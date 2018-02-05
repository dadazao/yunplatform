package com.cloudstong.platform.third.bpm.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.engine.IScript;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserService;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@Service
public class TaskUserAssignService implements IScript {
	private Logger logger = LoggerFactory.getLogger(TaskUserAssignService.class);

	private static ThreadLocal<Map<String, List<TaskExecutor>>> nodeUserMapLocal = new ThreadLocal();

	private static ThreadLocal<List<TaskExecutor>> formUsers = new ThreadLocal();

	private static ThreadLocal<List<TaskExecutor>> taskExecutors = new ThreadLocal();

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	public void setNodeUser(Map<String, List<TaskExecutor>> map) {
		nodeUserMapLocal.set(map);
	}

	public void addNodeUser(String nodeId, List<TaskExecutor> executors) {
		if (BeanUtils.isEmpty(executors))
			return;

		Map nodeUserMap = (Map) nodeUserMapLocal.get();
		if (nodeUserMap == null)
			nodeUserMap = new HashMap();
		nodeUserMap.put(nodeId, executors);
		nodeUserMapLocal.set(nodeUserMap);
	}

	public void addNodeUser(String nodeId, String userIds) {
		if (StringUtil.isEmpty(userIds))
			return;
		List executorList = BpmUtil.getTaskExecutors(userIds);
		addNodeUser(nodeId, executorList);
	}

	public void addNodeUser(String[] aryNodeId, String[] aryUserIds) {
		if (BeanUtils.isEmpty(aryUserIds))
			return;
		Map nodeUserMap = (Map) nodeUserMapLocal.get();
		if (nodeUserMap == null)
			nodeUserMap = new HashMap();
		for (int i = 0; i < aryNodeId.length; i++) {
			String nodeId = aryNodeId[i];
			String userIds = aryUserIds[i];
			if (userIds != null) {
				List executorList = BpmUtil.getTaskExecutors(userIds);
				nodeUserMap.put(nodeId, executorList);
			}
		}
		nodeUserMapLocal.set(nodeUserMap);
	}

	public Map<String, List<TaskExecutor>> getNodeUserMap() {
		return (Map) nodeUserMapLocal.get();
	}

	public void clearNodeUserMap() {
		nodeUserMapLocal.remove();
	}

	public List<TaskExecutor> getMultipleUser(ActivityExecution execution) throws Exception {
		String nodeId = execution.getActivity().getId();
		ExecutionEntity executionEnt = (ExecutionEntity) execution;

		List list = (List) execution.getVariable("subAssignIds");

		if (BeanUtils.isNotEmpty(list))
			return list;

		Map nodeMap = NodeCache.getByActDefId(executionEnt.getProcessDefinitionId());
		FlowNode subProcessNode = (FlowNode) nodeMap.get(nodeId);
		FlowNode firstNode = subProcessNode.getSubFirstNode();
		FlowNode secondeNode = (FlowNode) firstNode.getNextFlowNodes().get(0);

		List userList = getExecutors();

		if (BeanUtils.isEmpty(userList)) {
			userList = (List) ((Map) nodeUserMapLocal.get()).get(secondeNode.getNodeId());
			if ((BeanUtils.isEmpty(userList)) && (BeanUtils.isNotEmpty(getFormUsers()))) {
				userList = getFormUsers();
			}
		}

		logger.debug("userList size:" + userList.size());
		execution.setVariable("subAssignIds", userList);
		return userList;
	}

	public List<TaskExecutor> getExtSubProcessMultipleUser(ActivityExecution execution) throws Exception {
		String nodeId = execution.getActivity().getId();
		String nodeName = execution.getCurrentActivityName();
		ExecutionEntity executionEnt = (ExecutionEntity) execution;

		String multiInstance = (String) executionEnt.getActivity().getProperty("multiInstance");

		String varName = executionEnt.getActivityId() + "_" + "subExtAssignIds";

		if ("sequential".equals(multiInstance)) {
			List userIds = (List) executionEnt.getParent().getVariable(varName);
			if (userIds != null)
				return userIds;
		} else {
			List userIds = (List) execution.getVariable(varName);
			if (userIds != null)
				return userIds;

		}

		Map nodeMap = NodeCache.getByActDefId(executionEnt.getProcessDefinitionId());

		FlowNode subProcessNode = (FlowNode) nodeMap.get(nodeId);

		Map subProcessNodesMap = subProcessNode.getSubProcessNodes();

		FlowNode startNode = NodeCache.getStartNode(subProcessNodesMap);

		FlowNode secodeNode = (FlowNode) startNode.getNextFlowNodes().get(0);

		List userList = getExecutors();

		if (BeanUtils.isEmpty(userList)) {
			userList = (List) getNodeUserMap().get(secodeNode.getNodeId());
			if (userList == null) {
				userList = new ArrayList();
			}
		}

		if ((multiInstance != null) && (BeanUtils.isEmpty(userList))) {
			MessageUtil.addMsg("请设置子流程:[" + nodeName + "]的人员!");
		}

		logger.debug("userList size:" + userList.size());

		executionEnt.setVariable(varName, userList);

		return userList;
	}

	public List<TaskExecutor> getSignUser(ActivityExecution execution) throws Exception {
		String nodeId = execution.getActivity().getId();
		String nodeName = (String) execution.getActivity().getProperty("name");
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");

		List userIds = null;

		String varName = nodeId + "_" + "signUsers";

		if ("sequential".equals(multiInstance)) {
			userIds = (List) execution.getVariable(varName);
			if (userIds != null)
				return userIds;
		}

		userIds = getExecutors();

		if (BeanUtils.isNotEmpty(userIds)) {
			saveExecutorVar(execution, userIds);
			return userIds;
		}

		Map nodeUserMap = (Map) nodeUserMapLocal.get();

		if ((nodeUserMap != null) && (BeanUtils.isNotEmpty(nodeUserMap.get(nodeId)))) {
			userIds = (List) nodeUserMap.get(nodeId);
			if (taskExecutors.get() == null) {
				setExecutorsFromList(userIds);
			}
			saveExecutorVar(execution, userIds);
			return userIds;
		}

		List formUsers = getFormUsers();
		if (BeanUtils.isNotEmpty(formUsers)) {
			setExecutorsFromList(formUsers);
			saveExecutorVar(execution, formUsers);
			return formUsers;
		}

		ExecutionEntity ent = (ExecutionEntity) execution;
		String actDefId = ent.getProcessDefinitionId();

		String startUserId = (String) execution.getVariable("startUser");
		Map vars = new HashMap();
		vars.put("executionId", execution.getId());
		List list = (List) bpmNodeUserService.getExeUserIds(actDefId, nodeId, startUserId, vars).get("PARTICIPATION");

		setExecutorsFromList(list);

		if (BeanUtils.isEmpty(list)) {
			MessageUtil.addMsg("请设置会签节点:[" + nodeName + "]的人员!");
		}
		if (BeanUtils.isNotEmpty(list)) {
			saveExecutorVar(execution, list);
		}

		return list;
	}

	private void saveExecutorVar(ActivityExecution execution, List<TaskExecutor> userIds) {
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");
		if ("sequential".equals(multiInstance)) {
			String nodeId = execution.getActivity().getId();
			String varName = nodeId + "_" + "signUsers";
			execution.setVariable(varName, userIds);
		}
	}

	public void setExecutors(String users) {
		if (StringUtil.isEmpty(users)) {
			return;
		}
		String[] aryUsers = users.split(",");

		List list = Arrays.asList(aryUsers);
		taskExecutors.set(list);
	}

	public void setExecutorsFromList(List<TaskExecutor> users) {
		taskExecutors.set(users);
	}

	public List<TaskExecutor> getExecutors() {
		return (List) taskExecutors.get();
	}

	public void clearExecutors() {
		taskExecutors.remove();
	}

	public List<TaskExecutor> getFormUsers() {
		return (List) formUsers.get();
	}

	public static void setFormUsers(List<TaskExecutor> formUsers_) {
		formUsers.set(formUsers_);
	}

	public static void addFormUsers(List<TaskExecutor> formUsers_) {
		if (formUsers.get() == null) {
			formUsers.set(formUsers_);
		} else
			((List) formUsers.get()).addAll(formUsers_);
	}

	public static void clearFormUsers() {
		formUsers.remove();
	}

	public static void clearAll() {
		formUsers.remove();
		taskExecutors.remove();
		nodeUserMapLocal.remove();
	}
}