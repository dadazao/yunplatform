package com.cloudstong.platform.core.model;

import java.io.Serializable;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单参数
 */
public class DomainVO implements Serializable{

	/**
	 * 表名称
	 */
	private String table;

	/**
	 * 记录ID
	 */
	private Long domainId;

	public DomainVO() {
	}

	public DomainVO(String table, Long domainId) {
		super();
		this.table = table;
		this.domainId = domainId;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	@Override
	public String toString() {
		return this.domainId+this.table;
	}
	

}
