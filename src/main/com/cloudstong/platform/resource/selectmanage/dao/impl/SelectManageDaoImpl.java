package com.cloudstong.platform.resource.selectmanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.selectmanage.dao.SelectManageDao;
import com.cloudstong.platform.resource.selectmanage.model.SelectManage;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选择列操作数据库接口实现类
 */
@Repository("selectManageDao")
public class SelectManageDaoImpl implements SelectManageDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'findSelectManageById:'+#selectManageId")
	public SelectManage findSelectManageById(String selectManageId) {
		String sql = "select * from sys_liebiaoselect where id = ?";
		final Object[] params = new Object[] { selectManageId };
		SelectManage selectManage = new SelectManage();
		query(sql, params, selectManage);
		return selectManage;
	}

	private void query(String sql, Object[] params,
			final SelectManage selectManage) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				selectManage.setId(rs.getLong("id"));
				selectManage.setSelectManageName(rs.getString("tbl_name"));
				selectManage.setSelectManageIsSelect(rs.getInt("tbl_isselect"));
				selectManage.setSelectManagePosition(rs.getString("tbl_weizhi"));
				selectManage.setSelectManageStyle(rs.getInt("tbl_yangshi"));
				selectManage.setSelectManageUnit(rs.getString("tbl_danwei"));
				selectManage.setSelectManageWidth(rs.getInt("tbl_width"));
				selectManage.setComment(rs.getString("tbl_comment"));
				selectManage.setRemark(rs.getString("tbl_remark"));
			}
		});
	}
}
