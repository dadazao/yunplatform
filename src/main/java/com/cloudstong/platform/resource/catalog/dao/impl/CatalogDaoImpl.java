package com.cloudstong.platform.resource.catalog.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;

/**
 * @author Allan Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:目录操作数据库接口实现类
 */
@Repository("catalogDao")
public class CatalogDaoImpl implements CatalogDao {

	Log logger = LogFactory.getLog(this.getClass());

	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public CatalogDaoImpl() {
		super();
	}

	public CatalogDaoImpl(WebApplicationContext wc) {
		super();
		this.jdbcTemplate = (JdbcTemplate) wc.getBean("jdbcTemplate");
	}

	class CatalogRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Catalog u = new Catalog();
			u.setId(rs.getLong("id"));
			u.setCode(rs.getString("tbl_bianma"));
			u.setAlias(rs.getString("tbl_alias"));
			u.setName(rs.getString("tbl_name"));
			u.setParentName(rs.getString("tbl_parentName"));
			u.setPath(rs.getString("tbl_path"));
			u.setParentId(rs.getLong("tbl_parentId"));
			u.setOrderNum(rs.getInt("tbl_orderNum"));
			u.setPackageFolder(rs.getString("tbl_folderpackage"));
			return u;
		}
	}

	@Override
	@Cacheable(value = "privilegeCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplateExtend.query(where, args, new CatalogRowMapper());
		} else {
			retList = this.jdbcTemplateExtend.querySP(where, args, startRow, rowsCount, new CatalogRowMapper());
		}
		return retList;
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void update(Catalog u) {
		String sql = "update sys_catalog set tbl_name=?,tbl_code=?,tbl_path=?,tbl_parentId=?,tbl_orderNum=? where id=?";
		Object[] params = new Object[] { u.getName(), u.getCode(), u.getPath(), u.getParentId(), u.getOrderNum(), u.getId() };
		try {
			jdbcTemplateExtend.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void insert(Catalog u) {
		try {
			Long id = UniqueIdUtil.genId();
			String sql = "insert into sys_catalog(id,tbl_name,tbl_alias,tbl_bianma,tbl_path,tbl_parentId,tbl_orderNum,comm_status,tbl_systemteam,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] { id, u.getName(), u.getAlias(),u.getCode(), u.getPath(), u.getParentId(), u.getOrderNum(), u.getStatus(), u.getSystemTeam(),u.getCreateBy(),u.getCreateDate(),u.getCreateBy(),u.getCreateDate() };
			jdbcTemplateExtend.update(sql, params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Cacheable(value = "privilegeCache")
	public Catalog selectById(Long id) {
		String sql = "select * from sys_catalog where id=?";
		final Catalog u = new Catalog();
		final Object[] params = new Object[] { id };
		query(sql, params, u);

		final Catalog parent = new Catalog();
		final Object[] parentParams = new Object[] { u.getParentId() };
		query(sql, parentParams, parent);
		if (parent != null) {
			u.setParentName(parent.getName());
		}
		return u;
	}

	private void query(String sql, Object[] params, final Catalog u) {
		jdbcTemplateExtend.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setCode(rs.getString("tbl_bianma"));
				u.setName(rs.getString("tbl_name"));
				u.setPath(rs.getString("tbl_path"));
				u.setParentId(rs.getLong("tbl_parentId"));
				u.setOrderNum(rs.getInt("tbl_orderNum"));
				u.setRule(rs.getString("tbl_rule"));
				u.setPackageFolder(rs.getString("tbl_folderpackage"));
				u.setTabulationId(rs.getLong("tbl_listId"));
				u.setCreateBy(rs.getLong("comm_createBy"));
				u.setUpdateBy(rs.getLong("comm_updateBy"));
			}
		});
	}

	private void servletQuery(String sql, Object[] params, final Catalog u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setCode(rs.getString("tbl_bianma"));
				u.setName(rs.getString("tbl_name"));
				u.setPath(rs.getString("tbl_path"));
				u.setParentId(rs.getLong("tbl_parentId"));
				u.setOrderNum(rs.getInt("tbl_orderNum"));
				u.setRule(rs.getString("tbl_rule"));
				u.setPackageFolder(rs.getString("tbl_folderpackage"));
				u.setTabulationId(rs.getLong("tbl_listId"));
				u.setCreateBy(rs.getLong("comm_createBy"));
				u.setUpdateBy(rs.getLong("comm_updateBy"));
			}
		});
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void delete(Long id) {
		String sql = "delete from sys_catalog where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplateExtend.update(sql, params);
	}

	@Override
	public int count() {
		String sql = "select count(*) from sys_catalog";
		List counts = jdbcTemplateExtend.query(sql, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public boolean isHasNode(Long id) {
		String sql = "select count(*) from sys_catalog where tbl_parentId=?";
		Object[] params = new Object[] { id };
		List counts = jdbcTemplateExtend.query(sql, params, new CountRowMapper());
		if ((Integer) counts.get(0) > 0) {
			return true;
		}
		return false;
	}

	public List<Catalog> getSysCatalogTree() {
		List<Catalog> _catalog = new ArrayList();
		try {
			String sql = "select * from sys_catalog order by tbl_orderNum";
			_catalog = this.jdbcTemplateExtend.query(sql, new TreeCatalogRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _catalog;
	}

	class TreeCatalogRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Catalog u = new Catalog();
			u.setId(rs.getLong("id"));
			u.setCode(rs.getString("tbl_bianma"));
			u.setAlias(rs.getString("tbl_alias"));
			u.setName(rs.getString("tbl_name"));
			u.setPath(rs.getString("tbl_path"));
			u.setParentId(rs.getLong("tbl_parentId"));
			u.setOrderNum(rs.getInt("tbl_orderNum"));
			return u;
		}
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doUpdateCatalogList(Long currentSaveId, Long listId) {
		String _sql = "update sys_catalog set tbl_listid = ? where id = ?";
		Object[] _params = new Object[] { listId, currentSaveId };
		jdbcTemplateExtend.update(_sql, _params);
	}

	@Override
	public Catalog findCatalogByListId(Long tabulationId) {
		String sql = "select * from sys_catalog where tbl_listid = ?";
		final Catalog u = new Catalog();
		final Object[] params = new Object[] { tabulationId };
		query(sql, params, u);
		return u;
	}

	@Override
	public List useListCount(String listid, Long id) {
		String _sql = "select * from sys_catalog where tbl_listid = ?";
		Object[] _objs = new Object[] { listid };
		if (id != null && !id.equals("")) {
			_sql = "select * from sys_catalog where tbl_listid = ? and id <> ?";
			_objs = new Object[] { listid, id };
		}
		List list = null;
		try {
			list = jdbcTemplateExtend.queryForList(_sql, _objs);
		} catch (Exception e) {
			list = new ArrayList();
		}
		return list;
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void updateOrder(Catalog catalog) {
		String selectSql = "select * from sys_catalog where tbl_parentId = ? and id != ? and tbl_orderNum = ?";
		String sql = "update sys_catalog set tbl_orderNum = tbl_orderNum+1 where tbl_parentId = ? and id != ? and tbl_orderNum >= ?";
		Object[] obj = new Object[] { catalog.getParentId(), catalog.getId(), catalog.getOrderNum() };
		List list = jdbcTemplateExtend.queryForList(selectSql, obj);
		if (list.size() > 0) {
			jdbcTemplateExtend.update(sql, obj);
		}
	}

	@Override
	public void executeSql(String sql) {
		jdbcTemplateExtend.execute(sql);
	}

	@Override
	public Catalog getCatalogByFormId(Long id) {
		String sql = "select c.* from SYS_CATALOG c where c.TBL_LISTID = (select id from SYS_LIEBIAO l WHERE l.TBL_FORMID = ?)";
		final Catalog u = new Catalog();
		final Object[] params = new Object[] { id };
		query(sql, params, u);
		return u;
	}

	@Override
	public Catalog findCatalogByFormId(Long id) {
		String sql = "select c.* from SYS_CATALOG c where c.TBL_LISTID = (select id from SYS_LIEBIAO l WHERE l.TBL_FORMID = ?)";
		final Catalog u = new Catalog();
		final Object[] params = new Object[] { id };
		servletQuery(sql, params, u);
		return u;
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void updateAuth(Long catalogId,Integer isAuth) {
		String sql = "update sys_catalog set tbl_isauth=? where id=?";
		final Object[] params = new Object[] { isAuth,catalogId };
		jdbcTemplateExtend.update(sql, params);
	}

	@Override
	public String getMaintable(Long id) {
		String sql = "select b.tbl_zhubiao from sys_catalog c left join sys_liebiao l on c.tbl_listid=l.id left join sys_biaodan b on l.tbl_formid=b.id where c.id=?";
		Object[] args = new Object[]{id};
		try {
			return jdbcTemplateExtend.queryForObject(sql, args, String.class);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	

}
