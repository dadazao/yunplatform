package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tExtension", propOrder={"documentation"})
public class Extension
{
  protected List<Documentation> documentation;

  @XmlAttribute
  protected QName definition;

  @XmlAttribute
  protected Boolean mustUnderstand;

  public List<Documentation> getDocumentation()
  {
    if (documentation == null) {
      documentation = new ArrayList();
    }
    return documentation;
  }

  public QName getDefinition()
  {
    return definition;
  }

  public void setDefinition(QName value)
  {
    definition = value;
  }

  public boolean isMustUnderstand()
  {
    if (mustUnderstand == null) {
      return false;
    }
    return mustUnderstand.booleanValue();
  }

  public void setMustUnderstand(Boolean value)
  {
    mustUnderstand = value;
  }
}