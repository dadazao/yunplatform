package com.cloudstong.platform.resource.textbox.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.textbox.dao.TextBoxDao;
import com.cloudstong.platform.resource.textbox.model.TextBox;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本框操作数据库接口实现类
 */
@Repository("textBoxDao")
public class TextBoxDaoImpl implements TextBoxDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	// 新增
	@Override
	public long insert(TextBox textBox) throws AppException, Exception {
		long result = -1;
		try {
			result = jdbcTemplateExtend.update(this.getButtonInsertSQL(),
					this.getButtonInsertParams(textBox));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return result;
	}

	// 修改
	@Override
	public long update(TextBox textBox) throws AppException, Exception {
		long result = -1;
		try {
			result = jdbcTemplateExtend.update(this.getButtonUpdateSQL(),
					this.getButtonUpdateParams(textBox));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return result;
	}

	// 删除
	@Override
	public int delete(Long id) throws Exception {
		Object[] params = new Object[] { id };
		try {
			return jdbcTemplateExtend.update(
					"UPDATE sys_textbox SET COMM_STATUS=1 WHERE ID=?", params);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	// 根据ID查找详细信息
	@Override
	@Cacheable(value = "resourceCache", key = "'TextBox_findByID:'+#id")
	public TextBox findByID(Long id) {
		TextBox textBox = null;
		try {
			textBox = (TextBox) jdbcTemplateExtend.queryForObject(
					this.findByIDSQL(), new Object[] {id},
					new TextBoxRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textBox;
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplateExtend.query(
						this.getQueryForListSQL(where), args,
						new TextBoxRowMapper());
			} else {
				queryForPageList = jdbcTemplateExtend.querySP(
						this.getQueryForListSQL(where), args, startRow,
						rowsCount, new TextBoxRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}

	class TextBoxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TextBox textBox = new TextBox();
			textBox.setId(rs.getLong("id"));
			textBox.setTextBoxName(rs.getString("tbl_compname"));
			textBox.setTextBoxEnabled(rs.getLong("tbl_isEnabled"));
			textBox.setTextBoxWidth(rs.getLong("tbl_kuandu"));
			textBox.setTextBoxHeight(rs.getLong("tbl_gaodu"));
			textBox.setTextBoxRemarks(rs.getString("tbl_beizhu"));
			textBox.setStatus(rs.getInt("comm_status"));
			textBox.setTextBoxCode(rs.getString("tbl_bianma"));
			return textBox;
		}
	}

	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_textbox where comm_status=0 and id = ?");
		return buffer.toString();
	}

	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_textbox where comm_status=0 and tbl_passed=1 ").append(where);
		return buffer.toString();
	}

	private String getButtonInsertSQL() {
		StringBuffer _InsertSQL = new StringBuffer();
		_InsertSQL
				.append("insert into sys_textbox(id,tbl_textBoxName,tbl_textBoxEnabled,tbl_textBoxWidth,tbl_textBoxHeight,tbl_textBoxRemarks,comm_status,comm_createBy,comm_createDate, tbl_bianma)");
		_InsertSQL.append("values(?,?,?,?,?,?,?,?,?,?)");
		return _InsertSQL.toString();
	}

	private Object[] getButtonInsertParams(TextBox textBox) {
		Object[] params = new Object[] {UniqueIdUtil.genId(),
				textBox.getTextBoxName(), textBox.getTextBoxEnabled(),
				textBox.getTextBoxWidth(), textBox.getTextBoxHeight(),
				textBox.getTextBoxRemarks(),textBox.getStatus(), 
				textBox.getCreateBy(),textBox.getCreateDate(),
				textBox.getTextBoxCode() };
		return params;
	}

	private String getButtonUpdateSQL() {
		StringBuffer _UpdateSQL = new StringBuffer();
		_UpdateSQL
				.append("update sys_textbox set tbl_textBoxName = ?,tbl_textBoxEnabled = ?,tbl_textBoxWidth = ?,tbl_textBoxHeight = ?,tbl_textBoxRemarks = ?,comm_updateBy = ?,comm_updateDate = ?, tbl_bianma=? where id = ?");
		return _UpdateSQL.toString();
	}

	private Object[] getButtonUpdateParams(TextBox textBox) {
		Object[] params = new Object[] { textBox.getTextBoxName(),
				textBox.getTextBoxEnabled(), textBox.getTextBoxWidth(),
				textBox.getTextBoxHeight(), textBox.getTextBoxRemarks(),
				textBox.getUpdateBy(), textBox.getUpdateDate(), 
				textBox.getTextBoxCode(), textBox.getId() };
		return params;
	}

	@Override
	@Cacheable(value = "resourceCache")
	public int getTotalCount(String where, Object[] args) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer(
					"select count(*) as totalCount from sys_textbox where comm_status = 0")
					.append(where);
			return (Integer)jdbcTemplateExtend.queryForObject(buffer.toString(), args,
					new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:"
					+ e.getMessage());
			throw e;
		}
	}

	class TotalCountMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt("totalCount");
		}
	}

}
