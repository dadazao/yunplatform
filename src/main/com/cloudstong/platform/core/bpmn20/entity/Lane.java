package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tLane", propOrder={"partitionElement", "flowNodeRef", "childLaneSet"})
public class Lane extends BaseElement
{
  protected BaseElement partitionElement;

  @XmlElementRef(name="flowNodeRef", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> flowNodeRef;
  protected LaneSet childLaneSet;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected QName partitionElementRef;

  public BaseElement getPartitionElement()
  {
    return partitionElement;
  }

  public void setPartitionElement(BaseElement value)
  {
    partitionElement = value;
  }

  public List<JAXBElement<Object>> getFlowNodeRef()
  {
    if (flowNodeRef == null) {
      flowNodeRef = new ArrayList();
    }
    return flowNodeRef;
  }

  public LaneSet getChildLaneSet()
  {
    return childLaneSet;
  }

  public void setChildLaneSet(LaneSet value)
  {
    childLaneSet = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getPartitionElementRef()
  {
    return partitionElementRef;
  }

  public void setPartitionElementRef(QName value)
  {
    partitionElementRef = value;
  }
}