package com.cloudstong.platform.resource.querycontrol.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.querycontrol.dao.QueryControlDao;
import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询组件操作数据库接口实现类
 */
@Repository("queryControlDao")
public class QueryControlDaoImpl implements QueryControlDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<QueryControl> selectAllQueryControl() {
		String sql = " select * from sys_chaxunzujian where tbl_passed = 1 and tbl_isenabled = 0";
		List<QueryControl> result = this.jdbcTemplate.query(sql,
				new QueryControlRowMap());
		return result;
	}

	class QueryControlRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			QueryControl q = new QueryControl();
			q.setId(rs.getLong("id"));
			q.setQueryControlName(rs.getString("tbl_name"));
			q.setQueryControlWidth(rs.getInt("tbl_kuandu"));
			q.setQueryControlHeight(rs.getInt("tbl_gaodu"));
			q.setIsHasButton(rs.getInt("tbl_ishasbutton"));
			q.setIsHasGjbutton(rs.getInt("tbl_ishasgjbutton"));
			q.setComment(rs.getString("tbl_comment"));
			q.setRemark(rs.getString("tbl_remark"));
			return q;
		}
	}

	@Override
	@Cacheable(value="resourceCache",key="'findQueryControlById:'+#queryControlId")
	public QueryControl findQueryControlById(String queryControlId) {
		String sql = "select * from sys_chaxunzujian where id = ?";
		final Object[] params = new Object[] { queryControlId };
		QueryControl queryControl = new QueryControl();
		query(sql, params, queryControl);
		return queryControl;
	}

	private void query(String sql, Object[] params,
			final QueryControl queryControl) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				queryControl.setId(rs.getLong("id"));
				queryControl.setQueryControlName(rs.getString("tbl_name"));
				queryControl.setQueryControlWidth(rs.getInt("tbl_kuandu"));
				queryControl.setQueryControlHeight(rs.getInt("tbl_gaodu"));
				queryControl.setIsHasButton(rs.getInt("tbl_ishasbutton"));
				queryControl.setIsHasGjbutton(rs.getInt("tbl_ishasgjbutton"));
				queryControl.setComment(rs.getString("tbl_comment"));
				queryControl.setRemark(rs.getString("tbl_remark"));
			}
		});
	}

	@Override
	public List<AdvanceQueryControl> selectAllAdvanceQueryControl() {
		String sql = " select * from sys_gaojichaxun where tbl_passed = 1";
		List<AdvanceQueryControl> result = this.jdbcTemplate.query(sql,	new AdvanceQueryControlRowMap());
		return result;
	}
	
	class AdvanceQueryControlRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AdvanceQueryControl q = new AdvanceQueryControl();
			q.setId(rs.getLong("id"));
			q.setQueryControlName(rs.getString("tbl_gaojichaxunname"));
			q.setQueryControlWidth(rs.getInt("tbl_kuandu"));
			q.setQueryControlHeight(rs.getInt("tbl_gaodu"));
			q.setComment(rs.getString("tbl_comment"));
			q.setRemark(rs.getString("tbl_remark"));
			q.setColumnNumber(rs.getInt("tbl_columnNumber"));
			return q;
		}
	}

	@Override
	@Cacheable(value="resourceCache",key="'findAdvanceQueryControlById:'+#advanceQueryControlId")
	public AdvanceQueryControl findAdvanceQueryControlById(Long advanceQueryControlId) {
		String sql = "select * from sys_gaojichaxun where id = ?";
		final Object[] params = new Object[] { advanceQueryControlId };
		AdvanceQueryControl queryControl = new AdvanceQueryControl();
		queryAdvance(sql, params, queryControl);
		return queryControl;
	}

	private void queryAdvance(String sql, Object[] params,
			final AdvanceQueryControl queryControl) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				queryControl.setId(rs.getLong("id"));
				queryControl.setQueryControlName(rs.getString("tbl_gaojichaxunname"));
				queryControl.setQueryControlWidth(rs.getInt("tbl_kuandu"));
				queryControl.setQueryControlHeight(rs.getInt("tbl_gaodu"));
				queryControl.setComment(rs.getString("tbl_comment"));
				queryControl.setRemark(rs.getString("tbl_remark"));
				queryControl.setColumnNumber(rs.getInt("tbl_columnNumber"));
			}
		});
	}
}
