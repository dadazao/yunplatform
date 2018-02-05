package com.cloudstong.platform.resource.buttongroup.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮组操作数据库接口实现类
 */
@Repository("buttonGroupDao")
public class ButtonGroupDaoImpl implements ButtonGroupDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	public Long insert(ButtonGroup buttonGroup) throws Exception {
		Long id = UniqueIdUtil.genId();
		Object[] params = this.getInsertParams(buttonGroup,id);
		jdbcTemplateExtend.update(getInsertSQL(),params);
		return id;
	}

	private String getInsertSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_buttonGroup(id,tbl_buttonGroupName,tbl_buttonGroupEnabled,tbl_buttonGroupMaxCount,tbl_buttonGroupRemarks,comm_status,comm_createBy,comm_createDate,tbl_type)");
		sql.append("values(?,?,?,?,?,?,?,?,?)");
		return sql.toString();
	}

	private Object[] getInsertParams(ButtonGroup buttonGroup,Long id) {
		return new Object[] {id,buttonGroup.getButtonGroupName(),
				buttonGroup.getButtonGroupEnabled(),
				buttonGroup.getButtonGroupMaxCount(),
				buttonGroup.getButtonGroupRemarks(), buttonGroup.getStatus(),
				buttonGroup.getCreateBy(), buttonGroup.getCreateDate(),
				buttonGroup.getType() };
	}

	@Override
	public int update(ButtonGroup buttonGroup) throws Exception {
		try {
			return jdbcTemplateExtend.update(this.getUpdateSQL(),
					this.getUpdateParams(buttonGroup));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return 0;
	}

	private String getUpdateSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("update sys_buttongroup set tbl_buttongroupname = ?,tbl_buttongroupenabled = ?,tbl_buttongroupmaxcount = ?,tbl_buttongroupremarks = ?,");
		sql.append("comm_updateby = ?,comm_updatedate = ?,tbl_type = ? where id = ?");
		return sql.toString();
	}

	private Object[] getUpdateParams(ButtonGroup buttonGroup) {
		return new Object[] { buttonGroup.getButtonGroupName(),
				buttonGroup.getButtonGroupEnabled(),
				buttonGroup.getButtonGroupMaxCount(),
				buttonGroup.getButtonGroupRemarks(), buttonGroup.getUpdateBy(),
				buttonGroup.getUpdateDate(), buttonGroup.getType(),
				buttonGroup.getId() };
	}

	@Override
	public int updateStatus(Long buttonGroupID) throws Exception {
		try {
			return jdbcTemplateExtend.update(
					"update tbl_buttongroup set comm_status=1 where id=?",
					new Object[] { buttonGroupID });
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplateExtend.query(where, args,
						new ButtonGroupRowMapper());
			} else {
				queryForPageList = jdbcTemplateExtend.querySP(
						this.getQueryForPageListSQL(where), args, startRow,
						rowsCount, new ButtonGroupRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForPageList;
	}

	public String getQueryForPageListSQL(String where) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,tbl_buttongroupname,tbl_buttongroupenabled,tbl_buttongroupmaxcount,tbl_buttongroupremarks,comm_status,comm_createby,comm_createdate,comm_updateby,comm_updatedate,tbl_type");
		sql.append(" from sys_buttongroup where comm_status = 0 ").append(where);
		return sql.toString();
	}

	@Override
	public int getTotalCount(String where, Object[] args) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer(
					"select count(*) as tbl_totalCount from sys_buttonGroup where comm_status = 0")
					.append(where);
			return (Integer)jdbcTemplateExtend.queryForObject(buffer.toString(), args,
					new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:"
					+ e.getMessage());
		}
		return 0;
	}

	@Override
	public ButtonGroup findByID(Long buttonGroupID) {
		try {
			return (ButtonGroup)jdbcTemplateExtend.queryForObject(this.getFindByIDSQL(),
					new Object[] { buttonGroupID }, new ButtonGroupRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFindByIDSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,tbl_buttonGroupName,tbl_buttonGroupEnabled,tbl_buttonGroupMaxCount,tbl_comment,tbl_buttonGroupRemarks,comm_status,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,tbl_type");
		sql.append(" from sys_buttonGroup where ID = ?");
		return sql.toString();
	}

	class ButtonGroupRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.setId(rs.getLong("id"));
			buttonGroup.setButtonGroupName(rs.getString("tbl_buttonGroupName"));
			buttonGroup.setButtonGroupEnabled(rs.getLong("tbl_buttonGroupEnabled"));
			String buttonGroupEnabledName = rs.getString("tbl_buttonGroupEnabled");
			buttonGroup.setButtonGroupEnabledName(buttonGroupEnabledName);
			buttonGroup.setButtonGroupMaxCount(rs
					.getLong("tbl_buttonGroupMaxCount"));
			buttonGroup.setButtonGroupRemarks(rs
					.getString("tbl_buttonGroupRemarks"));
			buttonGroup.setComment(rs.getString("tbl_comment"));
			buttonGroup.setStatus(rs.getLong("comm_status"));
			buttonGroup.setCreateBy(rs.getString("comm_createBy"));
			buttonGroup.setCreateDate(rs.getTimestamp("comm_createDate"));
			buttonGroup.setUpdateBy(rs.getString("comm_updateBy"));
			buttonGroup.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			buttonGroup.setType(rs.getString("tbl_type"));
			return buttonGroup;
		}
	}

	class TotalCountMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt("tbl_totalCount");
		}
	}

	@Override
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		try {
			if (rowsCount == -1) {// 查询出所有记录
				retList = this.jdbcTemplateExtend.query(where, args,
						new ButtonGroupRowMapper());
			} else {
				retList = this.jdbcTemplateExtend.querySP(where, args,
						startRow, rowsCount, new ButtonGroupRowMapper());
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}
}
