package com.cloudstong.platform.resource.pagemanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.pagemanage.dao.PageManageDao;
import com.cloudstong.platform.resource.pagemanage.model.PageManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件操作数据库接口实现类
 */
@Repository("pageManageDao")
public class PageManageDaoImpl implements PageManageDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'findPageManageById:'+#pageManageId")
	public PageManage findPageManageById(String pageManageId) {
		String sql = "select * from sys_liebiaopagination where id = ?";
		final Object[] params = new Object[] { pageManageId };
		PageManage pageManage = new PageManage();
		query(sql, params, pageManage);
		return pageManage;
	}

	private void query(String sql, Object[] params, final PageManage pageManage) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				pageManage.setId(rs.getLong("id"));
				pageManage.setPageManageName(rs.getString("tbl_name"));
				pageManage.setPagesize(rs.getInt("tbl_pagesize"));
				pageManage.setShowPageNumberCount(rs.getInt("tbl_shownumbercount"));
				pageManage.setComment(rs.getString("tbl_comment"));
				pageManage.setRemark(rs.getString("tbl_remark"));
			}
		});
	}

}
