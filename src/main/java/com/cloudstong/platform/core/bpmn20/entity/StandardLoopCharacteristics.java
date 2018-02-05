package com.cloudstong.platform.core.bpmn20.entity;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tStandardLoopCharacteristics", propOrder={"loopCondition"})
public class StandardLoopCharacteristics extends LoopCharacteristics
{
  protected Expression loopCondition;

  @XmlAttribute
  protected Boolean testBefore;

  @XmlAttribute
  protected BigInteger loopMaximum;

  public Expression getLoopCondition()
  {
    return loopCondition;
  }

  public void setLoopCondition(Expression value)
  {
    loopCondition = value;
  }

  public boolean isTestBefore()
  {
    if (testBefore == null) {
      return false;
    }
    return testBefore.booleanValue();
  }

  public void setTestBefore(Boolean value)
  {
    testBefore = value;
  }

  public BigInteger getLoopMaximum()
  {
    return loopMaximum;
  }

  public void setLoopMaximum(BigInteger value)
  {
    loopMaximum = value;
  }
}