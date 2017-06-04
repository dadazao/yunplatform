/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.email.dao.MailAccountDao;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.model.mapper.MailAccountMapper;
import com.cloudstong.platform.email.service.MailAccountService;
import com.cloudstong.platform.system.model.SysUser;

@Service("mailAccountService")
public class MailAccountServiceImpl implements MailAccountService {

	@Resource
	private MailAccountDao mailAccountDao;

	@Override
	public MailAccount findMailAccountById(Long id) {
		return mailAccountDao.selectById(id, new MailAccountMapper());
	}

	@Override
	public List<MailAccount> getAllMailAccount() {
		return mailAccountDao.getAll(new MailAccountMapper());
	}

	@Override
	public PageResult queryMailAccount(QueryCriteria queryCriteria) {
		return mailAccountDao.query(queryCriteria, new MailAccountMapper());
	}

	@Override
	public void doUpdateMailAccount(MailAccount mailAccount) {
		String sql = "UPDATE sys_mailaccount SET tbl_name = ?,tbl_mailtype=?,tbl_address=?,tbl_password=?,tbl_smtphost=?,"
				+ "tbl_smtpport = ?,tbl_pophost=?,tbl_popport=?,tbl_deflt=? WHERE id = ?";
		mailAccountDao.update(
				sql,
				new Object[] { mailAccount.getName(), mailAccount.getMailType(), mailAccount.getAddress(), mailAccount.getPassword(),
						mailAccount.getSmtpHost(), mailAccount.getSmtpPort(), mailAccount.getPopHost(), mailAccount.getPopPort(),
						mailAccount.getDeflt(), mailAccount.getId() });
	}

	@Override
	public void doSaveMailAccount(MailAccount mailAccount) {
		mailAccount.setId(UniqueIdUtil.genId());
		String sql = " INSERT INTO sys_mailaccount (id,tbl_name,tbl_mailtype,tbl_address,tbl_password,tbl_smtphost,"
				+ "tbl_smtpport,tbl_pophost,tbl_popport,tbl_userid) VALUES (?,?,?,?,?,?,?,?,?,?)";
		mailAccountDao.insert(sql, new Object[] { mailAccount.getId(), mailAccount.getName(), mailAccount.getMailType(), mailAccount.getAddress(),
				mailAccount.getPassword(), mailAccount.getSmtpHost(), mailAccount.getSmtpPort(), mailAccount.getPopHost(), mailAccount.getPopPort(),
				mailAccount.getUserId() });
	}

	/**
	 * 1.删除账号 2.删除垃圾箱、收件箱、发件箱相关内容 3.删除邮箱附件表中的内容
	 */
	@Override
	public void doDeleteMailAccounts(Long[] ids, Long userId) {
		for (Long id : ids) {
			mailAccountDao.deleteCascade(id, userId);
		}
	}

	@Override
	public List<MailAccount> getMailAccountByUser(SysUser user) {
		if (user == null) {
			return Collections.emptyList();
		}
		String sql = "SELECT * FROM sys_mailaccount WHERE tbl_userid = ? ORDER BY tbl_deflt DESC";
		return mailAccountDao.select(sql, new Object[] { user.getId() }, new MailAccountMapper());
	}

	@Override
	public MailAccount findMailAccountByAddress(String from) {
		if (from == null) {
			throw new IllegalArgumentException("the param: from can no be null");
		}

		String sql = "SELECT * FROM sys_mailaccount WHERE tbl_address = ? ";
		List<MailAccount> list = mailAccountDao.select(sql, new Object[] { from }, new MailAccountMapper());
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public boolean checkExist(MailAccount mailAccount, SysUser user) {
		String sql = "select count(1) from sys_mailaccount where tbl_address = ? and tbl_userid = ? ";
		int count = mailAccountDao.count(sql, new Object[] { mailAccount.getAddress(), user.getId() });
		return count > 0 ? true : false;
	}

	@Override
	public void doSetDefault(Long id, SysUser user) {
		String sqlfirst = "update sys_mailaccount set tbl_deflt = ? where tbl_userid = ?";
		mailAccountDao.update(sqlfirst, new Object[] { 0, user.getId() });

		String sqlsecond = "update sys_mailaccount set tbl_deflt = ? where id = ?";
		mailAccountDao.update(sqlsecond, new Object[] { 1, id });
	}

	@Override
	public MailAccount findDefaultMailAccount(SysUser user) {
		String sql = "SELECT * FROM sys_mailaccount WHERE tbl_deflt = ? and tbl_userid = ? ";
		List<MailAccount> list = mailAccountDao.select(sql, new Object[] { 1, user.getId() }, new MailAccountMapper());
		if (list == null || list.isEmpty()) {// 如果没设置默认，则查找第一个
			String sqldefault = "SELECT * FROM sys_mailaccount WHERE tbl_userid = ? ";
			List<MailAccount> _list = mailAccountDao.select(sqldefault, new Object[] { user.getId() }, new MailAccountMapper());
			return _list == null || _list.isEmpty() ? null : _list.get(0);
		} else {
			return list.get(0);
		}
	}
}
