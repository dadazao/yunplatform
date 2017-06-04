package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tPartnerEntity", propOrder={"participantRef"})
public class PartnerEntity extends RootElement
{
  protected List<QName> participantRef;

  @XmlAttribute
  protected String name;

  public List<QName> getParticipantRef()
  {
    if (participantRef == null) {
      participantRef = new ArrayList();
    }
    return participantRef;
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