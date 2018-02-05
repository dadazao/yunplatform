package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tBoundaryEvent")
public class BoundaryEvent extends CatchEvent
{

  @XmlAttribute
  protected Boolean cancelActivity;

  @XmlAttribute(required=true)
  protected QName attachedToRef;

  public boolean isCancelActivity()
  {
    if (cancelActivity == null) {
      return true;
    }
    return cancelActivity.booleanValue();
  }

  public void setCancelActivity(Boolean value)
  {
    cancelActivity = value;
  }

  public QName getAttachedToRef()
  {
    return attachedToRef;
  }

  public void setAttachedToRef(QName value)
  {
    attachedToRef = value;
  }
}