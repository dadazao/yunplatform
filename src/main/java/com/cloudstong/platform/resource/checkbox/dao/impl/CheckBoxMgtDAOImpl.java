package com.cloudstong.platform.resource.checkbox.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.checkbox.dao.CheckBoxMgtDAO;
import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:复选框操作数据库接口实现类
 */
@Repository("checkBoxMgtDAO")
public class CheckBoxMgtDAOImpl implements CheckBoxMgtDAO {

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'CheckBoxMgt_selectAllCheckBoxVOs")
	public List<CheckBoxMgtVO> selectAllCheckBoxVOs() throws Exception {
		String sql = "select * from sys_checkboxmgt where tbl_passed=1 ";
		List result = this.jdbcTemplateExtend.query(sql,
				new BeanPropertyRowMapper(CheckBoxMgtVO.class));
		return result;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'CheckBoxMgt_selectById:'+#id")
	public List<CheckBoxMgtVO> selectById(Long id) throws Exception {
		String sql = "select * from sys_checkboxmgt where id = ? ";
		Object[] args = { id };
		List<CheckBoxMgtVO> result = this.jdbcTemplateExtend.query(sql, args,
				new BeanPropertyRowMapper(CheckBoxMgtVO.class));
		return result;
	}

}
