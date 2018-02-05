package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.SysPositionDao;
import com.cloudstong.platform.system.model.SysPosition;

/**
 * @author liuqi
 * Created on 2014-8-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("positionDao")
public class SysPositionDaoImpl extends BaseJdbcDaoImpl<SysPosition, Long> implements SysPositionDao {

	@Override
	public String getTable() {
		return "sys_position";
	}

	@Override
	public SysPosition getById(Long id) {
		return super.selectById(id, new RowMapper<SysPosition>() {
			@Override
			public SysPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysPosition sp = new SysPosition();
				sp.setPositionName(rs.getString("tbl_positionname"));
				return sp;
			}
		});
	}

	@Override
	public SysPosition getPosByUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void deleteMiddleTable(Long id) {
		String sql = "delete from sys_user_position where tbl_positionid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}
	
}
