package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tLinkEventDefinition", propOrder={"source", "target"})
public class LinkEventDefinition extends EventDefinition
{
  protected List<QName> source;
  protected QName target;

  @XmlAttribute(required=true)
  protected String name;

  public List<QName> getSource()
  {
    if (source == null) {
      source = new ArrayList();
    }
    return source;
  }

  public QName getTarget()
  {
    return target;
  }

  public void setTarget(QName value)
  {
    target = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }
}