package com.cloudstong.platform.resource.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.template.dao.PartitionDao;
import com.cloudstong.platform.resource.template.model.Partition;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区操作数据库接口实现类
 */
@Repository("partitionDao")
public class PartitionDaoImpl implements PartitionDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int count(String templateId) {
		String sql = "select count(*) from sys_partition where tbl_templateid = ?";
		Object[] arrays = new Object[] { templateId };
		List counts = jdbcTemplate.query(sql, arrays, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public List select(String sql, Object[] params, int startRow, int rowsCount) {
		List retList = new ArrayList();
		try {
			if (rowsCount == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.query(sql, params,
						new TemplateRowMapper());
			} else {
				try {
					retList = this.jdbcTemplate.querySP(sql, params, startRow,
							rowsCount, new TemplateRowMapper());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}

	class TemplateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Partition partition = new Partition();
			partition.setId(rs.getLong("id"));
			partition.setPartitionName(rs.getString("tbl_partitionname"));
			partition.setPartitionType(rs.getInt("tbl_partitiontype"));
			partition.setBaseTemplate(rs.getLong("tbl_baseTemplate"));
			partition.setShowOrder(rs.getInt("tbl_shunxu"));
			partition.setBaseTemplateName(rs.getString("tbl_baseTemplateName"));
			partition.setTemplateFileName(rs.getString("tbl_templateFileName"));
			return partition;
		}
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void insert(Partition partition) {
		String sql = "insert into sys_partition(id, tbl_partitionname,tbl_partitiontype,tbl_baseTemplate,tbl_shunxu,tbl_templateid) values (?,?,?,?,?,?)";
		Object[] params = new Object[] {UniqueIdUtil.genId(), partition.getPartitionName(),
				partition.getPartitionType(), partition.getBaseTemplate(),
				partition.getShowOrder(), partition.getTemplateId() };
		jdbcTemplate.update(sql, params);

	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void update(Partition partition) {
		String sql = "update sys_partition set tbl_partitionname=?,tbl_partitiontype=?,tbl_baseTemplate=?,tbl_shunxu=? where id=?";
		Object[] params = new Object[] { partition.getPartitionName(),
				partition.getPartitionType(), partition.getBaseTemplate(),
				partition.getShowOrder(), partition.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void delete(Long id) {
		String sql = "delete from sys_partition where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "formCache")
	public Partition selectById(Long id) {
		String sql = "select p.*,t.tbl_templatefilename as tbl_templatefilename from sys_partition p left join sys_template t on p.tbl_basetemplate = t.id where p.id=?";
		final Partition t = new Partition();
		final Object[] params = new Object[] { id };
		try {
			query(sql, params, t);
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return t;
	}

	private void query(String sql, Object[] params, final Partition partition) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				partition.setId(rs.getLong("id"));
				partition.setPartitionName(rs.getString("tbl_partitionname"));
				partition.setPartitionType(rs.getInt("tbl_partitiontype"));
				partition.setBaseTemplate(rs.getLong("tbl_baseTemplate"));
				partition.setShowOrder(rs.getInt("tbl_shunxu"));
				partition.setTemplateFileName(rs.getString("tbl_templatefilename"));
			}
		});
	}
}
