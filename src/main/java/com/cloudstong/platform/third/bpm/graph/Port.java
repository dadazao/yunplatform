package com.cloudstong.platform.third.bpm.graph;

public class Port
{
  private PortType portType;
  private double x;
  private double y;
  private double verticalOffset;
  private double horizontalOffset;
  private String nodePartReference;
  private boolean clipOnShape;

  public Port()
  {
  }

  public Port(PortType portType, double x, double y, double horizontalOffset, double verticalOffset, String nodePartReference, boolean clipOnShape)
  {
    this.portType = portType;
    this.x = x;
    this.y = y;
    this.verticalOffset = verticalOffset;
    this.horizontalOffset = horizontalOffset;
    this.nodePartReference = nodePartReference;
    this.clipOnShape = clipOnShape;
  }

  public PortType getPortType() {
    return portType;
  }

  public void setPortType(PortType portType) {
    this.portType = portType;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getVerticalOffset() {
    return verticalOffset;
  }

  public void setVerticalOffset(double verticalOffset) {
    this.verticalOffset = verticalOffset;
  }

  public double getHorizontalOffset() {
    return horizontalOffset;
  }

  public void setHorizontalOffset(double horizontalOffset) {
    this.horizontalOffset = horizontalOffset;
  }

  public String getNodePartReference() {
    return nodePartReference;
  }

  public void setNodePartReference(String nodePartReference) {
    this.nodePartReference = nodePartReference;
  }

  public boolean isClipOnShape() {
    return clipOnShape;
  }

  public void setClipOnShape(boolean clipOnShape) {
    this.clipOnShape = clipOnShape;
  }
}