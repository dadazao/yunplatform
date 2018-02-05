package com.cloudstong.platform.third.bpm.graph.activiti;

import java.util.Properties;

public class BPMNShap
{
  private BPMNShapType type;
  private String name;
  private String bpmnElement;
  private Boolean isHorizontal;
  private Boolean isExpanded;
  private Boolean isMarkerVisible;
  private Boolean isMessageVisible;
  private String participantBandKind;
  private String choreographyActivityShape;
  private double x;
  private double y;
  private double width;
  private double height;
  private Properties properties;

  public BPMNShapType getType()
  {
    return type;
  }
  public void setType(BPMNShapType type) {
    this.type = type;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getBpmnElement() {
    return bpmnElement;
  }
  public void setBpmnElement(String bpmnElement) {
    this.bpmnElement = bpmnElement;
  }
  public Boolean isHorizontal() {
    return isHorizontal;
  }
  public void setHorizontal(Boolean isHorizontal) {
    this.isHorizontal = isHorizontal;
  }
  public Boolean isExpanded() {
    return isExpanded;
  }
  public void setExpanded(Boolean isExpanded) {
    this.isExpanded = isExpanded;
  }
  public Boolean isMarkerVisible() {
    return isMarkerVisible;
  }
  public void setMarkerVisible(Boolean isMarkerVisible) {
    this.isMarkerVisible = isMarkerVisible;
  }
  public Boolean isMessageVisible() {
    return isMessageVisible;
  }
  public void setMessageVisible(Boolean isMessageVisible) {
    this.isMessageVisible = isMessageVisible;
  }
  public String getParticipantBandKind() {
    return participantBandKind;
  }
  public void setParticipantBandKind(String participantBandKind) {
    this.participantBandKind = participantBandKind;
  }
  public String getChoreographyActivityShape() {
    return choreographyActivityShape;
  }
  public void setChoreographyActivityShape(String choreographyActivityShape) {
    this.choreographyActivityShape = choreographyActivityShape;
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
  public double getWidth() {
    return width;
  }
  public void setWidth(double width) {
    this.width = width;
  }
  public double getHeight() {
    return height;
  }
  public void setHeight(double height) {
    this.height = height;
  }
  public Properties getProperties() {
    return properties;
  }
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public String toString() {
    return "BPMNShap [type=" + type + ", name=" + name + ", bpmnElement=" + 
      bpmnElement + ", isHorizontal=" + isHorizontal + 
      ", isExpanded=" + isExpanded + ", isMarkerVisible=" + 
      isMarkerVisible + ", isMessageVisible=" + isMessageVisible + 
      ", participantBandKind=" + participantBandKind + 
      ", choreographyActivityShape=" + choreographyActivityShape + 
      ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + 
      height + ", properties=" + properties + "]";
  }
}