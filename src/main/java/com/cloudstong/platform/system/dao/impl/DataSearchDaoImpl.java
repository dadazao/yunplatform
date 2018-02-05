package com.cloudstong.platform.system.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.system.dao.IDataSearchDao;
import com.cloudstong.platform.system.model.DataSearch;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据查询数据库接口的实现类
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository("dataSearchDao")
public class DataSearchDaoImpl implements IDataSearchDao {
	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	@Cacheable(value="resourceCache",key="'getDataSearchContent:'+#plRoleId+#plTableId")
	public List<DataSearch> getDataSearchContent(String plRoleId, String plTableId){
		String _sDataSearchSql = "select * from sys_shujuchaxun where exists (select 'x' from sys_roles_privilege where sys_shujuchaxun.id =sys_roles_privilege.tbl_subid and tbl_mainid = ? and tbl_belongTable=?)";
		return jdbcTemplate.query(_sDataSearchSql, new Object[] { plRoleId, plTableId }, new BeanPropertyRowMapper(DataSearch.class));
	}
	
	public List<DataSearch> queryDataSearch(String psColumnName, String plColumnValue){
		String _sDataSearchSql = "select * from sys_shujuchaxun where "+psColumnName+"=?";//2
		return jdbcTemplate.query(_sDataSearchSql, new Object[] { plColumnValue }, new BeanPropertyRowMapper(DataSearch.class));
	}

	@Override
	public boolean testSql(String psSql) {
		// TODO Auto-generated method stub need to be implemented.
		try {
			jdbcTemplate.queryForList(psSql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
