package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tUserTask", propOrder={"rendering"})
public class UserTask extends Task
{
  protected List<Rendering> rendering;

  @XmlAttribute
  protected String implementation;

  public List<Rendering> getRendering()
  {
    if (rendering == null) {
      rendering = new ArrayList();
    }
    return rendering;
  }

  public String getImplementation()
  {
    if (implementation == null) {
      return "##unspecified";
    }
    return implementation;
  }

  public void setImplementation(String value)
  {
    implementation = value;
  }
}