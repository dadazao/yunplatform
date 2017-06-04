package com.cloudstong.platform.system.dao.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.LogoDao;
import com.cloudstong.platform.system.model.Logo;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作Logo的数据库接口实现类
 */
@Repository("logoDao")
public class LogoDaoImpl implements LogoDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	public void insert(Logo logo) {
		String sql = "insert into sys_logo(id,tbl_logomingcheng,tbl_suozailujing)values(?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), logo.getLogoName(),
				logo.getLogoPath()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public String getLogoName() {
		String logoName = "";
		String sql = "select tbl_logomingcheng from sys_logo where tbl_isdefault = 1";
		List list = jdbcTemplate.queryForList(sql);
		if (list.size() > 0) {
			logoName = ((Map) list.get(0)).get("tbl_logomingcheng").toString();
		}

		return logoName;
	}

	class LogoRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Logo logo = new Logo();
			logo.setId(rs.getLong("id"));
			logo.setLogoName(rs.getString("tbl_logomingcheng"));
			logo.setLogoPath(rs.getString("tbl_suozailujing"));
			logo.setBackgroundColor(rs.getString("tbl_background"));
			logo.setIsDefault(rs.getInt("tbl_isdefault"));
			logo.setTheme(rs.getString("tbl_zhuti"));
			return logo;
		}
	}

	@Override
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	public void delete(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Logo logo = this.findLogoById(ids[i]);

			try {
				String dsql = "delete from sys_logo where id = '" + ids[i] + "'";
				jdbcTemplate.execute(dsql);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}

			String path = logo.getLogoPath();
			if (path != null) {
				File file = new File(path);
				if (file.isFile() && file.exists()) {
					file.delete();
				}
			}
		}
	}

	@Override
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	public void update(Long id) {
		try {
			String allUsql = "update sys_logo set tbl_isdefault = 0";
			jdbcTemplate.update(allUsql);
			String usql = "update sys_logo set tbl_isdefault = 1 where id = '" + id + "'";
			jdbcTemplate.update(usql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Logo findLogoById(Long id) {
		String sql = "select * from sys_logo where id = ?";
		Object[] params = new Object[] { id };
		Logo logo = null;
		try {
			logo = (Logo)jdbcTemplate.queryForObject(sql, new Object[] { id },
					new LogoRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logo;
	}

	@Override
	public List<Logo> findLogByThemeId(Long themeId) {
		String sql = "select * from sys_logo where tbl_zhuti = ? order by comm_createDate asc";
		Object[] params = new Object[] { themeId };
		List<Logo> logos = new ArrayList<Logo>();
		try {
			logos = jdbcTemplate.query(sql, params, new LogoRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logos;
	}

	@Override
	@Cacheable(value="themeCache")
	public Logo findDefaultLogo() {
		Logo logo = null;
		try{
			String sql = "select * from sys_logo where tbl_isdefault = 1";
			logo = (Logo)jdbcTemplate.queryForObject(sql,new LogoRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logo;
	}

}
