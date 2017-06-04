package com.cloudstong.platform.third.bpm.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class BpmTaskComment extends EntityBase {
	protected Long commentId;
	protected Long runId;
	protected Long authorId;
	protected String author;
	protected Date commentTime;
	protected String content;
	protected String nodeName;
	protected Long taskId;
	protected String actDefId;

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
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

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmTaskComment)) {
			return false;
		}
		BpmTaskComment rhs = (BpmTaskComment) object;
		return new EqualsBuilder().append(commentId, rhs.commentId).append(runId, rhs.runId).append(authorId, rhs.authorId)
				.append(author, rhs.author).append(commentTime, rhs.commentTime).append(content, rhs.content).append(nodeName, rhs.nodeName)
				.append(taskId, rhs.taskId).append(actDefId, rhs.actDefId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(commentId).append(runId).append(authorId).append(author).append(commentTime)
				.append(content).append(nodeName).append(taskId).append(actDefId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("commentId", commentId).append("runId", runId).append("authorId", authorId).append("author", author)
				.append("commentTime", commentTime).append("content", content).append("nodeName", nodeName).append("taskId", taskId)
				.append("actDefId", actDefId).toString();
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