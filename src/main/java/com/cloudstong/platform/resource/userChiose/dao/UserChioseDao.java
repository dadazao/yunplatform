/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.userChiose.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * Created on 2012-12-25
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public interface UserChioseDao {

	@Cacheable(value = "resourceCache", key = "getUsers")
	List<SysUser> getUsers();

}
