package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tDataState")
public class DataState extends BaseElement
{

  @XmlAttribute
  protected String name;

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }
}