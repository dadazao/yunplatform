package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmRunLog extends EntityBase {
	public static final Integer OPERATOR_TYPE_START = Integer.valueOf(0);

	public static final Integer OPERATOR_TYPE_DELEGATE = Integer.valueOf(1);

	public static final Integer OPERATOR_TYPE_RETRIEVE = Integer.valueOf(2);

	public static final Integer OPERATOR_TYPE_DELETEINSTANCE = Integer.valueOf(3);

	public static final Integer OPERATOR_TYPE_AGREE = Integer.valueOf(4);

	public static final Integer OPERATOR_TYPE_OBJECTION = Integer.valueOf(5);

	public static final Integer OPERATOR_TYPE_ABSTENTION = Integer.valueOf(6);

	public static final Integer OPERATOR_TYPE_SIGN = Integer.valueOf(7);

	public static final Integer OPERATOR_TYPE_REJECT = Integer.valueOf(8);

	public static final Integer OPERATOR_TYPE_REJECT2SPONSOR = Integer.valueOf(9);

	public static final Integer OPERATOR_TYPE_DELETETASK = Integer.valueOf(10);

	public static final Integer OPERATOR_TYPE_AGENT = Integer.valueOf(11);

	public static final Integer OPERATOR_TYPE_LOCK = Integer.valueOf(13);

	public static final Integer OPERATOR_TYPE_UNLOCK = Integer.valueOf(14);

	public static final Integer OPERATOR_TYPE_ADDOPINION = Integer.valueOf(15);

	public static final Integer OPERATOR_TYPE_ASSIGN = Integer.valueOf(16);

	public static final Integer OPERATOR_TYPE_SETOWNER = Integer.valueOf(17);

	public static final Integer OPERATOR_TYPE_ENDTASK = Integer.valueOf(18);

	public static final Integer OPERATOR_TYPE_CHANGEPATH = Integer.valueOf(19);

	public static final Integer OPERATOR_TYPE_BACK = Integer.valueOf(20);

	public static final Integer OPERATOR_TYPE_ENDINSTANCE = Integer.valueOf(21);
	protected Long id;
	protected Long userid;
	protected String username;
	protected Date createtime;
	protected Integer operatortype;
	protected String memo;
	protected Long runid;
	protected String processSubject;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setOperatortype(Integer operatortype) {
		this.operatortype = operatortype;
	}

	public Integer getOperatortype() {
		return operatortype;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public Long getRunid() {
		return runid;
	}

	public void setRunid(Long runid) {
		this.runid = runid;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmRunLog)) {
			return false;
		}
		BpmRunLog rhs = (BpmRunLog) object;
		return new EqualsBuilder().append(id, rhs.id).append(userid, rhs.userid).append(username, rhs.username).append(createtime, rhs.createtime)
				.append(operatortype, rhs.operatortype).append(memo, rhs.memo).append(processSubject, rhs.processSubject).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(userid).append(username).append(createtime).append(operatortype)
				.append(memo).append(processSubject).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("userid", userid).append("username", username).append("createtime", createtime)
				.append("operatortype", operatortype).append("memo", memo).append("processSubject", processSubject).toString();
	}

	public String getProcessSubject() {
		return processSubject;
	}

	public void setProcessSubject(String processSubject) {
		this.processSubject = processSubject;
	}
}