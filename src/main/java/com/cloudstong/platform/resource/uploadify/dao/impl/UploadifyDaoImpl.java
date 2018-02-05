package com.cloudstong.platform.resource.uploadify.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.editor.model.TextEditor;
import com.cloudstong.platform.resource.uploadify.dao.UploadifyDao;
import com.cloudstong.platform.resource.uploadify.model.Uploadify;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件操作数据库接口实现类
 */
@Repository("uploadifyDao")
public class UploadifyDaoImpl implements UploadifyDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "findAllUploadify")
	public List<Uploadify> findAllUploadify() {
		String sql = " select * from sys_uploadify where tbl_passed=1";
		List<Uploadify> result = this.jdbcTemplate.query(sql,new UploadifyRowMap());
		return result;
	}

	class UploadifyRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Uploadify u = new Uploadify();
			u.setId(rs.getLong("id"));
			u.setUploadifyName(rs.getString("tbl_compname"));
			u.setButtonText(rs.getString("tbl_buttontext"));
			u.setMulti(rs.getInt("tbl_multi"));
			u.setAutoUpload(rs.getInt("tbl_autoupload"));
			u.setIsEnabled(rs.getInt("tbl_isenabled"));
			u.setFileExt(rs.getString("tbl_fileext"));
			u.setFileSizeLimit(rs.getString("tbl_filesizelimit"));
			u.setQueueSizeLimit(rs.getInt("tbl_queueSizeLimit"));
			u.setComment(rs.getString("tbl_comment"));
			u.setRemark(rs.getString("tbl_remark"));
			return u;
		}
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'findUploadifyById:'+#uploadifyId")
	public Uploadify findUploadifyById(Long uploadifyId) {
		String sql = "select * from sys_uploadify where id = ?";
		final Object[] params = new Object[] { uploadifyId };
		Uploadify uploadify = new Uploadify();
		query(sql, params, uploadify);
		return uploadify;
	}
	
	private void query(String sql, Object[] params, final Uploadify u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setUploadifyName(rs.getString("tbl_compname"));
				u.setButtonText(rs.getString("tbl_buttontext"));
				u.setMulti(rs.getInt("tbl_multi"));
				u.setAutoUpload(rs.getInt("tbl_autoupload"));
				u.setIsEnabled(rs.getInt("tbl_isenabled"));
				u.setFileExt(rs.getString("tbl_fileext"));
				u.setFileSizeLimit(rs.getString("tbl_filesizelimit"));
				u.setQueueSizeLimit(rs.getInt("tbl_queueSizeLimit"));
				u.setComment(rs.getString("tbl_comment"));
				u.setRemark(rs.getString("tbl_remark"));
			}
		});
	}
}
