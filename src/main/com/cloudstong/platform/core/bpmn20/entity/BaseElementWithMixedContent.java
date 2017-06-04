package com.cloudstong.platform.core.bpmn20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tBaseElementWithMixedContent", propOrder={"content"})
@XmlSeeAlso({Expression.class})
public abstract class BaseElementWithMixedContent
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="documentation", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="extensionElements", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=javax.xml.bind.JAXBElement.class)})
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap();

  public List<Serializable> getContent()
  {
    if (content == null) {
      content = new ArrayList();
    }
    return content;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String value)
  {
    id = value;
  }

  public Map<QName, String> getOtherAttributes()
  {
    return otherAttributes;
  }
}