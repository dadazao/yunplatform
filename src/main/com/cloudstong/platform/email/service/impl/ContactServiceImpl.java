/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.email.dao.ContactDao;
import com.cloudstong.platform.email.model.Contact;
import com.cloudstong.platform.email.model.mapper.ContactMapper;
import com.cloudstong.platform.email.service.ContactService;
import com.cloudstong.platform.email.util.MailUtils;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

	@Resource
	private ContactDao contactDao;

	@Override
	public Contact findContactById(Long id) {
		return contactDao.selectById(id, new ContactMapper());
	}

	@Override
	public List<Contact> getAllContactOrderByLastUsedTime(SysUser user) {
		if (user == null) {
			throw new IllegalArgumentException("the param:user can not be null");
		}
		String sql = "SELECT * FROM sys_contact WHERE tbl_userid = ? ORDER BY tbl_lastusedtime DESC";
		return contactDao.select(sql, new Object[] { user.getId() }, new ContactMapper());
	}

	@Override
	public PageResult queryContact(QueryCriteria queryCriteria) {
		return contactDao.query(queryCriteria);
	}

	@Override
	public void doUpdateContact(Contact contact) {
		String sql = "UPDATE sys_contact SET tbl_email= ? ,tbl_lastusedtime= ? ,tbl_userid= ?,comm_updateDate= ?  WHERE id = ? ";
		contactDao.update(sql, new Object[] { contact.getEmail(), contact.getLastUsedTime(), contact.getUserId(), new Date(), contact.getId() });
	}

	@Override
	public void doSaveContact(Contact contact) {
		contact.setId(UniqueIdUtil.genId());
		String sql = "insert into sys_contact ( id,tbl_email,tbl_lastusedtime,tbl_userid,comm_createDate,comm_createBy) values(?,?,?,?,?,?)";
		contactDao.update(sql, new Object[] { contact.getId(), contact.getEmail(), contact.getLastUsedTime(), contact.getUserId(), new Date(),
				contact.getUserId() });

	}

	@Override
	public void doDeleteContacts(Long[] ids) {
		for (Long id : ids) {
			contactDao.delete(id);
		}
	}

	@Override
	public void doSaveOrUpdate(Contact contact) {
		if (contact == null) {
			throw new IllegalArgumentException("the param:contact can not be null");
		}
		if (contact.getId() == null) {
			doSaveContact(contact);
		} else {
			doUpdateContact(contact);
		}
	}

	@Override
	public void doUpdateContactLastUsedTime(Set<String> emailSet) {
		if (emailSet != null && !emailSet.isEmpty()) {
			String sql = "update sys_contact set tbl_lastusedtime = ? where tbl_email = ? ";
			List<Object[]> batchArgs = new ArrayList<Object[]>(emailSet.size());
			Date now = new Date();
			for (String email : emailSet) {
				batchArgs.add(new Object[] { now, email });
			}
			contactDao.batchUpdate(sql, batchArgs);
		}
	}

	@Override
	public void doInsertNewContact(Set<String> emailSet, Long userid) {
		for (String email : emailSet) {
			if (!MailUtils.isEmail(email)) {
				continue;
			}
			String sql = "insert into sys_contact(id,comm_createBy,comm_createDate,tbl_email,tbl_lastusedtime,tbl_userid)" + " select "
					+ UniqueIdUtil.genId() + "," + userid + ",now(),'" + email + "',now()," + userid
					+ " from dual where not exists ( select 1 from sys_contact where tbl_email = ?)";
			contactDao.update(sql, new Object[] { email });
		}
	}
}
