package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.cloudstong.platform.core.util.StringUtil;

@XmlRootElement(name="bpmUserCondition")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmUserCondition 
  implements Cloneable, Comparable<BpmUserCondition>
{
  public static final short OPERATE_TYPE_EQUAL = 0;
  public static final short OPERATE_TYPE_UNEQUAL = 1;
  public static final short OPERATE_TYPE_MORE_THAN = 2;
  public static final short OPERATE_TYPE_MORE_EQUAL_THAN = 3;
  public static final short OPERATE_TYPE_LESS_THAN = 4;
  public static final short OPERATE_TYPE_LESS_EQUAL_THAN = 5;
  public static final short OPERATE_TYPE_LIKE = 6;
  public static final short OPERATE_TYPE_UNLIKE = 7;
  public static final String VARIABLE_TYPE_NUMBER = "number";
  public static final String VARIABLE_TYPE_DATE = "date";
  public static final String VARIABLE_TYPE_STRING = "varchar";
  public static final int CONDITION_TYPE_EXEUSER = 0;
  public static final int CONDITION_TYPE_COPYUSER = 1;
  public static final int CONDITION_TYPE_MSG_MAIL_RECEIVER = 3;
  public static final int CONDITION_TYPE_MSG_MAIL_COPYTO = 4;
  public static final int CONDITION_TYPE_MSG_MAIL_BCC = 5;
  public static final int CONDITION_TYPE_MSG_INNER_RECEIVER = 6;
  public static final int CONDITION_TYPE_MSG_MOBILE_RECEIVER = 7;

  @XmlAttribute
  protected Long id = Long.valueOf(0L);

  @XmlAttribute
  protected String actdefid = "";

  @XmlAttribute
  protected String nodeid = "";

  @XmlAttribute
  protected String condition = "";

  @XmlAttribute
  protected Long sn = Long.valueOf(0L);

  @XmlAttribute
  protected String formIdentity = "";

  @XmlAttribute
  protected String conditionname = "";

  @XmlAttribute
  protected String conditionShow = "";

  @XmlAttribute
  protected Long setId = Long.valueOf(0L);

  @XmlAttribute
  protected Integer conditionType = Integer.valueOf(0);

  @XmlAttribute
  protected Integer groupNo = Integer.valueOf(1);

  public String getConditionShow()
  {
    return this.conditionShow;
  }
  public void setConditionShow(String conditionShow) {
    this.conditionShow = conditionShow;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId()
  {
    return this.id;
  }

  public void setActdefid(String actdefid) {
    this.actdefid = actdefid;
  }

  public String getActdefid()
  {
    return this.actdefid;
  }

  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
  }

  public String getNodeid()
  {
    return this.nodeid;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getCondition()
  {
    return this.condition;
  }
  public Long getSn() {
    return this.sn;
  }
  public void setSn(Long sn) {
    this.sn = sn;
  }
  public String getFormIdentity() {
    return this.formIdentity;
  }
  public void setFormIdentity(String formIdentity) {
    this.formIdentity = formIdentity;
  }
  public String getConditionname() {
    return this.conditionname;
  }
  public void setConditionname(String conditionname) {
    this.conditionname = conditionname;
  }
  public Long getSetId() {
    return this.setId;
  }
  public void setSetId(Long setId) {
    this.setId = setId;
  }
  public Integer getConditionType() {
    return this.conditionType;
  }
  public void setConditionType(Integer conditionType) {
    this.conditionType = conditionType;
  }
  public Integer getGroupNo() {
    return this.groupNo;
  }
  public void setGroupNo(Integer groupNo) {
    this.groupNo = groupNo;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof BpmUserCondition))
    {
      return false;
    }
    BpmUserCondition rhs = (BpmUserCondition)object;
    return new EqualsBuilder().append(this.id, rhs.id).isEquals();
  }

  public int hashCode()
  {
    return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.actdefid).append(this.nodeid).append(this.condition).append(this.sn).append(this.conditionname).append(this.conditionShow).append(this.setId).append(this.groupNo).toHashCode();
  }

  public String toString()
  {
    return new ToStringBuilder(this).append("id", this.id).append("actdefid", this.actdefid).append("nodeid", this.nodeid).append("condition", this.condition).append("sn", this.sn).append("conditionname", this.conditionname).append("conditionShow", this.conditionShow).append("setId", this.setId).append("groupNo", this.groupNo).toString();
  }

  public Object clone()
  {
    BpmUserCondition obj = null;
    try {
      obj = (BpmUserCondition)super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return obj;
  }

  public List<ConditionJsonStruct> getConditionJson() {
    if (StringUtil.isEmpty(this.condition)) {
      return new ArrayList();
    }
    List conditionJson = JSONArray.parseArray(this.condition, ConditionJsonStruct.class);
    return conditionJson;
  }


  public int compareTo(BpmUserCondition condition)
  {
    if (getGroupNo() == null)
      return -1;
    if (condition.getGroupNo() == null) {
      return 1;
    }
    return getGroupNo().compareTo(condition.getGroupNo());
  }
}