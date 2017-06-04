package com.cloudstong.platform.resource.listcontrol.dao.impl;

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
import com.cloudstong.platform.resource.listcontrol.dao.ListControlDao;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件操作数据库接口实现类
 */
@Repository("listControlDao")
public class ListControlDaoImpl implements ListControlDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ListControl> selectAllListControl() {
		String sql = " select * from sys_liebiaozujian where tbl_passed = 1 and tbl_isenabled = 0";
		List<ListControl> result = this.jdbcTemplate.query(sql,
				new ListControlRowMap());
		return result;
	}

	class ListControlRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ListControl l = new ListControl();
			l.setId(rs.getLong("id"));
			l.setListControlName(rs.getString("tbl_name"));
			l.setSelectId(rs.getString("tbl_selectid"));
			l.setOrderId(rs.getString("tbl_orderid"));
			l.setOperationId(rs.getString("tbl_operationid"));
			l.setPageId(rs.getString("tbl_paginationid"));
			l.setComment(rs.getString("tbl_comment"));
			l.setRemark(rs.getString("tbl_remark"));
			return l;
		}
	}

	@Override
	@Cacheable(value="resourceCache",key="'findListControlById:'+#listControlId")
	public ListControl findListControlById(Long listControlId) {
		String sql = "select * from sys_liebiaozujian where id = ?";
		final Object[] params = new Object[] { listControlId };
		ListControl listControl = new ListControl();
		query(sql, params, listControl);
		return listControl;
	}

	private void query(String sql, Object[] params,
			final ListControl listControl) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				listControl.setId(rs.getLong("id"));
				listControl.setListControlName(rs.getString("tbl_name"));
				listControl.setSelectId(rs.getString("tbl_selectid"));
				listControl.setOrderId(rs.getString("tbl_orderid"));
				listControl.setOperationId(rs.getString("tbl_operationid"));
				listControl.setPageId(rs.getString("tbl_paginationid"));
				listControl.setComment(rs.getString("tbl_comment"));
				listControl.setRemark(rs.getString("tbl_remark"));
			}
		});
	}

}
