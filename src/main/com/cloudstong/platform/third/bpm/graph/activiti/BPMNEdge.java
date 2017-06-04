package com.cloudstong.platform.third.bpm.graph.activiti;

import java.awt.geom.Point2D;
import java.util.List;

public class BPMNEdge
{
  private List<Point2D.Double> points;
  private String name;
  private Point2D.Double midpoint;
  private DirectionType direction;
  private String sourceRef;
  private String targetRef;

  public List<Point2D.Double> getPoints()
  {
    return points;
  }
  public void setPoints(List<Point2D.Double> points) {
    this.points = points;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Point2D.Double getMidpoint() {
    return midpoint;
  }
  public void setMidpoint(Point2D.Double midpoint) {
    this.midpoint = midpoint;
  }
  public DirectionType getDirection() {
    return direction;
  }
  public void setDirection(DirectionType direction) {
    this.direction = direction;
  }
  public String getSourceRef() {
    return sourceRef;
  }
  public void setSourceRef(String sourceRef) {
    this.sourceRef = sourceRef;
  }
  public String getTargetRef() {
    return targetRef;
  }
  public void setTargetRef(String targetRef) {
    this.targetRef = targetRef;
  }

  public String toString() {
    String str = "";
    for (Point2D.Double point : points) {
      str = str + point.getX() + ":" + point.getY() + "  ";
    }

    return "BPMNEdge [points=" + 
      str + ", name=" + name + " <midpoint=" + 
      midpoint.getX() + ":" + midpoint.getY() + ">]";
  }
}