package com.cloudstong.platform.resource.tree.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.template.vo.TemplateColumn;
import com.cloudstong.platform.resource.tree.dao.MgrTreeDao;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;

/**
 * @author Allan
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:树模板操作数据库接口实现类
 */
@Repository("mgrTreeDao")
public class MgrTreeDaoImpl implements MgrTreeDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	@Resource
	private IUserDao userDao;
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	class MgrTreeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			MgrTree u = new MgrTree();
			u.setId(rs.getLong(1));
			u.setTreename(rs.getString(2));
			u.setTableId(rs.getLong(3));
			u.setTableName(rs.getString(4));
			u.setTableZhName(rs.getString(5));
			u.setDisColumnId(rs.getLong(6));
			u.setDisColumnName(rs.getString(7));
			u.setDisColumnZhName(rs.getString(8));
			u.setParentColumnId(rs.getLong(9));
			u.setParentColumnName(rs.getString(10));
			u.setParentColumnZhName(rs.getString(11));
			u.setOrderColumnId(rs.getLong(12));
			u.setOrderColumnName(rs.getString(13));
			u.setOrderColumnZhName(rs.getString(14));
			u.setRootId(rs.getLong(15));
			u.setRootZhName(rs.getString(16));
			u.setCreateDate(rs.getTimestamp(18));
			u.setRemarks(rs.getString(19));
			u.setComment(rs.getString(20));
			u.setUpdateDate(rs.getTimestamp(22));
			u.setSystemTeam(rs.getString(23));
			u.setType(rs.getLong(24));
			u.setTableIdChild(rs.getLong(25));
			u.setDisColumnIdChild(rs.getLong(26));
			u.setOrderColumnIdChild(rs.getLong(27));
			u.setParentColumnIdChild(rs.getLong(28));
			u.setTableType(rs.getString(29));
			u.setCreateUser(rs.getString(30));
			u.setUpdateUser(rs.getString(31));
			return u;
		}
	}

	@Override
	public Table getTable(Long tableId) {
		String sql = "select * from sys_tables where id=?";
		final Object[] paramsTable = new Object[] { tableId };
		final Table t = new Table();
		queryTable(sql, paramsTable, t);
		return t;
	}

	@Override
	public Column getColumn(Long columnId) {
		String sql = "select * from sys_columns where id=?";
		final Object[] paramsColumn = new Object[] { columnId };
		final Column c = new Column();
		queryColumn(sql, paramsColumn, c);
		return c;
	}

	@Override
	public Long getRootId(String tableName, String colName) {
		String sql = "select min(" + colName + ")  from " + tableName;

		String sqlStr = "select id  from " + tableName + " where " + colName
				+ " = ( select min( " + colName + " ) from " + tableName + " )";

		Long rootId = this.jdbcTemplate.queryForObject(sqlStr,Long.class);
		return rootId;
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args,
					new MgrTreeRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow,
					rowsCount, new MgrTreeRowMapper());
		}
		return retList;
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(MgrTree u) {
		String sql = "update sys_tree set tbl_systemteam=?, tbl_compname=?, tbl_xianshiziduan=?, tbl_fuidziduan=? ,tbl_genid=?, tbl_suoshubiao=?, tbl_paixu=?, tbl_genidname=?, " +
				"tbl_comment=?,  tbl_beizhu=? , tbl_type=?, tbl_xianshiziduanname=?, tbl_fujiedianziduan=?, tbl_paixuname=?, tbl_suoshubiaoname=?, comm_updateBy=?, " +
				"comm_updateDate=?,tbl_tableidchild=?,tbl_discolumnidchild=?,tbl_ordercolumnidchild=?,tbl_parentcolumnidchild=?,tbl_tabletype=?  where id=? ";
		Object[] params = new Object[] { u.getSystemTeam(),u.getTreename(), u.getDisColumnId(),
				u.getParentColumnId(), u.getRootId(), u.getTableId(),
				u.getOrderColumnId(), u.getRootZhName(), u.getComment(),
				u.getRemarks(), u.getType(), u.getDisColumnZhName(),
				u.getParentColumnZhName(), u.getOrderColumnZhName(),
				u.getTableZhName(), u.getUpdateBy(),
				u.getUpdateDate(), u.getTableIdChild(),
				u.getDisColumnIdChild(), u.getOrderColumnIdChild(),
				u.getParentColumnIdChild(), u.getTableType(), u.getId() };
		try {
			jdbcTemplate.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public Long insert(MgrTree u) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_tree(id,tbl_systemteam,tbl_compname,tbl_xianshiziduan,tbl_fuidziduan,tbl_genid,tbl_suoshubiao,tbl_paixu,tbl_genidname,tbl_xianshiziduanname," +
				"tbl_fujiedianziduan,tbl_paixuname,tbl_suoshubiaoname, comm_createBy, comm_createDate,comm_updateBy,comm_updateDate,tbl_comment, tbl_beizhu,tbl_type," +
				"tbl_tableidchild,tbl_discolumnidchild,tbl_ordercolumnidchild,tbl_parentcolumnidchild,tbl_tabletype) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { 
				id,u.getSystemTeam(),
				u.getTreename(), u.getDisColumnId(),
				u.getParentColumnId(), u.getRootId(), u.getTableId(),
				u.getOrderColumnId(), u.getRootZhName(),
				u.getDisColumnZhName(), u.getParentColumnZhName(),
				u.getOrderColumnZhName(), u.getTableZhName(), u.getCreateBy(),
				u.getCreateDate(),u.getUpdateBy(),u.getUpdateDate(),
				u.getComment(), u.getRemarks(), u.getType(), u.getTableIdChild(),
				u.getDisColumnIdChild(), u.getOrderColumnIdChild(),
				u.getParentColumnIdChild(), u.getTableType(), };
		jdbcTemplate.update(sql, params);
		return id;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'MgrTree_selectById:'+#id")
	public MgrTree selectById(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.id ,s.tbl_compname ,s.tbl_suoshubiao, t.tbl_tablename , t.tbl_tableZhname , c1.id , c1.tbl_columnName,  c1.tbl_columnZhName, c2.id, c2.tbl_columnName,  " +
				"c2.tbl_columnZhName, c3.id , c3.tbl_columnName ,  c3.tbl_columnZhName , s.tbl_genid , s.tbl_genidname , s.comm_createBy , s.comm_createDate , s.tbl_beizhu ,s.tbl_comment, " +
				"s.comm_updateBy , s.comm_updateDate, s.tbl_systemteam,s.tbl_type,s.tbl_tableidchild,s.tbl_discolumnidchild,s.tbl_ordercolumnidchild,s.tbl_parentcolumnidchild,s.tbl_tabletype,user.tbl_yonghuxingming,user2.tbl_yonghuxingming ");
		sql.append(" from sys_tree  s left join  sys_tables t on s.tbl_suoshubiao = t.id   ");
		sql.append(" left join sys_columns c1 on  s.tbl_xianshiziduan = c1.id ");
		sql.append(" left join sys_columns c2 on  s.tbl_fuidziduan=c2.id  ");
		sql.append(" left join sys_columns c3 on  s.tbl_paixu=c3.id ");
		sql.append(" left join sys_user user on  s.comm_createBy=user.id ");
		sql.append(" left join sys_user user2 on  s.comm_updateBy=user2.id ");
		sql.append(" where s.id= ? ");

		// final Tree u = new Tree();
		final Object[] params = new Object[] { id };
		// query(sql.toString(), params, u);
		List retList = this.jdbcTemplate.query(sql.toString(), params,
				new MgrTreeRowMapper());
		MgrTree u = null;
		if(retList != null && retList.size()>0) {
			u = (MgrTree) retList.get(0);
		}else{
			u = new MgrTree();
		}
		return u;
	}

	private void queryColumn(String sql, Object[] params,
			final TemplateColumn column) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				column.setId(rs.getLong("id"));
				column.setColumnZhName(rs.getString("tbl_columnZhName"));
			}
		});
	}

	private void query(String sql, Object[] params, final MgrTree u) {

		List result = this.jdbcTemplate.queryForList(sql, params);
		Iterator it = result.iterator();
		while (it.hasNext()) {
			it.next();
		}

		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				MgrTree u = new MgrTree();
				u.setId(rs.getLong(1));
				u.setTreename(rs.getString(2));
				u.setTableId(rs.getLong(3));
				u.setTableName(rs.getString(4));
				u.setTableZhName(rs.getString(5));
				u.setDisColumnId(rs.getLong(6));
				u.setDisColumnName(rs.getString(7));
				u.setDisColumnZhName(rs.getString(8));
				u.setParentColumnId(rs.getLong(9));
				u.setParentColumnName(rs.getString(10));
				u.setParentColumnZhName(rs.getString(11));
				u.setOrderColumnId(rs.getLong(12));
				u.setOrderColumnName(rs.getString(13));
				u.setOrderColumnZhName(rs.getString(14));
				u.setRootId(rs.getLong(15));
				u.setRootZhName(rs.getString(16));
				u.setCreateBy(rs.getLong(17));
				u.setCreateDate(rs.getTimestamp(18));
				u.setRemarks(rs.getString(19));
				u.setType(rs.getLong(24));
			}
		});
	}

	private void queryTable(String sql, Object[] params, final Table u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setTableZhName(rs.getString("tbl_tableZhName"));
				u.setTableName(rs.getString("tbl_tableName"));
			}
		});
	}

	private void queryColumn(String sql, Object[] params, final Column c) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				c.setColumnName(rs.getString("tbl_columnName"));
				c.setColumnZhName(rs.getString("tbl_columnZhName"));
			}
		});
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id) {
		String sql = "delete from sys_tree where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public int count() {
		String sql = "select count(*) from sys_tree";
		List counts = jdbcTemplate.query(sql, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public List<TemplateColumn> selectColumnAll(Long id) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String sql = "select id,tbl_columnZhName from sys_columns where tbl_tableId='" + id+"'";
		list = this.jdbcTemplate.query(sql, new TableRowMapper());
		return list;
	}

	class TableRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TemplateColumn column = new TemplateColumn();
			column.setId(rs.getLong("id"));
			column.setColumnZhName(rs.getString("tbl_columnZhName"));
			return column;
		}
	}

	@Override
	public String getRootName(String tableName, String disColName, Long rootId) {
		String sql = "select " + disColName + " from " + tableName
				+ " where id='" + rootId+"'";
		List temp = this.jdbcTemplate.queryForList(sql);
		if (temp == null || temp.isEmpty()) {
			return null;
		}
		Map map = (Map) temp.get(0);
		Iterator it = map.keySet().iterator();
		String result = null;
		while (it.hasNext()) {
			String key = (String) it.next();
			result = String.valueOf(map.get(key));
		}
		return result;
	}

	@Cacheable(value = "resourceCache", key = "'MgrTree_queryTreeByParam:'+#belongTable+#orderColumn+#orderType")
	public List queryTreeByParam(String belongTable, String orderColumn, String orderType){
		if(null == orderType){
			orderType = "asc";
		}
		String sql = "select * from "+belongTable + " order by "+orderColumn+" "+orderType;
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Catalog> getCommentById(Long catalogId){
		return jdbcTemplate.query("select * from sys_catalog where id = ? or tbl_parentId = ?",  new Object[]{catalogId, catalogId}, new TreeCatalogRowMapper());
	}
	
	public String getValueByParam(String belongTable, String columntName, Long id){
		return jdbcTemplate.queryForObject("select ? from ? where id=?", new Object[] {columntName, belongTable, id}, String.class);
	}
	
	@Override
	@Cacheable(value = "resourceCache", key = "'MgrTree_findCommonTree:'+#mgrTree.id+#id")
	public CommonTree findCommonTree(MgrTree mgrTree, Long id) {
		String sql = "select a.*,b." + mgrTree.getDisColumnName()
				+ " as tbl_parentName from " + mgrTree.getTableName()
				+ " a left join " + mgrTree.getTableName() + " b on a."
				+ mgrTree.getParentColumnName() + "=b.id where a.id=?";
		final Object[] params = new Object[] { id };
		final String name = mgrTree.getDisColumnName();
		final String parentId = mgrTree.getParentColumnName();
		CommonTree ct = new CommonTree();

		query(sql, params, ct, name, parentId);

		return ct;
	}
	
	private void query(String sql, Object[] params, final CommonTree u,
			final String name, final String parentId) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setName(rs.getString(name));
				u.setParentId(rs.getString(parentId));
				u.setParentName(rs.getString("tbl_parentName"));
			}
		});
	}
	
	@Override
	@Cacheable(value = "resourceCache", key = "'MgrTree_findParentName:'+#pid")
	public String findParentName(Long pid) {
		String sql = "select * from sys_catalog";
		List<Catalog> list = jdbcTemplate.query(sql,new TreeCatalogRowMapper());
		List pNameList = new ArrayList();
		searchName(pid, list, pNameList);
		StringBuffer _pName = new StringBuffer("");
		for(int i=pNameList.size()-1;i>=0;i--){
			if(i==pNameList.size()-1){
				_pName.append(pNameList.get(i));
			}else{
				_pName.append("》"+pNameList.get(i));
			}
		}
		return _pName.toString();
	}
	
	private void searchName(Long pid, List<Catalog> list, List pNameList) {
		Catalog _catalog = null;
		for(int i=0;i<list.size();i++){
			_catalog = list.get(i);
			if(pid.equals(_catalog.getId())){
				pNameList.add(_catalog.getName());
				break;
			}
		}
		if(_catalog.getParentId()!=0L){
			searchName(_catalog.getParentId(), list, pNameList);
		}
	}
}

class TreeCatalogRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Catalog u = new Catalog();
		u.setId(rs.getLong("id"));
		u.setName(rs.getString("tbl_name"));
		u.setParentId(rs.getLong("tbl_parentId"));
		u.setComment(rs.getString("tbl_comment"));
		return u;
	}
}
