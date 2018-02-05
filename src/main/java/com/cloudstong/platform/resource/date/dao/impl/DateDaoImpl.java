package com.cloudstong.platform.resource.date.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.date.dao.DateDao;
import com.cloudstong.platform.resource.date.model.DateControl;
import com.cloudstong.platform.resource.form.model.Form;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:日期组件操作数据库接口实现类
 */
@Repository("dateDao")
public class DateDaoImpl implements DateDao {
	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	public Long insert(DateControl date) throws Exception {
		Long id = UniqueIdUtil.genId();
		try {
			jdbcTemplateExtend.update(this.getDateInsertSql(),
					this.getDateInsertParams(date,id));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return id;
	}

	@Override
	public Long update(DateControl date) throws Exception {
		try {
			jdbcTemplateExtend.update(this.getDateUpdateSql(),
					this.getDateUpdateParams(date));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return date.getId();
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'Date_findById:'+#id")
	public DateControl findById(Long id) throws Exception {
		DateControl date = null;
		try {
			date = (DateControl) jdbcTemplateExtend.queryForObject(
					this.findByIdSql(), new Object[] { id },
					new DateRowMapper());
		} catch (Exception e) {
		}
		return date;
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplateExtend.query(
						this.getQueryForListSql(where), args,
						new DateRowMapper());
			} else {
				queryForPageList = jdbcTemplateExtend.querySP(
						this.getQueryForListSql(where), args, startRow,
						rowsCount, new DateRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'Date_getTotalCount:'+#where.hashCode()+#args")
	public int getTotalCount(String where, Object[] args) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer(
					"select count(*) as tbl_totalCount from sys_riqizujian where comm_status = 0")
					.append(where);
			return (Integer)jdbcTemplateExtend.queryForObject(buffer.toString(), args,
					new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:"
					+ e.getMessage());
			throw e;
		}
	}

	@Override
	public int delete(Long id) throws Exception {
		Object[] params = new Object[] { String.valueOf(id) };
		try {
			return jdbcTemplateExtend.update(
					"update sys_riqizujian set comm_status=1 where id = ?", params);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	private String getDateInsertSql() {
		StringBuffer _InsertSQL = new StringBuffer();
		_InsertSQL
				.append("insert into sys_riqizujian(id,tbl_compname,tbl_dateWidth,tbl_dateenabled,tbl_datestyle,comm_status,comm_createby,comm_createdate,tbl_dateremark,tbl_bianma,tbl_remark)");
		_InsertSQL.append("values(?,?,?,?,?,?,?,?,?,?,?)");
		return _InsertSQL.toString();
	}

	private Object[] getDateInsertParams(DateControl date,Long id) {
		Object[] params = new Object[] { id, date.getDateName(),
				date.getDateWidth(), date.getDateEnabled(),
				date.getDateStyle(), date.getStatus(), date.getCreateBy(),
				date.getCreateDate(), date.getDateRemarks(),
				date.getDateCode(), date.getDateDesc() };
		return params;
	}

	private String getDateUpdateSql() {
		StringBuffer _UpdateSQL = new StringBuffer();
		_UpdateSQL
				.append("update riqizujian set tbl_compname = ?,tbl_dateWidth = ?,tbl_dateenabled = ?,tbl_datestyle = ?,comm_updateBy = ?,comm_updateDate = ?,tbl_dateremark = ?,tbl_bianma = ?,tbl_remark = ? where id = ?");
		return _UpdateSQL.toString();
	}

	private Object[] getDateUpdateParams(DateControl date) {
		Object[] params = new Object[] { date.getDateName(),
				date.getDateWidth(), date.getDateEnabled(),
				date.getDateStyle(), date.getUpdateBy(), date.getUpdateDate(),
				date.getDateRemarks(), date.getDateCode(), date.getDateDesc(),
				date.getId() };
		return params;
	}

	private String findByIdSql() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id,tbl_compname,tbl_dateWidth,tbl_dateenabled,tbl_datestyle,comm_createby,comm_createdate,comm_updateBy,comm_updateDate,tbl_dateremark,comm_status,tbl_bianma,tbl_remark from sys_riqizujian where comm_status=0 and id = ?");
		return buffer.toString();
	}

	private String getQueryForListSql(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id,tbl_compname,tbl_dateWidth,tbl_dateenabled,tbl_datestyle,comm_createby,comm_createdate,comm_updateBy,comm_updateDate,tbl_dateremark,comm_status,tbl_bianma,tbl_remark from sys_riqizujian where comm_status=0 and tbl_passed=1 ");
		buffer.append(where);
		return buffer.toString();
	}

	class DateRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			DateControl date = new DateControl();
			date.setId(rs.getLong("id"));
			date.setDateName(rs.getString("tbl_compname"));
			date.setDateWidth(rs.getInt("tbl_dateWidth"));
			date.setDateEnabled(rs.getInt("tbl_dateenabled"));
			date.setDateStyle(rs.getString("tbl_datestyle"));
			date.setCreateBy(rs.getString("comm_createby"));
			date.setCreateDate(rs.getDate("comm_createdate"));
			date.setUpdateBy(rs.getString("comm_updateBy"));
			date.setUpdateDate(rs.getDate("comm_updateDate"));
			date.setDateRemarks(rs.getString("tbl_dateremark"));
			date.setStatus(rs.getInt("comm_status"));
			date.setDateCode(rs.getString("tbl_bianma"));
			date.setDateDesc(rs.getString("tbl_remark"));
			return date;
		}
	}

	class TotalCountMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt("tbl_totalCount");
		}
	}

	@Override
	public List selectFormList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplateExtend.query(where, args,
					new FormRowMapper());
		} else {
			retList = this.jdbcTemplateExtend.querySP(where, args, startRow,
					rowsCount, new FormRowMapper());
		}
		return retList;
	}

	class FormRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Form t = new Form();
			t.setFormName(rs.getString("tbl_formName"));
			t.setCode(rs.getString("tbl_bianma"));
			t.setCreateDate(rs.getDate("tbl_createtime"));
			return t;
		}
	}

	@Override
	public int countFormList(String where, Object[] args) throws Exception {
		List counts = jdbcTemplateExtend.query(where, args,
				new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}
}
