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
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
public class AuthProduct extends EntityBase {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -1744131480975826023L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 产品版本 
	 */
	private String version;
	
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
	 * @return Returns the value of version field with the type String.
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version. Set the value of version field with the type String by the version parameter.
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
