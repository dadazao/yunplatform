package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tScriptTask", propOrder={"script"})
public class ScriptTask extends Task
{
  protected Script script;

  @XmlAttribute
  protected String scriptFormat;

  public Script getScript()
  {
    return script;
  }

  public void setScript(Script value)
  {
    script = value;
  }

  public String getScriptFormat()
  {
    return scriptFormat;
  }

  public void setScriptFormat(String value)
  {
    scriptFormat = value;
  }
}