package com.cloudstong.platform.third.office.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.third.office.dao.OfficeDao;
import com.cloudstong.platform.third.office.model.Office;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:字处理组件数据库接口实现
 */
@Repository("officeDao")
public class OfficeDaoImpl implements OfficeDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Office> getAllOffice() {
		List<Office> result=null;
		try {
			String sql = "select * from bus_zichulizujian where tbl_passed=1";
			result = this.jdbcTemplate.query(sql,new OfficeRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	class OfficeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Office office = new Office();
			office.setId(rs.getLong("id"));
			office.setOfficeName(rs.getString("tbl_compname"));
			office.setDefaultMethod(rs.getString("tbl_defaultMethod"));
			office.setComment(rs.getString("tbl_comment"));
			office.setRemark(rs.getString("tbl_remark"));
			return office;
		}
	}
}