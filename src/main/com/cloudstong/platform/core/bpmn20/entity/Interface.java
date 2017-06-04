package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tInterface", propOrder={"operation"})
public class Interface extends RootElement
{

  @XmlElement(required=true)
  protected List<Operation> operation;

  @XmlAttribute(required=true)
  protected String name;

  @XmlAttribute
  protected QName implementationRef;

  public List<Operation> getOperation()
  {
    if (operation == null) {
      operation = new ArrayList();
    }
    return operation;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getImplementationRef()
  {
    return implementationRef;
  }

  public void setImplementationRef(QName value)
  {
    implementationRef = value;
  }
}