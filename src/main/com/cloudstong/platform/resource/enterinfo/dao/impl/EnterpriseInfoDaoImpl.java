package com.cloudstong.platform.resource.enterinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.enterinfo.dao.EnterpriseInfoDao;
import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息操作数据库接口实现类
 */
@Repository("enterpriseInfoDao")
public class EnterpriseInfoDaoImpl implements EnterpriseInfoDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="themeCache")
	public EnterpriseInfo findDefaultInfo() {
		EnterpriseInfo enterpriseInfo = null;
		try{
			String sql = "select * from sys_enterpriseinfo where tbl_isdefault = 1";
			enterpriseInfo = (EnterpriseInfo)jdbcTemplate.queryForObject(sql,new EnterpriseInfoRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enterpriseInfo;
	}

	class EnterpriseInfoRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
			enterpriseInfo.setId(rs.getLong("id"));
			enterpriseInfo.setName(rs.getString("tbl_enterprisename"));
			enterpriseInfo.setCopyrightYear(rs.getString("tbl_copyrightyear"));
			enterpriseInfo.setSystemCnName(rs.getString("tbl_systemcnname"));
			enterpriseInfo.setSystemEnName(rs.getString("tbl_systemenname"));
			enterpriseInfo.setEnterAbout(rs.getString("tbl_enterpriseabout"));
			enterpriseInfo.setVersion(rs.getString("tbl_version"));
			return enterpriseInfo;
		}
	}
}
