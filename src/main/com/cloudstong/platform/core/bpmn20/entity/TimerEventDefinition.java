package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tTimerEventDefinition", propOrder={"timeDate", "timeDuration", "timeCycle"})
public class TimerEventDefinition extends EventDefinition
{
  protected Expression timeDate;
  protected Expression timeDuration;
  protected Expression timeCycle;

  public Expression getTimeDate()
  {
    return timeDate;
  }

  public void setTimeDate(Expression value)
  {
    timeDate = value;
  }

  public Expression getTimeDuration()
  {
    return timeDuration;
  }

  public void setTimeDuration(Expression value)
  {
    timeDuration = value;
  }

  public Expression getTimeCycle()
  {
    return timeCycle;
  }

  public void setTimeCycle(Expression value)
  {
    timeCycle = value;
  }
}