/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.message.model.SentMessage;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
public interface SentMessageDao extends BaseJdbcDao<SentMessage, Long> {

	List<Map<String, Object>> getReaderList(Long id);

	List<Map<String, Object>> getReplyedMessageList(Long id,SysUser user);

	JdbcTemplate getJdbcTemplate();

}
