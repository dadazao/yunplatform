package com.cloudstong.platform.resource.systematom.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.systematom.dao.SystemAtomDao;
import com.cloudstong.platform.resource.systematom.model.SystemAtom;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统元素操作数据库接口实现类
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository("systemAtomDao")
public class SystemAtomDaoImpl implements SystemAtomDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SystemAtom getSystemAtomByCatalogId(String catalogId) {
		String sql = "select * from sys_xitongyuansu where tbl_duiyingmulu = ?";
		List<SystemAtom> _lstResult = this.jdbcTemplate.query(sql, new Object[] { catalogId },
				new BeanPropertyRowMapper(SystemAtom.class));
		if(_lstResult != null && _lstResult.size()>0){
			return _lstResult.get(0);
		}else{
			return new SystemAtom();
		}
		
	}

	public List<SystemAtom> getSystemAtomTree(){
		List<SystemAtom> _systemAtom = new ArrayList();
		try {
			String sql = "select * from sys_xitongyuansu";
			_systemAtom = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SystemAtom.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _systemAtom;
	}
}
