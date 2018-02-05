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
import com.cloudstong.platform.core.util.DateUtil;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
public class AuthTemplate extends EntityBase {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = 4423371837666572632L;
	
	private Long id;
	
	private String name;
	
	private String authtype;
	
	private Date startdate;
	
	private Date enddate;
	
	private Long validdays;
	
	private Long productid;
	
	private String productname;
	
	private String startdatestr;
	
	private String enddatestr;

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
	 * @return Returns the value of name field with the type String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name. Set the value of name field with the type String by the name parameter.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the value of authtype field with the type String.
	 */
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
	 * @return Returns the value of productid field with the type Long.
	 */
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
	 * @return Returns the value of productname field with the type String.
	 */
	@Transient
	public String getProductname() {
		return productname;
	}

	/**
	 * @param productname. Set the value of productname field with the type String by the productname parameter.
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * @return Returns the value of startdatestr field with the type String.
	 */
	@Transient
	public String getStartdatestr() {
		startdatestr = (startdate != null)?DateUtil.getDateString(startdate):"";
		return startdatestr;
	}

	/**
	 * @return Returns the value of enddatestr field with the type String.
	 */
	@Transient
	public String getEnddatestr() {
		enddatestr = (enddate != null)?DateUtil.getDateString(enddate):"";
		return enddatestr;
	}
	
	

}
