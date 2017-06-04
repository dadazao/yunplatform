package com.cloudstong.platform.resource.useinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;
import com.cloudstong.platform.resource.useinfo.model.ChartComp;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:使用信息操作数据库接口实现类
 */
@Repository("useInfoDao")
public class UseInfoDaoImpl implements UseInfoDao {
	
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(UseInfo useInfo) {
		final String sql = "insert into sys_useinfo(id,tbl_yewuid,tbl_zujianid,tbl_yewumingchen,tbl_zujianmingcheng,tbl_bianma,tbl_leixing,tbl_leixingid,tbl_relationid,comm_createby,comm_createdate) values (?,?,?,?,?,?,?,?,?,?,?)";
		final Object[] params = new Object[] {UniqueIdUtil.genId(), useInfo.getBizId(), useInfo.getCompId(),
				useInfo.getBizName(), useInfo.getCompName(), useInfo.getCode(), useInfo.getType(),useInfo.getTypeId(),useInfo.getRelationId(),useInfo.getCreateBy(),useInfo.getCreateDate() };
		jdbcTemplate.update(sql,params);
	}

	@Override
	public void update(UseInfo useInfo) {
		
	}

	@Override
	public void deleteUseInfo(Long relId,int type) {
		try {
			String sql="delete from sys_useinfo where tbl_relationid = ? and tbl_leixingid = ?";
			final Object[] params = new Object[] {relId,type};
			jdbcTemplate.update(sql,params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public List<ChartComp> stat(Integer number) {
		String sql="select count(*) as tbl_amount,tbl_zujianmingcheng from sys_useinfo group by tbl_zujianid,tbl_zujianmingcheng order by tbl_amount desc";
		List<ChartComp> comps = jdbcTemplate.query(sql, new ComponentRowMapper());
		if(number != -1){
			for(int i=comps.size()-1; i>=number; i--) {
				comps.remove(i);
			}
		}
		return comps;
	}
	
	class ComponentRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ChartComp c = new ChartComp();
			c.setValue(rs.getInt("tbl_amount"));
			c.setName(rs.getString("tbl_zujianmingcheng"));
			return c;
		}
	}
	
}
