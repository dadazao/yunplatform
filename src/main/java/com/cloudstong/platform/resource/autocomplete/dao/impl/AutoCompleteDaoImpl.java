/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.autocomplete.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.autocomplete.dao.AutoCompleteDao;
import com.cloudstong.platform.resource.autocomplete.model.AutoComplete;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
@Repository("autoCompleteDao")
public class AutoCompleteDaoImpl implements AutoCompleteDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Override
	public List queryForPageList(String sql, Object[] args, int startRow,int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplate.query(this.getQueryForListSQL(sql), args,new AutoCompleteRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(sql), args, startRow,
						rowsCount, new AutoCompleteRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	class AutoCompleteRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AutoComplete autoComplete = new AutoComplete();
			autoComplete.setId(rs.getLong("id"));
			autoComplete.setAutoCompleteName(rs.getString("tbl_compname"));
			autoComplete.setAutoCompleteCode(rs.getString("tbl_bianma"));
			autoComplete.setAutoCompleteEnabled(rs.getLong("tbl_isEnabled"));
			autoComplete.setAutoCompleteWidth(rs.getLong("tbl_kuandu"));
			autoComplete.setAutoCompleteHeight(rs.getLong("tbl_gaodu"));
			return autoComplete;
		}
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_autocomplete where comm_mark_for_delete = 0").append(where);
		return buffer.toString();
	}

	@Override
	public AutoComplete findByID(Long compexId) {
		AutoComplete autoComplete = null;
		try {
			autoComplete = (AutoComplete) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] {compexId},new AutoCompleteRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return autoComplete;
	}
	
	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_autocomplete where comm_mark_for_delete=0 and id = ?");
		return buffer.toString();
	}

	@Override
	public String findColumnValueList(String belongTable, String columnName) {
		String str = "";
		String sql = "select "+columnName+" from "+belongTable;
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		for(int i=0;i<list.size();i++){
			if(i==0){
				str+=list.get(i).get(columnName);
			}else{
				str+=","+list.get(i).get(columnName);
			}
		}
		return str;
	}
}
