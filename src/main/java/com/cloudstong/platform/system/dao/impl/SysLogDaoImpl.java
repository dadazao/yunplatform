/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysLogDao;
import com.cloudstong.platform.system.model.SysLog;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统日志操作数据库接口实现类
 * 
 */
@Repository("sysLogDao")
public class SysLogDaoImpl implements SysLogDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void doSaveLog(SysLog sysLog) {
		try {
			Long id = UniqueIdUtil.genId();
			String sql = "insert into sys_log (id,tbl_operator,tbl_operationmodule,tbl_operationcontent,tbl_operationtime,tbl_ip,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate) values (?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] {id,sysLog.getOperator(),sysLog.getOperationModule(),sysLog.getOperationContent(),sysLog.getOperationTime(),sysLog.getIp(),sysLog.getOperator(),sysLog.getOperationTime(),sysLog.getOperator(),sysLog.getOperationTime() };
			jdbcTemplate.update(sql,params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysLog> selectLogs(String sql, Object[] array, int currentIndex, int pageSize) {
		List logList = new ArrayList();
		if (pageSize == -1) {// 查询出所有记录
			logList = this.jdbcTemplate.query(sql, array, new SysLogRowMapper());
		} else {
			logList = this.jdbcTemplate.querySP(sql, array, currentIndex, pageSize, new SysLogRowMapper());
		}
		return logList;
	}
	
	@SuppressWarnings("rawtypes")
	class SysLogRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysLog sysLog = new SysLog();
			sysLog.setOperatorName(rs.getString("tbl_username"));
			sysLog.setIp(rs.getString("tbl_loginip"));
			sysLog.setOperationContent(rs.getString("tbl_operation"));
			sysLog.setOperationModule(rs.getString("tbl_module"));
			sysLog.setType(rs.getString("tbl_type"));
			sysLog.setOperationTime(rs.getTimestamp("comm_createDate"));
			sysLog.setRemark(rs.getString("tbl_remark"));
			return sysLog;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List checkTableExists(String logTableName) {
		String sql = "select * from information_schema.tables where table_name = ?";
		Object[]params = new Object[]{logTableName};
		return jdbcTemplate.queryForList(sql,params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int countLog(String sql, Object[] array) {
		List counts = null;
		try {
			counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return (Integer) (counts.get(0));
	}
	
	@SuppressWarnings("rawtypes")
	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt(1);
		}
	}
}
