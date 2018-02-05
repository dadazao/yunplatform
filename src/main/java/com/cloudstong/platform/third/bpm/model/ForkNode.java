package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.List;

public class ForkNode
{
  private boolean isMulti = false;

  private String forkNodeId = "";

  private String preNodeId = "";

  private List<NodeCondition> list = new ArrayList();

  public String getPreNodeId() {
    return preNodeId;
  }

  public void setPreNodeId(String preNodeId) {
    this.preNodeId = preNodeId;
  }

  public void addNode(NodeCondition condition)
  {
    list.add(condition);
  }

  public boolean getIsMulti() {
    return isMulti;
  }

  public void setMulti(boolean isMulti) {
    this.isMulti = isMulti;
  }

  public String getForkNodeId() {
    return forkNodeId;
  }

  public void setForkNodeId(String forkNodeId) {
    this.forkNodeId = forkNodeId;
  }

  public List<NodeCondition> getList() {
    return list;
  }

  public void setList(List<NodeCondition> list) {
    this.list = list;
  }
}