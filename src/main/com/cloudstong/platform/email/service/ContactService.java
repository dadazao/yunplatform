/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service;

import java.util.List;
import java.util.Set;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.model.Contact;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
public interface ContactService {

	public Contact findContactById(Long id);

	public List<Contact> getAllContactOrderByLastUsedTime(SysUser user);

	public PageResult queryContact(QueryCriteria queryCriteria);

	public void doUpdateContact(Contact contact);

	public void doSaveContact(Contact contact);

	public void doDeleteContacts(Long[] ids);

	public void doSaveOrUpdate(Contact contact);

	public void doUpdateContactLastUsedTime(Set<String> emailSet);

	/**
	 * Description: 插入新的联系人
	 * 
	 * @param emailSet
	 * @param userid
	 */
	public void doInsertNewContact(Set<String> emailSet, Long userid);

}
