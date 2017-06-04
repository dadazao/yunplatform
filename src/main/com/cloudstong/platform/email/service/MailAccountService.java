/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-13
 * 
 *         Description:
 * 
 */
public interface MailAccountService {
	public MailAccount findMailAccountById(Long id);

	public List<MailAccount> getAllMailAccount();

	public PageResult queryMailAccount(QueryCriteria queryCriteria);

	public void doUpdateMailAccount(MailAccount mailAccount);

	public void doSaveMailAccount(MailAccount mailAccount);

	public void doDeleteMailAccounts(Long[] ids,Long userId);

	public List<MailAccount> getMailAccountByUser(SysUser user);

	public MailAccount findMailAccountByAddress(String from);

	/**
	 * Description:检查该用户的账号是否存在
	 * 
	 * @param mailAccount
	 * @param sysUser
	 * @return
	 */
	public boolean checkExist(MailAccount mailAccount, SysUser sysUser);

	public void doSetDefault(Long id, SysUser sysUser);

	/**
	 * Description:查找当前用户的默认邮箱账号
	 * 
	 * @param sysUser
	 * @return
	 */
	public MailAccount findDefaultMailAccount(SysUser sysUser);
}
