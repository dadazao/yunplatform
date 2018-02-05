package com.cloudstong.platform.resource.textarea.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.textarea.dao.TextareaDao;
import com.cloudstong.platform.resource.textarea.model.Textarea;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域操作数据库接口实现类
 */
@Repository("textareaDao")
public class TextareaDaoImpl implements TextareaDao {
	
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'Textarea_findByID:'+#id")
	public Textarea findByID(Long id) {
		Textarea textarea = null;
		try {
			textarea = (Textarea) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] { id },new TextareaRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textarea;
	}
	
	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_textarea where comm_status=0 and id = ?");
		return buffer.toString();
	}
	
	class TextareaRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Textarea textarea = new Textarea();
			textarea.setId(rs.getLong("id"));
			textarea.setTextareaName(rs.getString("tbl_compname"));
			textarea.setTextareaEnabled(rs.getLong("tbl_isEnabled"));
			textarea.setTextareaWidth(rs.getLong("tbl_kuandu"));
			textarea.setTextareaHeight(rs.getLong("tbl_gaodu"));
			textarea.setTextareaRemarks(rs.getString("tbl_beizhu"));
			textarea.setStatus(rs.getInt("comm_status"));
			textarea.setTextareaCode(rs.getString("tbl_bianma"));
			return textarea;
		}
	}

	@Override
	@Cacheable(value="resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow,int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplate.query(this.getQueryForListSQL(where), args, new TextareaRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(where), args, startRow,
						rowsCount, new TextareaRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_textarea where comm_status=0 and tbl_passed=1 ").append(where);
		return buffer.toString();
	}
}
