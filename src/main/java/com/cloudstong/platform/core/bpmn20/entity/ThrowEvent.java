package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tThrowEvent", propOrder={"dataInput", "dataInputAssociation", "inputSet", "eventDefinition", "eventDefinitionRef"})
@XmlSeeAlso({IntermediateThrowEvent.class, ImplicitThrowEvent.class, EndEvent.class})
public abstract class ThrowEvent extends Event
{
  protected List<DataInput> dataInput;
  protected List<DataInputAssociation> dataInputAssociation;
  protected InputSet inputSet;

  @XmlElementRef(name="eventDefinition", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends EventDefinition>> eventDefinition;
  protected List<QName> eventDefinitionRef;

  public List<DataInput> getDataInput()
  {
    if (dataInput == null) {
      dataInput = new ArrayList();
    }
    return dataInput;
  }

  public List<DataInputAssociation> getDataInputAssociation()
  {
    if (dataInputAssociation == null) {
      dataInputAssociation = new ArrayList();
    }
    return dataInputAssociation;
  }

  public InputSet getInputSet()
  {
    return inputSet;
  }

  public void setInputSet(InputSet value)
  {
    inputSet = value;
  }

  public List<JAXBElement<? extends EventDefinition>> getEventDefinition()
  {
    if (eventDefinition == null) {
      eventDefinition = new ArrayList();
    }
    return eventDefinition;
  }

  public List<QName> getEventDefinitionRef()
  {
    if (eventDefinitionRef == null) {
      eventDefinitionRef = new ArrayList();
    }
    return eventDefinitionRef;
  }
}