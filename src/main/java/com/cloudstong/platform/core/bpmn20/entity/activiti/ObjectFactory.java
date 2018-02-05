package com.cloudstong.platform.core.bpmn20.entity.activiti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _PotentialStarter_QNAME = new QName("http://activiti.org/bpmn", "potentialStarter");

  public Out createOut()
  {
    return new Out();
  }

  public Field createField()
  {
    return new Field();
  }

  public FormProperty.Value createFormPropertyValue()
  {
    return new FormProperty.Value();
  }

  public ExecutionListener createExecutionListener()
  {
    return new ExecutionListener();
  }

  public FormProperty createFormProperty()
  {
    return new FormProperty();
  }

  public PotentialStarter createPotentialStarter()
  {
    return new PotentialStarter();
  }

  public In createIn()
  {
    return new In();
  }

  public TaskListener createTaskListener()
  {
    return new TaskListener();
  }

  @XmlElementDecl(namespace="http://activiti.org/bpmn", name="potentialStarter")
  public JAXBElement<String> createPotentialStarter(String value)
  {
    return new JAXBElement(_PotentialStarter_QNAME, String.class, null, value);
  }
}