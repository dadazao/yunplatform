package com.cloudstong.platform.third.bpm.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bpmNode")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNode
{

  @XmlAttribute
  private String nodeId;

  @XmlAttribute
  private String nodeName;

  @XmlAttribute
  private String nodeType;

  @XmlAttribute
  private Boolean isMultiple = Boolean.valueOf(false);

  @XmlAttribute
  private String condition = "";

  public BpmNode()
  {
  }

  public BpmNode(String nodeId, String nodeName, String nodeType, Boolean isMultiple) {
    this.nodeId = nodeId;
    this.nodeName = nodeName;
    this.nodeType = nodeType;
    this.isMultiple = isMultiple;
  }

  public String getNodeId()
  {
    return nodeId;
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

  public Boolean getIsMultiple() {
    return isMultiple;
  }

  public void setIsMultiple(Boolean isMultiple) {
    this.isMultiple = isMultiple;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }
}