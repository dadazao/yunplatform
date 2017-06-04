package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tEscalationEventDefinition")
public class EscalationEventDefinition extends EventDefinition
{

  @XmlAttribute
  protected QName escalationRef;

  public QName getEscalationRef()
  {
    return escalationRef;
  }

  public void setEscalationRef(QName value)
  {
    escalationRef = value;
  }
}