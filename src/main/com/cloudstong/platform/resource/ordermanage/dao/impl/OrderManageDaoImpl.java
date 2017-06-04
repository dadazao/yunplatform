package com.cloudstong.platform.resource.ordermanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.ordermanage.dao.OrderManageDao;
import com.cloudstong.platform.resource.ordermanage.model.OrderManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列操作数据库接口实现类
 */
@Repository("orderManageDao")
public class OrderManageDaoImpl implements OrderManageDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(value="resourceCache",key="'findOrderManageById:'+#orderManageId")
	public OrderManage findOrderManageById(String orderManageId) {
		String sql = "select * from sys_liebiaoorder where id = ?";
		final Object[] params = new Object[] { orderManageId };
		OrderManage orderManage = new OrderManage();
		query(sql, params, orderManage);
		return orderManage;
	}

	private void query(String sql, Object[] params,
			final OrderManage orderManage) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				orderManage.setId(rs.getLong("id"));
				orderManage.setOrderManageName(rs.getString("tbl_name"));
				orderManage.setOrderManagePosition(rs.getString("tbl_weizhi"));
				orderManage.setOrderManageShowName(rs.getString("tbl_showname"));
				orderManage.setOrderManageUnit(rs.getString("tbl_danwei"));
				orderManage.setOrderManageWidth(rs.getInt("tbl_kuandu"));
				orderManage.setComment(rs.getString("tbl_comment"));
				orderManage.setRemark(rs.getString("tbl_remark"));
			}
		});
	}

}
