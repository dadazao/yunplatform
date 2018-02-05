package com.cloudstong.platform.third.bpm.graph;

import java.util.List;

public class Link
{
  private ShapeType shapeType;
  private Shape startNode;
  private Shape endNode;
  private Port startPort;
  private Port endPort;
  private Point fallbackStartPoint;
  private Point fallbackEndPoint;
  private List<Point> intermediatePoints;

  public ShapeType getShapeType()
  {
    return shapeType;
  }
  public void setShapeType(ShapeType shapeType) {
    this.shapeType = shapeType;
  }
  public Shape getStartNode() {
    return startNode;
  }
  public void setStartNode(Shape startNode) {
    this.startNode = startNode;
  }
  public Shape getEndNode() {
    return endNode;
  }
  public void setEndNode(Shape endNode) {
    this.endNode = endNode;
  }
  public Port getStartPort() {
    return startPort;
  }
  public void setStartPort(Port startPort) {
    this.startPort = startPort;
  }
  public Port getEndPort() {
    return endPort;
  }
  public void setEndPort(Port endPort) {
    this.endPort = endPort;
  }
  public Point getFallbackStartPoint() {
    return fallbackStartPoint;
  }
  public void setFallbackStartPoint(Point fallbackStartPoint) {
    this.fallbackStartPoint = fallbackStartPoint;
  }
  public Point getFallbackEndPoint() {
    return fallbackEndPoint;
  }
  public void setFallbackEndPoint(Point fallbackEndPoint) {
    this.fallbackEndPoint = fallbackEndPoint;
  }
  public List<Point> getIntermediatePoints() {
    return intermediatePoints;
  }
  public void setIntermediatePoints(List<Point> intermediatePoints) {
    this.intermediatePoints = intermediatePoints;
  }
}