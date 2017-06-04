package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tInputSet", propOrder={"dataInputRefs", "optionalInputRefs", "whileExecutingInputRefs", "outputSetRefs"})
public class InputSet extends BaseElement
{

  @XmlElementRef(name="dataInputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> dataInputRefs;

  @XmlElementRef(name="optionalInputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> optionalInputRefs;

  @XmlElementRef(name="whileExecutingInputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> whileExecutingInputRefs;

  @XmlElementRef(name="outputSetRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> outputSetRefs;

  @XmlAttribute
  protected String name;

  public List<JAXBElement<Object>> getDataInputRefs()
  {
    if (dataInputRefs == null) {
      dataInputRefs = new ArrayList();
    }
    return dataInputRefs;
  }

  public List<JAXBElement<Object>> getOptionalInputRefs()
  {
    if (optionalInputRefs == null) {
      optionalInputRefs = new ArrayList();
    }
    return optionalInputRefs;
  }

  public List<JAXBElement<Object>> getWhileExecutingInputRefs()
  {
    if (whileExecutingInputRefs == null) {
      whileExecutingInputRefs = new ArrayList();
    }
    return whileExecutingInputRefs;
  }

  public List<JAXBElement<Object>> getOutputSetRefs()
  {
    if (outputSetRefs == null) {
      outputSetRefs = new ArrayList();
    }
    return outputSetRefs;
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