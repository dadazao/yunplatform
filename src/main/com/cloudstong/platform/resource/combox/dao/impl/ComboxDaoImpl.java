package com.cloudstong.platform.resource.combox.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.combox.dao.ComboxDao;
import com.cloudstong.platform.resource.combox.model.Combox;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:下拉框操作数据库接口
 */
@Repository("comboxDao")
public class ComboxDaoImpl implements ComboxDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'findCodeCaseCadeById:'+#codeCaseCadeId")
	public Combox findByID(Long id) {
		Combox combox = null;
		try {
			combox = (Combox) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] { id },new ComboxRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return combox;
	}
	
	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_combox where comm_status=0 and id = ?");
		return buffer.toString();
	}
	
	class ComboxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Combox combox = new Combox();
			combox.setId(rs.getLong("id"));
			combox.setComboxName(rs.getString("tbl_compname"));
			combox.setComboxEnabled(rs.getLong("tbl_isEnabled"));
			combox.setComboxWidth(rs.getLong("tbl_kuandu"));
			combox.setComboxHeight(rs.getLong("tbl_gaodu"));
			combox.setComboxRemarks(rs.getString("tbl_beizhu"));
			combox.setStatus(rs.getInt("comm_status"));
			combox.setComboxCode(rs.getString("tbl_bianma"));
			return combox;
		}
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow,int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplate.query(
						this.getQueryForListSQL(where), args,
						new ComboxRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(where), args, startRow,
						rowsCount, new ComboxRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_combox where comm_status=0 and tbl_passed=1 ").append(where);
		return buffer.toString();
	}
}
