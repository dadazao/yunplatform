package com.cloudstong.platform.resource.editor.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.editor.dao.TextEditorDao;
import com.cloudstong.platform.resource.editor.model.TextEditor;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本编辑器操作数据库接口实现类
 */
@Repository("textEditorDao")
public class TextEditorDaoImpl implements TextEditorDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	public List<TextEditor> selectAllTextEditor() {
		String sql = " select * from sys_texteditor where tbl_passed=1";
		List<TextEditor> result = this.jdbcTemplateExtend.query(sql,
				new TextEditorRowMap());
		return result;
	}

	class TextEditorRowMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TextEditor t = new TextEditor();
			t.setId(rs.getLong("id"));
			t.setTextEditorName(rs.getString("tbl_compname"));
			t.setEnabled(rs.getInt("tbl_enabled"));
			t.setRows(rs.getInt("tbl_rows"));
			t.setCols(rs.getInt("tbl_cols"));
			t.setRemark(rs.getString("tbl_remark"));
			t.setBeizhu(rs.getString("tbl_beizhu"));
			t.setEditorId(rs.getString("tbl_editorid"));
			return t;
		}
	}

	@Override
	public TextEditor findTextEditorById(Long editorId) {
		String sql = "select * from sys_texteditor where id = ?";
		final Object[] params = new Object[] { editorId };
		TextEditor textEditor = new TextEditor();
		query(sql, params, textEditor);
		return textEditor;
	}

	private void query(String sql, Object[] params, final TextEditor textEditor) {
		jdbcTemplateExtend.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				textEditor.setId(rs.getLong("id"));
				textEditor.setTextEditorName(rs.getString("tbl_compname"));
				textEditor.setEnabled(rs.getInt("tbl_enabled"));
				textEditor.setRows(rs.getInt("tbl_rows"));
				textEditor.setCols(rs.getInt("tbl_cols"));
				textEditor.setRemark(rs.getString("tbl_remark"));
				textEditor.setBeizhu(rs.getString("tbl_beizhu"));
				textEditor.setEditorId(rs.getString("tbl_editorid"));
			}
		});
	}
}
