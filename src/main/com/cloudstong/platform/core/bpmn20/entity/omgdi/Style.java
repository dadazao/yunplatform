package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNLabelStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Style")
@XmlSeeAlso({BPMNLabelStyle.class})
public abstract class Style
{

  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  public String getId()
  {
    return id;
  }

  public void setId(String value)
  {
    id = value;
  }
}