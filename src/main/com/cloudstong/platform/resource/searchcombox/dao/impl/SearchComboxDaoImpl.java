package com.cloudstong.platform.resource.searchcombox.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.searchcombox.dao.SearchComboxDao;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:搜索下拉框操作数据库接口实现类
 */
@Repository("searchComboxDao")
public class SearchComboxDaoImpl implements SearchComboxDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'SearchCombox_findByID:'+#id")
	public SearchCombox findByID(Long id) {
		SearchCombox searchCombox = null;
		try {
			searchCombox = (SearchCombox) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] { id },new SearchComboxRowMapper());
		} catch (Exception e) {
		}
		return searchCombox;
	}
	
	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_searchcombox where comm_status=0 and id = ?");
		return buffer.toString();
	}
	
	class SearchComboxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SearchCombox searchCombox = new SearchCombox();
			searchCombox.setId(rs.getLong("id"));
			searchCombox.setSearchComboxName(rs.getString("tbl_compname"));
			searchCombox.setSearchComboxEnabled(rs.getLong("tbl_isEnabled"));
			searchCombox.setSearchComboxWidth(rs.getLong("tbl_kuandu"));
			searchCombox.setSearchComboxHeight(rs.getLong("tbl_gaodu"));
			searchCombox.setSearchComboxRemarks(rs.getString("tbl_beizhu"));
			searchCombox.setStatus(rs.getInt("comm_status"));
			searchCombox.setSearchComboxCode(rs.getString("tbl_bianma"));
			return searchCombox;
		}
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow,int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplate.query(this.getQueryForListSQL(where), args,new SearchComboxRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(where), args, startRow,
						rowsCount, new SearchComboxRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_searchcombox where comm_status=0 and tbl_passed=1 ").append(where);
		return buffer.toString();
	}
}
