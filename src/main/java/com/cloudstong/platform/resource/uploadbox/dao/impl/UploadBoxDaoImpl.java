package com.cloudstong.platform.resource.uploadbox.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.uploadbox.dao.UploadBoxDao;
import com.cloudstong.platform.resource.uploadbox.model.UploadBox;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框操作数据库接口实现类
 */
@Repository("uploadBoxDao")
public class UploadBoxDaoImpl implements UploadBoxDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value = "resourceCache")
	public UploadBox findByID(Long id) {
		UploadBox uploadBox = null;
		try {
			uploadBox = (UploadBox) jdbcTemplate.queryForObject(this.findByIDSQL(), new Object[] { id },new UploadBoxRowMapper());
		} catch (Exception e) {
		}
		return uploadBox;
	}
	
	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_uploadfilebox where comm_status=0 and id = ?");
		return buffer.toString();
	}
	
	class UploadBoxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			UploadBox uploadBox = new UploadBox();
			uploadBox.setId(rs.getLong("id"));
			uploadBox.setUploadBoxName(rs.getString("tbl_compname"));
			uploadBox.setUploadBoxEnabled(rs.getLong("tbl_isEnabled"));
			uploadBox.setUploadBoxWidth(rs.getLong("tbl_kuandu"));
			uploadBox.setUploadBoxHeight(rs.getLong("tbl_gaodu"));
			uploadBox.setUploadBoxRemarks(rs.getString("tbl_beizhu"));
			uploadBox.setStatus(rs.getInt("comm_status"));
			uploadBox.setUploadBoxCode(rs.getString("tbl_bianma"));
			return uploadBox;
		}
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int currentIndex,int pageSize) {
		List queryForPageList = null;
		try {
			if (pageSize == -1) {
				queryForPageList = jdbcTemplate.query(this.getQueryForListSQL(where), args, new UploadBoxRowMapper());
			} else {
				queryForPageList = jdbcTemplate.querySP(
						this.getQueryForListSQL(where), args, currentIndex,
						pageSize, new UploadBoxRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}
	
	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from sys_uploadfilebox where comm_status=0 and tbl_passed=1 ").append(where);
		return buffer.toString();
	}
}
