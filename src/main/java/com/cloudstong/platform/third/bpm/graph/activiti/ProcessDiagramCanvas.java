package com.cloudstong.platform.third.bpm.graph.activiti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.util.ReflectUtil;

import com.cloudstong.platform.third.bpm.model.TaskOpinion;

public class ProcessDiagramCanvas
{
  protected static final Logger LOGGER = Logger.getLogger(ProcessDiagramCanvas.class.getName());
  protected static final int ARROW_WIDTH = 5;
  protected static final int CONDITIONAL_INDICATOR_WIDTH = 16;
  protected static final int MARKER_WIDTH = 12;
  protected static Color DEFAULT_COLOR = new Color(0, 0, 0);
  protected static Color START_COLOR = new Color(113, 146, 75);
  protected static Color END_COLOR = new Color(210, 112, 0);
  protected static Color POOL_BOUNDARY_COLOR = new Color(113, 184, 255);
  protected static Color POOL_BACKGROUP_COLOR = new Color(181, 218, 255);
  protected static Color LANE_BOUNDARY_COLOR = new Color(113, 184, 255);
  protected static Color LANE_BACKGROUP_COLOR = new Color(181, 218, 255);
  protected static Color TASK_COLOR = new Color(255, 255, 204);
  protected static Color EVENT_BOUNDARY_COLOR = new Color(255, 255, 255);
  protected static Color CONDITIONAL_INDICATOR_COLOR = new Color(255, 255, 
    255);

  protected static Color HIGHLIGHT_COLOR = Color.RED;

  protected static Stroke THIN_TASK_BORDER_STROKE = new BasicStroke(1.0F);
  protected static Stroke THICK_TASK_BORDER_STROKE = new BasicStroke(3.0F);
  protected static Stroke THICK2_TASK_BORDER_STROKE = new BasicStroke(2.0F);
  protected static Stroke GATEWAY_TYPE_STROKE = new BasicStroke(3.0F);
  protected static Stroke END_EVENT_STROKE = new BasicStroke(3.0F);
  protected static Stroke START_EVENT_STROKE = new BasicStroke(3.0F);
  protected static Stroke MULTI_INSTANCE_STROKE = new BasicStroke(1.3F);
  protected static Stroke LABEL_STROKE = new BasicStroke(1.0F);

  protected static int ICON_SIZE = 16;
  protected static Image USERTASK_IMAGE;
  protected static Image SCRIPTTASK_IMAGE;
  protected static Image SERVICETASK_IMAGE;
  protected static Image RECEIVETASK_IMAGE;
  protected static Image SENDTASK_IMAGE;
  protected static Image MANUALTASK_IMAGE;
  protected static Image TIMER_IMAGE;
  protected static Image ERROR_THROW_IMAGE;
  protected static Image ERROR_CATCH_IMAGE;
  protected static Image CALLACTIVITY_IMAGE;
  protected int canvasWidth = -1;
  protected int canvasHeight = -1;
  protected int minX = -1;
  protected int minY = -1;
  protected BufferedImage processDiagram;
  protected Graphics2D g;
  protected FontMetrics fontMetrics;
  protected boolean closed;
  protected static Map<Short, Color> colorsMap = new HashMap();

  static
  {
    try
    {
      USERTASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/user.png"));
      SCRIPTTASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/script.png"));
      SERVICETASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/service.png"));
      RECEIVETASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/receive.png"));
      SENDTASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/send.png"));
      MANUALTASK_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/manual.png"));
      TIMER_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/timer.png"));
      ERROR_THROW_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/error_throw.png"));
      ERROR_CATCH_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/error_catch.png"));
      CALLACTIVITY_IMAGE = 
        ImageIO.read(
        ReflectUtil.getResourceAsStream("com/cloudstong/platform/third/bpm/graph/image/call_activity.png"));

      colorsMap.put(TaskOpinion.STATUS_INIT, Color.darkGray);
      colorsMap.put(TaskOpinion.STATUS_AGREE, Color.green);
      colorsMap.put(TaskOpinion.STATUS_ABANDON, Color.orange);
      colorsMap.put(TaskOpinion.STATUS_CHECKING, Color.red);
      colorsMap.put(TaskOpinion.STATUS_REFUSE, Color.blue);
      colorsMap.put(TaskOpinion.STATUS_REJECT, new Color(138, 9, 2));
      colorsMap.put(TaskOpinion.STATUS_RECOVER, new Color(2, 59, 98));
      colorsMap.put(TaskOpinion.STATUS_PASSED, new Color(51, 136, 72));
      colorsMap.put(TaskOpinion.STATUS_NOT_PASSED, new Color(130, 183, 
        215));
    }
    catch (IOException e) {
      LOGGER.warning("Could not load image for process diagram creation: " + 
        e.getMessage());
    }
  }

  public ProcessDiagramCanvas(int width, int height)
  {
    canvasWidth = width;
    canvasHeight = height;
    processDiagram = new BufferedImage(width, height, 2);
    g = processDiagram.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
      RenderingHints.VALUE_ANTIALIAS_ON);
    g.setPaint(Color.black);

    Font font = new Font("宋体", 1, 12);
    g.setFont(font);
    fontMetrics = g.getFontMetrics();
  }

  public ProcessDiagramCanvas(int width, int height, int minX, int minY) {
    this(width, height);
    this.minX = minX;
    this.minY = minY;
  }

  public InputStream generateImage(String imageType) {
    if (closed) {
      throw new ActivitiException(
        "ProcessDiagramGenerator already closed");
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      minX = (minX <= 5 ? 5 : minX);
      minY = (minY <= 5 ? 5 : minY);
      BufferedImage imageToSerialize = processDiagram;
      if ((minX >= 0) && (minY >= 0)) {
        imageToSerialize = processDiagram.getSubimage(
          minX - 5, minY - 5, canvasWidth - 
          minX + 5, canvasHeight - minY + 
          5);
      }
      ImageIO.write(imageToSerialize, imageType, out);
    } catch (IOException e) {
      throw new ActivitiException("Error while generating process image", 
        e);
    } finally {
      IoUtil.closeSilently(out);
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  public void close() {
    g.dispose();
    closed = true;
  }

  public void drawNoneStartEvent(String name, int x, int y, int width, int height)
  {
    drawStartEvent(name, x, y, width, height, null);
  }

  public void drawTimerStartEvent(String name, int x, int y, int width, int height)
  {
    drawStartEvent(name, x, y, width, height, TIMER_IMAGE);
  }

  public void drawStartEvent(String name, int x, int y, int width, int height, Image image)
  {
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();
    g.setPaint(START_COLOR);
    g.setStroke(START_EVENT_STROKE);
    g.draw(new Ellipse2D.Double(x, y, width, height));
    if (image != null)
      g.drawImage(image, x, y, width, height, null);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawNoneEndEvent(String name, int x, int y, int width, int height)
  {
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();
    g.setPaint(END_COLOR);
    g.setStroke(END_EVENT_STROKE);

    g.draw(new Ellipse2D.Double(x, y, width, height));
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawErrorEndEvent(String name, int x, int y, int width, int height)
  {
    drawNoneEndEvent(name, x, y, width, height);
    g.drawImage(ERROR_THROW_IMAGE, x + 3, y + 3, width - 6, 
      height - 6, null);
  }

  public void drawCatchingEvent(int x, int y, int width, int height, Image image)
  {
    Ellipse2D outerCircle = new Ellipse2D.Double(x, y, width, height);
    int innerCircleX = x + 3;
    int innerCircleY = y + 3;
    int innerCircleWidth = width - 6;
    int innerCircleHeight = height - 6;
    Ellipse2D innerCircle = new Ellipse2D.Double(innerCircleX, 
      innerCircleY, innerCircleWidth, innerCircleHeight);

    Paint originalPaint = g.getPaint();
    g.setPaint(EVENT_BOUNDARY_COLOR);
    g.fill(outerCircle);

    g.setPaint(originalPaint);
    g.draw(outerCircle);
    g.draw(innerCircle);

    g.drawImage(image, innerCircleX, innerCircleY, innerCircleWidth, 
      innerCircleHeight, null);
  }

  public void drawCatchingTimerEvent(int x, int y, int width, int height) {
    drawCatchingEvent(x, y, width, height, TIMER_IMAGE);
  }

  public void drawCatchingErroEvent(int x, int y, int width, int height) {
    drawCatchingEvent(x, y, width, height, ERROR_CATCH_IMAGE);
  }

  public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY, boolean conditional)
  {
    Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
    g.draw(line);
    drawArrowHead(line);

    if (conditional)
      drawConditionalSequenceFlowIndicator(line);
  }

  public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX, int targetY, boolean conditional)
  {
    Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
    g.draw(line);

    if (conditional)
      drawConditionalSequenceFlowIndicator(line);
  }

  public void drawSequenceflow(List<Point2D.Double> points) {
    for (int i = 0; i < points.size() - 1; i++) {
      Line2D.Double line = new Line2D.Double(((Point2D.Double)points.get(i)).getX(), 
        ((Point2D.Double)points
        .get(i)).getY(), ((Point2D.Double)points.get(i + 1)).getX(), ((Point2D.Double)points.get(i + 1))
        .getY());
      g.draw(line);
      if (i == points.size() - 2)
        drawArrowHead(line);
    }
  }

  public void drawSequenceflowWidthLabel(BPMNEdge bpmnEdge)
  {
    drawSequenceflow(bpmnEdge.getPoints());
    int flag = 0;
    DirectionType directionType = bpmnEdge.getDirection();
    if (directionType == DirectionType.UpToDown)
      flag = 1;
    else if (directionType == DirectionType.DownToUp)
      flag = 2;
    else if (directionType == DirectionType.LeftToRight)
      flag = 3;
    else if (directionType == DirectionType.RightToLef) {
      flag = 4;
    }
    drawSequenceflowLabel(bpmnEdge.getName(), (int)bpmnEdge.getMidpoint().getX(), (int)bpmnEdge.getMidpoint().getY(), flag);
  }

  public void drawSequenceflowLabel(String name, int x, int y, int flag)
  {
    if (name == null) {
      return;
    }
    int drawX = x; int drawY = y;
    switch (flag) {
    case 1:
      drawX = x + g.getFontMetrics().getHeight() / 2;
      drawY = y;
      break;
    case 2:
      drawX = x - g.getFontMetrics().stringWidth(name) - 
        g.getFontMetrics().getHeight() / 2;
      drawY = y + g.getFontMetrics().getHeight();
      break;
    case 3:
      drawX = x - g.getFontMetrics().stringWidth(name) / 2;
      drawY = y - g.getFontMetrics().getHeight() / 2;
      break;
    case 4:
      drawX = x - g.getFontMetrics().stringWidth(name) / 2;
      drawY = y + g.getFontMetrics().getHeight();
    }

    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();
    g.setPaint(new Color(80, 160, 240));
    g.setStroke(LABEL_STROKE);
    g.drawString(name, drawX, drawY);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawArrowHead(Line2D.Double line) {
    int doubleArrowWidth = 10;
    Polygon arrowHead = new Polygon();
    arrowHead.addPoint(0, 0);
    arrowHead.addPoint(-5, -doubleArrowWidth);
    arrowHead.addPoint(5, -doubleArrowWidth);

    AffineTransform transformation = new AffineTransform();
    transformation.setToIdentity();
    double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
    transformation.translate(line.x2, line.y2);
    transformation.rotate(angle - 1.570796326794897D);

    AffineTransform originalTransformation = g.getTransform();
    g.setTransform(transformation);
    g.fill(arrowHead);
    g.setTransform(originalTransformation);
  }

  public void drawConditionalSequenceFlowIndicator(Line2D.Double line) {
    int horizontal = 11;
    int halfOfHorizontal = horizontal / 2;
    int halfOfVertical = 8;

    Polygon conditionalIndicator = new Polygon();
    conditionalIndicator.addPoint(0, 0);
    conditionalIndicator.addPoint(-halfOfHorizontal, halfOfVertical);
    conditionalIndicator.addPoint(0, 16);
    conditionalIndicator.addPoint(halfOfHorizontal, halfOfVertical);

    AffineTransform transformation = new AffineTransform();
    transformation.setToIdentity();
    double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
    transformation.translate(line.x1, line.y1);
    transformation.rotate(angle - 1.570796326794897D);

    AffineTransform originalTransformation = g.getTransform();
    g.setTransform(transformation);
    g.draw(conditionalIndicator);

    Paint originalPaint = g.getPaint();
    g.setPaint(CONDITIONAL_INDICATOR_COLOR);
    g.fill(conditionalIndicator);

    g.setPaint(originalPaint);
    g.setTransform(originalTransformation);
  }

  public void drawTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height, false);
  }

  protected void drawTask(String name, int x, int y, int width, int height, boolean thickBorder)
  {
    Paint originalPaint = g.getPaint();
    g.setPaint(TASK_COLOR);

    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, 
      height, 20.0D, 20.0D);
    g.fill(rect);
    g.setPaint(originalPaint);

    if (thickBorder) {
      Stroke originalStroke = g.getStroke();
      g.setStroke(THICK_TASK_BORDER_STROKE);
      g.draw(rect);
      g.setStroke(originalStroke);
    } else {
      g.draw(rect);
    }

    if (name != null) {
      String text = fitTextToWidth(name, width);
      int textX = x + (width - fontMetrics.stringWidth(text)) / 2;
      int textY = y + (height - fontMetrics.getHeight()) / 2 + 
        fontMetrics.getHeight();
      g.drawString(text, textX, textY);
    }
  }

  protected String fitTextToWidth(String original, int width) {
    String text = original;

    int maxWidth = width - 10;

    while ((fontMetrics.stringWidth(text + "...") > maxWidth) && 
      (text.length() > 0)) {
      text = text.substring(0, text.length() - 1);
    }

    if (!text.equals(original)) {
      text = text + "...";
    }

    return text;
  }

  public void drawUserTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(USERTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawScriptTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(SCRIPTTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawServiceTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(SERVICETASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawReceiveTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(RECEIVETASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawSendTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(SENDTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawManualTask(String name, int x, int y, int width, int height) {
    drawTask(name, x, y, width, height);
    g.drawImage(MANUALTASK_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  public void drawExpandedSubProcess(String name, int x, int y, int width, int height)
  {
    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, 
      height, 20.0D, 20.0D);
    g.draw(rect);

    String text = fitTextToWidth(name, width);
    g.drawString(text, x + 10, y + 15);
  }

  public void drawCollapsedSubProcess(String name, int x, int y, int width, int height)
  {
    drawCollapsedTask(name, x, y, width, height, false);
  }

  public void drawCollapsedCallActivity(String name, int x, int y, int width, int height)
  {
    drawCollapsedTask(name, x, y, width, height, false);
    g.drawImage(CALLACTIVITY_IMAGE, x + 7, y + 7, ICON_SIZE, ICON_SIZE, 
      null);
  }

  protected void drawCollapsedTask(String name, int x, int y, int width, int height, boolean thickBorder)
  {
    drawTask(name, x, y, width, height, thickBorder);
  }

  public void drawCollapsedMarker(int x, int y, int width, int height) {
    int rectangleWidth = 12;
    int rectangleHeight = 12;
    Rectangle rect = new Rectangle(x + (width - rectangleWidth) / 2, y + 
      height - rectangleHeight - 3, rectangleWidth, rectangleHeight);
    g.draw(rect);

    Line2D.Double line = new Line2D.Double(rect.getCenterX(), 
      rect.getY() + 2.0D, rect.getCenterX(), rect.getMaxY() - 2.0D);
    g.draw(line);
    line = new Line2D.Double(rect.getMinX() + 2.0D, rect.getCenterY(), 
      rect.getMaxX() - 2.0D, rect.getCenterY());
    g.draw(line);
  }

  public void drawActivityMarkers(int x, int y, int width, int height, boolean multiInstanceSequential, boolean multiInstanceParallel, boolean collapsed)
  {
    if (collapsed) {
      if ((!multiInstanceSequential) && (!multiInstanceParallel)) {
        drawCollapsedMarker(x, y, width, height);
      } else {
        drawCollapsedMarker(x - 6 - 2, y, width, height);
        if (multiInstanceSequential)
          drawMultiInstanceMarker(true, x + 6 + 2, y, width, height);
        else if (multiInstanceParallel)
          drawMultiInstanceMarker(false, x + 6 + 2, y, width, height);
      }
    }
    else if (multiInstanceSequential)
      drawMultiInstanceMarker(false, x, y, width, height);
    else if (multiInstanceParallel)
      drawMultiInstanceMarker(true, x, y, width, height);
  }

  public void drawGateway(String name, int x, int y, int width, int height) {
    Polygon rhombus = new Polygon();
    rhombus.addPoint(x, y + height / 2);
    rhombus.addPoint(x + width / 2, y + height);
    rhombus.addPoint(x + width, y + height / 2);
    rhombus.addPoint(x + width / 2, y);
    g.draw(rhombus);

    if (name != null) {
      int textX = x + width / 4 - fontMetrics.stringWidth(name) - 
        fontMetrics.getHeight() / 4;
      int textY = y + height / 4 - fontMetrics.getHeight() / 4;
      g.drawString(name, textX, textY);
    }
  }

  public void drawParallelGateway(String name, int x, int y, int width, int height)
  {
    drawGateway(name, x, y, width, height);
    Stroke orginalStroke = g.getStroke();
    g.setStroke(GATEWAY_TYPE_STROKE);
    Line2D.Double line = new Line2D.Double(x + 10, y + height / 2, x + 
      width - 10, y + height / 2);
    g.draw(line);
    line = new Line2D.Double(x + width / 2, y + height - 10, x + width / 2, 
      y + 10);
    g.draw(line);
    g.setStroke(orginalStroke);
  }

  public void drawExclusiveGateway(String name, int x, int y, int width, int height)
  {
    drawGateway(name, x, y, width, height);

    int quarterWidth = width / 4;
    int quarterHeight = height / 4;

    Stroke orginalStroke = g.getStroke();
    g.setStroke(GATEWAY_TYPE_STROKE);
    Line2D.Double line = new Line2D.Double(x + quarterWidth + 3, y + 
      quarterHeight + 3, x + 3 * quarterWidth - 3, y + 3 * 
      quarterHeight - 3);
    g.draw(line);
    line = new Line2D.Double(x + quarterWidth + 3, y + 3 * quarterHeight - 
      3, x + 3 * quarterWidth - 3, y + quarterHeight + 3);
    g.draw(line);

    g.setStroke(orginalStroke);
  }

  public void drawInclusiveGateway(String name, int x, int y, int width, int height)
  {
    drawGateway(name, x, y, width, height);

    int diameter = width / 2;

    Stroke orginalStroke = g.getStroke();
    g.setStroke(GATEWAY_TYPE_STROKE);
    Ellipse2D.Double circle = new Ellipse2D.Double((width - diameter) / 2 + 
      x, (height - diameter) / 2 + y, diameter, diameter);
    g.draw(circle);
    g.setStroke(orginalStroke);
  }

  public void drawMultiInstanceMarker(boolean sequential, int x, int y, int width, int height)
  {
    int rectangleWidth = 12;
    int rectangleHeight = 12;
    int lineX = x + (width - rectangleWidth) / 2;
    int lineY = y + height - rectangleHeight - 3;

    Stroke orginalStroke = g.getStroke();
    g.setStroke(MULTI_INSTANCE_STROKE);

    if (sequential) {
      g.draw(new Line2D.Double(lineX, lineY, lineX, lineY + 
        rectangleHeight));
      g.draw(new Line2D.Double(lineX + rectangleWidth / 2, lineY, 
        lineX + rectangleWidth / 2, lineY + rectangleHeight));
      g.draw(new Line2D.Double(lineX + rectangleWidth, lineY, lineX + 
        rectangleWidth, lineY + rectangleHeight));
    } else {
      g.draw(new Line2D.Double(lineX, lineY, lineX + rectangleWidth, 
        lineY));
      g.draw(new Line2D.Double(lineX, lineY + rectangleHeight / 2, 
        lineX + rectangleWidth, lineY + rectangleHeight / 2));
      g.draw(new Line2D.Double(lineX, lineY + rectangleHeight, lineX + 
        rectangleWidth, lineY + rectangleHeight));
    }

    g.setStroke(orginalStroke);
  }

  public void drawHighLight(int x, int y, int width, int height) {
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();

    g.setPaint(HIGHLIGHT_COLOR);
    g.setStroke(THICK_TASK_BORDER_STROKE);

    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, 
      height, 20.0D, 20.0D);
    g.draw(rect);

    g.setPaint(originalPaint);
    g.setStroke(originalStroke);
  }

  public void drawHighLight(int x, int y, int width, int height, Short status) {
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();
    Color color = (Color)colorsMap.get(status);
    g.setPaint(color);
    if (status != TaskOpinion.STATUS_INIT)
      g.setStroke(THICK_TASK_BORDER_STROKE);
    else {
      g.setStroke(THIN_TASK_BORDER_STROKE);
    }

    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, 
      height, 20.0D, 20.0D);
    g.draw(rect);

    g.setPaint(originalPaint);
    g.setStroke(originalStroke);
  }

  public void drawHighLight(int x, int y, int width, int height, String colorStr)
  {
    boolean isNormal = false;
    if (colorStr == null) {
      colorStr = "#000000";
      isNormal = true;
    }
    Color color = Color.decode(colorStr);
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();

    g.setPaint(color);
    if (isNormal)
      g.setStroke(THICK2_TASK_BORDER_STROKE);
    else {
      g.setStroke(THICK_TASK_BORDER_STROKE);
    }

    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20.0D, 20.0D);
    g.draw(rect);

    g.setPaint(originalPaint);
    g.setStroke(originalStroke);
  }

  public void drawHPool(String name, int x, int y, int width, int height) {
    Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
    Paint originalPaint = g.getPaint();

    g.setPaint(POOL_BACKGROUP_COLOR);
    RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(x, y, 20.0D, height, 0.0D, 0.0D);
    g.fill(roundRectangle2D);

    Stroke originalStroke = g.getStroke();
    g.setPaint(POOL_BOUNDARY_COLOR);
    g.draw(rect);

    int textLen = fontMetrics.stringWidth(name);
    int textX = x + fontMetrics.getHeight() / 2;
    int textY = y + height / 2 - textLen / 2;

    AffineTransform oldAffineTransform = g.getTransform();
    AffineTransform newAffineTransform = AffineTransform.getRotateInstance(
      -1.570796326794897D, textX, textY);
    g.setTransform(newAffineTransform);
    g.setPaint(DEFAULT_COLOR);
    g.drawString(name, textX - textLen, textY + fontMetrics.getHeight() / 2);
    g.setTransform(oldAffineTransform);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawHLane(String name, int x, int y, int width, int height)
  {
    Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
    Paint originalPaint = g.getPaint();

    g.setPaint(LANE_BACKGROUP_COLOR);
    RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(x, y, 20.0D, height, 0.0D, 0.0D);
    g.fill(roundRectangle2D);

    Stroke originalStroke = g.getStroke();
    g.setPaint(LANE_BOUNDARY_COLOR);
    g.draw(rect);

    int textLen = fontMetrics.stringWidth(name);
    int textX = x + fontMetrics.getHeight() / 2;
    int textY = y + height / 2 - textLen / 2;

    AffineTransform oldAffineTransform = g.getTransform();
    AffineTransform newAffineTransform = AffineTransform.getRotateInstance(
      -1.570796326794897D, textX, textY);

    g.setTransform(newAffineTransform);
    g.setPaint(DEFAULT_COLOR);
    g.drawString(name, textX - textLen, textY + fontMetrics.getHeight() / 2);
    g.setTransform(oldAffineTransform);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawVPool(String name, int x, int y, int width, int height)
  {
    Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();

    g.setPaint(POOL_BACKGROUP_COLOR);
    RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(x, y, width, 20.0D, 0.0D, 0.0D);
    g.fill(roundRectangle2D);

    g.setPaint(POOL_BOUNDARY_COLOR);
    g.draw(rect);

    name = fitTextToWidth(name, width);
    int textLen = fontMetrics.stringWidth(name);
    int textY = y + fontMetrics.getHeight();
    int textX = x + width / 2 - textLen / 2;
    g.setPaint(DEFAULT_COLOR);
    g.drawString(name, textX, textY);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }

  public void drawVLane(String name, int x, int y, int width, int height)
  {
    Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();

    g.setPaint(LANE_BACKGROUP_COLOR);
    RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(x, y, width, 20.0D, 0.0D, 0.0D);
    g.fill(roundRectangle2D);

    g.setPaint(LANE_BOUNDARY_COLOR);
    g.draw(rect);

    name = fitTextToWidth(name, width);
    int textLen = fontMetrics.stringWidth(name);
    int textY = y + fontMetrics.getHeight();
    int textX = x + width / 2 - textLen / 2;
    g.setPaint(DEFAULT_COLOR);
    g.drawString(name, textX, textY);
    g.setStroke(originalStroke);
    g.setPaint(originalPaint);
  }
}