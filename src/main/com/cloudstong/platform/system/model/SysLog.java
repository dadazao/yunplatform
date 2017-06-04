/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 系统日志
 * 
 */
public class SysLog extends EntityBase {

	private static final long serialVersionUID = 1570908773237207681L;
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 操作人名称
	 */
	private String operatorName;
	
	/**
	 * 操作人
	 */
	private Long operator;
	
	/**
	 * 操作模块
	 */
	private String operationModule;
	
	/**
	 * 操作类型
	 */
	private String type;
	
	/**
	 * 操作内容
	 */
	private String operationContent;
	
	/**
	 * 操作时间
	 */
	private Date operationTime;
	
	/**
	 * 操作人IP
	 */
	private String ip;
	
	/**
	 * 备注
	 */
	private String remark;

	public SysLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getOperationModule() {
		return operationModule;
	}

	public void setOperationModule(String operationModule) {
		this.operationModule = operationModule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
