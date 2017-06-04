package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tEvent", propOrder={"property"})
@XmlSeeAlso({ThrowEvent.class, CatchEvent.class})
public abstract class Event extends FlowNode
{
  protected List<Property> property;

  public List<Property> getProperty()
  {
    if (property == null) {
      property = new ArrayList();
    }
    return property;
  }
}