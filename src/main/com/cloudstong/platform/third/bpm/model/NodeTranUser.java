package com.cloudstong.platform.third.bpm.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class NodeTranUser
{
  private String nodeName;
  private String nodeId;
  Set<NodeUserMap> nodeUserMapSet = new LinkedHashSet();

  Map<String, String> nextPathMap = new HashMap();

  public Map<String, String> getNextPathMap() {
    return nextPathMap;
  }

  public void setNextPathMap(Map<String, String> nextPathMap) {
    this.nextPathMap = nextPathMap;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public NodeTranUser()
  {
  }

  public NodeTranUser(String nodeId, String nodeName, Set<NodeUserMap> nodeUserMapSet)
  {
    this.nodeId = nodeId;
    this.nodeName = nodeName;
    this.nodeUserMapSet = nodeUserMapSet;
  }

  public String getNodeId()
  {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public Set<NodeUserMap> getNodeUserMapSet() {
    return nodeUserMapSet;
  }

  public void setNodeUserMapSet(Set<NodeUserMap> nodeUserMapSet) {
    this.nodeUserMapSet = nodeUserMapSet;
  }
}