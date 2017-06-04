package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tParticipantMultiplicity")
public class ParticipantMultiplicity extends BaseElement
{

  @XmlAttribute
  protected Integer minimum;

  @XmlAttribute
  protected Integer maximum;

  public int getMinimum()
  {
    if (minimum == null) {
      return 0;
    }
    return minimum.intValue();
  }

  public void setMinimum(Integer value)
  {
    minimum = value;
  }

  public int getMaximum()
  {
    if (maximum == null) {
      return 1;
    }
    return maximum.intValue();
  }

  public void setMaximum(Integer value)
  {
    maximum = value;
  }
}