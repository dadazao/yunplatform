package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tGlobalScriptTask", propOrder={"script"})
public class GlobalScriptTask extends GlobalTask
{
  protected Script script;

  @XmlAttribute
  @XmlSchemaType(name="anyURI")
  protected String scriptLanguage;

  public Script getScript()
  {
    return script;
  }

  public void setScript(Script value)
  {
    script = value;
  }

  public String getScriptLanguage()
  {
    return scriptLanguage;
  }

  public void setScriptLanguage(String value)
  {
    scriptLanguage = value;
  }
}