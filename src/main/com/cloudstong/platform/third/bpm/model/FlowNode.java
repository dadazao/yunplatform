package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowNode {
	private String nodeId = null;

	private String nodeName = null;

	private String nodeType = null;

	private Boolean isMultiInstance = Boolean.valueOf(false);

	private FlowNode parentNode = null;

	private boolean isFirstNode = false;

	private Map<String, String> attrMap = new HashMap();

	private Map<String, FlowNode> subProcessNodes = new HashMap();

	private List<FlowNode> preFlowNodes = new ArrayList();

	private List<FlowNode> nextFlowNodes = new ArrayList();

	private List<FlowNode> subFlowNodes = new ArrayList();

	private FlowNode subFirstNode = null;

	public String getNodeId() {
		return nodeId;
	}

	public FlowNode() {
	}

	public FlowNode(String nodeId, String nodeName, String nodeType) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, List<FlowNode> subFlowNodes) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.subFlowNodes = subFlowNodes;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, boolean isMultiInstance) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, boolean isMultiInstance, FlowNode parentNode) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
		this.parentNode = parentNode;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, List<FlowNode> subFlowNodes,
			boolean isMultiInstance) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.subFlowNodes = subFlowNodes;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public List<FlowNode> getPreFlowNodes() {
		return preFlowNodes;
	}

	public void setPreFlowNodes(List<FlowNode> preFlowNodes) {
		this.preFlowNodes = preFlowNodes;
	}

	public List<FlowNode> getNextFlowNodes() {
		return nextFlowNodes;
	}

	public void setNextFlowNodes(List<FlowNode> nextFlowNodes) {
		this.nextFlowNodes = nextFlowNodes;
	}

	public List<FlowNode> getSubFlowNodes() {
		return subFlowNodes;
	}

	public void setSubFlowNodes(List<FlowNode> subFlowNodes) {
		this.subFlowNodes = subFlowNodes;
	}

	public Boolean getIsMultiInstance() {
		return isMultiInstance;
	}

	public void setIsMultiInstance(Boolean isMultiInstance) {
		this.isMultiInstance = isMultiInstance;
	}

	public boolean isFirstNode() {
		return isFirstNode;
	}

	public void setFirstNode(boolean isFirstNode) {
		this.isFirstNode = isFirstNode;
	}

	public FlowNode getSubFirstNode() {
		return subFirstNode;
	}

	public void setSubFirstNode(FlowNode subFirstNode) {
		this.subFirstNode = subFirstNode;
	}

	public FlowNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(FlowNode parentNode) {
		this.parentNode = parentNode;
	}

	public void setAttribute(String name, String value) {
		attrMap.put(name, value);
	}

	public String getAttribute(String name) {
		if (attrMap.containsKey(name)) {
			return (String) attrMap.get(name);
		}
		return "";
	}

	public Map<String, FlowNode> getSubProcessNodes() {
		return subProcessNodes;
	}

	public void setSubProcessNodes(Map<String, FlowNode> subProcessNodes) {
		this.subProcessNodes = subProcessNodes;
	}

	public boolean getIsSubProcess() {
		return this.nodeType.equalsIgnoreCase("subProcess");
	}

	public boolean getIsCallActivity() {
		return this.nodeType.equalsIgnoreCase("callActivity");
	}

	public boolean getIsSignNode() {
		return (this.nodeType.equalsIgnoreCase("userTask")) && (getIsMultiInstance().booleanValue());
	}

	public boolean getIsFirstNode() {
		if (getPreFlowNodes() != null) {
			List<FlowNode> list = getPreFlowNodes();
			for (FlowNode node : list) {
				if (node.getNodeType().equalsIgnoreCase("startEvent")) {
					return true;
				}
			}
		}

		return false;
	}
}