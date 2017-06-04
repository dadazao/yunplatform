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
@XmlType(name="tOutputSet", propOrder={"dataOutputRefs", "optionalOutputRefs", "whileExecutingOutputRefs", "inputSetRefs"})
public class OutputSet extends BaseElement
{

  @XmlElementRef(name="dataOutputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> dataOutputRefs;

  @XmlElementRef(name="optionalOutputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> optionalOutputRefs;

  @XmlElementRef(name="whileExecutingOutputRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> whileExecutingOutputRefs;

  @XmlElementRef(name="inputSetRefs", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> inputSetRefs;

  @XmlAttribute
  protected String name;

  public List<JAXBElement<Object>> getDataOutputRefs()
  {
    if (dataOutputRefs == null) {
      dataOutputRefs = new ArrayList();
    }
    return dataOutputRefs;
  }

  public List<JAXBElement<Object>> getOptionalOutputRefs()
  {
    if (optionalOutputRefs == null) {
      optionalOutputRefs = new ArrayList();
    }
    return optionalOutputRefs;
  }

  public List<JAXBElement<Object>> getWhileExecutingOutputRefs()
  {
    if (whileExecutingOutputRefs == null) {
      whileExecutingOutputRefs = new ArrayList();
    }
    return whileExecutingOutputRefs;
  }

  public List<JAXBElement<Object>> getInputSetRefs()
  {
    if (inputSetRefs == null) {
      inputSetRefs = new ArrayList();
    }
    return inputSetRefs;
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