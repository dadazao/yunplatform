package com.cloudstong.platform.resource.form.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.form.dao.TabDao;
import com.cloudstong.platform.resource.form.model.Tab;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选项卡操作数据库接口实现类
 */
@Repository("tabDao")
public class TabDaoImpl implements TabDao {

	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	class TabRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Tab u = new Tab();
			u.setId(rs.getLong("id"));
			u.setTabName(rs.getString("tbl_mingcheng"));
			u.setTemplateId(rs.getLong("tbl_muban"));
			u.setFormId(rs.getLong("tbl_form"));
			u.setTemplateName(rs.getString("tbl_templatename"));
			u.setTemplateFileName(rs.getString("tbl_templatefilename"));
			u.setTabFontStyle(rs.getString("tbl_ziti"));
			u.setTabFontColor(rs.getString("tbl_zitiyanse"));
			u.setTabOrder(rs.getInt("tbl_taborder"));
			u.setTabType(rs.getInt("tbl_tabtype"));
			u.setIsIncludeList(rs.getInt("tbl_haslist"));
			u.setButtonId(rs.getString("tbl_buttonid"));
			u.setType(rs.getInt("tbl_type"));
			u.setTemplateType(rs.getInt("tbl_templateType"));
			u.setCode(rs.getString("tbl_bianma"));
			u.setComment(rs.getString("tbl_comment"));
			u.setRemark(rs.getString("tbl_remark"));
			return u;
		}
	}

	@Override
	@Cacheable(value = "formCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new TabRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow,
					rowsCount, new TabRowMapper());
		}
		return retList;
	}

	@Override
	public void update(Tab u) {
		String sql = "update sys_biaodantab set tbl_mingcheng=?,tbl_muban=?,tbl_form=?,tbl_ziti=?,tbl_zitiyanse=?,tbl_taborder=?,tbl_tabtype=?,tbl_haslist=?,tbl_buttonid=?,tbl_type=?,tbl_templatetype=?," +
				"tbl_comment=?,tbl_remark=? where id=?";
		Object[] params = new Object[] { u.getTabName(), u.getTemplateId(),
				u.getFormId(), u.getTabFontStyle(), u.getTabFontColor(),
				u.getTabOrder(), u.getTabType(), u.getIsIncludeList(), u.getButtonId(),
				u.getType(), u.getTemplateType(),u.getComment(),u.getRemark(),u.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Long insert(Tab u) {
		Long id=UniqueIdUtil.genId();
		String sql = "insert into sys_biaodantab(id,tbl_mingcheng,tbl_muban,tbl_form,tbl_ziti,tbl_zitiyanse,tbl_taborder,tbl_tabtype,tbl_haslist,tbl_buttonid,tbl_type,tbl_templatetype,tbl_comment,tbl_remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] {id,u.getTabName(), u.getTemplateId(),
				u.getFormId(), u.getTabFontStyle(), u.getTabFontColor(),
				u.getTabOrder(), u.getTabType(), u.getIsIncludeList(), u.getButtonId(),
				u.getType(), u.getTemplateType(),u.getComment(),u.getRemark() };
		jdbcTemplate.update(sql, params);
		return id;
	}

	@Override
	public Tab selectById(Long id) {
		String sql = "select a.*,b.tbl_templatechname as tbl_templatename,b.tbl_templatefilename as tbl_templatefilename from sys_biaodantab a left join sys_template b on a.tbl_muban=b.id  where a.id=?";
		final Tab u = new Tab();
		final Object[] params = new Object[] { id };
		query(sql, params, u);

		return u;
	}

	private void query(String sql, Object[] params, final Tab u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setTabName(rs.getString("tbl_mingcheng"));
				u.setTemplateName(rs.getString("tbl_templatename"));
				u.setTemplateId(rs.getLong("tbl_muban"));
				u.setFormId(rs.getLong("tbl_form"));
				u.setTemplateFileName(rs.getString("tbl_templatefilename"));
				u.setTabFontStyle(rs.getString("tbl_ziti"));
				u.setTabFontColor(rs.getString("tbl_zitiyanse"));
				u.setTabOrder(rs.getInt("tbl_taborder"));
				u.setTabType(rs.getInt("tbl_tabtype"));
				u.setIsIncludeList(rs.getInt("tbl_haslist"));
				u.setButtonId(rs.getString("tbl_buttonid"));
				u.setType(rs.getInt("tbl_type"));
				u.setTemplateType(rs.getInt("tbl_templateType"));
				u.setComment(rs.getString("tbl_comment"));
				u.setRemark(rs.getString("tbl_remark"));
				u.setCode(rs.getString("tbl_bianma"));
			}
		});
	}

	@Override
	public void delete(Long id) {
		String sql = "delete from sys_biaodantab where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "resourceCache")
	public int count(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public Long selectMaxId() {
		String sql = "select MAX(id) from sys_biaodantab";
		List counts = jdbcTemplate.query(sql, new MaxRowMapper());
		return (Long) (counts.get(0));
	}

	class MaxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Long ret = rs.getLong(1);
			return ret;
		}
	}

	@Override
	@Cacheable(value = "formCache")
	public List<Tab> selectByFormId(Long formId) {
		String sql = "select a.*,b.tbl_templatechname as tbl_templatename,b.tbl_templatefilename as tbl_templatefilename from sys_biaodantab a left join sys_template b on a.tbl_muban=b.id where tbl_form=? order by a.tbl_taborder asc";
		Object[] params = new Object[] { formId };
		return jdbcTemplate.query(sql, params, new TabRowMapper());
	}

	@Override
	@Cacheable(value = "formCache", key = "'tab_selectListByFormId:'+#formId")
	public List<Tab> selectListByFormId(Long formId) {
		String sql = "select a.*,b.tbl_templatechname as tbl_templatename,b.tbl_templatefilename as tbl_templatefilename from sys_biaodantab a left join sys_template b on a.tbl_muban=b.id where tbl_form=? order by a.tbl_taborder asc";
		Object[] params = new Object[] { formId };
		return jdbcTemplate.query(sql, params, new TabRowMapper());
	}

	@Override
	public void deleteColumns(Long id) {
		String _dsql = "delete from sys_biaodansheji where tbl_tabid = ?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(_dsql,params);
	}

	@Override
	public List findReplyResult(String tableName, String columnName,
			String tabName, Long id, Long formId) {
		String sql="select * from "+tableName+" where "+columnName+" = ? and tbl_form = ?";
		Object[]objs = new Object[]{tabName,formId};
		if(id!=null&&!id.equals("")){
			sql="select * from "+tableName+" where "+columnName+" = ? and id <> ? and tbl_form = ?";
			objs=new Object[]{tabName, id, formId};
		}
		
		List list = null;
		try {
			list=jdbcTemplate.queryForList(sql,objs);
		} catch (Exception e) {
			list = new ArrayList();
		}
		
		return list;
	}

	@Override
	public void updateOrder(Tab tab, Long tabId) {
		try {
			String sql="select * from sys_biaodantab where tbl_form = ? and tbl_taborder = ? and id <> ?";
			Object [] objs = new Object[]{tab.getFormId(),tab.getTabOrder(),tabId};
			List list=jdbcTemplate.queryForList(sql,objs);
			if(list.size()>0){
				String usql = "update sys_biaodantab set tbl_taborder = tbl_taborder+1 where tbl_form = ? and id <> ? and tbl_taborder >= ?";
				Object [] uobjs = new Object[]{tab.getFormId(),tabId,tab.getTabOrder()};
				jdbcTemplate.update(usql,uobjs);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTabTable(Tab tab) {
		String sql = "update sys_biaodantab set tbl_tableid = ? where id = ?";
		Object [] params = new Object[]{tab.getTableId(),tab.getId()};
		jdbcTemplate.update(sql,params);
	}

}
