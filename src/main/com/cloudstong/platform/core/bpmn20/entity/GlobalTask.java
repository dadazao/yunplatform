package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tGlobalTask", propOrder={"resourceRole"})
@XmlSeeAlso({GlobalUserTask.class, GlobalBusinessRuleTask.class, GlobalScriptTask.class, GlobalManualTask.class})
public class GlobalTask extends CallableElement
{

  @XmlElementRef(name="resourceRole", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends ResourceRole>> resourceRole;

  public List<JAXBElement<? extends ResourceRole>> getResourceRole()
  {
    if (resourceRole == null) {
      resourceRole = new ArrayList();
    }
    return resourceRole;
  }
}