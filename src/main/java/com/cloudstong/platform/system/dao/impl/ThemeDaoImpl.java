package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.system.dao.LogoDao;
import com.cloudstong.platform.system.dao.ThemeDao;
import com.cloudstong.platform.system.model.Theme;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("themeDao")
public class ThemeDaoImpl implements ThemeDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	private LogoDao logoDao;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public LogoDao getLogoDao() {
		return logoDao;
	}

	public void setLogoDao(LogoDao logoDao) {
		this.logoDao = logoDao;
	}

	@Override
	@CacheEvict(value = {"themeCache","domainCache"}, allEntries = true, beforeInvocation = true)
	public void update(Long id) {
		try {
			String allUsql = "update sys_theme set tbl_isDefault = 0";
			jdbcTemplate.update(allUsql);
			String usql = "update sys_theme set tbl_isDefault = 1 where id = '" + id +"'";
			jdbcTemplate.update(usql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Cacheable(value="themeCache")
	public Theme getDefaultTheme() {
		String sql = "select * from sys_theme where tbl_isDefault = 1";
		Theme theme = null;
		try {
			theme = (Theme)jdbcTemplate.queryForObject(sql, new ThemeRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theme;
	}

	class ThemeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Theme theme = new Theme();
			theme.setId(rs.getLong("id"));
			theme.setThemeName(rs.getString("tbl_themeName"));
			theme.setThemeCode(rs.getString("tbl_biaoshi"));
			theme.setFunction(rs.getString("tbl_gongnengshuoming"));
			theme.setComment(rs.getString("tbl_beizhu"));
			theme.setIsDefault(rs.getInt("tbl_isDefault"));
			return theme;
		}
	}

	@Override
	@CacheEvict(value = {"themeCache","domainCache"}, allEntries = true, beforeInvocation = true)
	public void delete(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			try {
				String dsql = "delete from sys_theme where id = ?";
				jdbcTemplate.update(dsql,new Object[]{ids[i]});
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
