package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.util.BpmConst;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class ProcessCmd {
	private String actDefId;
	private String flowKey;
	private String taskId;
	private String subject = "";
	private String destTask;
	private String[] lastDestTaskIds;
	private String[] lastDestTaskUids;
	private List<TaskExecutor> taskExecutors = new ArrayList();

	private String businessKey = "";
	private Long stackId;
	private boolean skipPreHandler = false;

	private boolean skipAfterHandler = false;

	private Integer isBack = Integer.valueOf(0);

	private boolean isRecover = false;

	private boolean isOnlyCompleteTask = false;

	private Short voteAgree = Short.valueOf((short) 1);

	private String voteContent = "";

	private Map<String, Object> variables = new HashMap();

	private String formData = "";

	private Map formDataMap = new HashMap();

	private boolean isAgentTask = false;

	private String currentUserId = "";

	private ProcessRun processRun = null;

	private String userAccount = null;

	private boolean invokeExternal = false;

	private String informType = "";

	public ProcessCmd() {
	}

	public ProcessCmd(String flowKey) {
		this.flowKey = flowKey;
	}

	public ProcessCmd(String flowKey, boolean skipPreHandler, boolean skipAfterHandler) {
		this.flowKey = flowKey;
		this.skipPreHandler = skipPreHandler;
		this.skipAfterHandler = skipAfterHandler;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void putVariables(Map<String, Object> variables) {
		this.variables.putAll(variables);
	}

	public void addVariable(String key, Object obj) {
		variables.put(key, obj);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDestTask() {
		return destTask;
	}

	public void setDestTask(String destTask) {
		this.destTask = destTask;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getFlowKey() {
		return flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public Integer isBack() {
		return isBack;
	}

	public void setBack(Integer isBack) {
		this.isBack = isBack;
	}

	public boolean isRecover() {
		return isRecover;
	}

	public void setRecover(boolean isRecover) {
		if (isRecover) {
			isBack = BpmConst.TASK_BACK;
		}
		this.isRecover = isRecover;
	}

	public Short getVoteAgree() {
		return voteAgree;
	}

	public void setVoteAgree(Short voteAgree) {
		if (TaskOpinion.STATUS_RECOVER.equals(voteAgree)) {
			setRecover(true);
		}
		this.voteAgree = voteAgree;
	}

	public String getVoteContent() {
		return voteContent;
	}

	public void setVoteContent(String voteContent) {
		this.voteContent = voteContent;
	}

	public Long getStackId() {
		return stackId;
	}

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public Map getFormDataMap() {
		return formDataMap;
	}

	public void setFormDataMap(Map formDataMap) {
		this.formDataMap = formDataMap;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		businessKey = processRun.getBusinessKey();
		this.processRun = processRun;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String[] getLastDestTaskIds() {
		return lastDestTaskIds;
	}

	public void setLastDestTaskIds(String[] lastDestTaskIds) {
		this.lastDestTaskIds = lastDestTaskIds;
	}

	public String[] getLastDestTaskUids() {
		return lastDestTaskUids;
	}

	public void setLastDestTaskUids(String[] lastDestTaskUids) {
		this.lastDestTaskUids = lastDestTaskUids;
	}

	public boolean isOnlyCompleteTask() {
		return isOnlyCompleteTask;
	}

	public void setOnlyCompleteTask(boolean isOnlyCompleteTask) {
		this.isOnlyCompleteTask = isOnlyCompleteTask;
	}

	public boolean isAgentTask() {
		return isAgentTask;
	}

	public void setAgentTask(boolean isAgentTask) {
		this.isAgentTask = isAgentTask;
	}

	public boolean isInvokeExternal() {
		return invokeExternal;
	}

	public void setInvokeExternal(boolean invokeExternal) {
		this.invokeExternal = invokeExternal;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public boolean isSkipPreHandler() {
		return skipPreHandler;
	}

	public void setSkipPreHandler(boolean skipPreHandler) {
		this.skipPreHandler = skipPreHandler;
	}

	public boolean isSkipAfterHandler() {
		return skipAfterHandler;
	}

	public void setSkipAfterHandler(boolean skipAfterHandler) {
		this.skipAfterHandler = skipAfterHandler;
	}

	public Map<String, List<TaskExecutor>> getTaskExecutor() {
		Map map = new HashMap();
		if (BeanUtils.isEmpty(lastDestTaskIds))
			return map;
		for (int i = 0; i < lastDestTaskIds.length; i++) {
			String nodeId = lastDestTaskIds[i];
			String executor = lastDestTaskUids[i];
			if (!StringUtil.isEmpty(executor)) {
				List list = BpmUtil.getTaskExecutors(executor);
				map.put(nodeId, list);
			}
		}
		return map;
	}

	public List<TaskExecutor> getTaskExecutors() {
		return taskExecutors;
	}

	public void setTaskExecutors(List<TaskExecutor> taskExecutors) {
		this.taskExecutors = taskExecutors;
	}
}