package com.cloudstong.platform.resource.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.template.dao.TemplateDao;
import com.cloudstong.platform.resource.template.model.SysTemplate;
import com.cloudstong.platform.resource.template.model.Template;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:模板管理数据库接口实现类
 */
@Repository("templateDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TemplateDaoImpl implements TemplateDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Cacheable(value = "formCache", key = "'template_sequeryTemplateById:'+#id")
	public SysTemplate queryTemplateById(Long id) {
		String sql = "select * from sys_template where id=?";
		Object[] obj = new Object[] { id };
		List<SysTemplate> _lstResult = jdbcTemplate.query(sql, obj, new SysTemplateMapper());
		if (_lstResult.size() > 0) {
			return _lstResult.get(0);
		} else {
			return new SysTemplate();
		}
	}
	
	public class SysTemplateMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysTemplate u = new SysTemplate();
			u.setId(rs.getLong("id"));
			u.setTblTemplatechname(rs.getString("tbl_templatechname"));
			u.setTblComment(rs.getString("tbl_comment"));
			u.setTblContent(rs.getString("tbl_content"));
			u.setTblTemplatetype(rs.getString("tbl_templatetype"));
			u.setCreateBy(rs.getLong("comm_createBy"));
			u.setCreateDate(rs.getDate("comm_createDate"));
			u.setUpdateBy(rs.getLong("comm_updateBy"));
			u.setUpdateDate(rs.getDate("comm_updateDate"));
			return u;
		}
	}

	@CacheEvict(value={"formCache"},allEntries=true,beforeInvocation=true)
	public Long saveOrUpdateTemplate(SysTemplate sysTemplate){
		Long id = null;
		try {
			if(sysTemplate.getId()==null || sysTemplate.getId().equals("")){
				id=UniqueIdUtil.genId();
				String insertSql = "insert into sys_template(id,tbl_templatechname,tbl_templatetype,tbl_templatefilename,tbl_picfilename,comm_createBy,comm_createdate,comm_updateBy,tbl_type,tbl_designType,tbl_content,tbl_comment,tbl_remark,comm_updateDate,tbl_tds,tbl_mobanyongtu,tbl_trs,tbl_systemteam) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params = new Object[] { id,
						sysTemplate.getTblTemplatechname(), sysTemplate.getTblTemplatetype(),
						sysTemplate.getTblTemplatefilename(), sysTemplate.getTblPicfilename(),
						sysTemplate.getCreateBy(), new Date(System.currentTimeMillis()),
						sysTemplate.getUpdateBy(), 
						sysTemplate.getTblType(), sysTemplate.getTblDesignType(),
						sysTemplate.getTblContent(), sysTemplate.getTblComment(),
						sysTemplate.getTblRemark(), new Date(System.currentTimeMillis()), 
						Integer.parseInt(sysTemplate.getTblTds()), sysTemplate.getTblMobanyongtu(), 
						Integer.parseInt(sysTemplate.getTblTrs()), sysTemplate.getTblSystemteam()};
				jdbcTemplate.update(insertSql, params);
			}else{
				id=sysTemplate.getId();
				String updateSql = "update sys_template set tbl_templatechname=?,tbl_templatetype=?,tbl_templatefilename=?,tbl_picfilename=?,tbl_type=?,tbl_designType=?,tbl_content=?,tbl_comment=?,tbl_remark=?,comm_updateBy=?,comm_updateDate=?,tbl_tds=?,tbl_mobanyongtu=?,tbl_trs=?,tbl_systemteam=? where id=?";
				Object[] paramsByUpdate = new Object[] {
						sysTemplate.getTblTemplatechname(), sysTemplate.getTblTemplatetype(),
						sysTemplate.getTblTemplatefilename(), sysTemplate.getTblPicfilename(),
						sysTemplate.getTblType(), sysTemplate.getTblDesignType(),
						sysTemplate.getTblContent(), sysTemplate.getTblComment(),
						sysTemplate.getTblRemark(), sysTemplate.getUpdateBy(),
						new Date(System.currentTimeMillis()), sysTemplate.getTblTds(),
						sysTemplate.getTblMobanyongtu(), sysTemplate.getTblTrs(),
						sysTemplate.getTblSystemteam(), sysTemplate.getId()};
				jdbcTemplate.update(updateSql, paramsByUpdate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	@Cacheable(value = "formCache")
	public List select(String sql, Object[] params, int startRow, int rowsCount) {
		List retList = new ArrayList();
		try {
			if (rowsCount == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.query(sql, params,
						new TemplateRowMapper());
			} else {
				try {
					retList = this.jdbcTemplate.querySP(sql, params, startRow,
							rowsCount, new TemplateRowMapper());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}

	@Override
	@Cacheable(value = "formCache", key = "'template_selectById:'+#id")
	public Template selectById(Long id) {
		String sql = "select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_template t left join sys_user u on t.comm_createBy=u.id   left join sys_user u2 on t.comm_updateBy=u2.id   where t.id=?";
		final Template t = new Template();
		final Object[] params = new Object[] { id };
		try {
			query(sql, params, t);
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return t;
	}

	private void query(String sql, Object[] params, final Template t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setTemplateChName(rs.getString("tbl_templatechname"));
				t.setTemplateFileName(rs.getString("tbl_templatefilename"));
				t.setPicFileName(rs.getString("tbl_picfilename"));
				t.setTemplateType(rs.getString("tbl_templatetype"));
				t.setType(rs.getString("tbl_type"));
				t.setDesignType(rs.getString("tbl_designType"));
				t.setContent(rs.getString("tbl_content"));
				t.setComment(rs.getString("tbl_comment"));
				t.setRemark(rs.getString("tbl_remark"));
				t.setCreateBy(rs.getLong("comm_createBy"));
				t.setCreateUser(rs.getString("tbl_createname"));
				t.setCreateDate(rs.getTimestamp("comm_createDate"));
				t.setUpdateBy(rs.getLong("comm_updateBy"));
				t.setUpdateUser(rs.getString("tbl_updatename"));
				t.setUpdateDate(rs.getTimestamp("comm_updateDate"));
				t.setTds(rs.getInt("tbl_tds"));
				t.setTrs(rs.getInt("tbl_trs"));
			}
		});
	}

	@Override
	public void insertLibrary(Template template) {
		try {
			String sql = "insert into sys_templatelibrary(id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,tbl_mobanmingcheng,tbl_gongnengshuoming,tbl_beizhu,tbl_mobanneirong,tbl_hangshu,tbl_lieshu) values (?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] { UniqueIdUtil.genId(),
					template.getCreateBy(), template.getCreateDate(),
					template.getUpdateBy(), template.getUpdateDate(),
					template.getTemplateChName(), template.getComment(),
					template.getRemark(), template.getContent(),
					template.getTrs(), template.getTds() };
			jdbcTemplate.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Template> selectLibary() {
		List<Template> retList = null;
		try {
			retList = this.jdbcTemplate.query(
					"select * from sys_templatelibrary",
					new TemplateLibraryRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retList;
	}
	
	class TemplateLibraryRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Template template = new Template();
			template.setId(rs.getLong("id"));
			template.setComment(rs.getString("tbl_gongnengshuoming"));
			template.setRemark(rs.getString("tbl_beizhu"));
			template.setContent(rs.getString("tbl_mobanneirong"));
			template.setTemplateChName(rs.getString("tbl_mobanmingcheng"));
			template.setTrs(rs.getInt("tbl_hangshu"));
			template.setTds(rs.getInt("tbl_lieshu"));
			return template;
		}
	}

	class TemplateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Template template = new Template();
			template.setId(rs.getLong("id"));
			template.setTemplateChName(rs.getString("tbl_templatechname"));
			template.setTemplateFileName(rs.getString("tbl_templatefilename"));
			template.setPicFileName(rs.getString("tbl_picfilename"));
			template.setTemplateType(rs.getString("tbl_templatetype"));
			template.setType(rs.getString("tbl_type"));
			template.setComment(rs.getString("tbl_comment"));
			template.setRemark(rs.getString("tbl_remark"));
			template.setCreateBy(rs.getLong("comm_createBy"));
			template.setCreateDate(rs.getDate("comm_createDate"));
			template.setUpdateBy(rs.getLong("comm_updateBy"));
			template.setUpdateDate(rs.getDate("comm_updateDate"));
			template.setTrs(rs.getInt("tbl_trs"));
			return template;
		}
	}

	@Override
	@CacheEvict(value={"formCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id) {
		String sql = "delete from sys_template where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);		
	}
}
