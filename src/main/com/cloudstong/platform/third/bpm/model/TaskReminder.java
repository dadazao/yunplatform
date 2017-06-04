package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "taskReminder")
@XmlAccessorType(XmlAccessType.NONE)
public class TaskReminder extends EntityBase {

	@XmlAttribute
	protected Long taskDueId;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected Integer reminderStart;

	@XmlAttribute
	protected Integer reminderEnd;

	@XmlAttribute
	protected Integer times;

	@XmlAttribute
	protected String mailContent;

	@XmlAttribute
	protected String msgContent;

	@XmlAttribute
	protected String smsContent;

	@XmlAttribute
	protected Integer action;

	@XmlAttribute
	protected String script;

	@XmlAttribute
	protected Integer completeTime;

	@XmlAttribute
	protected String condExp;

	@XmlAttribute
	protected String name;

	@XmlAttribute
	protected String relativeNodeId;

	@XmlAttribute
	protected Integer relativeNodeType;

	@XmlAttribute
	protected Integer relativeTimeType;

	@XmlAttribute
	protected Long assignerId;

	@XmlAttribute
	protected String assignerName;

	public void setTaskDueId(Long taskDueId) {
		this.taskDueId = taskDueId;
	}

	public Long getTaskDueId() {
		return taskDueId;
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

	public void setReminderStart(Integer reminderStart) {
		this.reminderStart = reminderStart;
	}

	public Integer getReminderStart() {
		return reminderStart;
	}

	public void setReminderEnd(Integer reminderEnd) {
		this.reminderEnd = reminderEnd;
	}

	public Integer getReminderEnd() {
		return reminderEnd;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getTimes() {
		return times;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getAction() {
		return action;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getCompleteTime() {
		return completeTime;
	}

	public String getCondExp() {
		return condExp;
	}

	public void setCondExp(String condExp) {
		this.condExp = condExp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelativeNodeId() {
		return relativeNodeId;
	}

	public void setRelativeNodeId(String relativeNodeId) {
		this.relativeNodeId = relativeNodeId;
	}

	public Integer getRelativeNodeType() {
		return relativeNodeType;
	}

	public void setRelativeNodeType(Integer relativeNodeType) {
		this.relativeNodeType = relativeNodeType;
	}

	public Integer getRelativeTimeType() {
		return relativeTimeType;
	}

	public void setRelativeTimeType(Integer relativeTimeType) {
		this.relativeTimeType = relativeTimeType;
	}

	public Long getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(Long assignerId) {
		this.assignerId = assignerId;
	}

	public String getAssignerName() {
		return assignerName;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskReminder)) {
			return false;
		}
		TaskReminder rhs = (TaskReminder) object;
		return new EqualsBuilder().append(taskDueId, rhs.taskDueId).append(actDefId, rhs.actDefId).append(nodeId, rhs.nodeId)
				.append(reminderStart, rhs.reminderStart).append(reminderEnd, rhs.reminderEnd).append(times, rhs.times)
				.append(mailContent, rhs.mailContent).append(msgContent, rhs.msgContent).append(smsContent, rhs.smsContent)
				.append(action, rhs.action).append(script, rhs.script).append(completeTime, rhs.completeTime).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(taskDueId).append(actDefId).append(nodeId).append(reminderStart).append(reminderEnd)
				.append(times).append(mailContent).append(msgContent).append(smsContent).append(action).append(script).append(completeTime)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("taskDueId", taskDueId).append("actDefId", actDefId).append("nodeId", nodeId)
				.append("reminderStart", reminderStart).append("reminderEnd", reminderEnd).append("times", times).append("mailContent", mailContent)
				.append("msgContent", msgContent).append("smsContent", smsContent).append("action", action).append("script", script)
				.append("completeTime", completeTime).toString();
	}

	public TimeDay getReminderStartTimeDay() {
		TimeDay timeDay = new TimeDay();
		int reminderStartDay = reminderStart.intValue() / 1440;
		int reminderStartHour = (reminderStart.intValue() - reminderStartDay * 1440) / 60;
		int reminderStartMinute = reminderStart.intValue() - reminderStartDay * 1440 - reminderStartHour * 60;
		timeDay.setDay(reminderStartDay);
		timeDay.setHour(reminderStartHour);
		timeDay.setMinute(reminderStartMinute);
		return timeDay;
	}

	public TimeDay getCompleteTimeTimeDay() {
		TimeDay timeDay = new TimeDay();
		int completeTimeDay = completeTime.intValue() / 1440;
		int completeTimeHour = (completeTime.intValue() - completeTimeDay * 1440) / 60;
		int completeTimeMinute = completeTime.intValue() - completeTimeDay * 1440 - completeTimeHour * 60;
		timeDay.setDay(completeTimeDay);
		timeDay.setHour(completeTimeHour);
		timeDay.setMinute(completeTimeMinute);
		return timeDay;
	}

	public TimeDay getReminderEndTimeDay() {
		TimeDay timeDay = new TimeDay();
		int reminderEndDay = reminderEnd.intValue() / 1440;
		int reminderEndHour = (reminderEnd.intValue() - reminderEndDay * 1440) / 60;
		int reminderEndMinute = reminderEnd.intValue() - reminderEndDay * 1440 - reminderEndHour * 60;
		timeDay.setDay(reminderEndDay);
		timeDay.setHour(reminderEndHour);
		timeDay.setMinute(reminderEndMinute);
		return timeDay;
	}

	public class TimeDay {
		int day;
		int hour;
		int minute;

		public TimeDay() {
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.minute = minute;
		}
	}

	@Override
	public Long getId() {
		return taskDueId;
	}

	@Override
	public void setId(Long id) {
		taskDueId = id;
	}

}