/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import java.util.Date;

import javax.persistence.Transient;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author John
 * 
 *         Created on 2014-11-25
 * 
 *         Description:
 * 
 */
public class AuthKey extends EntityBase {

	public static String AUTHKEYSTATUS_UNACTIVE = "UNACTIVE";
	public static String AUTHKEYSTATUS_ACTIVE= "ACTIVED";
	public static String AUTHKEYSTATUS_UNVALID = "UNVALID";
	
	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -9177327970783689937L;
	
	private Long id;
	
	private Long templateid;
	
	private String sn;
	
	private Long customerid;
	
	private String status;
	
	//激活日期
	private Date activationdate;
	
	//失效日期
	private Date expirydate;
	
	//到期提醒日期
	private Date reminddate;
	
	//激活起始日期
	private Date startdate;
	
	//激活截止日期
	private Date enddate;
	
	//有效期
	private Long validdays;
	
	//以下为非数据库字段
	private String customername;
	
	private Long productid;
	
	private String authtype;
	
	private String templatename;

	/**
	 * @return Returns the value of id field with the type Long.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id. Set the value of id field with the type Long by the id parameter.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the value of templateid field with the type Long.
	 */
	public Long getTemplateid() {
		return templateid;
	}

	/**
	 * @param templateid. Set the value of templateid field with the type Long by the templateid parameter.
	 */
	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

	/**
	 * @return Returns the value of sn field with the type String.
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn. Set the value of sn field with the type String by the sn parameter.
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return Returns the value of customerid field with the type Long.
	 */
	public Long getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid. Set the value of customerid field with the type Long by the customerid parameter.
	 */
	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	/**
	 * @return Returns the value of status field with the type String.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status. Set the value of status field with the type String by the status parameter.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the value of activationdate field with the type Date.
	 */
	public Date getActivationdate() {
		return activationdate;
	}

	/**
	 * @param activationdate. Set the value of activationdate field with the type Date by the activationdate parameter.
	 */
	public void setActivationdate(Date activationdate) {
		this.activationdate = activationdate;
	}

	/**
	 * @return Returns the value of expirydate field with the type Date.
	 */
	public Date getExpirydate() {
		return expirydate;
	}

	/**
	 * @param expirydate. Set the value of expirydate field with the type Date by the expirydate parameter.
	 */
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	/**
	 * @return Returns the value of reminddate field with the type Date.
	 */
	public Date getReminddate() {
		return reminddate;
	}

	/**
	 * @param reminddate. Set the value of reminddate field with the type Date by the reminddate parameter.
	 */
	public void setReminddate(Date reminddate) {
		this.reminddate = reminddate;
	}

	/**
	 * @return Returns the value of startdate field with the type Date.
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate. Set the value of startdate field with the type Date by the startdate parameter.
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return Returns the value of enddate field with the type Date.
	 */
	public Date getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate. Set the value of enddate field with the type Date by the enddate parameter.
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return Returns the value of validdays field with the type Long.
	 */
	public Long getValiddays() {
		return validdays;
	}

	/**
	 * @param validdays. Set the value of validdays field with the type Long by the validdays parameter.
	 */
	public void setValiddays(Long validdays) {
		this.validdays = validdays;
	}

	/**
	 * @return Returns the value of customername field with the type String.
	 */
	@Transient
	public String getCustomername() {
		return customername;
	}

	/**
	 * @param customername. Set the value of customername field with the type String by the customername parameter.
	 */
	public void setCustomername(String customername) {
		this.customername = customername;
	}

	/**
	 * @return Returns the value of productid field with the type Long.
	 */
	@Transient
	public Long getProductid() {
		return productid;
	}

	/**
	 * @param productid. Set the value of productid field with the type Long by the productid parameter.
	 */
	public void setProductid(Long productid) {
		this.productid = productid;
	}

	/**
	 * @return Returns the value of authtype field with the type String.
	 */
	@Transient
	public String getAuthtype() {
		return authtype;
	}

	/**
	 * @param authtype. Set the value of authtype field with the type String by the authtype parameter.
	 */
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}

	/**
	 * @return Returns the value of templatename field with the type String.
	 */
	public String getTemplatename() {
		return templatename;
	}

	/**
	 * @param templatename. Set the value of templatename field with the type String by the templatename parameter.
	 */
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	
	
	
}
