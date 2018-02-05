package com.cloudstong.platform.resource.radio.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.radio.dao.RadioMgtDAO;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:单选框操作数据库接口实现类
 */
@Repository("radioMgtDAO")
public class RadioMgtDAOImpl implements RadioMgtDAO {

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	public List<RadioMgtVO> selectAll() throws Exception {
		String sql = " select * from sys_radiomgt where tbl_passed=1 ";
		List result = this.jdbcTemplateExtend.query(sql,
				new BeanPropertyRowMapper(RadioMgtVO.class));
		return result;
	}

	@Override
	public List<RadioMgtVO> selectById(Long id) throws Exception {
		String sql = "select * from sys_radiomgt where id = ? ";
		Object[] args = { id };
		List<RadioMgtVO> result = this.jdbcTemplateExtend.query(sql, args,
				new BeanPropertyRowMapper(RadioMgtVO.class));
		return result;
	}

}
