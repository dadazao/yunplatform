package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNDiagram;

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
@XmlType(name="Diagram")
@XmlSeeAlso({BPMNDiagram.class})
public abstract class Diagram
{

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected String documentation;

  @XmlAttribute
  protected Double resolution;

  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public String getDocumentation()
  {
    return documentation;
  }

  public void setDocumentation(String value)
  {
    documentation = value;
  }

  public Double getResolution()
  {
    return resolution;
  }

  public void setResolution(Double value)
  {
    resolution = value;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String value)
  {
    id = value;
  }
}