package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tMessageFlow")
public class MessageFlow extends BaseElement
{

  @XmlAttribute
  protected String name;

  @XmlAttribute(required=true)
  protected QName sourceRef;

  @XmlAttribute(required=true)
  protected QName targetRef;

  @XmlAttribute
  protected QName messageRef;

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getSourceRef()
  {
    return sourceRef;
  }

  public void setSourceRef(QName value)
  {
    sourceRef = value;
  }

  public QName getTargetRef()
  {
    return targetRef;
  }

  public void setTargetRef(QName value)
  {
    targetRef = value;
  }

  public QName getMessageRef()
  {
    return messageRef;
  }

  public void setMessageRef(QName value)
  {
    messageRef = value;
  }
}