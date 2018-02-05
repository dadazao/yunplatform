package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tChoreographyTask", propOrder={"messageFlowRef"})
public class ChoreographyTask extends ChoreographyActivity
{

  @XmlElement(required=true)
  protected List<QName> messageFlowRef;

  public List<QName> getMessageFlowRef()
  {
    if (messageFlowRef == null) {
      messageFlowRef = new ArrayList();
    }
    return messageFlowRef;
  }
}