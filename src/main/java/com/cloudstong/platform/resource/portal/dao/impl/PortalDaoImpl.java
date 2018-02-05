package com.cloudstong.platform.resource.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.portal.dao.PortalDao;
import com.cloudstong.platform.resource.portal.model.Portal;
import com.cloudstong.platform.system.model.SysUser;
/**
 * 
 * @author Jason
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 门户数据操作接口实现类
 */
@Repository("portalDao")
public class PortalDaoImpl implements PortalDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	/**
	 * 根据门户id获取门户
	 */
	@Override
	public Portal findPortalById(String portalId) throws Exception {
		String sql = "select id,tbl_pname,tbl_pstyle,tbl_pdescription,tbl_pisinuse from sys_portal where id = ?";
		Object params[] = new Object[] { portalId };
		final Portal portal = new Portal();
		buildPortal(sql, params, portal);
		return portal;
	}

	/**
	 * 根据门户id获取组件id列表
	 */
	@Override
	public List<Long> findPortletIdListByPortalId(Long id) throws Exception {
		//left join 顺序不正确
		String sql = "select portlet.id from sys_portal_portlet  portal join (SELECT * FROM sys_portlet ORDER BY tbl_showorder ASC ) portlet on portal.tbl_subid = portlet.id where portal.tbl_mainid = ?";
		//String sql = "select p.id from sys_portlet portlet  tbl_subid from sys_portal_portlet where tbl_mainid = ?";
		Object params[] = new Object[] { id };
		List<Long> portletIdList = jdbcTemplate.queryForList(sql,
				Long.class, params);
		return portletIdList;
	}

	/**
	 * 查询数据库中的数据，并给Portal对象属性赋值
	 * 
	 * @param sql
	 * @param params
	 * @param portal
	 */
	private void buildPortal(String sql, Object[] params, final Portal portal)
			throws Exception {

		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				portal.setId(rs.getLong("id"));
				portal.setName(rs.getString("tbl_pname"));
				portal.setStyle(rs.getString("tbl_pstyle"));
				portal.setDesc(rs.getString("tbl_pdescription"));
				portal.setIsInUse(rs.getInt("tbl_isdefault"));
			}
		});
	}

	/**
	 * 查询当前默认的portal
	 * 
	 * @throws Exception
	 */
	@Override
	public Portal findDefaultPortal(SysUser user) throws Exception {
		String sql = "select id,tbl_pname,tbl_pstyle,tbl_pdescription,tbl_isdefault from sys_portal where tbl_isdefault = 1";
		final Portal portal = new Portal();
		try {
			buildPortal(sql, null, portal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return portal;
	}

	/**
	 * Getters and Setters
	 */

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> findCurrentPortletIdListByParams(String string, SysUser user) {
		List<String> list = new ArrayList<String>();
		try {
			String params[] = string.split(";");
			StringBuilder sqlBuilder = new StringBuilder("select id from sys_portlet where 1=1 ");
			for (String param : params) {
				sqlBuilder.append(" and ");
				sqlBuilder.append(param.split(":")[0]);
				sqlBuilder.append(" = '" + param.split(":")[1] +"'");
			}
			sqlBuilder.append(" order by tbl_showorder asc");

			list = jdbcTemplate.queryForList(sqlBuilder.toString(), String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}


}
