/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.model.Contact;
import com.cloudstong.platform.email.service.ContactService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/contact")
public class ContactAction extends BaseAction {

	private static final long serialVersionUID = 5942032284128860723L;

	@Resource
	private ContactService contactService;

	private Contact contact;

	@Action("save")
	public String save() {
		contactService.doSaveOrUpdate(contact);
		printSuccess(contact);
		return NONE;
	}

	@Action("jsonListAll")
	public String jsonListAll() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		List<Contact> list = contactService.getAllContactOrderByLastUsedTime(user);
		printObject(list);
		return NONE;
	}

	@Action("ztreeList")
	public String ztreeList() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		List<Contact> list = contactService.getAllContactOrderByLastUsedTime(user);
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		for (Contact c : list) {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id", c.getId());
			treeMap.put("name", c.getEmail());
			tree.add(treeMap);
		}
		printObject(tree);
		return NONE;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
