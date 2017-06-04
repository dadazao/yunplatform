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
public class AuthCustomer extends EntityBase {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = 3963623849564076050L;

	private Long id;
	
	private String name;
	
	private String linkman;
	
	private String mobile;
	
	private String email;
	
	private String tel;
	
	private String address;

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
	 * @return Returns the value of linkman field with the type String.
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * @param linkman. Set the value of linkman field with the type String by the linkman parameter.
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * @return Returns the value of mobile field with the type String.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile. Set the value of mobile field with the type String by the mobile parameter.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return Returns the value of email field with the type String.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email. Set the value of email field with the type String by the email parameter.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the value of tel field with the type String.
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel. Set the value of tel field with the type String by the tel parameter.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return Returns the value of address field with the type String.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address. Set the value of address field with the type String by the address parameter.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
