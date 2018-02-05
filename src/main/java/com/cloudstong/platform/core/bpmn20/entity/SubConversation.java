package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tSubConversation", propOrder={"conversationNode"})
public class SubConversation extends ConversationNode
{

  @XmlElementRef(name="conversationNode", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends ConversationNode>> conversationNode;

  public List<JAXBElement<? extends ConversationNode>> getConversationNode()
  {
    if (conversationNode == null) {
      conversationNode = new ArrayList();
    }
    return conversationNode;
  }
}