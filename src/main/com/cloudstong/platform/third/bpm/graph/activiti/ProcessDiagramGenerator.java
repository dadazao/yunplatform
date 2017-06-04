package com.cloudstong.platform.third.bpm.graph.activiti;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.third.bpm.model.BpmProStatus;

public class ProcessDiagramGenerator {
	protected static final Map<BPMNShapType, GraphDrawInstruction> graphDrawInstructions = new HashMap();

	static {
		graphDrawInstructions.put(BPMNShapType.StartEvent, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawNoneStartEvent(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ErrorEvent, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawErrorEndEvent(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.EndEvent, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawNoneEndEvent(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.CancelEvent, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawErrorEndEvent(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.Task, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.UserTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawUserTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ScriptTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawScriptTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ServiceTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawServiceTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ReceiveTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawReceiveTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.SendTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawSendTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ManualTask, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawManualTask(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ExclusiveGateway, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawExclusiveGateway(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.InclusiveGateway, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawInclusiveGateway(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.ParallelGateway, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawParallelGateway(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.SubProcess, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				if ((shap.isExpanded() != null) && (!shap.isExpanded().booleanValue()))
					processDiagramCanvas.drawCollapsedSubProcess(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
							(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
				else
					processDiagramCanvas.drawExpandedSubProcess(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
							(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.CallActivity, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawCollapsedCallActivity(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.HPool, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawHPool(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.HLane, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawHLane(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.VPool, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawVPool(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
		graphDrawInstructions.put(BPMNShapType.VLane, new GraphDrawInstruction() {
			public void draw(ProcessDiagramCanvas processDiagramCanvas, BPMNShap shap) {
				processDiagramCanvas.drawVLane(shap.getName(), (int) Math.round(shap.getX()), (int) Math.round(shap.getY()),
						(int) Math.round(shap.getWidth()), (int) Math.round(shap.getHeight()));
			}
		});
	}

	public static ProcessDiagramCanvas initProcessDiagramCanvas(List<BPMNShap> shaps, List<BPMNEdge> edges) {
		Point2D.Double[] points = caculateCanvasSize(shaps, edges);
		double shiftX = points[0].getX() < 0.0D ? points[0].getX() : 0.0D;
		double shiftY = points[0].getY() < 0.0D ? points[0].getY() : 0.0D;
		shiftProcessDefinition(shaps, edges, shiftX, shiftY);

		int width = (int) Math.round(points[1].getX() + 10.0D - shiftX);
		int height = (int) Math.round(points[1].getY() + 10.0D - shiftY);
		int minX = (int) Math.round(points[0].getX() - shiftX);
		int minY = (int) Math.round(points[0].getY() - shiftY);
		return new ProcessDiagramCanvas(width, height, minX, minY);
	}

	public static InputStream generatePngDiagram(String bpmnXml) {
		return generateDiagram(bpmnXml, "png", (List) Collections.emptyList());
	}

	public static InputStream generateJpgDiagram(String bpmnXml) {
		return generateDiagram(bpmnXml, "jpg", (List) Collections.emptyList());
	}

	public static ProcessDiagramCanvas generateDiagram(String bpmnXml, List<String> highLightedActivities) {
		List<BPMNShap> bpmnShaps = extractBPMNShap(bpmnXml);
		List bpmnEdges = extractBPMNEdge(bpmnXml);
		ProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnShaps, bpmnEdges);
		for (BPMNShap bpmnShap : bpmnShaps) {
			drawActivity(processDiagramCanvas, bpmnShap, highLightedActivities);
		}
		drawSequenceFlows(processDiagramCanvas, bpmnEdges);
		return processDiagramCanvas;
	}

	public static ProcessDiagramCanvas generateDiagram(Map<String, String> highLightedActivities, String bpmnXml) {
		List bpmnEdges = extractBPMNEdge(bpmnXml);
		List<BPMNShap> bpmnShaps = extractBPMNShap(bpmnXml);
		ProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnShaps, bpmnEdges);
		for (BPMNShap bpmnShap : bpmnShaps) {
			drawActivity(processDiagramCanvas, highLightedActivities, bpmnShap);
		}
		drawSequenceFlows(processDiagramCanvas, bpmnEdges);
		return processDiagramCanvas;
	}

	public static InputStream generateDiagram(String bpmnXml, String imageType, List<String> highLightedActivities) {
		return generateDiagram(bpmnXml, highLightedActivities).generateImage(imageType);
	}

	public static InputStream generateDiagram(String bpmnXml, Map<String, String> highLightedActivities, String imageType) {
		return generateDiagram(highLightedActivities, bpmnXml).generateImage(imageType);
	}

	public static void drawActivity(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap, List<String> highLightedActivities) {
		GraphDrawInstruction drawInstruction = (GraphDrawInstruction) graphDrawInstructions.get(bpmnShap.getType());
		if (drawInstruction != null) {
			drawInstruction.draw(processDiagramCanvas, bpmnShap);

			boolean multiInstanceSequential = false;
			boolean multiInstanceParallel = false;
			boolean collapsed = false;
			Properties properties = bpmnShap.getProperties();
			String isSequential = null;
			if (properties != null) {
				isSequential = (String) bpmnShap.getProperties().get("isSequential");
			}

			if (isSequential != null) {
				if ("true".equals(isSequential))
					multiInstanceSequential = true;
				else {
					multiInstanceParallel = true;
				}

			}

			Boolean expanded = bpmnShap.isExpanded();
			if (expanded != null) {
				collapsed = !expanded.booleanValue();
			}

			processDiagramCanvas.drawActivityMarkers((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
					(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), multiInstanceSequential, multiInstanceParallel,
					collapsed);

			if (highLightedActivities.contains(bpmnShap.getBpmnElement()))
				drawHighLight(processDiagramCanvas, bpmnShap);
		}
	}

	public static void drawActivity(ProcessDiagramCanvas processDiagramCanvas, Map<String, String> highLightedActivities, BPMNShap bpmnShap) {
		GraphDrawInstruction drawInstruction = (GraphDrawInstruction) graphDrawInstructions.get(bpmnShap.getType());
		if (drawInstruction != null) {
			drawInstruction.draw(processDiagramCanvas, bpmnShap);

			boolean multiInstanceSequential = false;
			boolean multiInstanceParallel = false;
			boolean collapsed = false;
			Properties properties = bpmnShap.getProperties();
			String isSequential = null;
			if (properties != null) {
				isSequential = (String) bpmnShap.getProperties().get("isSequential");
			}
			if (isSequential != null) {
				if ("true".equals(isSequential))
					multiInstanceSequential = true;
				else {
					multiInstanceParallel = true;
				}

			}

			Boolean expanded = bpmnShap.isExpanded();
			if (expanded != null) {
				collapsed = !expanded.booleanValue();
			}

			processDiagramCanvas.drawActivityMarkers((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
					(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), multiInstanceSequential, multiInstanceParallel,
					collapsed);

			if (highLightedActivities.containsKey(bpmnShap.getBpmnElement())) {
				String color = (String) highLightedActivities.get(bpmnShap.getBpmnElement());

				drawHighLight(processDiagramCanvas, bpmnShap, color);
			}
		}
	}

	public static void drawSequenceFlows(ProcessDiagramCanvas processDiagramCanvas, List<BPMNEdge> bpmnEdges) {
		for (BPMNEdge bpmnEdge : bpmnEdges)
			processDiagramCanvas.drawSequenceflowWidthLabel(bpmnEdge);
	}

	private static Map<String, Short> getHighLightedMap(List<BpmProStatus> highLightedActivities) {
		Map map = new HashMap();
		for (BpmProStatus status : highLightedActivities) {
			map.put(status.getNodeid(), status.getStatus());
		}
		return map;
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()));
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap, String color) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), color);
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap, Short status) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), status);
	}

	private static FontMetrics getFontMetrics() {
		BufferedImage processDiagram = new BufferedImage(2, 2, 2);
		Graphics2D g = processDiagram.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(Color.black);
		Font font = new Font("宋体", 1, 12);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		return fontMetrics;
	}

	public static Point2D.Double[] caculateCanvasSize(List<BPMNShap> shaps, List<BPMNEdge> edges) {
		double minX = 1.7976931348623157E+308D;
		double minY = 1.7976931348623157E+308D;
		double maxX = 0.0D;
		double maxY = 0.0D;

		for (BPMNShap shap : shaps) {
			if (shap.getX() < minX) {
				minX = shap.getX();
			}
			if (shap.getY() < minY) {
				minY = shap.getY();
			}

			if (shap.getX() + shap.getWidth() > maxX) {
				maxX = shap.getX() + shap.getWidth();
			}

			if (shap.getY() + shap.getHeight() > maxY) {
				maxY = shap.getY() + shap.getHeight();
			}
		}

		for (BPMNEdge edge : edges) {
			for (Point2D.Double point : edge.getPoints()) {
				if (point.getX() < minX) {
					minX = point.getX();
				}
				if (point.getY() < minY) {
					minY = point.getY();
				}

				if (point.getX() > maxX) {
					maxX = point.getX();
				}

				if (point.getY() > maxY) {
					maxY = point.getY();
				}

			}

			String label = edge.getName() == null ? "" : edge.getName();
			Point2D.Double midPoint = edge.getMidpoint();
			DirectionType directionType = edge.getDirection();
			FontMetrics fontMetrics = getFontMetrics();
			double labelMinY;
			double labelMinX;
			if (directionType == DirectionType.UpToDown) {
				labelMinX = midPoint.getX() + fontMetrics.getHeight() / 2;
				labelMinY = midPoint.getY();
			} else {
				if (directionType == DirectionType.DownToUp) {
					labelMinX = midPoint.getX() - fontMetrics.stringWidth(label) - fontMetrics.getHeight() / 2;
					labelMinY = midPoint.getY() - fontMetrics.getHeight() / 2 - fontMetrics.getHeight();
				} else {
					if (directionType == DirectionType.LeftToRight) {
						labelMinX = midPoint.getX() - fontMetrics.stringWidth(label) / 2;
						labelMinY = midPoint.getY();
					} else {
						labelMinX = fontMetrics.stringWidth(label) / 2;
						labelMinY = midPoint.getY() + fontMetrics.getHeight() - fontMetrics.getHeight();
					}
				}
			}
			double labelMaxX = labelMinX + fontMetrics.stringWidth(label);
			double labelMaxY = labelMinY + fontMetrics.getHeight();

			if (labelMinX < minX) {
				minX = labelMinX;
			}
			if (labelMinY < minY) {
				minY = labelMinY;
			}
			if (labelMaxX > maxX) {
				maxX = labelMaxX;
			}
			if (labelMaxY > maxY) {
				maxY = labelMaxY;
			}

		}

		return new Point2D.Double[] { new Point2D.Double(minX, minY), new Point2D.Double(maxX, maxY) };
	}

	private static void shiftProcessDefinition(List<BPMNShap> shaps, List<BPMNEdge> edges, double x, double y) {
		for (BPMNShap shap : shaps) {
			shap.setX(shap.getX() - x);
			shap.setY(shap.getY() - y);
		}
		for (BPMNEdge edge : edges) {
			for (Point2D.Double point : edge.getPoints()) {
				point.x = (point.getX() - x);
				point.y = (point.getY() - y);
			}
			edge.getMidpoint().x = (edge.getMidpoint().getX() - x);
			edge.getMidpoint().y = (edge.getMidpoint().getY() - y);
		}
	}

	public static List<BPMNShap> extractBPMNShap(String bpmnXml) {
		bpmnXml = bpmnXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(bpmnXml);
		Element root = doc.getRootElement();

		List<Element> shaps = root.selectNodes("//bpmndi:BPMNShape");
		List bpmnShaps = new ArrayList();
		for (Element shap : shaps) {
			BPMNShap bpmnShap = new BPMNShap();

			bpmnShap.setBpmnElement(shap.attributeValue("bpmnElement"));
			bpmnShap.setChoreographyActivityShape(shap.attributeValue("choreographyActivityShape"));
			bpmnShap.setHorizontal(getBooleanAttr(shap, "isHorizontal"));
			bpmnShap.setExpanded(getBooleanAttr(shap, "isExpanded"));
			bpmnShap.setMarkerVisible(getBooleanAttr(shap, "isMarkerVisible"));
			bpmnShap.setMessageVisible(getBooleanAttr(shap, "isMessageVisible"));
			bpmnShap.setParticipantBandKind(shap.attributeValue("participantBandKind"));

			Element bound = (Element) shap.selectSingleNode("./omgdc:Bounds");
			bpmnShap.setX(java.lang.Double.parseDouble(bound.attributeValue("x")));
			bpmnShap.setY(java.lang.Double.parseDouble(bound.attributeValue("y")));
			bpmnShap.setWidth(java.lang.Double.parseDouble(bound.attributeValue("width")));
			bpmnShap.setHeight(java.lang.Double.parseDouble(bound.attributeValue("height")));

			Element component = (Element) root.selectSingleNode("//*[@id='" + bpmnShap.getBpmnElement() + "']");
			if (component != null) {
				BPMNShapType type = getBPMNShapType(component);

				bpmnShap.setType(type);

				bpmnShap.setName(component.attributeValue("name"));

				setBPMNShapProperties(component, bpmnShap);
				bpmnShaps.add(bpmnShap);
			}
		}
		return bpmnShaps;
	}

	public static List<BPMNEdge> extractBPMNEdge(String bpmnXml) {
		List bpmnEdges = new ArrayList();

		bpmnXml = bpmnXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(bpmnXml);
		Element root = doc.getRootElement();

		List<Element> edges = root.selectNodes("//bpmndi:BPMNEdge");
		for (Element edge : edges) {
			BPMNEdge bpmnEdge = new BPMNEdge();

			DirectionType directionType = null;
			List points = new ArrayList();
			List<Element> waypoints = edge.selectNodes("./omgdi:waypoint");
			for (Element waypoint : waypoints) {
				double x = java.lang.Double.parseDouble(waypoint.attributeValue("x"));
				double y = java.lang.Double.parseDouble(waypoint.attributeValue("y"));
				Point2D.Double point = new Point2D.Double(x, y);
				points.add(point);
			}
			bpmnEdge.setPoints(points);

			String bpmnElement = edge.attributeValue("bpmnElement");
			Element component = (Element) root.selectSingleNode("//sequenceFlow[@id='" + bpmnElement + "']");
			bpmnEdge.setName(component.attributeValue("name"));

			double x = 0.0D;
			double y = 0.0D;

			List<java.lang.Double> lens = new ArrayList<java.lang.Double>();
			for (int i = 1; i < points.size(); i++) {
				lens.add(java.lang.Double.valueOf(Math.abs(((Point2D.Double)points.get(i - 1)).getX() - ((Point2D.Double)points.get(i)).getX()) + Math.abs(((Point2D.Double)points.get(i - 1)).getY() - ((Point2D.Double)points.get(i)).getY())));
				double halfLen = 0.0D;
				for (java.lang.Double len : lens) {
					halfLen += len;
				}
				halfLen /= 2.0D;

				double accumulativeLen = 0.0D;

				for (int j = 0; j < lens.size(); j++) {
					accumulativeLen += ((java.lang.Double) lens.get(j)).doubleValue();
					if (accumulativeLen > halfLen) {
						break;
					}
				}

				if (((Point2D.Double) points.get(i-1)).getX() == ((Point2D.Double) points.get(i )).getX()) {
					if (((Point2D.Double) points.get(i-1)).getY() < ((Point2D.Double) points.get(i )).getY()) {
						y = halfLen - accumulativeLen + ((Point2D.Double) points.get(i )).getY();
						directionType = DirectionType.UpToDown;
					} else {
						y = accumulativeLen - halfLen + ((Point2D.Double) points.get(i )).getY();
						directionType = DirectionType.DownToUp;
					}
					x = ((Point2D.Double) points.get(i-1)).getX();
				} else {
					if (((Point2D.Double) points.get(i-1)).getX() < ((Point2D.Double) points.get(i )).getX()) {
						x = halfLen - accumulativeLen + ((Point2D.Double) points.get(i )).getX();
						directionType = DirectionType.LeftToRight;
					} else {
						x = accumulativeLen - halfLen + ((Point2D.Double) points.get(i )).getX();
						directionType = DirectionType.RightToLef;
					}
					y = ((Point2D.Double) points.get(i-1)).getY();
				}
			}
			Point2D.Double midpoint = new Point2D.Double(x, y);

			bpmnEdge.setMidpoint(midpoint);

			bpmnEdge.setDirection(directionType);

			bpmnEdges.add(bpmnEdge);
		}

		return bpmnEdges;
	}

	private static BPMNShap setBPMNShapProperties(Element component, BPMNShap bpmnShap) {
		BPMNShapType type = bpmnShap.getType();
		Properties properties = bpmnShap.getProperties();
		if (properties == null) {
			properties = new Properties();
		}

		if ((type == BPMNShapType.Task) || (type == BPMNShapType.ScriptTask) || (type == BPMNShapType.ServiceTask)
				|| (type == BPMNShapType.BusinessRuleTask) || (type == BPMNShapType.ManualTask) || (type == BPMNShapType.UserTask)
				|| (type == BPMNShapType.CallActivity) || (type == BPMNShapType.SubProcess)) {
			Element multiInstanceLoopCharacteristics = (Element) component.selectSingleNode("./multiInstanceLoopCharacteristics");
			if (multiInstanceLoopCharacteristics != null) {
				String isSequential = multiInstanceLoopCharacteristics.attributeValue("isSequential");
				properties.put("isSequential", isSequential);
			}
		}

		if (type == BPMNShapType.ErrorEvent) {
			Element errorEventDefinition = (Element) component.selectSingleNode("errorEventDefinition");
			String errorRef = errorEventDefinition.attributeValue("errorRef");
			properties.put("errorRef", errorRef);
		}

		bpmnShap.setProperties(properties);
		return bpmnShap;
	}

	private static Boolean getBooleanAttr(Element element, String attr) {
		String attrVal = element.attributeValue(attr);
		if (attrVal != null) {
			return Boolean.valueOf(attrVal.equalsIgnoreCase("true"));
		}
		return null;
	}

	public static BPMNShapType getBPMNShapType(Element component) {
		BPMNShapType retVal = BPMNShapType.UnknowType;

		if (component.getName().equals("startEvent")) {
			retVal = BPMNShapType.StartEvent;
		} else if (component.getName().equals("endEvent")) {
			Element errorEventDefinition = (Element) component.selectSingleNode("errorEventDefinition");
			if (errorEventDefinition == null)
				retVal = BPMNShapType.EndEvent;
			else
				retVal = BPMNShapType.ErrorEvent;
		} else if (component.getName().equals("exclusiveGateway")) {
			retVal = BPMNShapType.ExclusiveGateway;
		} else if (component.getName().equals("inclusiveGateway")) {
			retVal = BPMNShapType.InclusiveGateway;
		} else if (component.getName().equals("parallelGateway")) {
			retVal = BPMNShapType.ParallelGateway;
		} else if (component.getName().equals("scriptTask")) {
			retVal = BPMNShapType.ScriptTask;
		} else if (component.getName().equals("serviceTask")) {
			retVal = BPMNShapType.ServiceTask;
		} else if (component.getName().equals("businessRuleTask")) {
			retVal = BPMNShapType.BusinessRuleTask;
		} else if (component.getName().equals("task")) {
			retVal = BPMNShapType.Task;
		} else if (component.getName().equals("manualTask")) {
			retVal = BPMNShapType.ManualTask;
		} else if (component.getName().equals("userTask")) {
			retVal = BPMNShapType.UserTask;
		} else if (component.getName().equals("sendTask")) {
			retVal = BPMNShapType.SendTask;
		} else if (component.getName().equals("receiveTask")) {
			retVal = BPMNShapType.ReceiveTask;
		} else if (component.getName().equals("subProcess")) {
			retVal = BPMNShapType.SubProcess;
		} else if (component.getName().equals("callActivity")) {
			retVal = BPMNShapType.CallActivity;
		} else if (component.getName().equals("intermediateCatchEvent")) {
			retVal = BPMNShapType.IntermediateCatchEvent;
		} else if (component.getName().equals("adHocSubProcess")) {
			retVal = BPMNShapType.ComplexGateway;
		} else if (component.getName().equals("eventBasedGateway")) {
			retVal = BPMNShapType.EventBasedGateway;
		} else if (component.getName().equals("transaction")) {
			retVal = BPMNShapType.Transaction;
		} else if (component.getName().equals("participant")) {
			String id = component.attributeValue("id");
			String processRef = component.attributeValue("processRef");
			Element root = component.getDocument().getRootElement();
			Element process = (Element) root.selectSingleNode("//*[@id='" + processRef + "']");
			if (process.element("laneSet") != null) {
				Element shap = (Element) root.selectSingleNode("//*[@bpmnElement='" + id + "']");
				String isHorizontal = shap.attributeValue("isHorizontal");
				if ((isHorizontal != null) && (isHorizontal.equalsIgnoreCase("false")))
					retVal = BPMNShapType.VPool;
				else
					retVal = BPMNShapType.HPool;
			}
		} else if (component.getName().equals("lane")) {
			String id = component.attributeValue("id");
			Element root = component.getDocument().getRootElement();
			Element shap = (Element) root.selectSingleNode("//*[@bpmnElement='" + id + "']");
			String isHorizontal = shap.attributeValue("isHorizontal");
			if ((isHorizontal != null) && (isHorizontal.equalsIgnoreCase("false")))
				retVal = BPMNShapType.VLane;
			else {
				retVal = BPMNShapType.HLane;
			}
		}
		return retVal;
	}
}