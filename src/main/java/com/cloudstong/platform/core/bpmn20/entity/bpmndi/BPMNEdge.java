package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import com.cloudstong.platform.core.bpmn20.entity.omgdi.LabeledEdge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BPMNEdge", propOrder={"bpmnLabel"})
public class BPMNEdge extends LabeledEdge
{

  @XmlElement(name="BPMNLabel")
  protected BPMNLabel bpmnLabel;

  @XmlAttribute
  protected QName bpmnElement;

  @XmlAttribute
  protected QName sourceElement;

  @XmlAttribute
  protected QName targetElement;

  @XmlAttribute
  protected MessageVisibleKind messageVisibleKind;

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

  public QName getSourceElement()
  {
    return sourceElement;
  }

  public void setSourceElement(QName value)
  {
    sourceElement = value;
  }

  public QName getTargetElement()
  {
    return targetElement;
  }

  public void setTargetElement(QName value)
  {
    targetElement = value;
  }

  public MessageVisibleKind getMessageVisibleKind()
  {
    return messageVisibleKind;
  }

  public void setMessageVisibleKind(MessageVisibleKind value)
  {
    messageVisibleKind = value;
  }
}