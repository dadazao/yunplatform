package com.cloudstong.platform.resource.dictionary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.dictionary.dao.DictionaryDao;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.system.dao.IDataSearchDao;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:代码操作数据库接口实现类
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl implements DictionaryDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	IDataSearchDao dataSearchDao;

	@Override
	@Cacheable(value="resourceCache",key="'Dictionary_selectById:'+#id")
	public Dictionary selectById(Long id) {
		String sql = "select a.*, b.tbl_name tbl_parent from sys_dictionarys a left join sys_dictionarys b on b.id=a.tbl_parentId where a.id=? and a.comm_status=?";
		final Dictionary dic = new Dictionary();
		final Object[] obj = new Object[] { id, Constants.DICTIONARY_STATUS_USE };
		try {
			jdbcTemplate.query(sql, obj, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					dic.setId(rs.getLong("id"));
					dic.setName(rs.getString("tbl_name"));
					dic.setValue(rs.getString("tbl_value"));
					dic.setType(rs.getString("tbl_type"));
					dic.setBelong(rs.getLong("tbl_parentId"));
					dic.setLevel(rs.getInt("tbl_level"));
					dic.setCreateBy(rs.getString("comm_createBy"));
					dic.setCreateDate(rs.getTimestamp("comm_createDate"));
					dic.setUpdateBy(rs.getString("comm_updateBy"));
					dic.setUpdateDate(rs.getTimestamp("comm_updateDate"));
					dic.setComment(rs.getString("tbl_comment"));
					dic.setStatus(rs.getInt("comm_status"));
					dic.setRemark(rs.getString("tbl_remark"));
					dic.setParent(rs.getString("tbl_parent"));
					dic.setDicOrder(rs.getInt("tbl_dicOrder"));
					dic.setDicCode(rs.getString("tbl_dicCode"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dic;
	}
	
	public Dictionary selectByCode(String code) {
		String sql = "select a.*, b.tbl_name tbl_parent from sys_dictionarys a left join sys_dictionarys b on b.id=a.tbl_parentId where a.tbl_dicCode=? and a.comm_status=?";
		final Dictionary dic = new Dictionary();
		final Object[] obj = new Object[] { code, Constants.DICTIONARY_STATUS_USE };
		try {
			jdbcTemplate.query(sql, obj, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					dic.setId(rs.getLong("id"));
					dic.setName(rs.getString("tbl_name"));
					dic.setValue(rs.getString("tbl_value"));
					dic.setType(rs.getString("tbl_type"));
					dic.setBelong(rs.getLong("tbl_parentId"));
					dic.setLevel(rs.getInt("tbl_level"));
					dic.setCreateBy(rs.getString("comm_createBy"));
					dic.setCreateDate(rs.getTimestamp("comm_createDate"));
					dic.setUpdateBy(rs.getString("comm_updateBy"));
					dic.setUpdateDate(rs.getTimestamp("comm_updateDate"));
					dic.setComment(rs.getString("tbl_comment"));
					dic.setStatus(rs.getInt("comm_status"));
					dic.setRemark(rs.getString("tbl_remark"));
					dic.setParent(rs.getString("tbl_parent"));
					dic.setDicOrder(rs.getInt("tbl_dicOrder"));
					dic.setDicCode(rs.getString("tbl_dicCode"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dic;
	}

	@Override
	@Cacheable(value="resourceCache",key="'selectByParent:'+#parentId+#order")
	public List<Dictionary> selectByParent(Long parentId, String order) {
		String tableName = "sys_dictionarys";
		List<Dictionary> list = new ArrayList<Dictionary>();
		StringBuffer sql = new StringBuffer("select a.*, b.tbl_name tbl_parent from " + tableName + " a left join " + tableName
				+ " b on b.id=a.tbl_parentId where a.tbl_parentId=? and a.comm_status=? and a.tbl_parentId!='0'");
		sql.append(" order by a.tbl_dicOrder " + order);
		Object[] obj = new Object[] { parentId, Constants.DICTIONARY_STATUS_USE };
		list = this.jdbcTemplate.query(sql.toString(), obj, new TableRowMapper());
		// 如果选择的是叶子节点，把自己放进集合
		if (list.size() == 0) {
			Dictionary dictionary = this.selectById(parentId);
			list.add(dictionary);
		}
		return list;
	}
	
	

	@Override
	public List<Dictionary> selectByParentCode(String parentCode, String order) {
		String tableName = "sys_dictionarys";
		List<Dictionary> list = new ArrayList<Dictionary>();
		StringBuffer sql = new StringBuffer("select a.*, b.tbl_name tbl_parent from " + tableName + " a left join " + tableName
				+ " b on b.id=a.tbl_parentId where a.tbl_dicCode=? and a.comm_status=? and a.tbl_parentId!='0'");
		sql.append(" order by a.tbl_dicOrder " + order);
		Object[] obj = new Object[] { parentCode, Constants.DICTIONARY_STATUS_USE };
		list = this.jdbcTemplate.query(sql.toString(), obj, new TableRowMapper());
		// 如果选择的是叶子节点，把自己放进集合
		if (list.size() == 0) {
			Dictionary dictionary = this.selectByCode(parentCode);
			list.add(dictionary);
		}
		return list;
	}

	@Override
	public List<Dictionary> selectCaseCadeByParent(Long parentId) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		String sql = "select a.*, b.tbl_name tbl_parent from sys_dictionarys a left join sys_dictionarys b on b.id=a.tbl_parentId where a.tbl_parentId=? and a.comm_status=? and a.tbl_parentId!='0' order by a.tbl_dicOrder";
		Object[] obj = new Object[] { parentId, Constants.DICTIONARY_STATUS_USE };
		list = this.jdbcTemplate.query(sql, obj, new TableRowMapper());
		return list;
	}

	class TableRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Dictionary dic = new Dictionary();
			dic.setId(rs.getLong("id"));
			dic.setName(rs.getString("tbl_name"));
			dic.setValue(rs.getString("tbl_value"));
			dic.setType(rs.getString("tbl_type"));
			dic.setBelong(rs.getLong("tbl_parentId"));
			dic.setLevel(rs.getInt("tbl_level"));
			dic.setCreateBy(rs.getString("comm_createBy"));
			dic.setCreateDate(rs.getTimestamp("comm_createDate"));
			dic.setUpdateBy(rs.getString("comm_updateBy"));
			dic.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			dic.setComment(rs.getString("tbl_comment"));
			dic.setStatus(rs.getInt("comm_status"));
			dic.setRemark(rs.getString("tbl_remark"));
			dic.setParent(rs.getString("tbl_parent"));
			dic.setDicOrder(rs.getInt("tbl_dicOrder"));
			dic.setDicCode(rs.getString("tbl_dicCode"));
			return dic;
		}
	}

	@Override
	public List<Dictionary> selectByLevel(int level) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		String sql = "select a.*, b.tbl_name tbl_parent from sys_dictionarys a left join sys_dictionarys b on b.id=a.tbl_parentId where a.tbl_level = ? and a.comm_status = ?";
		Object[] obj = new Object[] { level, Constants.DICTIONARY_STATUS_USE };
		list = this.jdbcTemplate.query(sql, obj, new TableRowMapper());
		return list;
	}

	@Override
	public void updateOrder(Dictionary dictionary) {
		String selectSql = "select * from sys_dictionarys where tbl_parentId = ? and id != ? and tbl_dicOrder = ?";
		String sql = "update sys_dictionarys set tbl_dicOrder = tbl_dicOrder+1 where tbl_parentId = ? and id != ? and tbl_dicOrder >= ?";
		Object[] obj = new Object[] {dictionary.getBelong(), dictionary.getId(), dictionary.getDicOrder() };
		List list = jdbcTemplate.queryForList(selectSql, obj);
		if (list.size() > 0) {
			jdbcTemplate.update(sql, obj);
		}
	}
	
	

	@Override
	public void doUpdateCode(Dictionary dictionary) {
		String sql = "update sys_dictionarys set tbl_dicCode = ? where  id = ?";
		Object[] obj = new Object[] {dictionary.getDicCode(), dictionary.getId()};
		jdbcTemplate.update(sql, obj);
	}

	@Override
	@Cacheable(value="resourceCache",key="'selectByParent:'+#parentId+#canSelectItem.hashCode()+#order")
	public List<Dictionary> selectByParent(Long parentId, String canSelectItem, String order) {
		// Set<Role> setRoles = user.getRole();
		// //查询策略集合
		// List<DataSearch> lstDataSearch=new ArrayList<DataSearch>();
		// if(setRoles != null){
		// for (Role role : setRoles) {
		// List<DataSearch> list =
		// dataSearchDao.getDataSearchContent(role.getId(), "9");
		// lstDataSearch.addAll(list);
		// }
		// }
		//
		//
		// //拼查询策略
		// StringBuffer querysql=new StringBuffer("");
		// if(lstDataSearch.size()>0){
		// querysql.append("(select * from sys_dictionarys where ");
		// for(int j=0;j<lstDataSearch.size();j++){
		// if(j==0){
		// querysql.append("("+lstDataSearch.get(j).getTblContent()+")");
		// }else {
		// querysql.append(" or "+"("+lstDataSearch.get(j).getTblContent()+")");
		// }
		// }
		// querysql.append(")");
		// tableName=querysql.toString();
		// }
		String tableName = "sys_dictionarys";
		String[] selectItems = canSelectItem.split(",");
		List<Object> _paramList = new ArrayList<Object>();
		_paramList.add(parentId);
		_paramList.add(Constants.DICTIONARY_STATUS_USE);
		List<Dictionary> list = new ArrayList<Dictionary>();
		StringBuffer sql = new StringBuffer("select a.*, b.tbl_name tbl_parent from " + tableName + " a left join " + tableName
				+ " b on b.id=a.tbl_parentId where a.tbl_parentId=? and a.comm_status=? and a.tbl_parentId!='0' and a.id in (");
		for (int i = 0; i < selectItems.length; i++) {
			if (i == 0) {
				sql.append("?");
			} else {
				sql.append(",?");
			}
			_paramList.add(selectItems[i].trim());
		}
		sql.append(")");

		sql.append(" order by a.tbl_dicOrder " + order);
		list = this.jdbcTemplate.query(sql.toString(), _paramList.toArray(), new TableRowMapper());
		// 如果选择的是叶子节点，把自己放进集合
		if (list.size() == 0) {
			Dictionary dictionary = this.selectById(parentId);
			list.add(dictionary);
		}
		return list;
	}

	@Override
	@Cacheable(value="resourceCache",key="'queryKeyValueMapByParentId:'+#parentId")
	public List<Map<String, String>> queryKeyValueMapByParentId(Long parentId) {
		String sql = "select tbl_value,tbl_name from sys_dictionarys where tbl_parentId = ? order by tbl_dicOrder asc";
		return jdbcTemplate.query(sql, new Object[] { parentId }, new RowMapper<Map<String, String>>() {
			@Override
			public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, String> map = new HashMap<String, String>(2);
				map.put("key",rs.getString("tbl_value"));
				map.put("value",rs.getString("tbl_name"));
				return map;
			}
		});
	}
}
