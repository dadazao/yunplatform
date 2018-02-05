package com.cloudstong.platform.core.bpmn20.entity.activiti;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"field"})
@XmlRootElement(name="taskListener")
public class TaskListener
{
  protected List<Field> field;

  @XmlAttribute(name="class")
  protected String clazz;

  @XmlAttribute
  protected String expression;

  @XmlAttribute
  protected String delegateExpression;

  @XmlAttribute(required=true)
  protected String event;

  public List<Field> getField()
  {
    if (field == null) {
      field = new ArrayList();
    }
    return field;
  }

  public String getClazz()
  {
    return clazz;
  }

  public void setClazz(String value)
  {
    clazz = value;
  }

  public String getExpression()
  {
    return expression;
  }

  public void setExpression(String value)
  {
    expression = value;
  }

  public String getDelegateExpression()
  {
    return delegateExpression;
  }

  public void setDelegateExpression(String value)
  {
    delegateExpression = value;
  }

  public String getEvent()
  {
    return event;
  }

  public void setEvent(String value)
  {
    event = value;
  }
}