package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCallConversation", propOrder={"participantAssociation"})
public class CallConversation extends ConversationNode
{
  protected List<ParticipantAssociation> participantAssociation;

  @XmlAttribute
  protected QName calledCollaborationRef;

  public List<ParticipantAssociation> getParticipantAssociation()
  {
    if (participantAssociation == null) {
      participantAssociation = new ArrayList();
    }
    return participantAssociation;
  }

  public QName getCalledCollaborationRef()
  {
    return calledCollaborationRef;
  }

  public void setCalledCollaborationRef(QName value)
  {
    calledCollaborationRef = value;
  }
}