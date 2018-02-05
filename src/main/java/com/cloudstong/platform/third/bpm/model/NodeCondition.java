package com.cloudstong.platform.third.bpm.model;

public class NodeCondition
{
  private String nodeName = "";

  private String nodeId = "";

  private String condition = "";

  public NodeCondition(String nodeName, String nodeId, String condition)
  {
    this.nodeName = nodeName;
    this.nodeId = nodeId;
    this.condition = condition;
  }

  public String getNodeName()
  {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getNodeId()
  {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public String getCondition()
  {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }
}