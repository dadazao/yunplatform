package com.cloudstong.platform.resource.passbox.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.passbox.dao.PassboxDao;
import com.cloudstong.platform.resource.passbox.model.Passbox;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作密码框数据库接口实现类
 */
@Repository("passboxDao")
public class PassboxDaoImpl implements PassboxDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Passbox findByID(Long id) {
		Passbox passbox = null;
		try {
			passbox = (Passbox) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] {id},new PassboxRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passbox;
	}

	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_passwordbox where comm_status=0 and id = ?");
		return buffer.toString();
	}
	
	class PassboxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Passbox passbox = new Passbox();
			passbox.setId(rs.getLong("id"));
			passbox.setPassboxName(rs.getString("tbl_compname"));
			passbox.setPassboxEnabled(rs.getLong("tbl_isEnabled"));
			passbox.setPassboxWidth(rs.getLong("tbl_kuandu"));
			passbox.setPassboxHeight(rs.getLong("tbl_gaodu"));
			passbox.setPassboxRemarks(rs.getString("tbl_beizhu"));
			passbox.setStatus(rs.getInt("comm_status"));
			passbox.setPassboxCode(rs.getString("tbl_bianma"));
			return passbox;
		}
	}

	@Override
	public List queryForPageList(String sql, Object[] args, int startRow,int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplate.query(this.getQueryForListSQL(sql), args,new PassboxRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(sql), args, startRow,
						rowsCount, new PassboxRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_passwordbox where comm_status = 0").append(where);
		return buffer.toString();
	}
}
