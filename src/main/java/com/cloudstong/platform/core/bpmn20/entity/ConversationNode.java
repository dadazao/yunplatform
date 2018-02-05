package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tConversationNode", propOrder={"participantRef", "messageFlowRef", "correlationKey"})
@XmlSeeAlso({Conversation.class, CallConversation.class, SubConversation.class})
public abstract class ConversationNode extends BaseElement
{
  protected List<QName> participantRef;
  protected List<QName> messageFlowRef;
  protected List<CorrelationKey> correlationKey;

  @XmlAttribute
  protected String name;

  public List<QName> getParticipantRef()
  {
    if (participantRef == null) {
      participantRef = new ArrayList();
    }
    return participantRef;
  }

  public List<QName> getMessageFlowRef()
  {
    if (messageFlowRef == null) {
      messageFlowRef = new ArrayList();
    }
    return messageFlowRef;
  }

  public List<CorrelationKey> getCorrelationKey()
  {
    if (correlationKey == null) {
      correlationKey = new ArrayList();
    }
    return correlationKey;
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