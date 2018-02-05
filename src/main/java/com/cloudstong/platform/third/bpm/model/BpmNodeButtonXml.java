package com.cloudstong.platform.third.bpm.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bpm")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmNodeButtonXml
{

  @XmlElementWrapper(name="buttons")
  @XmlElements({@javax.xml.bind.annotation.XmlElement(name="button", type=BpmButton.class)})
  private List<BpmButton> buttons;

  public List<BpmButton> getButtons()
  {
    return this.buttons;
  }

  public void setButtons(List<BpmButton> buttons) {
    this.buttons = buttons;
  }
}