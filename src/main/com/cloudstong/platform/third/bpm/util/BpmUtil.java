package com.cloudstong.platform.third.bpm.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.cloudstong.platform.core.model.ResultMessage;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.FileUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.graph.DivShape;
import com.cloudstong.platform.third.bpm.graph.Point;
import com.cloudstong.platform.third.bpm.graph.ShapeMeta;
import com.cloudstong.platform.third.bpm.graph.activiti.ProcessDiagramGenerator;
import com.cloudstong.platform.third.bpm.model.ForkNode;
import com.cloudstong.platform.third.bpm.model.NodeCondition;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;

public class BpmUtil {
	private static final Log logger = LogFactory.getLog(BpmUtil.class);
	private static final String VAR_PRE_NAME = "v_";
	private static final String BPM_XML_NS = "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"";

	public static ProcessCmd getProcessCmd(HttpServletRequest request) throws Exception {
		ProcessCmd cmd = new ProcessCmd();

		String temp = request.getParameter("taskId");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setTaskId(temp);
		}
		temp = request.getParameter("agentTask");

		if ("true".equals(temp)) {
			cmd.setAgentTask(true);
		}

		temp = request.getParameter("formData");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setFormData(temp);
		}

		Map paraMap = RequestUtil.getParameterValueMap(request, false, false);
		cmd.setFormDataMap(paraMap);

		temp = request.getParameter("actDefId");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setActDefId(temp);
		} else {
			temp = request.getParameter("flowKey");
			if (StringUtil.isNotEmpty(temp)) {
				cmd.setFlowKey(temp);
			}
		}

		temp = request.getParameter("destTask");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setDestTask(temp);
		}

		temp = request.getParameter("businessKey");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setBusinessKey(temp);
		}

		String[] destTaskIds = request.getParameterValues("lastDestTaskId");
		if (destTaskIds != null) {
			cmd.setLastDestTaskIds(destTaskIds);
			String[] destTaskUserIds = new String[destTaskIds.length];
			for (int i = 0; i < destTaskIds.length; i++) {
				String[] userIds = request.getParameterValues(destTaskIds[i] + "_userId");
				if (userIds != null) {
					destTaskUserIds[i] = StringUtil.getArrayAsString(userIds);
				}
			}
			cmd.setLastDestTaskUids(destTaskUserIds);
		}

		temp = request.getParameter("back");
		if (StringUtil.isNotEmpty(temp)) {
			Integer rtn = Integer.valueOf(Integer.parseInt(temp));
			cmd.setBack(rtn);
		}

		cmd.setVoteContent(request.getParameter("voteContent"));

		temp = request.getParameter("stackId");
		if (StringUtils.isNotEmpty(temp)) {
			cmd.setStackId(new Long(temp));
		}
		temp = request.getParameter("voteAgree");
		if (StringUtil.isNotEmpty(temp)) {
			cmd.setVoteAgree(new Short(temp));
		}

		temp = RequestUtil.getStringValues(request, "informType");
		cmd.setInformType(temp);

		Enumeration paramEnums = request.getParameterNames();
		while (paramEnums.hasMoreElements()) {
			String paramName = (String) paramEnums.nextElement();
			if (paramName.startsWith("v_")) {
				String[] vnames = paramName.split("[_]");
				if ((vnames != null) && (vnames.length == 3)) {
					String varName = vnames[1];
					String val = request.getParameter(paramName);
					if (!val.isEmpty()) {
						Object valObj = getValue(vnames[2], val);
						cmd.getVariables().put(varName, valObj);
					}

				}

			}

		}

		return cmd;
	}

	public static List<TaskExecutor> getTaskExecutors(String executors) {
		String[] aryExecutor = executors.split(",");
		List list = new ArrayList();
		for (String tmp : aryExecutor) {
			String[] aryTmp = tmp.split("\\^");
			if (aryTmp.length >= 3) {
				TaskExecutor taskExecutor = new TaskExecutor(aryTmp[0], aryTmp[1], aryTmp[2]);
				list.add(taskExecutor);
			}
		}
		return list;
	}

	public static Object getValue(String type, String paramValue) {
		Object value = null;

		if ("S".equals(type))
			value = paramValue;
		else if ("L".equals(type))
			value = new Long(paramValue);
		else if ("I".equals(type))
			value = new Integer(paramValue);
		else if ("DB".equals(type))
			value = new Double(paramValue);
		else if ("BD".equals(type))
			value = new BigDecimal(paramValue);
		else if ("F".equals(type))
			value = new Float(paramValue);
		else if ("SH".equals(type))
			value = new Short(paramValue);
		else if ("D".equals(type))
			try {
				value = DateUtils.parseDate(paramValue, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
			} catch (Exception localException) {
			}
		else
			value = paramValue;

		return value;
	}

	public static Map<String, Map<String, String>> getTaskActivitys(String defXml) {
		return getTaskActivitys(defXml, Boolean.valueOf(true));
	}

	public static Map<String, Map<String, String>> getTaskActivitys(String defXml, Boolean flag) {
		Map rtnMap = new HashMap();

		defXml = defXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");

		Document doc = Dom4jUtil.loadXml(defXml);
		Element root = doc.getRootElement();

		List list = root.selectNodes("./process//userTask");
		Map taskMap = new HashMap();
		addToMap(list, taskMap);

		List callActivityList = root.selectNodes("./process//callActivity");
		if (callActivityList.size() > 0) {
			addToMap(callActivityList, taskMap);
		}
		rtnMap.put("任务节点", taskMap);

		Map gateWayMap = new HashMap();

		List parallelGatewayList = root.selectNodes("./process//parallelGateway");
		if (parallelGatewayList.size() > 0) {
			addToMap(parallelGatewayList, gateWayMap);
		}

		List inclusiveGatewayList = root.selectNodes("./process//inclusiveGateway");
		if (inclusiveGatewayList.size() > 0) {
			addToMap(inclusiveGatewayList, gateWayMap);
		}

		List exclusiveGatewayGatewayList = root.selectNodes("./process//exclusiveGateway");
		if (exclusiveGatewayGatewayList.size() > 0) {
			addToMap(exclusiveGatewayGatewayList, gateWayMap);
		}

		if (gateWayMap.size() > 0) {
			rtnMap.put("网关节点", gateWayMap);
		}

		if (flag.booleanValue()) {
			List startList = root.selectNodes("./process//startEvent");
			Map startMap = new HashMap();
			addToMap(startList, startMap);
			rtnMap.put("开始节点", startMap);
		}

		List endList = root.selectNodes("./process//endEvent");
		Map endMap = new HashMap();
		addToMap(endList, endMap);
		rtnMap.put("结束节点", endMap);

		List serviceTask = root.selectNodes("./process//serviceTask");
		if (serviceTask.size() > 0) {
			Map serviceMap = new HashMap();
			addToMap(serviceTask, serviceMap);
			rtnMap.put("自动任务", serviceMap);
		}

		return rtnMap;
	}

	private static void addToMap(List<Node> list, Map<String, String> map) {
		for (Node node : list) {
			Element el = (Element) node;
			String id = el.attributeValue("id");
			String name = el.attributeValue("name");
			map.put(id, name);
		}
	}

	public static String getFirstTaskNode(String defXml) {
		defXml = defXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(defXml);
		Element root = doc.getRootElement();
		Element startNode = (Element) root.selectSingleNode("/definitions/process/startEvent");
		if (startNode == null)
			return "";
		String startId = startNode.attributeValue("id");
		Element sequenceFlow = (Element) root.selectSingleNode("/definitions/process/sequenceFlow[@sourceRef='" + startId + "']");
		if (sequenceFlow == null)
			return "";
		String taskId = sequenceFlow.attributeValue("targetRef");
		return taskId;
	}

	public static Map<String, Map<String, String>> getTranstoActivitys(String defXml, List<String> nodes, Boolean flag) {
		Map actMap = getTaskActivitys(defXml, flag);
		Collection values = actMap.values();
		for (Iterator localIterator1 = nodes.iterator(); localIterator1.hasNext();) {
			String node = (String) localIterator1.next();
			for (Iterator localIterator2 = values.iterator(); localIterator2.hasNext();) {
				Map map = (Map) localIterator2.next();
				map.remove(node);
			}
		}

		return actMap;
	}

	public static Map<String, Map<String, String>> getTranstoActivitys(String defXml, List<String> nodes) {
		Map actMap = getTaskActivitys(defXml);
		Collection values = actMap.values();
		for (Iterator localIterator1 = nodes.iterator(); localIterator1.hasNext();) {
			String node = (String) localIterator1.next();
			for (Iterator localIterator2 = values.iterator(); localIterator2.hasNext();) {
				Map map = (Map) localIterator2.next();
				map.remove(node);
			}
		}

		return actMap;
	}

	public static boolean isTaskListener(String className) {
		try {
			Class cls = Class.forName(className);
			return BeanUtils.isInherit(cls, TaskListener.class);
		} catch (ClassNotFoundException e) {
		}
		return false;
	}

	public static int isHandlerValid(String handler) {
		if (handler.indexOf(".") == -1)
			return -1;
		String[] aryHandler = handler.split("[.]");
		if (aryHandler.length != 2)
			return -1;
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		Object serviceBean = null;
		try {
			serviceBean = AppUtil.getBean(beanId);
		} catch (Exception ex) {
			return -2;
		}
		if (serviceBean == null)
			return -2;
		try {
			Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[] { ProcessCmd.class });
			return 0;
		} catch (NoSuchMethodException e) {
			return -3;
		} catch (Exception e) {
		}
		return -4;
	}

	public static int isHandlerValidNoCmd(String handler) {
		if (handler.indexOf(".") == -1)
			return -1;
		String[] aryHandler = handler.split("[.]");
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		Object serviceBean = null;
		try {
			serviceBean = AppUtil.getBean(beanId);
		} catch (Exception ex) {
			return -2;
		}
		if (serviceBean == null)
			return -2;
		try {
			Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[0]);
			if (invokeMethod != null) {
				return 0;
			}
			return -3;
		} catch (NoSuchMethodException e) {
			return -3;
		} catch (Exception e) {
		}
		return -4;
	}

	public static String transform(String id, String name, String xml) throws TransformerFactoryConfigurationError, Exception {
		Map map = new HashMap();
		map.put("id", id);
		map.put("name", name);

		String xlstPath = FileUtil.getClassesPath() + "com/cloudstong/platform/third/bpm/graph/transform.xsl".replace("/", File.separator);
		xml = xml.trim();
		String str = Dom4jUtil.transXmlByXslt(xml, xlstPath, map);
		str = str.replace("&lt;", "<").replace("&gt;", ">").replace("xmlns=\"\"", "").replace("&amp;", "&");

		Pattern regex = Pattern.compile("name=\".*?\"");
		Matcher regexMatcher = regex.matcher(str);
		while (regexMatcher.find()) {
			String strReplace = regexMatcher.group(0);
			String strReplaceWith = strReplace.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
			str = str.replace(strReplace, strReplaceWith);
		}

		return str;
	}

	public static ShapeMeta transGraph(String xml) throws Exception {
		List shaps = ProcessDiagramGenerator.extractBPMNShap(xml);
		List edges = ProcessDiagramGenerator.extractBPMNEdge(xml);
		Point2D.Double[] points = ProcessDiagramGenerator.caculateCanvasSize(shaps, edges);
		double shiftX = points[0].getX() < 0.0D ? points[0].getX() : 0.0D;
		double shiftY = points[0].getY() < 0.0D ? points[0].getY() : 0.0D;
		int width = (int) Math.round(points[1].getX() + 10.0D - shiftX);
		int height = (int) Math.round(points[1].getY() + 10.0D - shiftY);
		int minX = (int) Math.round(points[0].getX() - shiftX);

		int minY = (int) Math.round(points[0].getY() - shiftY);
		minX = minX <= 5 ? 5 : minX;
		minY = minY <= 5 ? 5 : minY;

		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		List sequenceFlows = root.selectNodes("//sequenceFlow");
		Map seqIdandName = new HashMap();
		StringBuffer sb = new StringBuffer();
		for (Iterator localIterator = sequenceFlows.iterator(); localIterator.hasNext();) {
			Object node = localIterator.next();
			String id = ((Element) node).attributeValue("id");
			String name = ((Element) node).attributeValue("name");
			seqIdandName.put(id, name);
		}
		List list = root.selectNodes("//bpmndi:BPMNShape");
		for (int i = 0; i < list.size(); i++) {
			Element el = (Element) list.get(i);

			String id = el.attributeValue("bpmnElement");
			Element component = (Element) root.selectSingleNode("//*[@id='" + id + "']");

			if ((component != null) && (!component.getName().equalsIgnoreCase("participant")) && (!component.getName().equalsIgnoreCase("lane"))) {
				Element tmp = (Element) el.selectSingleNode("omgdc:Bounds");
				int x = (int) Float.parseFloat(tmp.attributeValue("x"));
				int y = (int) Float.parseFloat(tmp.attributeValue("y"));

				int w = (int) Float.parseFloat(tmp.attributeValue("width"));
				int h = (int) Float.parseFloat(tmp.attributeValue("height"));
				x = (int) (x - minX + 5 - shiftX);
				y = (int) (y - minY + 5 - shiftY);

				Element procEl = (Element) root.selectSingleNode("//process/descendant::*[@id='" + id + "']");
				if (procEl != null) {
					String type = procEl.getName();
					if (type.equals("serviceTask")) {
						String attribute = procEl.attributeValue("class");
						if (attribute != null) {
							if (attribute.equals("com.cloudstongplatform.third.bpm.service.MessageTask")) {
								type = "email";
							} else if (attribute.equals("com.cloudstongplatform.third.bpm.service.ScriptTask")) {
								type = "script";
							} else if (attribute.equals("com.cloudstongplatform.third.bpm.service.WebServiceTask")) {
								type = "webService";
							}
						}
					}
					if ((!"subProcess".equals(type)) && (!"callActivity".equals(type))) {
						Element multiObj = procEl.element("multiInstanceLoopCharacteristics");
						if ((multiObj != null) && (!"subProcess".equals(type))) {
							type = "multiUserTask";
						}
					}
					Element parent = procEl.getParent();

					String name = procEl.attributeValue("name");

					int zIndex = 10;

					String parentName = parent.getName();

					if (parentName.equals("subProcess")) {
						zIndex = 11;
						if (type.equalsIgnoreCase("startEvent")) {
							type = "subStartEvent";
						}
					}

					if (parentName.equals("subProcess")) {
						zIndex = 11;
						if (type.equalsIgnoreCase("endEvent")) {
							type = "subEndEvent";
						}
					}

					DivShape shape = new DivShape(name, x, y, w, h, zIndex, id, type);
					sb.append(shape);
				}
			}
		}
		ShapeMeta shapeMeta = new ShapeMeta(width, height, sb.toString());
		return shapeMeta;
	}

	private static Map<String, Integer> caculateCenterPosition(List<Integer> waypoints) {
		int x = 0;
		int y = 0;
		int flag = 0;
		Map point = new HashMap();
		List lens = new ArrayList();
		for (int i = 2; i < waypoints.size(); i += 2) {
			lens.add(Integer.valueOf(Math.abs(((Integer) waypoints.get(i - 2)).intValue() - ((Integer) waypoints.get(i)).intValue())
					+ Math.abs(((Integer) waypoints.get(i - 1)).intValue() - ((Integer) waypoints.get(i + 1)).intValue())));
		}

		int halfLen = 0;
		for (Iterator localIterator = lens.iterator(); localIterator.hasNext();) {
			int len = ((Integer) localIterator.next()).intValue();
			halfLen += len;
		}
		halfLen /= 2;

		int accumulativeLen = 0;

		for (int i = 0; i < lens.size(); i++) {
			accumulativeLen += ((Integer) lens.get(i)).intValue();
			if (accumulativeLen > halfLen) {
				break;
			}
			i++;
			if (((Integer) waypoints.get(2 * i)).intValue() == ((Integer) waypoints.get(2 * (i - 1))).intValue()) {
				if (((Integer) waypoints.get(2 * i + 1)).intValue() > ((Integer) waypoints.get(2 * (i - 1) + 1)).intValue()) {
					y = halfLen - (accumulativeLen - ((Integer) lens.get(i - 1)).intValue()) + ((Integer) waypoints.get(2 * (i - 1) + 1)).intValue();
					flag = 1;
				} else {
					y = accumulativeLen - ((Integer) lens.get(i - 1)).intValue() + ((Integer) waypoints.get(2 * (i - 1) + 1)).intValue() - halfLen;
					flag = 2;
				}
				x = ((Integer) waypoints.get(2 * i)).intValue();
			} else {
				if (((Integer) waypoints.get(2 * i)).intValue() > ((Integer) waypoints.get(2 * (i - 1))).intValue()) {
					x = halfLen - (accumulativeLen - ((Integer) lens.get(i - 1)).intValue()) + ((Integer) waypoints.get(2 * (i - 1))).intValue();
					flag = 3;
				} else {
					x = accumulativeLen - ((Integer) lens.get(i - 1)).intValue() + ((Integer) waypoints.get(2 * (i - 1))).intValue() - halfLen;
					flag = 4;
				}
				y = ((Integer) waypoints.get(2 * i + 1)).intValue();
			}

			point.put("x", Integer.valueOf(x));
			point.put("y", Integer.valueOf(y));
			point.put("flag", Integer.valueOf(flag));
		}
		return point;
	}

	private static Point[] caculateLabelRectangle(String name, List<Integer> waypoints) {
		if (name == null) {
			return new Point[0];
		}
		BufferedImage processDiagram = new BufferedImage(2, 2, 2);
		Graphics2D g = processDiagram.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(Color.black);
		Font font = new Font("宋体", 1, 12);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();

		Map pos = caculateCenterPosition(waypoints);
		int x = ((Integer) pos.get("x")).intValue();
		int y = ((Integer) pos.get("y")).intValue();
		int flag = ((Integer) pos.get("flag")).intValue();

		int drawX = x;
		int drawY = y;
		switch (flag) {
		case 1:
			drawX = x + fontMetrics.getHeight() / 2;
			drawY = y;
			break;
		case 2:
			drawX = x - fontMetrics.stringWidth(name) - fontMetrics.getHeight() / 2;
			drawY = y + fontMetrics.getHeight();
			break;
		case 3:
			drawX = x - fontMetrics.stringWidth(name) / 2;
			drawY = y - fontMetrics.getHeight() / 2 - fontMetrics.getHeight();
			break;
		case 4:
			drawX = x - fontMetrics.stringWidth(name) / 2;
			drawY = y + fontMetrics.getHeight() - fontMetrics.getHeight();
		}

		Point[] points = { new Point(drawX, drawY), new Point(drawX + fontMetrics.stringWidth(name), drawY + fontMetrics.getHeight()) };

		return points;
	}

	public static ForkNode getForkNode(String forkNode, String xml) throws IOException {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "xmlns:bpm='cloudstong'");
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		List preNodes = root.selectNodes("//sequenceFlow[@targetRef='" + forkNode + "']");
		ForkNode model = new ForkNode();
		model.setForkNodeId(forkNode);
		Element soureNode;
		if (preNodes.size() == 1) {
			Element preLine = (Element) preNodes.get(0);
			String sourceId = preLine.attributeValue("sourceRef");

			soureNode = (Element) root.selectSingleNode("//userTask[@id='" + sourceId + "']");
			if (soureNode != null) {
				model.setPreNodeId(sourceId);
				Element multiNode = soureNode.element("multiInstanceLoopCharacteristics");
				if (multiNode != null) {
					model.setMulti(true);
				}
			}
		}

		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='" + forkNode + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}

			Element targetNode = (Element) root.selectSingleNode("//*[@id='" + id + "']");
			String nodeName = targetNode.attributeValue("name");

			NodeCondition nodeCondition = new NodeCondition(nodeName, id, condition);
			model.addNode(nodeCondition);
		}
		return model;
	}

	public static Map<String, String> getDecisionConditions(String processXml, String decisionNodeId) {
		Map map = new HashMap();
		processXml = processXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "xmlns:bpm='cloudstong'");
		Document doc = Dom4jUtil.loadXml(processXml);
		Element root = doc.getRootElement();

		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='" + decisionNodeId + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}
			map.put(id, condition);
		}
		return map;
	}

	public static String setCondition(String sourceNode, Map<String, String> map, String xml) throws IOException {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "xmlns:bpm=\"cloudstong\"");
		Document doc = Dom4jUtil.loadXml(xml, "utf-8");
		Element root = doc.getRootElement();
		List<Element> nodes = root.selectNodes("//sequenceFlow[@sourceRef='" + sourceNode + "']");
		for (Element el : nodes) {
			String id = el.attributeValue("targetRef");
			String condition = (String) map.get(id);

			Element conditionEl = el.element("conditionExpression");
			if (conditionEl != null)
				el.remove(conditionEl);
			if (StringUtil.isNotEmpty(condition)) {
				Element elAdd = el.addElement("conditionExpression");
				elAdd.addAttribute("xsi:type", "tFormalExpression");
				elAdd.addCDATA("${" + condition + "}");
			}
		}
		String outXml = doc.asXML();
		outXml = outXml.replace("xmlns:bpm=\"cloudstong\"", "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"");
		return outXml;
	}

	public static String setGraphXml(String sourceNode, Map<String, String> map, String xml) throws IOException {
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();

		Element node = (Element) root.selectSingleNode("//bg:Gateway[@id='" + sourceNode + "']");
		Element portsEl = node.element("ports");
		List portList = portsEl.elements();

		for (int i = 0; i < portList.size(); i++) {
			Element portEl = (Element) portList.get(i);
			if ((portEl.attribute("x") != null) || (portEl.attribute("y") != null)) {
				String id = portEl.attributeValue("id");
				Element outNode = (Element) root.selectSingleNode("//bg:SequenceFlow[@startPort='" + id + "']");
				if (outNode != null) {
					String outPort = outNode.attributeValue("endPort");
					Element tmpNode = (Element) root.selectSingleNode("//ciied:Port[@id='" + outPort + "']");
					Element taskNode = tmpNode.getParent().getParent();
					String taskId = taskNode.attributeValue("id");

					Element conditionEl = outNode.element("Condition");
					if (conditionEl != null) {
						outNode.remove(conditionEl);
					}
					if (map.containsKey(taskId)) {
						String condition = (String) map.get(taskId);
						Element elAdd = outNode.addElement("Condition");
						elAdd.addText(condition);
					}
				}
			}
		}
		return doc.asXML();
	}

	public static ResultMessage genImageByDepolyId(String deployId, String fileName, RepositoryService repositoryService) throws IOException {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition def = (ProcessDefinition) query.deploymentId(deployId).singleResult();
		if (def == null) {
			ResultMessage result = new ResultMessage();
			result.setMessage("没有找到流程定义!");
			result.setResult(0);
			return result;
		}
		String defId = def.getId();
		return genImageByDefId(defId, fileName, repositoryService);
	}

	public static ResultMessage genImageByDepolyId(String deployId, String fileName, RepositoryService repositoryService, String[] activitys)
			throws IOException {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition def = (ProcessDefinition) query.deploymentId(deployId).singleResult();
		if (def == null) {
			ResultMessage result = new ResultMessage();
			result.setMessage("没有找到流程定义!");
			result.setResult(0);
			return result;
		}
		String defId = def.getId();
		return genImageByDefId(defId, fileName, repositoryService, activitys);
	}

	public static ResultMessage genImageByDefId(String actDefId, String fileName, RepositoryService repositoryService) throws IOException {
		ResultMessage result = new ResultMessage();

//		BpmService bpmService = (BpmService) AppUtil.getBean(BpmService.class);
//		String bpmXml = bpmService.getDefXmlByProcessDefinitionId(actDefId);
//		if (bpmXml == null) {
//			result.setMessage("没有找到对应的流程定义!");
//			result.setResult(0);
//			return result;
//		}
//		InputStream is = ProcessDiagramGenerator.generatePngDiagram(bpmXml);
//		FileUtil.writeFile(fileName, is);
//
//		result.setMessage("成功生成流程定义!");
//		result.setResult(1);

		return result;
	}

	public static ResultMessage genImageByDefId(String defId, String fileName, RepositoryService repositoryService, String[] activitys)
			throws IOException {
		ResultMessage result = new ResultMessage();
//
//		BpmService bpmService = (BpmService) AppUtil.getBean(BpmService.class);
//		String bpmXml = bpmService.getDefXmlByProcessDefinitionId(defId);
//		if (bpmXml == null) {
//			result.setMessage("没有找到对应的流程定义!");
//			result.setResult(0);
//			return result;
//		}
//
//		List list = new ArrayList();
//		for (String node : activitys) {
//			list.add(node);
//		}
//
//		InputStream is = ProcessDiagramGenerator.generateDiagram(bpmXml, "png", list);
//		FileUtil.writeFile(fileName, is);
//
//		result.setMessage("成功生成流程定义!");
//		result.setResult(1);

		return result;
	}

	public static String getStrByRule(String rule, Map<String, Object> map) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);

		Matcher matcher = regex.matcher(rule);
		while (matcher.find()) {
			String tag = matcher.group(0);
			String name = matcher.group(1);

			Object value = map.get(name);
			if (BeanUtils.isEmpty(value))
				rule = rule.replace(tag, "");
			else {
				rule = rule.replace(tag, value.toString());
			}
		}
		return rule;
	}

	public static String getTitleByRule(String titleRule, Map<String, Object> map) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);

		Matcher matcher = regex.matcher(titleRule);
		while (matcher.find()) {
			String tag = matcher.group(0);
			String rule = matcher.group(1);
			String[] aryRule = rule.split(":");
			String name = "";
			if (aryRule.length == 1)
				name = rule;
			else {
				name = aryRule[1];
			}
			if (map.containsKey(name)) {
				Object obj = map.get(name);
				titleRule = titleRule.replace(tag, obj.toString());
			} else {
				titleRule = titleRule.replace(tag, "");
			}
		}
		return titleRule;
	}

	public static boolean isMultiTask(DelegateTask delegateTask) {
		ActivityExecution execution = (ActivityExecution) delegateTask.getExecution();
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");
		if (multiInstance != null) {
			return true;
		}
		return false;
	}

	public static boolean isMuiltiExcetion(ExecutionEntity execution) {
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");
		if (multiInstance != null) {
			return true;
		}
		return false;
	}
}