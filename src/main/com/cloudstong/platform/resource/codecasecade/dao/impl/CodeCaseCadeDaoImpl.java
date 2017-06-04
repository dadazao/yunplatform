package com.cloudstong.platform.resource.codecasecade.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.codecasecade.dao.CodeCaseCadeDao;
import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.editor.model.TextEditor;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码级联操作数据库接口实现类
 */
@Repository("codeCaseCadeDao")
public class CodeCaseCadeDaoImpl implements CodeCaseCadeDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "findAllCodeCaseCade")
	public List<CodeCaseCade> findAllCodeCaseCade() {
		List<CodeCaseCade> result=null;
		try {
			String sql = " select * from sys_codecasecade where tbl_passed=1";
			result = this.jdbcTemplate.query(sql,new CodeCaseCadeRowMap());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	class CodeCaseCadeRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CodeCaseCade c = new CodeCaseCade();
			c.setId(rs.getLong("id"));
			c.setCodeCaseCadeName(rs.getString("tbl_compname"));
			c.setIsDefault(rs.getInt("tbl_isdefault"));
			c.setWidth(rs.getInt("tbl_width"));
			c.setHeight(rs.getInt("tbl_height"));
			c.setTopCode(rs.getString("tbl_topcode"));
			c.setShowProgression(rs.getInt("tbl_showprogression"));
			c.setComment(rs.getString("tbl_comment"));
			c.setRemark(rs.getString("tbl_remark"));
			return c;
		}
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'findCodeCaseCadeById:'+#codeCaseCadeId")
	public CodeCaseCade findCodeCaseCadeById(Long codeCaseCadeId) {
		String sql = "select * from sys_codecasecade where id = ?";
		final Object[] params = new Object[] { codeCaseCadeId };
		CodeCaseCade codeCaseCade = new CodeCaseCade();
		query(sql, params, codeCaseCade);
		return codeCaseCade;
	}

	private void query(String sql, Object[] params, final CodeCaseCade c) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				c.setId(rs.getLong("id"));
				c.setCodeCaseCadeName(rs.getString("tbl_compname"));
				c.setIsDefault(rs.getInt("tbl_isdefault"));
				c.setWidth(rs.getInt("tbl_width"));
				c.setHeight(rs.getInt("tbl_height"));
				c.setTopCode(rs.getString("tbl_topcode"));
				c.setShowProgression(rs.getInt("tbl_showprogression"));
				c.setComment(rs.getString("tbl_comment"));
				c.setRemark(rs.getString("tbl_remark"));
			}
		});
	}
}
