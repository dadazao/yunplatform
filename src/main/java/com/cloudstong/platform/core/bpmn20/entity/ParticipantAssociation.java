package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tParticipantAssociation", propOrder={"innerParticipantRef", "outerParticipantRef"})
public class ParticipantAssociation extends BaseElement
{

  @XmlElement(required=true)
  protected QName innerParticipantRef;

  @XmlElement(required=true)
  protected QName outerParticipantRef;

  public QName getInnerParticipantRef()
  {
    return innerParticipantRef;
  }

  public void setInnerParticipantRef(QName value)
  {
    innerParticipantRef = value;
  }

  public QName getOuterParticipantRef()
  {
    return outerParticipantRef;
  }

  public void setOuterParticipantRef(QName value)
  {
    outerParticipantRef = value;
  }
}