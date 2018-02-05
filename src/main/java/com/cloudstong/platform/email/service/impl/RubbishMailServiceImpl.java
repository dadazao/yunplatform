/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.dao.RubbishMailDao;
import com.cloudstong.platform.email.model.RubbishMail;
import com.cloudstong.platform.email.model.mapper.JavaBeanPropertyRowMapper;
import com.cloudstong.platform.email.service.RubbishMailService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
@Service("rubbishMailService")
public class RubbishMailServiceImpl implements RubbishMailService {

	@Resource
	private RubbishMailDao rubbishMailDao;

	@Override
	public PageResult queryRubbishMail(QueryCriteria queryCriteria) {
		return rubbishMailDao.query(queryCriteria, new JavaBeanPropertyRowMapper<RubbishMail>(RubbishMail.class));
	}

	@Override
	public RubbishMail findRubbishMailById(Long id) {
		return rubbishMailDao.selectById(id, new JavaBeanPropertyRowMapper<RubbishMail>(RubbishMail.class));
	}

	@Override
	public void doDeletePermanently(Long[] rubbishMailIDs) {
		for (Long id : rubbishMailIDs) {
			int type = rubbishMailDao.getRubbishMailTypeById(id);
			if (type == 1) {// 收件箱
				String delInboxSql = "delete from sys_inbox where id = ?";
				rubbishMailDao.update(delInboxSql, new Object[] { id });
			} else { // 发件箱
				String delOutboxSql = "delete from sys_outbox where id = ? ";
				rubbishMailDao.update(delOutboxSql, new Object[] { id });
			}
			rubbishMailDao.update("delete from sys_mailrubbish where id = ?", new Object[] { id });
		}
	}

	@Override
	public void doResetState(Long[] rubbishMailIDs) {
		for (Long id : rubbishMailIDs) {
			int type = rubbishMailDao.getRubbishMailTypeById(id);
			if (type == 1) {// 收件箱
				rubbishMailDao.update("update sys_inbox set comm_mark_for_delete = 0 where id = ?", new Object[] { id });
			} else { // 发件箱
				rubbishMailDao.update("update sys_outbox set comm_mark_for_delete = 0 where id = ?", new Object[] { id });
			}
			rubbishMailDao.update("delete from sys_mailrubbish where id = ?", new Object[] { id });
		}
	}
}
