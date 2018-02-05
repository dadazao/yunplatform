/**
 * 
 */
package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.SysOrgDao;
import com.cloudstong.platform.system.model.SysOrg;

/**
 * @author liuqi
 * Created on 2014-8-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("orgDao")
public class SysOrgDaoImpl extends BaseJdbcDaoImpl<SysOrg, Long> implements SysOrgDao {

	@Override
	public String getTable() {
		return "sys_org";
	}

	@Override
	public SysOrg getById(Long id) {
		return super.selectById(id, new RowMapper<SysOrg>() {
			@Override
			public SysOrg mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysOrg so = new SysOrg();
				so.setOrgName(rs.getString("tbl_orgname"));
				return so;
			}
		});
	}

	@Override
	public List<SysOrg> getOrgsByUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public SysOrg getPrimaryOrgByUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void deleteMiddleTable(Long id) {
		String sql = "delete from sys_user_org where tbl_orgid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}

}
