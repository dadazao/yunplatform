package com.cloudstong.platform.core.bpmn20.entity.activiti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="out")
public class Out
{

  @XmlAttribute
  protected String source;

  @XmlAttribute
  protected String sourceExpression;

  @XmlAttribute(required=true)
  protected String target;

  public String getSource()
  {
    return source;
  }

  public void setSource(String value)
  {
    source = value;
  }

  public String getSourceExpression()
  {
    return sourceExpression;
  }

  public void setSourceExpression(String value)
  {
    sourceExpression = value;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String value)
  {
    target = value;
  }
}