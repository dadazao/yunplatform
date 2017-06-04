package com.cloudstong.platform.third.bpm.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="button")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmButton
{

  @XmlAttribute
  protected Integer type;

  @XmlAttribute
  protected Integer operatortype;

  @XmlAttribute
  protected Integer script;

  @XmlAttribute
  protected String text;

  @XmlAttribute
  protected Integer init;

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getOperatortype() {
    return this.operatortype;
  }

  public void setOperatortype(Integer operatortype) {
    this.operatortype = operatortype;
  }

  public Integer getScript() {
    return this.script;
  }

  public void setScript(Integer script) {
    this.script = script;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getInit() {
    return this.init;
  }

  public void setInit(Integer init) {
    this.init = init;
  }
}