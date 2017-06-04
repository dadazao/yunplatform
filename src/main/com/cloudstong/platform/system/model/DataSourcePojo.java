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
 * @author liuqi
 * Created on 2014-10-9 15:14:37  
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class DataSourcePojo extends EntityBase{

	private static final long serialVersionUID = 4657823452297518640L;

	private Long id;
	
	/**
	 * 数据源名称
	 */
	private String dsName;
	
	/**
	 * 连接字串
	 */
	private String dsUrl;
	
	/**
	 * 数据库驱动
	 */
	private String dsDriver;
	
	/**
	 * 连接编码
	 */
	private String dsEncoding;
	
	/**
	 * 密码
	 */
	private String dsPasswd;
	
	/**
	 * 用户名
	 */
	private String dsUser;
	
	/**
	 * 数据库类型
	 */
	private String dsType;
	
	/**
	 * 是否默认
	 */
	private String dsStatus;
	
	/**
	 * 修改时间
	 */
	private Date xiugaishijian;
	
	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void setDsUrl(String dsUrl){
		this.dsUrl = dsUrl;
	}
	
	public String getDsUrl() {
		return dsUrl;
	}
	
	public void setDsDriver(String dsDriver){
		this.dsDriver = dsDriver;
	}
	
	public String getDsDriver() {
		return dsDriver;
	}
	
	public void setDsEncoding(String dsEncoding){
		this.dsEncoding = dsEncoding;
	}
	
	public String getDsEncoding() {
		return dsEncoding;
	}
	
	public void setDsPasswd(String dsPasswd){
		this.dsPasswd = dsPasswd;
	}
	
	public String getDsPasswd() {
		return dsPasswd;
	}
	
	public void setDsUser(String dsUser){
		this.dsUser = dsUser;
	}
	
	public String getDsUser() {
		return dsUser;
	}
	
	public void setDsType(String dsType){
		this.dsType = dsType;
	}
	
	public String getDsType() {
		return dsType;
	}
	
	public void setDsStatus(String dsStatus){
		this.dsStatus = dsStatus;
	}
	
	public String getDsStatus() {
		return dsStatus;
	}
	
	public void setXiugaishijian(Date xiugaishijian){
		this.xiugaishijian = xiugaishijian;
	}
	
	public Date getXiugaishijian() {
		return xiugaishijian;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	public String getRemark() {
		return remark;
	}
	
}