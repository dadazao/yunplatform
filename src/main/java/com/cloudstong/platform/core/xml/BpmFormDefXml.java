package com.cloudstong.platform.core.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.cloudstong.platform.third.bpm.form.model.BpmFormDef;
import com.cloudstong.platform.third.bpm.form.model.BpmFormRights;
import com.cloudstong.platform.third.bpm.form.model.BpmTableTemplate;

@XmlRootElement(name = "formDefs")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormDefXml {

	@XmlElement(name = "bpmFormDef", type = BpmFormDef.class)
	private BpmFormDef bpmFormDef;

	@XmlElementWrapper(name = "bpmFormDefList")
	@XmlElements({ @XmlElement(name = "formDefs", type = BpmFormDefXml.class) })
	private List<BpmFormDefXml> bpmFormDefXmlList;

	@XmlElementWrapper(name = "bpmFormRightsList")
	@XmlElements({ @XmlElement(name = "bpmFormRights", type = BpmFormRights.class) })
	private List<BpmFormRights> bpmFormRightsList;

	@XmlElementWrapper(name = "bpmTableTemplateList")
	@XmlElements({ @XmlElement(name = "bpmTableTemplate", type = BpmTableTemplate.class) })
	private List<BpmTableTemplate> bpmTableTemplateList;

	@XmlElement(name = "formTable", type = BpmFormTableXml.class)
	private BpmFormTableXml bpmFormTableXml;

	public BpmFormDef getBpmFormDef() {
		return bpmFormDef;
	}

	public void setBpmFormDef(BpmFormDef bpmFormDef) {
		this.bpmFormDef = bpmFormDef;
	}

	public List<BpmFormDefXml> getBpmFormDefXmlList() {
		return bpmFormDefXmlList;
	}

	public void setBpmFormDefXmlList(List<BpmFormDefXml> bpmFormDefXmlList) {
		this.bpmFormDefXmlList = bpmFormDefXmlList;
	}

	public List<BpmFormRights> getBpmFormRightsList() {
		return bpmFormRightsList;
	}

	public void setBpmFormRightsList(List<BpmFormRights> bpmFormRightsList) {
		this.bpmFormRightsList = bpmFormRightsList;
	}

	public List<BpmTableTemplate> getBpmTableTemplateList() {
		return bpmTableTemplateList;
	}

	public void setBpmTableTemplateList(List<BpmTableTemplate> bpmTableTemplateList) {
		this.bpmTableTemplateList = bpmTableTemplateList;
	}

	public BpmFormTableXml getBpmFormTableXml() {
		return bpmFormTableXml;
	}

	public void setBpmFormTableXml(BpmFormTableXml bpmFormTableXml) {
		this.bpmFormTableXml = bpmFormTableXml;
	}
}