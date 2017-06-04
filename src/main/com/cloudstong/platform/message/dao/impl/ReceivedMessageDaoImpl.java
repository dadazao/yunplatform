/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.message.dao.ReceivedMessageDao;
import com.cloudstong.platform.message.model.MessageType;
import com.cloudstong.platform.message.model.ReceivedMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
@Repository("receivedMessageDao")
public class ReceivedMessageDaoImpl extends BaseJdbcDaoImpl<ReceivedMessage, Long> implements ReceivedMessageDao {

	@Override
	public String getTable() {
		return "sys_receivedmessage";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageResult query(QueryCriteria queryCriteria, RowMapper rowMap) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append("select rm.id as id,rm.comm_createBy as comm_createBy,rm.comm_createDate as comm_createDate,rm.comm_updateBy as comm_updateBy,"
				+ "rm.comm_updateDate as comm_updateDate,rm.comm_opt_counter as comm_opt_counter,rm.comm_mark_for_delete as comm_mark_for_delete,"
				+ "rm.tbl_receiver as tbl_receiver,rm.tbl_sender as tbl_sender,rm.tbl_subject as tbl_subject,rm.tbl_content as tbl_content,rm.tbl_date as tbl_date,"
				+ "rm.tbl_read as tbl_read,rm.tbl_replystate as tbl_replystate,rm.tbl_sentmessageid as tbl_sentmessageid,rm.tbl_sendername as tbl_sendername,"
				+ "dic.tbl_name as tbl_type  from sys_receivedmessage rm left join sys_dictionarys dic on rm.tbl_type=dic.tbl_value and dic.tbl_parentId = ? where 1=1 ");

		List paramValueList = new ArrayList();
		paramValueList.add(MessageType.DICTIONARY_ID);
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());
		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by rm." + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}

		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List contentList = jdbcTemplate.query(pageSql, paramValueList.toArray(), rowMap);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);

		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected String assembWhere(Map queryCondition, List paramValueList) {
		StringBuffer where = new StringBuffer(" ");
		Set<Map.Entry> entrySet = (Set<Map.Entry>) (queryCondition.entrySet());
		for (Map.Entry entry : entrySet) {
			where.append(" and rm.").append((String) entry.getKey());
			String v = null;
			if (entry.getValue() instanceof String) {
				v = ((String) entry.getValue()).trim();
			}
			if (v != null) {
				if (v.startsWith("%") || v.endsWith("%")) {
					where.append(" like ?");
				} else if (v.startsWith(">")) {
					where.append(" > ? ");
					v = v.substring(1);
				} else if (v.startsWith("<")) {
					where.append(" < ? ");
					v = v.substring(1);
				} else if (v.startsWith("=")) {
					where.append(" = ? ");
					v = v.substring(1);
				} else if (v.startsWith(">=")) {
					where.append(" >= ? ");
					v = v.substring(2);
				} else if (v.startsWith("<=")) {
					where.append(" <= ? ");
					v = v.substring(2);
				} else {
					where.append(" = ? ");
				}
				paramValueList.add(v);
			} else {
				where.append(" = ? ");
				paramValueList.add(entry.getValue());
			}
		}
		return where.toString();
	}

}
