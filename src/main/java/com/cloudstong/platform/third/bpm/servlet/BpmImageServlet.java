package com.cloudstong.platform.third.bpm.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.graph.activiti.ProcessDiagramGenerator;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.IFlowStatus;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;

public class BpmImageServlet extends HttpServlet {
	private BpmService bpmService = (BpmService) AppUtil.getBean("bpmService");

	private IFlowStatus iFlowStatus = (IFlowStatus) AppUtil.getBean("iFlowStatus");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deployId = RequestUtil.getString(request, "deployId");
		String taskId = RequestUtil.getString(request, "taskId");
		String processInstanceId = RequestUtil.getString(request, "processInstanceId");
		String definitionId = RequestUtil.getString(request, "definitionId");
		String runId = request.getParameter("runId");

		InputStream is = null;

		if (StringUtil.isNotEmpty(deployId)) {
			String bpmnXml = bpmService.getDefXmlByDeployId(deployId);
			is = ProcessDiagramGenerator.generatePngDiagram(bpmnXml);
		} else if (StringUtils.isNotEmpty(definitionId)) {
			String bpmnXml = bpmService.getDefXmlByProcessDefinitionId(definitionId);
			is = ProcessDiagramGenerator.generatePngDiagram(bpmnXml);
		} else if (StringUtil.isNotEmpty(taskId)) {
			String bpmnXml = bpmService.getDefXmlByProcessTaskId(taskId);
			TaskEntity taskEntity = bpmService.getTask(taskId);
			Map highLightList = iFlowStatus.getStatusByInstanceId(new Long(taskEntity.getProcessInstanceId()));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml, highLightList, "png");
		} else if (StringUtils.isNotEmpty(processInstanceId)) {
			String bpmnXml = bpmService.getDefXmlByProcessProcessInanceId(processInstanceId);
			if (bpmnXml == null) {
				ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean(ProcessRunService.class);
				ProcessRun processRun = processRunService.getByActInstanceId(processInstanceId);
				bpmnXml = bpmService.getDefXmlByDeployId(processRun.getActDefId());
			}

			IFlowStatus flowStatus = (IFlowStatus) AppUtil.getBean(IFlowStatus.class);
			Map highLightMap = flowStatus.getStatusByInstanceId(Long.valueOf(Long.parseLong(processInstanceId)));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml, highLightMap, "png");
		} else if (StringUtils.isNotEmpty(runId)) {
			ProcessRunService processRunService = (ProcessRunService) AppUtil.getBean(ProcessRunService.class);
			ProcessRun processRun = (ProcessRun) processRunService.getById(new Long(runId));
			processInstanceId = processRun.getActInstId();
			String bpmnXml = bpmService.getDefXmlByProcessProcessInanceId(processRun.getActInstId());
			if (bpmnXml == null) {
				bpmnXml = bpmService.getDefXmlByDeployId(processRun.getActDefId());
			}

			IFlowStatus flowStatus = (IFlowStatus) AppUtil.getBean(IFlowStatus.class);
			Map highLightMap = flowStatus.getStatusByInstanceId(Long.valueOf(Long.parseLong(processInstanceId)));
			is = ProcessDiagramGenerator.generateDiagram(bpmnXml, highLightMap, "png");
		}

		if (is != null) {
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			try {
				byte[] bs = new byte[1024];
				int n = 0;
				while ((n = is.read(bs)) != -1) {
					out.write(bs, 0, n);
				}
				out.flush();
			} catch (Exception localException) {
			} finally {
				is.close();
				out.close();
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private List<ActivityImpl> getPoolActivities(String deployId) {
		List activities = new ArrayList();
		String defXml = bpmService.getDefXmlByDeployId(deployId);
		defXml = defXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(defXml);
		Element root = document.getRootElement();
		List participants = root.selectNodes("//collaboration/participant");
		for (Iterator localIterator1 = participants.iterator(); localIterator1.hasNext();) {
			Element participant = (Element) localIterator1.next();
			String participantId = participant.attributeValue("id");
			String participantRef = participant.attributeValue("processRef");
			Element shap = (Element) root.selectSingleNode("//bpmndi:BPMNShape[@bpmnElement='" + participantId + "']");
			Element pool = (Element) root.selectSingleNode("//process[@id='" + participantRef + "']");
			Element bounds = (Element) shap.selectSingleNode("omgdc:Bounds");
			ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl(participantRef);
			ActivityImpl activity = new ActivityImpl(participantRef, processDefinition);
			Map properties = new HashMap();
			properties.put("name", pool.attributeValue("name"));
			String isHorizontal = shap.attributeValue("isHorizontal");
			if (isHorizontal.equalsIgnoreCase("true"))
				properties.put("type", "HPool");
			else {
				properties.put("type", "VPool");
			}
			activity.setProperties(properties);
			int x = Integer.parseInt(bounds.attributeValue("x"));
			int y = Integer.parseInt(bounds.attributeValue("y"));
			int width = Integer.parseInt(bounds.attributeValue("width"));
			int height = Integer.parseInt(bounds.attributeValue("height"));
			activity.setX(x);
			activity.setY(y);
			activity.setWidth(width);
			activity.setHeight(height);
			activities.add(activity);

			List lanes = pool.selectNodes("laneSet/lane");
			for (Iterator localIterator2 = lanes.iterator(); localIterator2.hasNext();) {
				Object lane = localIterator2.next();
				String laneId = ((Element) lane).attributeValue("id");
				ProcessDefinitionImpl laneProcessDefinition = new ProcessDefinitionImpl(laneId);
				ActivityImpl laneActivity = new ActivityImpl(laneId, laneProcessDefinition);
				Element laneShap = (Element) root.selectSingleNode("//bpmndi:BPMNShape[@bpmnElement='" + laneId + "']");
				Element laneBounds = (Element) laneShap.selectSingleNode("omgdc:Bounds");
				if (isHorizontal.equalsIgnoreCase("true"))
					properties.put("type", "HLane");
				else {
					properties.put("type", "VLane");
				}
				laneActivity.setProperties(properties);
				int laneX = Integer.parseInt(laneBounds.attributeValue("x"));
				int laneY = Integer.parseInt(laneBounds.attributeValue("y"));
				int laneWidth = Integer.parseInt(laneBounds.attributeValue("width"));
				int laneHeight = Integer.parseInt(laneBounds.attributeValue("height"));
				laneActivity.setX(laneX);
				laneActivity.setY(laneY);
				laneActivity.setWidth(laneWidth);
				laneActivity.setHeight(laneHeight);
				activities.add(laneActivity);
			}
		}

		return activities;
	}
}