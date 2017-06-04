package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCorrelationPropertyRetrievalExpression", propOrder={"messagePath"})
public class CorrelationPropertyRetrievalExpression extends BaseElement
{

  @XmlElement(required=true)
  protected FormalExpression messagePath;

  @XmlAttribute(required=true)
  protected QName messageRef;

  public FormalExpression getMessagePath()
  {
    return messagePath;
  }

  public void setMessagePath(FormalExpression value)
  {
    messagePath = value;
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