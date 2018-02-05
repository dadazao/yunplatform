package com.cloudstong.platform.resource.imagepage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.imagepage.dao.ImagePageDao;
import com.cloudstong.platform.resource.imagepage.model.ImagePage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:图片页面操作数据库接口实现类
 */
@Repository("imagePageDao")
public class ImagePageDaoImpl implements ImagePageDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void doUpdateImagePage(ImagePage imagePage) {
		String sql = "update sys_imgpage set tbl_imagepagepath = ? where id = ?";
		Object[] params = new Object[] { imagePage.getImagePagePath(),
				imagePage.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public ImagePage selectImagePageById(Long id) {
		String sql = "select * from sys_imgpage where id = ?";
		ImagePage imagePage = null;
		try {
			imagePage = (ImagePage) jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new ImagePageRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagePage;
	}

	class ImagePageRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ImagePage imagePage = new ImagePage();
			imagePage.setId(rs.getLong("id"));
			imagePage.setName(rs.getString("tbl_name"));
			imagePage.setPicName(rs.getString("tbl_image"));
			imagePage.setComment(rs.getString("tbl_comment"));
			imagePage.setRemark(rs.getString("tbl_remark"));
			imagePage.setImagePagePath(rs.getString("tbl_imagepagepath"));
			return imagePage;
		}
	}

	@Override
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args,
					new ImagePageRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow,
					rowsCount, new ImagePageRowMapper());
		}
		return retList;
	}

}
