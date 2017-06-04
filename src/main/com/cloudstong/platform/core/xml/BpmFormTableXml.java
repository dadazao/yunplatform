package com.cloudstong.platform.core.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.cloudstong.platform.system.model.Identity;
import com.cloudstong.platform.third.bpm.form.model.BpmFormField;
import com.cloudstong.platform.third.bpm.form.model.BpmFormTable;

@XmlRootElement(name = "tables")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormTableXml {

	@XmlElement(name = "table", type = BpmFormTable.class)
	private BpmFormTable bpmFormTable;

	@XmlElementWrapper(name = "fields")
	@XmlElements({ @XmlElement(name = "field", type = BpmFormField.class) })
	private List<BpmFormField> bpmFormFieldList;

	@XmlElementWrapper(name = "identitys")
	@XmlElements({ @XmlElement(name = "identity", type = Identity.class) })
	private List<Identity> identityList;

	@XmlElementWrapper(name = "subTables")
	@XmlElements({ @XmlElement(name = "subTable", type = BpmFormTableXml.class) })
	private List<BpmFormTableXml> bpmFormTableXmlList;

	public BpmFormTable getBpmFormTable() {
		return bpmFormTable;
	}

	public void setBpmFormTable(BpmFormTable bpmFormTable) {
		this.bpmFormTable = bpmFormTable;
	}

	public List<BpmFormField> getBpmFormFieldList() {
		return bpmFormFieldList;
	}

	public void setBpmFormFieldList(List<BpmFormField> bpmFormFieldList) {
		this.bpmFormFieldList = bpmFormFieldList;
	}

	public List<Identity> getIdentityList() {
		return identityList;
	}

	public void setIdentityList(List<Identity> identityList) {
		this.identityList = identityList;
	}

	public List<BpmFormTableXml> getBpmFormTableXmlList() {
		return bpmFormTableXmlList;
	}

	public void setBpmFormTableXmlList(List<BpmFormTableXml> bpmFormTableXmlList) {
		this.bpmFormTableXmlList = bpmFormTableXmlList;
	}
}