package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tParticipant", propOrder={"interfaceRef", "endPointRef", "participantMultiplicity"})
public class Participant extends BaseElement
{
  protected List<QName> interfaceRef;
  protected List<QName> endPointRef;
  protected ParticipantMultiplicity participantMultiplicity;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected QName processRef;

  public List<QName> getInterfaceRef()
  {
    if (interfaceRef == null) {
      interfaceRef = new ArrayList();
    }
    return interfaceRef;
  }

  public List<QName> getEndPointRef()
  {
    if (endPointRef == null) {
      endPointRef = new ArrayList();
    }
    return endPointRef;
  }

  public ParticipantMultiplicity getParticipantMultiplicity()
  {
    return participantMultiplicity;
  }

  public void setParticipantMultiplicity(ParticipantMultiplicity value)
  {
    participantMultiplicity = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getProcessRef()
  {
    return processRef;
  }

  public void setProcessRef(QName value)
  {
    processRef = value;
  }
}