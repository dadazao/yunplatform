package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tSignal")
public class Signal extends RootElement
{

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected QName structureRef;

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getStructureRef()
  {
    return structureRef;
  }

  public void setStructureRef(QName value)
  {
    structureRef = value;
  }
}