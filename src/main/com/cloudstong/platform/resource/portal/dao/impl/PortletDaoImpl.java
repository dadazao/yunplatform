package com.cloudstong.platform.resource.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.portal.dao.PortletDao;
import com.cloudstong.platform.resource.portal.model.Portlet;
import com.cloudstong.platform.resource.tabulation.dao.TabulationDao;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
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
 * 门户组件数据操作接口实现类
 */
@Repository("portletDao")
public class PortletDaoImpl implements PortletDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	@Resource
	private TabulationDao tabulationDao;

	/**
	 * Getters and Setters
	 */
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public TabulationDao getTabulationDao() {
		return tabulationDao;
	}

	public void setTabulationDao(TabulationDao tabulationDao) {
		this.tabulationDao = tabulationDao;
	}

	/**
	 * 根据portlet id 查找某个portlet
	 */
	@Override
	public Portlet findPortletById(Long id) throws Exception {
		String sql = "select * from sys_portlet where id = ?";
		Object params[] = new Object[] { id };
		final Portlet portlet = new Portlet();
		buildPortlet(sql, params, portlet);
		return portlet;
	}

	private void buildPortlet(String sql, Object[] params, final Portlet portlet) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				portlet.setId(rs.getLong("id"));
				portlet.setName(rs.getString("tbl_name"));
				portlet.setRecordCount(rs.getInt("tbl_count"));
				portlet.setShowOrder(rs.getInt("tbl_showorder"));
				portlet.setListId(rs.getLong("tbl_listid"));
				portlet.setIsScroll(rs.getString("tbl_isscroll"));
				portlet.setMinHeight(rs.getString("tbl_minheight"));
				portlet.setMaxHeight(rs.getString("tbl_maxheight"));
				portlet.setBelongArea(rs.getString("tbl_belongarea"));
			}
		});
	}

	@Override
	public List<Portlet> findPortletListByIds(String... ids) throws Exception {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}


}
