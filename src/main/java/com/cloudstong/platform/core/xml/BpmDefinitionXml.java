package com.cloudstong.platform.core.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.cloudstong.platform.third.bpm.model.BpmDefRights;
import com.cloudstong.platform.third.bpm.model.BpmDefVar;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;
import com.cloudstong.platform.third.bpm.model.BpmNodeMessage;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmNodeSign;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.model.BpmNodeUserUplow;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;
import com.cloudstong.platform.third.bpm.model.TaskApprovalItems;
import com.cloudstong.platform.third.bpm.model.TaskReminder;

@XmlRootElement(name = "bpmDefinitions")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmDefinitionXml {

	@XmlElement(name = "bpmDefinition", type = BpmDefinition.class)
	private BpmDefinition bpmDefinition;

	@XmlElementWrapper(name = "bpmDefinitionList")
	@XmlElements({ @XmlElement(name = "bpmDefinitions", type = BpmDefinitionXml.class) })
	private List<BpmDefinitionXml> bpmDefinitionXmlList;

	@XmlElementWrapper(name = "bpmDefRightsList")
	@XmlElements({ @XmlElement(name = "bpmDefRights", type = BpmDefRights.class) })
	private List<BpmDefRights> bpmDefRightsList;

	@XmlElementWrapper(name = "bpmNodeRuleList")
	@XmlElements({ @XmlElement(name = "bpmNodeRule", type = BpmNodeRule.class) })
	private List<BpmNodeRule> bpmNodeRuleList;

	@XmlElementWrapper(name = "bpmNodeScriptList")
	@XmlElements({ @XmlElement(name = "bpmNodeScript", type = BpmNodeScript.class) })
	private List<BpmNodeScript> bpmNodeScriptList;

	@XmlElementWrapper(name = "bpmDefVarList")
	@XmlElements({ @XmlElement(name = "bpmDefVar", type = BpmDefVar.class) })
	private List<BpmDefVar> bpmDefVarList;

	@XmlElementWrapper(name = "bpmNodeSignList")
	@XmlElements({ @XmlElement(name = "bpmNodeSign", type = BpmNodeSign.class) })
	private List<BpmNodeSign> bpmNodeSignList;

	@XmlElementWrapper(name = "bpmNodeMessageList")
	@XmlElements({ @XmlElement(name = "bpmNodeMessage", type = BpmNodeMessage.class) })
	private List<BpmNodeMessage> bpmNodeMessageList;

	@XmlElementWrapper(name = "bpmNodeSetList")
	@XmlElements({ @XmlElement(name = "bpmNodeSet", type = BpmNodeSet.class) })
	private List<BpmNodeSet> bpmNodeSetList;

	@XmlElementWrapper(name = "bpmUserConditionList")
	@XmlElements({ @XmlElement(name = "bpmUserCondition", type = BpmUserCondition.class) })
	private List<BpmUserCondition> bpmUserConditionList;

	@XmlElementWrapper(name = "bpmNodeUserList")
	@XmlElements({ @XmlElement(name = "bpmNodeUser", type = BpmNodeUser.class) })
	private List<BpmNodeUser> bpmNodeUserList;

	@XmlElementWrapper(name = "bpmNodeUserUplowList")
	@XmlElements({ @XmlElement(name = "bpmNodeUserUplow", type = BpmNodeUserUplow.class) })
	private List<BpmNodeUserUplow> bpmNodeUserUplowList;

	@XmlElementWrapper(name = "bpmNodeButtonList")
	@XmlElements({ @XmlElement(name = "bpmNodeButton", type = BpmNodeButton.class) })
	private List<BpmNodeButton> bpmNodeButtonList;

	@XmlElementWrapper(name = "taskApprovalItemsList")
	@XmlElements({ @XmlElement(name = "taskApprovalItems", type = TaskApprovalItems.class) })
	private List<TaskApprovalItems> taskApprovalItemsList;

	@XmlElementWrapper(name = "taskReminderList")
	@XmlElements({ @XmlElement(name = "taskReminder", type = TaskReminder.class) })
	private List<TaskReminder> taskReminderList;

	@XmlElementWrapper(name = "subBpmDefinitionList")
	@XmlElements({ @XmlElement(name = "subBpmDefinitions", type = BpmDefinitionXml.class) })
	private List<BpmDefinitionXml> subBpmDefinitionXmlList;

	@XmlElementWrapper(name = "bpmFormDefXmlList")
	@XmlElements({ @XmlElement(name = "formDefs", type = BpmFormDefXml.class) })
	private List<BpmFormDefXml> bpmFormDefXmlList;

	@XmlElementWrapper(name = "bpmFormTableXmlList")
	@XmlElements({ @XmlElement(name = "tables", type = BpmFormTableXml.class) })
	private List<BpmFormTableXml> bpmFormTableXmlList;

	public BpmDefinition getBpmDefinition() {
		return bpmDefinition;
	}

	public void setBpmDefinition(BpmDefinition bpmDefinition) {
		this.bpmDefinition = bpmDefinition;
	}

	public List<BpmDefinitionXml> getBpmDefinitionXmlList() {
		return bpmDefinitionXmlList;
	}

	public void setBpmDefinitionXmlList(List<BpmDefinitionXml> bpmDefinitionXmlList) {
		this.bpmDefinitionXmlList = bpmDefinitionXmlList;
	}

	public List<BpmDefRights> getBpmDefRightsList() {
		return bpmDefRightsList;
	}

	public void setBpmDefRightsList(List<BpmDefRights> bpmDefRightsList) {
		this.bpmDefRightsList = bpmDefRightsList;
	}

	public List<BpmNodeRule> getBpmNodeRuleList() {
		return bpmNodeRuleList;
	}

	public void setBpmNodeRuleList(List<BpmNodeRule> bpmNodeRuleList) {
		this.bpmNodeRuleList = bpmNodeRuleList;
	}

	public List<BpmNodeScript> getBpmNodeScriptList() {
		return bpmNodeScriptList;
	}

	public void setBpmNodeScriptList(List<BpmNodeScript> bpmNodeScriptList) {
		this.bpmNodeScriptList = bpmNodeScriptList;
	}

	public List<BpmDefVar> getBpmDefVarList() {
		return bpmDefVarList;
	}

	public void setBpmDefVarList(List<BpmDefVar> bpmDefVarList) {
		this.bpmDefVarList = bpmDefVarList;
	}

	public List<BpmNodeSign> getBpmNodeSignList() {
		return bpmNodeSignList;
	}

	public void setBpmNodeSignList(List<BpmNodeSign> bpmNodeSignList) {
		this.bpmNodeSignList = bpmNodeSignList;
	}

	public List<BpmNodeMessage> getBpmNodeMessageList() {
		return bpmNodeMessageList;
	}

	public void setBpmNodeMessageList(List<BpmNodeMessage> bpmNodeMessageList) {
		this.bpmNodeMessageList = bpmNodeMessageList;
	}

	public List<BpmNodeSet> getBpmNodeSetList() {
		return bpmNodeSetList;
	}

	public void setBpmNodeSetList(List<BpmNodeSet> bpmNodeSetList) {
		this.bpmNodeSetList = bpmNodeSetList;
	}

	public List<BpmUserCondition> getBpmUserConditionList() {
		return bpmUserConditionList;
	}

	public void setBpmUserConditionList(List<BpmUserCondition> bpmUserConditionList) {
		this.bpmUserConditionList = bpmUserConditionList;
	}

	public List<BpmNodeUser> getBpmNodeUserList() {
		return bpmNodeUserList;
	}

	public void setBpmNodeUserList(List<BpmNodeUser> bpmNodeUserList) {
		this.bpmNodeUserList = bpmNodeUserList;
	}

	public List<BpmNodeUserUplow> getBpmNodeUserUplowList() {
		return bpmNodeUserUplowList;
	}

	public void setBpmNodeUserUplowList(List<BpmNodeUserUplow> bpmNodeUserUplowList) {
		this.bpmNodeUserUplowList = bpmNodeUserUplowList;
	}

	public List<BpmNodeButton> getBpmNodeButtonList() {
		return bpmNodeButtonList;
	}

	public void setBpmNodeButtonList(List<BpmNodeButton> bpmNodeButtonList) {
		this.bpmNodeButtonList = bpmNodeButtonList;
	}

	public List<TaskApprovalItems> getTaskApprovalItemsList() {
		return taskApprovalItemsList;
	}

	public void setTaskApprovalItemsList(List<TaskApprovalItems> taskApprovalItemsList) {
		this.taskApprovalItemsList = taskApprovalItemsList;
	}

	public List<TaskReminder> getTaskReminderList() {
		return taskReminderList;
	}

	public void setTaskReminderList(List<TaskReminder> taskReminderList) {
		this.taskReminderList = taskReminderList;
	}

	public List<BpmDefinitionXml> getSubBpmDefinitionXmlList() {
		return subBpmDefinitionXmlList;
	}

	public void setSubBpmDefinitionXmlList(List<BpmDefinitionXml> subBpmDefinitionXmlList) {
		this.subBpmDefinitionXmlList = subBpmDefinitionXmlList;
	}

	public List<BpmFormDefXml> getBpmFormDefXmlList() {
		return bpmFormDefXmlList;
	}

	public void setBpmFormDefXmlList(List<BpmFormDefXml> bpmFormDefXmlList) {
		this.bpmFormDefXmlList = bpmFormDefXmlList;
	}

	public List<BpmFormTableXml> getBpmFormTableXmlList() {
		return bpmFormTableXmlList;
	}

	public void setBpmFormTableXmlList(List<BpmFormTableXml> bpmFormTableXmlList) {
		this.bpmFormTableXmlList = bpmFormTableXmlList;
	}
}