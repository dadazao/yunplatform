package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.system.dao.MenuDao;
import com.cloudstong.platform.system.model.MenuItem;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单数据操作实现
 */
@Repository("menuDao")
public class MenuDaoImpl implements MenuDao{

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	
	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<MenuItem> getDefaultMenu() {
		String sql = "select i.*,c.id as catalogId,c.tbl_bianma,c.tbl_path as tbl_path from sys_menuitem i left join sys_menu_menuitem m on i.id=m.tbl_subid left join sys_menu e on m.tbl_mainid=e.id left join sys_catalog c on i.tbl_mulu=c.id where e.tbl_shifoumoren=1 order by i.tbl_itemorder asc";
		return jdbcTemplate.query(sql, new MenuItemRowMapper());
	}

	@Override
	public void doUpdateMenu(Long id) {
		try {
			String allUsql = "update sys_menu set tbl_shifoumoren = 0";
			jdbcTemplate.update(allUsql);
			String usql = "update sys_menu set tbl_shifoumoren = 1 where id = '" + id + "'";
			jdbcTemplate.update(usql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	class MenuItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			MenuItem item = new MenuItem();
			item.setId(rs.getLong("id"));
			item.setMenuItemName(rs.getString("tbl_itemname"));
			item.setOrder(rs.getInt("tbl_itemorder"));
			item.setRemark(rs.getString("tbl_beizhu"));
			item.setComment(rs.getString("tbl_gongnengshuoming"));
			item.setFontFamily(rs.getString("tbl_ziti"));
			item.setFontSize(rs.getInt("tbl_zihao"));
			item.setColor(rs.getString("tbl_yanse"));
			item.setHref(rs.getString("tbl_path"));
			item.setCatalogId(rs.getString("catalogId"));
			item.setCatalogCode(rs.getString("tbl_bianma"));
			return item;
		}
	}

}
