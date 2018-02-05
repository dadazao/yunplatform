package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.model.ExecutionStack;

@Repository
public class ExecutionStackDao extends BaseMyBatisDaoImpl<ExecutionStack, Long> {
	public Class getEntityClass() {
		return ExecutionStack.class;
	}

	public List<ExecutionStack> getByActInstIdNodeId(String actInstId, String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		return getBySqlKey("getByActInstIdNodeId", params);
	}

	public List<ExecutionStack> getByActInstIdNodeIdToken(String actInstId, String nodeId, String taskToken) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		params.put("taskToken", taskToken);
		return getBySqlKey("getByActInstIdNodeIdToken", params);
	}

	public ExecutionStack getLastestStack(String actInstId, String nodeId) {
		List list = getByActInstIdNodeId(actInstId, nodeId);
		if (list.size() > 0) {
			return (ExecutionStack) list.get(0);
		}
		return null;
	}

	public ExecutionStack getLastestStack(String actInstId, String parentNodeId, String taskToken) {
		if (StringUtil.isNotEmpty(taskToken)) {
			List list = getByActInstIdNodeIdToken(actInstId, parentNodeId, taskToken);
			if (list.size() > 0) {
				return (ExecutionStack) list.get(0);
			}
			return null;
		}
		return getLastestStack(actInstId, parentNodeId);
	}

	public List<ExecutionStack> getByActInstIdDepth(String actInstId, Integer depth) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("depth", depth);
		return getBySqlKey("getByActInstIdDepth", params);
	}

	public Integer delSubChilds(Long stackId, String nodePath) {
		Map params = new HashMap();
		params.put("stackId", stackId);
		params.put("nodePath", nodePath + "%");
		return Integer.valueOf(delBySqlKey("delSubChilds", params));
	}

	public List<ExecutionStack> getByActInstIdDepExStackId(String actInstId, Integer depth, Long stackId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("depth", depth);
		params.put("stackId", stackId);
		return getBySqlKey("getByActInstIdDepExStackId", params);
	}

	public List<ExecutionStack> getByLikeNodePath(String nodePath) {
		Map params = new HashMap();
		params.put("nodePath", nodePath + "%");
		return getBySqlKey("getByLikeNodePath", params);
	}

	public List<ExecutionStack> getByParentId(Long parentId) {
		return getBySqlKey("getByParentId", parentId);
	}

	public List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId) {
		return getBySqlKey("getByParentIdAndEndTimeNotNull", parentId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}

	public void udpTaskTokenByTaskIdNodeId(String taskToken, String taskId, String nodeId) {
		Map params = new HashMap();
		params.put("taskToken", taskToken);
		params.put("taskIds", taskId);
		params.put("nodeId", nodeId);
		update("udpTaskTokenByTaskIdNodeId", params);
	}
}