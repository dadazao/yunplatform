package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tMessageEventDefinition", propOrder={"operationRef"})
public class MessageEventDefinition extends EventDefinition
{
  protected QName operationRef;

  @XmlAttribute
  protected QName messageRef;

  public QName getOperationRef()
  {
    return operationRef;
  }

  public void setOperationRef(QName value)
  {
    operationRef = value;
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