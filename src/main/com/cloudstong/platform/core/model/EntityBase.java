package com.cloudstong.platform.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * @author Allan
 * 
 *         Created on 2014-08-12
 * 
 *         Description: 实体的基类
 */
@MappedSuperclass
public abstract class EntityBase implements Serializable {
	public abstract Long getId();

	public abstract void setId(Long id);

	/**
	 * 版本号
	 */
	@Version
	@Column(name = "comm_opt_counter")
	protected Long optCounter = 0L;

	/**
	 * 创建人
	 */
	@Column(name = "comm_createBy")
	protected Long createBy;

	/**
	 * 创建人姓名
	 */
	@Transient
	protected String createUser;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comm_createDate", length = 7)
	protected Date createDate;

	/**
	 * 修改人
	 */
	@Column(name = "comm_updateBy")
	protected Long updateBy;

	/**
	 * 修改人姓名
	 */
	@Transient
	protected String updateUser;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comm_updateDate", length = 7)
	protected Date updateDate;

	/**
	 * 删除标记
	 */
	@Column(name = "comm_mark_for_delete")
	protected boolean isDeleted = false;

	public Long getOptCounter() {
		return optCounter;
	}

	public void setOptCounter(Long optCounter) {
		this.optCounter = optCounter;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
