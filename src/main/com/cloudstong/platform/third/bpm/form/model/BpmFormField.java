package com.cloudstong.platform.third.bpm.form.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.StringUtil;

@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormField extends EntityBase implements Cloneable {
	public static short VALUE_FROM_FORM = 0;
	public static short VALUE_FROM_SCRIPT_SHOW = 1;
	public static short VALUE_FROM_SCRIPT_HIDDEN = 2;
	public static short VALUE_FROM_IDENTITY = 3;

	public static short COND_TYPE_FORM = 0;

	public static short COND_TYPE_SCRIPT = 1;

	public static short COND_TYPE_FIX = 2;
	public static final String FieldHidden = "ID";
	public static final short Selector_User = 4;
	public static final short Selector_UserMulti = 8;
	public static final short Selector_Role = 5;
	public static final short Selector_Org = 6;
	public static final short Selector_Position = 7;
	public static final short ATTACHEMENT = 9;
	public static String ElmName = "field";

	@XmlAttribute
	protected Long fieldId = Long.valueOf(0L);

	@XmlAttribute
	protected Long tableId = Long.valueOf(0L);

	@XmlAttribute
	protected String fieldName = "";

	@XmlAttribute
	protected String fieldType = "";

	@XmlAttribute
	protected Short isRequired;

	@XmlAttribute
	protected Short isList;

	@XmlAttribute
	protected Short isQuery;

	@XmlAttribute
	protected String fieldDesc = "";

	@XmlAttribute
	protected Integer charLen;

	@XmlAttribute
	protected Integer intLen;

	@XmlAttribute
	protected Integer decimalLen;
	protected String dictType = "";

	@XmlAttribute
	protected Short isDeleted = Short.valueOf((short) 0);

	@XmlAttribute
	protected String validRule = "";

	@XmlAttribute
	protected String originalName = "";
	protected Integer sn;

	@XmlAttribute
	protected Short valueFrom = Short.valueOf((short) 0);

	@XmlAttribute
	protected String script = "";

	@XmlAttribute
	protected Short controlType;

	@XmlAttribute
	protected Short isHidden = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short isFlowVar = Short.valueOf((short) 0);

	@XmlAttribute
	protected String identity = "";

	@XmlAttribute
	protected String options = "";

	@XmlAttribute
	protected String ctlProperty = "";

	protected boolean isAdded = false;

	protected Short isAllowMobile = Short.valueOf((short) 0);

	protected Short isDateString = Short.valueOf((short) 0);

	protected Short isCurrentDateStr = Short.valueOf((short) 0);
	protected String style;
	protected Integer isPk = Integer.valueOf(0);

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Short getIsAllowMobile() {
		return isAllowMobile;
	}

	public void setIsAllowMobile(Short isAllowMobile) {
		this.isAllowMobile = isAllowMobile;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setIsRequired(Short isRequired) {
		this.isRequired = isRequired;
	}

	public Short getIsRequired() {
		return isRequired;
	}

	public void setIsList(Short isList) {
		this.isList = isList;
	}

	public Short getIsList() {
		return isList;
	}

	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}

	public Short getIsQuery() {
		return isQuery;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setCharLen(Integer charLen) {
		this.charLen = charLen;
	}

	public Integer getCharLen() {
		return charLen;
	}

	public void setIntLen(Integer intLen) {
		this.intLen = intLen;
	}

	public Integer getIntLen() {
		return intLen;
	}

	public void setDecimalLen(Integer decimalLen) {
		this.decimalLen = decimalLen;
	}

	public Integer getDecimalLen() {
		return decimalLen;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setValidRule(String validRule) {
		this.validRule = validRule;
	}

	public String getValidRule() {
		return validRule;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return sn;
	}

	public void setValueFrom(Short valueFrom) {
		this.valueFrom = valueFrom;
	}

	public Short getValueFrom() {
		return valueFrom;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public void setControlType(Short controlType) {
		this.controlType = controlType;
	}

	public Short getControlType() {
		return controlType;
	}

	public Short getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Short isHidden) {
		this.isHidden = isHidden;
	}

	public Short getIsFlowVar() {
		return isFlowVar;
	}

	public void setIsFlowVar(Short isFlowVar) {
		this.isFlowVar = isFlowVar;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getIsPk() {
		return isPk;
	}

	public void setIsPk(Integer isPk) {
		this.isPk = isPk;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String[] getAryOptions() {
		return options.split("\n");
	}

	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	public Short getIsDateString() {
		return isDateString;
	}

	public void setIsDateString(Short isDateString) {
		this.isDateString = isDateString;
	}

	public Short getIsCurrentDateStr() {
		return isCurrentDateStr;
	}

	public void setIsCurrentDateStr(Short isCurrentDateStr) {
		this.isCurrentDateStr = isCurrentDateStr;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCtlProperty() {
		return ctlProperty;
	}

	public void setCtlProperty(String ctlProperty) {
		this.ctlProperty = ctlProperty;
	}

	public Map<String, String> getPropertyMap() {
		Map map = new HashMap();
		if (StringUtil.isEmpty(ctlProperty))
			return map;
		try {
			JSONObject jsonObject = JSONObject.fromObject(ctlProperty);
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObject.getString(key);
				map.put(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public String getFieldTypeDisplay() {
		if ("varchar".equals(fieldType)) {
			return "字符串,varchar(" + charLen + ")";
		}
		if ("number".equals(fieldType)) {
			if (decimalLen.intValue() == 0) {
				return "数字,number(" + intLen + ")";
			}
			return "数字,number(" + intLen + "," + decimalLen + ")";
		}
		if ("date".equals(fieldType)) {
			return "日期,date";
		}
		if ("clob".equals(fieldType)) {
			return "大文本";
		}
		return "字符串";
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormField)) {
			return false;
		}
		BpmFormField rhs = (BpmFormField) object;
		return new EqualsBuilder().append(fieldName, rhs.fieldName).append(fieldDesc, rhs.fieldDesc).append(fieldType, rhs.fieldType)
				.append(charLen, rhs.charLen).append(intLen, rhs.intLen).append(decimalLen, rhs.decimalLen).isEquals();
	}

	public Object clone() {
		BpmFormField obj = null;
		try {
			obj = (BpmFormField) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(tableId).append(fieldName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("fieldName", fieldName).toString();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub need to be implemented.
		
	}
}