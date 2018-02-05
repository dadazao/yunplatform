package com.cloudstong.platform.third.bpm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.xml.XmlDateAdapter;

@XmlRootElement(name = "bpmDefinition")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmDefinition extends EntityBase implements Cloneable {
	public static final String DefaultSubjectRule = "{流程标题:title}-{发起人:startUser}-{发起时间:startTime}";
	public static final Short MAIN = Short.valueOf((short) 1);

	public static final Short NOT_MAIN = Short.valueOf((short) 0);

	public static final Short STATUS_DEPLOYED = Short.valueOf((short) 1);
	
	public static final Short STATUS_UNDEPLOYED = 0;

	public static final Short STATUS_ENABLED = 1;

	public static final Short STATUS_DISABLED = 2;

	public static final Short STATUS_INST_DISABLED = 3;
	public static final Short STATUS_NOTDEPLOYED = Short.valueOf((short) 0);

	public static final Short STATUS_SUSPEND = Short.valueOf((short) -1);
	public static final String TABLE_NAME = "bpm_definition";
	public static final Short DISABLEStATUS_DA = Short.valueOf((short) 1);

	public static final Short DISABLEStATUS_EN = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long defId;

	@XmlAttribute
	protected Long typeId;

	@XmlAttribute
	protected String subject;

	@XmlAttribute
	protected String defKey;

	@XmlAttribute
	protected String taskNameRule;

	@XmlAttribute
	protected String descp;

	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	@XmlAttribute
	protected Date createtime;

	@XmlAttribute
	protected Short status;

	@XmlAttribute
	protected String defXml;

	@XmlAttribute
	protected Long actDeployId;

	@XmlAttribute
	protected String actDefKey;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected Long createBy;

	@XmlAttribute
	protected Long updateBy;

	@XmlAttribute
	protected String reason;

	@XmlAttribute
	protected Integer versionNo;

	@XmlAttribute
	protected Long parentDefId;

	@XmlAttribute
	protected Short isMain;

	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	@XmlAttribute
	protected Date updatetime;
	protected String typeName;

	@XmlAttribute
	protected Short needStartForm = Short.valueOf((short) 1);

	@XmlAttribute
	protected Short toFirstNode = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short showFirstAssignee = Short.valueOf((short) 0);
	protected String startFirstNode;
	protected String canChoicePath;
	private Map canChoicePathNodeMap;
	private Short isUseOutForm = Short.valueOf((short) 0);
	protected String formDetailUrl;
	protected Short disableStatus = Short.valueOf((short) 0);
	@XmlAttribute
	private Integer directstart = Integer.valueOf(0);

	public Map getCanChoicePathNodeMap() {
		if (canChoicePathNodeMap == null) {
			Map map = new HashMap();
			if (StringUtil.isEmpty(canChoicePath)) {
				canChoicePathNodeMap = map;
				return canChoicePathNodeMap;
			}
			Pattern regex = Pattern.compile("(\\w+):(\\w+)");
			Matcher regexMatcher = regex.matcher(canChoicePath);
			while (regexMatcher.find()) {
				map.put(regexMatcher.group(1), regexMatcher.group(2));
			}
			canChoicePathNodeMap = map;
		}
		return canChoicePathNodeMap;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Short getIsUseOutForm() {
		return isUseOutForm;
	}

	public void setIsUseOutForm(Short isUseOutForm) {
		this.isUseOutForm = isUseOutForm;
	}

	public Long getDefId() {
		return defId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getFormDetailUrl() {
		return formDetailUrl;
	}

	public void setFormDetailUrl(String formDetailUrl) {
		this.formDetailUrl = formDetailUrl;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	public String getDefKey() {
		return defKey;
	}

	public void setTaskNameRule(String taskNameRule) {
		this.taskNameRule = taskNameRule;
	}

	public String getTaskNameRule() {
		return taskNameRule;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getStartFirstNode() {
		return startFirstNode;
	}

	public void setStartFirstNode(String startFirstNode) {
		this.startFirstNode = startFirstNode;
	}

	public String getDescp() {
		return descp;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return status;
	}

	public void setDefXml(String defXml) {
		this.defXml = defXml;
	}

	public String getDefXml() {
		return defXml;
	}

	public void setActDeployId(Long actDeployId) {
		this.actDeployId = actDeployId;
	}

	public Long getActDeployId() {
		return actDeployId;
	}

	public void setActDefKey(String actDefKey) {
		this.actDefKey = actDefKey;
	}

	public String getActDefKey() {
		return actDefKey;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setParentDefId(Long parentDefId) {
		this.parentDefId = parentDefId;
	}

	public Long getParentDefId() {
		return parentDefId;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public Short getIsMain() {
		return isMain;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Short getShowFirstAssignee() {
		return showFirstAssignee;
	}

	public void setShowFirstAssignee(Short showFirstAssignee) {
		this.showFirstAssignee = showFirstAssignee;
	}

	public Short getToFirstNode() {
		return toFirstNode;
	}

	public void setToFirstNode(Short toFirstNode) {
		this.toFirstNode = toFirstNode;
	}

	public Short getNeedStartForm() {
		return needStartForm;
	}

	public void setNeedStartForm(Short needStartForm) {
		this.needStartForm = needStartForm;
	}

	public String getCanChoicePath() {
		return canChoicePath;
	}

	public void setCanChoicePath(String canChoicePath) {
		this.canChoicePath = canChoicePath;
	}

	public Short getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(Short disableStatus) {
		this.disableStatus = disableStatus;
	}

	public void updateCanChoicePath() {
		if (canChoicePathNodeMap != null) {
			StringBuffer sb = new StringBuffer();
			Iterator iter = canChoicePathNodeMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				sb.append(",");
				sb.append(key);
				sb.append(":");
				sb.append(val);
			}
			canChoicePath = sb.toString().replaceFirst(",", "");
		}
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmDefinition)) {
			return false;
		}
		BpmDefinition rhs = (BpmDefinition) object;
		return new EqualsBuilder().append(defId, rhs.defId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(defId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("defId", defId).append("typeId", typeId).append("subject", subject)
				.append("defKey", defKey).append("taskNameRule", taskNameRule).append("descp", descp)
				.append("createtime", createtime).append("status", status).append("defXml", defXml)
				.append("actDeployId", actDeployId).append("actDefKey", actDefKey).append("actDefId", actDefId)
				.append("createBy", createBy).append("updateBy", updateBy).append("reason", reason)
				.append("versionNo", versionNo).append("parentDefId", parentDefId).append("isMain", isMain)
				.append("updatetime", updatetime).toString();
	}

	public Object clone() {
		BpmDefinition obj = null;
		try {
			obj = (BpmDefinition) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return defId;
	}

	@Override
	public void setId(Long id) {
		defId = id;
	}

	public Integer getDirectstart() {
		return directstart;
	}

	public void setDirectstart(Integer directstart) {
		this.directstart = directstart;
	}

}