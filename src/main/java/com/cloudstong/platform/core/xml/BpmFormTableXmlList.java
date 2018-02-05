package com.cloudstong.platform.core.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bpm")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormTableXmlList {

	@XmlElements({ @javax.xml.bind.annotation.XmlElement(name = "tables", type = BpmFormTableXml.class) })
	private List<BpmFormTableXml> bpmFormTableXmlList;

	public List<BpmFormTableXml> getBpmFormTableXmlList() {
		return bpmFormTableXmlList;
	}

	public void setBpmFormTableXmlList(List<BpmFormTableXml> bpmFormTableXmlList) {
		this.bpmFormTableXmlList = bpmFormTableXmlList;
	}
}