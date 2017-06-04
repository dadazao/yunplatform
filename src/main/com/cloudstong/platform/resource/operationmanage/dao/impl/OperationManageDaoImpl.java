package com.cloudstong.platform.resource.operationmanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.operationmanage.dao.OperationManageDao;
import com.cloudstong.platform.resource.operationmanage.model.OperationManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列操作数据库接口
 */
@Repository("operationManageDao")
public class OperationManageDaoImpl implements OperationManageDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'findOperationManageById:'+#operationManageId")
	public OperationManage findOperationManageById(String operationManageId) {
		String sql = "select * from sys_liebiaooperation where id = ?";
		final Object[] params = new Object[] { operationManageId };
		OperationManage operationManage = new OperationManage();
		query(sql, params, operationManage);
		return operationManage;
	}

	private void query(String sql, Object[] params,
			final OperationManage operationManage) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				operationManage.setId(rs.getLong("id"));
				operationManage.setOptManageName(rs.getString("tbl_name"));
				operationManage.setOptManagerCount(rs.getInt("tbl_opcount"));
				operationManage.setOptManageShowName(rs.getString("tbl_showname"));
				operationManage.setOptManageUnit(rs.getString("tbl_danwei"));
				operationManage.setOptManageWidth(rs.getInt("tbl_kuandu"));
				operationManage.setOptManagePosition(rs.getString("tbl_position"));
				operationManage.setComment(rs.getString("tbl_comment"));
				operationManage.setRemark(rs.getString("tbl_remark"));
			}
		});
	}

}
