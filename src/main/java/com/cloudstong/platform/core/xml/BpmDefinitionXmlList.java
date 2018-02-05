package com.cloudstong.platform.core.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bpm")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmDefinitionXmlList {

	@XmlElements({ @javax.xml.bind.annotation.XmlElement(name = "bpmDefinitions", type = BpmDefinitionXml.class) })
	private List<BpmDefinitionXml> bpmDefinitionXmlList;

	public List<BpmDefinitionXml> getBpmDefinitionXmlList() {
		return bpmDefinitionXmlList;
	}

	public void setBpmDefinitionXmlList(List<BpmDefinitionXml> bpmDefinitionXmlList) {
		this.bpmDefinitionXmlList = bpmDefinitionXmlList;
	}
}