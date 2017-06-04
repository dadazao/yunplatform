package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskComment extends EntityBase {
	protected Long commentId;
	protected String actDefId;
	protected Long runId;
	protected Long authorId;
	protected String author;
	protected Date commentTime;
	protected String content;
	protected String nodeName;
	protected Long taskId;
	protected String subject;

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskComment)) {
			return false;
		}
		TaskComment rhs = (TaskComment) object;
		return new EqualsBuilder().append(commentId, rhs.commentId).append(actDefId, rhs.actDefId).append(runId, rhs.runId)
				.append(authorId, rhs.authorId).append(author, rhs.author).append(commentTime, rhs.commentTime).append(content, rhs.content)
				.append(nodeName, rhs.nodeName).append(taskId, rhs.taskId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(commentId).append(actDefId).append(runId).append(authorId).append(author)
				.append(commentTime).append(content).append(nodeName).append(taskId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("commentId", commentId).append("actDefId", actDefId).append("runId", runId)
				.append("authorId", authorId).append("author", author).append("commentTime", commentTime).append("content", content)
				.append("nodeName", nodeName).append("taskId", taskId).toString();
	}

	@Override
	public Long getId() {
		return commentId;
	}

	@Override
	public void setId(Long id) {
		commentId = id;
	}

}