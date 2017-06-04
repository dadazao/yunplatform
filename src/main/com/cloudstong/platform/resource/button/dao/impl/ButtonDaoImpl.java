package com.cloudstong.platform.resource.button.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.button.dao.ButtonDao;
import com.cloudstong.platform.resource.button.model.Button;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description: 按钮操作数据库接口实现类
 */
@Repository("buttonDao")
public class ButtonDaoImpl implements ButtonDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	// 新增
	@Override
	public Long insert(Button button) throws AppException, Exception {
		Long id = UniqueIdUtil.genId();
		try {
			jdbcTemplateExtend.update(this.getButtonInsertSQL(), this.getButtonInsertParams(button, id));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return id;
	}

	// 修改
	@Override
	public long update(Button button) throws AppException, Exception {
		long result = -1;
		try {
			result = jdbcTemplateExtend.update(this.getButtonUpdateSQL(), this.getButtonUpdateParams(button));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return result;
	}

	// 删除
	@Override
	public int delete(Long id) throws Exception {
		Object[] params = new Object[] { id };
		try {
			return jdbcTemplateExtend.update("UPDATE BUTTON SET STATUS=1 WHERE ID=?", params);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
			throw e;
		}
	}

	// 根据ID查找详细信息
	@Override
	@Cacheable(value = "resourceCache")
	public Button findByID(Long ID) {
		try {
			Button button = (Button) jdbcTemplateExtend.queryForObject(this.findByIDSQL(), new Object[] { ID }, new ButtonRowMapper());
			return button;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow, int rowsCount) {
		List queryForPageList = null;
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplateExtend.query(this.getQueryForListSQL(where), args, new ButtonRowMapper());
			} else {
				queryForPageList = jdbcTemplateExtend.querySP(this.getQueryForListSQL(where), args, startRow, rowsCount, new ButtonRowMapper());
			}

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return queryForPageList;
	}

	class ButtonRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Button button = new Button();
			button.setId(rs.getLong("id"));
			button.setButtonType(rs.getLong("tbl_buttonType"));
			button.setButtonBM(rs.getString("tbl_buttonBM"));
			button.setButtonName(rs.getString("tbl_buttonName"));
			button.setButtonWidth(rs.getLong("tbl_buttonWidth"));
			button.setButtonHeight(rs.getLong("tbl_buttonHeight"));
			button.setButtonNameFontStyle(rs.getString("tbl_buttonNameFontStyle"));
			button.setButtonNameFontSize(rs.getLong("tbl_buttonNameFontSize"));
			button.setButtonNameFontColor(rs.getString("tbl_buttonNameFontColor"));
			button.setButtonBackGroundColor(rs.getString("tbl_buttonBackGroundColor"));
			button.setButtonBackGroundImage(rs.getString("tbl_buttonBackGroundImage"));
			button.setButtonBorderSize(rs.getLong("tbl_buttonBorderSize"));
			button.setButtonBorderColor(rs.getString("tbl_buttonBorderColor"));
			button.setButtonStatus(rs.getLong("tbl_buttonStatus"));
			button.setButtonRemarks(rs.getString("tbl_buttonRemarks"));
			button.setStatus(rs.getLong("comm_status"));
			button.setCreateBy(rs.getString("comm_createBy"));
			button.setCreateDate(rs.getTimestamp("comm_createDate"));
			button.setUpdateBy(rs.getString("comm_updateBy"));
			button.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			button.setButtonCode(rs.getString("tbl_buttonCode"));
			button.setComment(rs.getString("tbl_comment"));
			return button;
		}
	}

	private String findByIDSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id,tbl_buttonType,tbl_buttonBM,tbl_buttonName,tbl_buttonWidth,tbl_buttonHeight,tbl_buttonNameFontStyle,");
		buffer.append("tbl_buttonNameFontSize,tbl_buttonNameFontColor,tbl_buttonBackGroundColor,tbl_buttonBackGroundImage,");
		buffer.append("tbl_buttonBorderSize,tbl_buttonBorderColor,tbl_buttonStatus,tbl_buttonRemarks,comm_status,comm_createBy,");
		buffer.append("comm_createDate,comm_updateBy,comm_updateDate,tbl_buttonCode,tbl_comment from sys_button where comm_status = 0 and ID = ?");
		return buffer.toString();
	}

	private String getQueryForListSQL(String where) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id,tbl_buttonType,tbl_buttonBM,tbl_buttonName,tbl_buttonWidth,tbl_buttonHeight,tbl_buttonNameFontStyle,");
		buffer.append("tbl_buttonNameFontSize,tbl_buttonNameFontColor,tbl_buttonBackGroundColor,tbl_buttonBackGroundImage,");
		buffer.append("tbl_buttonBorderSize,tbl_buttonBorderColor,tbl_buttonStatus,tbl_buttonRemarks,comm_status,comm_createBy,");
		buffer.append("comm_createDate,comm_updateBy,comm_updateDate,tbl_buttonCode,tbl_comment from sys_button where comm_status = 0 ")
				.append(where);
		return buffer.toString();
	}

	private String getButtonInsertSQL() {
		StringBuffer _InsertSQL = new StringBuffer();
		_InsertSQL
				.append("insert into button(id,tbl_buttonType,tbl_buttonBM,tbl_buttonName,tbl_buttonWidth,tbl_buttonHeight,tbl_buttonNameFontStyle,");
		_InsertSQL.append("tbl_buttonNameFontSize,tbl_buttonNameFontColor,tbl_buttonBackGroundColor,tbl_buttonBackGroundImage,");
		_InsertSQL
				.append("tbl_buttonBorderColor,tbl_buttonBorderSize,tbl_buttonStatus,tbl_buttonRemarks,comm_status,comm_createBy,comm_createDate,tbl_buttonCode)");
		_InsertSQL.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		return _InsertSQL.toString();
	}

	private Object[] getButtonInsertParams(Button button, Long id) {
		Object[] params = new Object[] { id, button.getButtonType(), button.getButtonBM(), button.getButtonName(), button.getButtonWidth(),
				button.getButtonHeight(), button.getButtonNameFontStyle(), button.getButtonNameFontSize(), button.getButtonNameFontColor(),
				button.getButtonBackGroundColor(), button.getButtonBackGroundImage(), button.getButtonBorderColor(), button.getButtonBorderSize(),
				button.getButtonStatus(), button.getButtonRemarks(), button.getStatus(), button.getCreateBy(), button.getCreateDate(),
				button.getButtonCode() };
		return params;
	}

	private String getButtonUpdateSQL() {
		StringBuffer _UpdateSQL = new StringBuffer();
		_UpdateSQL
				.append("update button set tbl_buttonType = ?,tbl_buttonBM = ?,tbl_buttonName = ?,tbl_buttonWidth = ?,tbl_buttonHeight = ?,tbl_buttonNameFontStyle = ?,");
		_UpdateSQL.append("tbl_buttonNameFontSize = ?,tbl_buttonNameFontColor = ?,tbl_buttonBackGroundColor = ?,tbl_buttonBackGroundImage = ?,");
		_UpdateSQL
				.append("tbl_buttonBorderColor = ?,tbl_buttonBorderSize = ?,tbl_buttonStatus = ?,tbl_buttonRemarks = ?,comm_updateBy = ?,comm_updateDate = ?,tbl_buttonCode = ? where id = ?");
		_UpdateSQL.append("");
		return _UpdateSQL.toString();
	}

	private Object[] getButtonUpdateParams(Button button) {
		Object[] params = new Object[] { button.getButtonType(), button.getButtonBM(), button.getButtonName(), button.getButtonWidth(),
				button.getButtonHeight(), button.getButtonNameFontStyle(), button.getButtonNameFontSize(), button.getButtonNameFontColor(),
				button.getButtonBackGroundColor(), button.getButtonBackGroundImage(), button.getButtonBorderColor(), button.getButtonBorderSize(),
				button.getButtonStatus(), button.getButtonRemarks(), button.getUpdateBy(), button.getUpdateDate(), button.getButtonCode(),
				button.getId() };
		return params;
	}

	@Override
	public int getTotalCount(String where, Object[] args) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer("select count(*) as tbl_totalCount from sys_button where comm_status = 0").append(where);
			return (Integer) jdbcTemplateExtend.queryForObject(buffer.toString(), args, new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:" + e.getMessage());
			throw e;
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
				retList = this.jdbcTemplateExtend.query(where, args, new ButtonRowMapper());
			} else {
				retList = this.jdbcTemplateExtend.querySP(where, args, startRow, rowsCount, new ButtonRowMapper());
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}

}
