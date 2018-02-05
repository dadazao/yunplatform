package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.third.bpm.dao.BpmDao;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;

public class NodeCache {
	private static final Log logger = LogFactory.getLog(NodeCache.class);

	private static Map<String, Map<String, FlowNode>> actNodesMap = new HashMap();

	public void add(String actDefId, Map<String, FlowNode> map) {
		actNodesMap.put(actDefId, map);
	}

	public static synchronized Map<String, FlowNode> getByActDefId(String actDefId) {
		if (actNodesMap.containsKey(actDefId)) {
			return (Map) actNodesMap.get(actDefId);
		}
		Map map = readFromXml(actDefId);
		actNodesMap.put(actDefId, map);
		return map;
	}

	public static FlowNode getStartNode(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		return getStartNode(nodeMap);
	}

	public static FlowNode getStartNode(Map<String, FlowNode> nodeMap) {
		for (FlowNode flowNode : nodeMap.values()) {
			if (("startEvent".equals(flowNode.getNodeType())) && (flowNode.getParentNode() == null)) {
				return flowNode;
			}
		}
		return null;
	}

	public static List<FlowNode> getEndNode(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		return getEndNode(nodeMap);
	}

	private static List<FlowNode> getEndNode(Map<String, FlowNode> nodeMap) {
		List flowNodeList = new ArrayList();
		for (FlowNode flowNode : nodeMap.values()) {
			if (("endEvent".equals(flowNode.getNodeType())) && (BeanUtils.isEmpty(flowNode.getNextFlowNodes()))) {
				flowNodeList.add(flowNode);
			}
		}
		return flowNodeList;
	}

	public static boolean hasExternalSubprocess(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		for (FlowNode flowNode : (List<FlowNode>) nodeMap.values()) {
			if ("callActivity".equals(flowNode.getNodeType())) {
				return true;
			}
		}
		return false;
	}

	public static void clear(String actDefId) {
		actNodesMap.remove(actDefId);
	}

	private static Map<String, FlowNode> readFromXml(String actDefId) {
		BpmDao dao = (BpmDao) AppUtil.getBean(BpmDao.class);
		BpmDefinitionDao bpmDefinitionDao = (BpmDefinitionDao) AppUtil.getBean(BpmDefinitionDao.class);
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);
		Long deployId = bpmDefinition.getActDeployId();
		String xml = dao.getDefXmlByDeployId(deployId.toString());
		return parseXml(xml);
	}

	private static String getXmlByDefKey(String actDefkey) {
		BpmDao dao = (BpmDao) AppUtil.getBean(BpmDao.class);
		BpmDefinitionDao bpmDefinitionDao = (BpmDefinitionDao) AppUtil.getBean(BpmDefinitionDao.class);
		BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefKeyIsMain(actDefkey);
		Long deployId = bpmDefinition.getActDeployId();
		String xml = dao.getDefXmlByDeployId(deployId.toString());
		return xml;
	}

	private static Map<String, FlowNode> parseXml(String xml) {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Map map = new HashMap();
		Element processEl = (Element) el.selectSingleNode("./process");
		Iterator its = processEl.elementIterator();

		while (its.hasNext()) {
			Element nodeEl = (Element) its.next();
			String nodeType = nodeEl.getName();

			String nodeId = nodeEl.attributeValue("id");
			String nodeName = nodeEl.attributeValue("name");

			boolean isMultiInstance = nodeEl.selectSingleNode("./multiInstanceLoopCharacteristics") != null;

			if (("startEvent".equals(nodeType)) || ("userTask".equals(nodeType))
					|| ("parallelGateway".equals(nodeType)) || ("inclusiveGateway".equals(nodeType))
					|| ("exclusiveGateway".equals(nodeType)) || ("endEvent".equals(nodeType))
					|| ("serviceTask".equals(nodeType))) {
				FlowNode flowNode = new FlowNode(nodeId, nodeName, nodeType, isMultiInstance);
				map.put(nodeId, flowNode);
			} else if ("subProcess".equals(nodeType)) {
				FlowNode subProcessNode = new FlowNode(nodeId, nodeName, nodeType, new ArrayList(), isMultiInstance);

				map.put(nodeId, subProcessNode);

				List<Element> subElements = nodeEl.elements();
				for (Element subEl : subElements) {
					String subNodeType = subEl.getName();
					if (("startEvent".equals(subNodeType)) || ("userTask".equals(subNodeType))
							|| ("parallelGateway".equals(subNodeType)) || ("inclusiveGateway".equals(subNodeType))
							|| ("exclusiveGateway".equals(subNodeType)) || ("endEvent".equals(subNodeType))
							|| ("serviceTask".equals(subNodeType))) {
						String subNodeName = subEl.attributeValue("name");
						String subNodeId = subEl.attributeValue("id");
						FlowNode flowNode = new FlowNode(subNodeId, subNodeName, subNodeType, false, subProcessNode);
						subProcessNode.getSubFlowNodes().add(flowNode);
						map.put(subNodeId, flowNode);
					}
				}

			} else if ("callActivity".equals(nodeType)) {
				String flowKey = nodeEl.attributeValue("calledElement");

				String subProcessXml = getXmlByDefKey(flowKey);

				Map subChildNodes = parseXml(subProcessXml);
				FlowNode flowNode = new FlowNode(nodeId, nodeName, nodeType, isMultiInstance);
				flowNode.setAttribute("subFlowKey", flowKey);
				map.put(nodeId, flowNode);

				flowNode.setSubProcessNodes(subChildNodes);
			}

		}

		List seqFlowList = document.selectNodes("/definitions/process//sequenceFlow");
		for (int i = 0; i < seqFlowList.size(); i++) {
			Element seqFlow = (Element) seqFlowList.get(i);
			String sourceRef = seqFlow.attributeValue("sourceRef");
			String targetRef = seqFlow.attributeValue("targetRef");

			FlowNode sourceFlowNode = (FlowNode) map.get(sourceRef);
			FlowNode targetFlowNode = (FlowNode) map.get(targetRef);
			if ((sourceFlowNode != null) && (targetFlowNode != null)) {
				logger.debug("sourceRef:" + sourceFlowNode.getNodeName() + " targetRef:" + targetFlowNode.getNodeName());
				if (targetFlowNode.getParentNode() != null) {
					logger.debug("parentNode:" + targetFlowNode.getParentNode().getNodeName());
				}

				if (("startEvent".equals(sourceFlowNode.getNodeType())) && (sourceFlowNode.getParentNode() != null)) {
					sourceFlowNode.setFirstNode(true);
					sourceFlowNode.getParentNode().setSubFirstNode(sourceFlowNode);

					if (targetFlowNode.getParentNode().getIsMultiInstance().booleanValue()) {
						targetFlowNode.setIsMultiInstance(Boolean.valueOf(true));
					}
				}
				sourceFlowNode.getNextFlowNodes().add(targetFlowNode);
				targetFlowNode.getPreFlowNodes().add(sourceFlowNode);
			}
		}
		return map;
	}

	public static Set<String> parseXmlBySubKey(String xml) {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Element processEl = (Element) el.selectSingleNode("./process");
		if (BeanUtils.isEmpty(processEl))
			return null;
		Iterator its = processEl.elementIterator();
		Set keySet = new HashSet();
		while (its.hasNext()) {
			Element nodeEl = (Element) its.next();
			String nodeType = nodeEl.getName();

			if ("callActivity".equals(nodeType)) {
				String flowKey = nodeEl.attributeValue("calledElement");
				keySet.add(flowKey);

				String subProcessXml = getXmlByDefKey(flowKey);

				Set<String> kSet = parseXmlBySubKey(subProcessXml);
				for (String key : kSet) {
					keySet.add(key);
				}
			}
		}
		return keySet;
	}

	public static boolean isMultipleFirstNode(String actDefId) throws Exception {
		FlowNode startNode = getStartNode(actDefId);
		if (startNode == null)
			return false;
		List list = startNode.getNextFlowNodes();
		if (list.size() == 0) {
			throw new Exception("流程定义:" + actDefId + ",起始节点没有后续节点，请检查流程图设置!");
		}

		return list.size() > 1;
	}

	public static FlowNode getFirstNodeId(String actDefId) throws Exception {
		FlowNode startNode = getStartNode(actDefId);
	    if (startNode == null) return null;
	    List list = startNode.getNextFlowNodes();
	    if (list.size() > 1) {
	      return null;
	    }
	    if (list.size() == 0) {
	      throw new Exception("流程定义:" + actDefId + ",起始节点没有后续节点，请检查流程图设置!");
	    }
	    return (FlowNode)list.get(0);
	}
}