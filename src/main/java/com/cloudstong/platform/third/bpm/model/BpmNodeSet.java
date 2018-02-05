package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeSet")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeSet extends EntityBase {
	public static final Short FORM_TYPE_NULL = -1;
	
	public static final Short FORM_TYPE_ONLINE = Short.valueOf((short) 0);

	public static final Short FORM_TYPE_URL = Short.valueOf((short) 1);

	public static final Short NODE_TYPE_NORMAL = Short.valueOf((short) 0);

	public static final Short NODE_TYPE_FORK = Short.valueOf((short) 1);

	public static final Short NODE_TYPE_JOIN = Short.valueOf((short) 2);

	public static final Short BACK_ALLOW = Short.valueOf((short) 1);

	public static final Short HIDE_OPTION = Short.valueOf((short) 1);

	public static final Short HIDE_PATH = Short.valueOf((short) 1);
	public static final Short NOT_HIDE_PATH = Short.valueOf((short) 0);
	public static final Short NOT_HIDE_OPTION = Short.valueOf((short) 0);

	public static final Short BACK_ALLOW_START = Short.valueOf((short) 1);

	public static final Short BACK_DENY = Short.valueOf((short) 0);

	public static final Short SetType_TaskNode = Short.valueOf((short) 0);

	public static final Short SetType_StartForm = Short.valueOf((short) 1);

	public static final Short SetType_GloabalForm = Short.valueOf((short) 2);
	
	public static final Short SetType_BpmForm = 3;

	public static final Short RULE_INVALID_NORMAL = Short.valueOf((short) 1);

	public static final Short RULE_INVALID_NO_NORMAL = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long setId;

	@XmlAttribute
	protected Long defId;

	@XmlAttribute
	protected String nodeName;

	@XmlAttribute
	protected Integer nodeOrder;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected Short formType = Short.valueOf((short) -1);

	@XmlAttribute
	protected String formUrl;

	@XmlAttribute
	protected Long formKey = Long.valueOf(0L);

	@XmlAttribute
	protected String formDefName;

	@XmlAttribute
	protected Long formDefId = Long.valueOf(0L);

	@XmlAttribute
	protected String detailUrl;

	@XmlAttribute
	protected Short isJumpForDef = RULE_INVALID_NORMAL;

	@XmlAttribute
	protected Short nodeType;

	@XmlAttribute
	protected String joinTaskKey;

	@XmlAttribute
	protected String joinTaskName;

	@XmlAttribute
	protected String beforeHandler;

	@XmlAttribute
	protected String afterHandler;

	@XmlAttribute
	protected String jumpType;

	@XmlAttribute
	protected Short setType = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short isHideOption = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short isHidePath = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short isAllowMobile = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long oldFormKey = Long.valueOf(0L);

	@XmlAttribute
	protected Short existSubTable = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long mainTableId = Long.valueOf(0L);
	
	protected String informType = "";
	
	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Short getIsAllowMobile() {
		return isAllowMobile;
	}

	public void setIsAllowMobile(Short isAllowMobile) {
		this.isAllowMobile = isAllowMobile;
	}

	public Long getDefId() {
		return defId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public Integer getNodeOrder() {
		return nodeOrder;
	}

	public void setNodeOrder(Integer nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setFormType(Short formType) {
		this.formType = formType;
	}

	public Short getFormType() {
		return formType;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public Long getFormKey() {
		return formKey;
	}

	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}

	public String getFormDefName() {
		return formDefName;
	}

	public void setFormDefName(String formDefName) {
		this.formDefName = formDefName;
	}

	public Short getNodeType() {
		return nodeType;
	}

	public void setNodeType(Short nodeType) {
		this.nodeType = nodeType;
	}

	public String getJoinTaskKey() {
		return joinTaskKey;
	}

	public void setJoinTaskKey(String joinTaskKey) {
		this.joinTaskKey = joinTaskKey;
	}

	public String getJoinTaskName() {
		return joinTaskName;
	}

	public void setJoinTaskName(String joinTaskName) {
		this.joinTaskName = joinTaskName;
	}

	public String getBeforeHandler() {
		return beforeHandler;
	}

	public void setBeforeHandler(String beforeHandler) {
		this.beforeHandler = beforeHandler;
	}

	public String getAfterHandler() {
		return afterHandler;
	}

	public void setAfterHandler(String afterHandler) {
		this.afterHandler = afterHandler;
	}

	public Short getSetType() {
		return setType;
	}

	public void setSetType(Short setType) {
		this.setType = setType;
	}

	public Long getFormDefId() {
		return formDefId;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

	public Short getIsJumpForDef() {
		return isJumpForDef;
	}

	public void setIsJumpForDef(Short isJumpForDef) {
		this.isJumpForDef = isJumpForDef;
	}

	public Long getOldFormKey() {
		return oldFormKey;
	}

	public void setOldFormKey(Long oldFormKey) {
		this.oldFormKey = oldFormKey;
	}

	public Short getIsHideOption() {
		return isHideOption;
	}

	public void setIsHideOption(Short isHideOption) {
		this.isHideOption = isHideOption;
	}

	public Short getIsHidePath() {
		return isHidePath;
	}

	public void setIsHidePath(Short isHidePath) {
		this.isHidePath = isHidePath;
	}

	public Short getExistSubTable() {
		return existSubTable;
	}

	public void setExistSubTable(Short existSubTable) {
		this.existSubTable = existSubTable;
	}

	public Long getMainTableId() {
		return mainTableId;
	}

	public void setMainTableId(Long mainTableId) {
		this.mainTableId = mainTableId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeSet)) {
			return false;
		}
		BpmNodeSet rhs = (BpmNodeSet) object;
		return new EqualsBuilder().append(setId, rhs.setId).append(defId, rhs.defId).append(nodeName, rhs.nodeName).append(actDefId, rhs.actDefId)
				.append(nodeId, rhs.nodeId).append(formType, rhs.formType).append(formUrl, rhs.formUrl).append(formKey, rhs.formKey)
				.append(nodeType, rhs.nodeType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(setId).append(defId).append(nodeName).append(actDefId).append(nodeId)
				.append(formType).append(formUrl).append(formKey).append(nodeType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("setId", setId).append("defId", defId).append("nodeName", nodeName).append("actDefId", actDefId)
				.append("nodeId", nodeId).append("formType", formType).append("formUrl", formUrl).append("formKey", formKey)
				.append("nodeType", nodeType).toString();
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	@Override
	public Long getId() {
		return setId;
	}

	@Override
	public void setId(Long id) {
		setId = id;
	}

}