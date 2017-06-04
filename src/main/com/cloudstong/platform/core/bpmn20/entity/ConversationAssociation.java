package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tConversationAssociation")
public class ConversationAssociation extends BaseElement
{

  @XmlAttribute(required=true)
  protected QName innerConversationNodeRef;

  @XmlAttribute(required=true)
  protected QName outerConversationNodeRef;

  public QName getInnerConversationNodeRef()
  {
    return innerConversationNodeRef;
  }

  public void setInnerConversationNodeRef(QName value)
  {
    innerConversationNodeRef = value;
  }

  public QName getOuterConversationNodeRef()
  {
    return outerConversationNodeRef;
  }

  public void setOuterConversationNodeRef(QName value)
  {
    outerConversationNodeRef = value;
  }
}