package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import com.cloudstong.platform.core.bpmn20.entity.omgdi.LabeledShape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BPMNShape", propOrder={"bpmnLabel"})
public class BPMNShape extends LabeledShape
{

  @XmlElement(name="BPMNLabel")
  protected BPMNLabel bpmnLabel;

  @XmlAttribute
  protected QName bpmnElement;

  @XmlAttribute
  protected Boolean isHorizontal;

  @XmlAttribute
  protected Boolean isExpanded;

  @XmlAttribute
  protected Boolean isMarkerVisible;

  @XmlAttribute
  protected Boolean isMessageVisible;

  @XmlAttribute
  protected ParticipantBandKind participantBandKind;

  @XmlAttribute
  protected QName choreographyActivityShape;

  public BPMNLabel getBPMNLabel()
  {
    return bpmnLabel;
  }

  public void setBPMNLabel(BPMNLabel value)
  {
    bpmnLabel = value;
  }

  public QName getBpmnElement()
  {
    return bpmnElement;
  }

  public void setBpmnElement(QName value)
  {
    bpmnElement = value;
  }

  public Boolean isIsHorizontal()
  {
    return isHorizontal;
  }

  public void setIsHorizontal(Boolean value)
  {
    isHorizontal = value;
  }

  public Boolean isIsExpanded()
  {
    return isExpanded;
  }

  public void setIsExpanded(Boolean value)
  {
    isExpanded = value;
  }

  public Boolean isIsMarkerVisible()
  {
    return isMarkerVisible;
  }

  public void setIsMarkerVisible(Boolean value)
  {
    isMarkerVisible = value;
  }

  public Boolean isIsMessageVisible()
  {
    return isMessageVisible;
  }

  public void setIsMessageVisible(Boolean value)
  {
    isMessageVisible = value;
  }

  public ParticipantBandKind getParticipantBandKind()
  {
    return participantBandKind;
  }

  public void setParticipantBandKind(ParticipantBandKind value)
  {
    participantBandKind = value;
  }

  public QName getChoreographyActivityShape()
  {
    return choreographyActivityShape;
  }

  public void setChoreographyActivityShape(QName value)
  {
    choreographyActivityShape = value;
  }
}