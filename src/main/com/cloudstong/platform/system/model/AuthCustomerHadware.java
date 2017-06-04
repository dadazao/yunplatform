/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
public class AuthCustomerHadware extends EntityBase {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -1592717062361873361L;

	private Long id;
	
	private String mac;
	
	private String computerkey;
	
	private Long customerid;

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
	 * @return Returns the value of mac field with the type String.
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac. Set the value of mac field with the type String by the mac parameter.
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return Returns the value of computerkey field with the type String.
	 */
	public String getComputerkey() {
		return computerkey;
	}

	/**
	 * @param computerkey. Set the value of computerkey field with the type String by the computerkey parameter.
	 */
	public void setComputerkey(String computerkey) {
		this.computerkey = computerkey;
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
	
	
}
